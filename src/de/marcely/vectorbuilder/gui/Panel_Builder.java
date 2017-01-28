package de.marcely.vectorbuilder.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import de.marcely.vectorbuilder.Location;
import de.marcely.vectorbuilder.Util;
import de.marcely.vectorbuilder.VectorShape;
import de.marcely.vectorbuilder.VectorShape.Point;

import javax.swing.JPanel;

public class Panel_Builder extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	private MainFrame frame;
	
	public Pane pane;
	public VectorShape shape = new VectorShape(Color.ORANGE);
	
	public Panel_Builder(MainFrame frame){
		this.frame = frame;
		
		pane = new Pane(this);
		setViewportView(pane);
	}
	
	
	
	public static class Pane extends JPanel implements MouseListener {
		private static final long serialVersionUID = 1L;
		
		private Panel_Builder panel;
		
		public Point selected_point = null;
		
		public Pane(final Panel_Builder panel){
			this.panel = panel;
			
			addMouseListener(this);
			setPreferredSize(new Dimension(500, 500));
			
			// rerender automaticly
			new Timer().scheduleAtFixedRate(new TimerTask(){
				public void run(){
					panel.repaint();
					
					try{
						if(getMousePosition() != null && panel.frame.lblInfo != null)
							panel.frame.lblInfo.setText("X: " + getMousePosition().getX() + " | Y:" + getMousePosition().getY());
						else
							panel.frame.lblInfo.setText("X: ? | Y: ?");
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}, 0, 25);
			panel.repaint();
		}
		
		@Override
		public void paintComponent(Graphics g){
			panel.shape.draw(g);
			
			// draw corners
			for(Point point:panel.shape.getPoints()){
				if(selected_point != null && selected_point.equals(point)) g.setColor(Color.GREEN);
					
				g.fillOval(point.getX() - 2, point.getY() - 2,  4, 4);
				
				g.setColor(Color.BLACK);
			}
			
			if(getMousePosition() != null){
				Line2D line = Util.getCollidingLine(panel.shape.calculateLines(), new Location((float) getMousePosition().getX(), (float) getMousePosition().getY()));
				
				if(line != null)
					g.drawLine((int) line.getX1(), (int) line.getY1(), (int) line.getX2(), (int) line.getY2());
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent event){ }

		@Override
		public void mouseEntered(MouseEvent event){ }

		@Override
		public void mouseExited(MouseEvent event){ }
		
		private Timer timer;
		
		@Override
		public void mousePressed(final MouseEvent event){
			Location loc = new Location(event.getX(), event.getY());
			final Point point = getNextPoint(loc);
			
			// add new one
			if(SwingUtilities.isRightMouseButton(event)){
				// first check if on line
				if(getMousePosition() != null){
					Line2D line = Util.getCollidingLine(panel.shape.calculateLines(), new Location((float) getMousePosition().getX(), (float) getMousePosition().getY()));
				
					if(line != null){
						if(panel.shape.addPointBetweenPoints(loc, panel.shape.getPoint((int) line.getX1(), (int) line.getY1()), panel.shape.getPoint((int) line.getX2(), (int) line.getY2())) == true)						
							return;
					}
				}
				
				// else
				panel.shape.addPoint(loc);
			}
			
			if(point == null) return;
			
			boolean inDistance = Util.distance(loc, point) <= 10;
			
			// move point
			if(SwingUtilities.isLeftMouseButton(event)){
				if(inDistance){
					timer = new Timer();
					
					timer.scheduleAtFixedRate(new TimerTask(){
						public void run(){
							if(getMousePosition() == null) return;
							
							Location l = new Location((float) (getMousePosition().getX()), (float) (getMousePosition().getY()));
							
							point.setX((int) l.x);
							point.setY((int) l.y);
							
							panel.shape.update();
						}
					}, 100, 10);
				}
			
			// remove point
			}else if(SwingUtilities.isMiddleMouseButton(event)){
				if(Util.distance(loc, point) <= 10){
					panel.shape.removePoint(point);
					
					// if it's selecetd point; reset selected point
					if(selected_point != null && selected_point.equals(point))
						selected_point = null;
				}
			}
			
			// set point selected
			if(inDistance && point.exists())
				this.selected_point = point;
		}

		@Override
		public void mouseReleased(MouseEvent event){
			if(timer != null){
				timer.cancel();
				timer = null;
			}
		}
		
		private Point getNextPoint(Location loc){
			Point p = null;
			double d = Double.MAX_VALUE;
			
			for(Point p1:panel.shape.getPoints()){
				double d1 = Util.distance(loc, p1);
				
				if(d1 < d){
					p = p1;
					d = d1;
				}
			}
			
			return p;
		}
	}
}
