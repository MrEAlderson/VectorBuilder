package de.marcely.vectorbuilder.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import de.marcely.vectorbuilder.VectorShape;
import de.marcely.vectorbuilder.VectorShape.Point;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	public JLabel lblInfo;
	
	public MainFrame(){
		super("VectorBuilder - v280117");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		final Panel_Builder panel = new Panel_Builder(this);
		contentPane.add(panel, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JButton btnCreate = new JButton("Create");
		panel_2.add(btnCreate, BorderLayout.NORTH);
		btnCreate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				JFrame frame = new JFrame("Creation");
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setBounds(100, 100, 450, 300);
				frame.getContentPane().setLayout(new BorderLayout());
				
				JTextPane textPane = new JTextPane();
				textPane.setText(panel.shape.toData());
				textPane.setEditable(false);
				frame.getContentPane().add(textPane, BorderLayout.CENTER);
			}
		});
		
		JButton btnLoad = new JButton("Load");
		panel_2.add(btnLoad, BorderLayout.SOUTH);
		btnLoad.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				final JFrame frame = new JFrame("Load");
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setBounds(100, 100, 450, 300);
				frame.getContentPane().setLayout(new BorderLayout());
				
				final JTextPane textPane = new JTextPane();
				frame.getContentPane().add(textPane, BorderLayout.CENTER);
				
				JButton load = new JButton("Load");
				load.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent event){
						panel.shape = new VectorShape(Color.ORANGE, textPane.getText());
						
						frame.dispose();
					}
				});
				frame.getContentPane().add(load, BorderLayout.SOUTH);
			}
		});
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JButton btnToXy = new JButton("To x0y0");
		panel_1.add(btnToXy, BorderLayout.NORTH);
		btnToXy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				int ma_x = 0, ma_y = 0;
				
				// first for left
				int r = Integer.MAX_VALUE;
				Point p = null;
				
				for(Point point:panel.shape.getPoints()){
					if(point.getX() < r){
						r = point.getX();
						p = point;
					}
				}
				
				if(p != null)
					ma_x = r;
				
				// then for bottom
				r = Integer.MAX_VALUE;
				p = null;
				
				for(Point point:panel.shape.getPoints()){
					if(point.getY() < r){
						r = point.getY();
						p = point;
					}
				}
				
				if(p != null)
					ma_y = r;
				
				// then move all
				for(Point point:panel.shape.getPoints()){
					point.setX(point.getX() - ma_x);;
					point.setY(point.getY() - ma_y);
				}
				
				panel.shape.update();
			}
		});
		
		lblInfo = new JLabel("/");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblInfo, BorderLayout.SOUTH);
		
		JButton btnPointSettings = new JButton("Point Settings");
		panel_1.add(btnPointSettings, BorderLayout.WEST);
		btnPointSettings.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				if(panel.pane.selected_point != null)
					new Frame_PointSettings(panel.pane.selected_point);
			}
		});
	}
}
