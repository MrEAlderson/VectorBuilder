/**
 * @author Marcel Seibel
 * @version 280117
 */

package de.marcely.vectorbuilder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VectorShape {
	
	private Color color;
	private Point[] points;
	
	private Polygon poly;
	private Polygon border_poly;
	private Color border_color;
	
	/**
	 * 
	 * @param data the vector data you can get with #.getData()
	 */
	public VectorShape(String pointsData){
		this(Color.BLACK, pointsData);
	}
	
	/**
	 * 
	 * @param color the color of the whole thing
	 * @param data the vector data you can get with #.getData()
	 */
	public VectorShape(Color color, String data){
		this(color, new Location[0]);
		
		setPoints(fromData(data));
	}
	
	/**
	 * 
	 * @param points location of each point in an array
	 */
	public VectorShape(Location... points){
		this(Color.BLACK, points);
	}
	
	/**
	 * 
	 * @param color color the color of the whole thing
	 * @param points points location of each point in an array
	 */
	public VectorShape(Color color, Location... points){
		this.color = color;
		
		poly = new Polygon();
		
		setPointsUsingLocation(Arrays.asList(points));
	}
	
	/**
	 * 
	 * @param color change the color of the whole vector
	 */
	public void setColor(Color color){
		this.color = color;
	}
	
	/**
	 * 
	 * @param points reset the points using an array of locations
	 */
	public void setPointsUsingLocation(List<Location> points){
		List<Point> list = new ArrayList<Point>();
	
		for(Location loc:points)
			list.add(new Point(this, (int) loc.x, (int) loc.y));
		
		setPoints(list);
	}
	
	/**
	 * 
	 * @param points reset the points using an array of points
	 */
	public void setPoints(List<Point> points){
		this.points = points.toArray(new Point[points.size()]);
		
		poly.reset();
		
		for(Point point:points)
			poly.addPoint(point.x, point.y);
	}
	
	/**
	 * 
	 * @return returns the color you set. default is black
	 */
	public Color getColor(){
		return this.color;
	}
	
	/**
	 * 
	 * @return returns every point of the vector shape
	 */
	public Point[] getPoints(){
		return this.points;
	}
	
	/**
	 * 
	 * @return returns every point of the vector shape which has in as identification name
	 */
	public List<Point> getPointsWithIdentificationName(String in){
		List<Point> list = new ArrayList<Point>();
		
		for(Point point:points){
			if(point.in.equals(in))
				list.add(point);
		}
		
		return list;
	}
	
	/**
	 * 
	 * updates the vector shape
	 */
	public void update(){
		List<Point> list = new ArrayList<Point>();
		
		list.addAll(Arrays.asList(points));
		
		setPoints(list);
	}
	
	/**
	 * 
	 * @param loc location of the point
	 */
	public void addPoint(Location loc){
		addPoint(new Point(this, (int) loc.x, (int) loc.y));
	}
	
	/**
	 * 
	 * @param point the point which should be added
	 */
	public void addPoint(Point point){
		List<Point> list = new ArrayList<Point>();
		
		list.addAll(Arrays.asList(points));
		list.add(point);
		
		setPoints(list);
	}
	
	/**
	 * 
	 * @param loc the location of the point you wanna add
	 * @param p1 the first point it should be between with
	 * @param p2 the 2nd point it should be between with
	 * @return returns true if p1 and p2 are actually 'neigbour' in the points array
	 */
	public boolean addPointBetweenPoints(Location loc, Point p1, Point p2){
		List<Point> list = new ArrayList<Point>(Arrays.asList(points));
		
		if(list.contains(p1) && list.contains(p2)){
			
			int i1 = list.indexOf(p1);
			int i2 = list.indexOf(p2);
			
			if(Util.distance(list.indexOf(p1), list.indexOf(p2)) == 1){
				
				list.add(i1 > i2 ? i1 : i2, new Point(this, (int) loc.x, (int) loc.y));
				setPoints(list);
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param loc removes a point using his location
	 * @return returns true if a point in this location exists
	 */
	public boolean removePoint(Location loc){
		Point point = getPoint(loc);
		
		if(point != null)
			return removePoint(point);
		else
			return false;
	}
	
	/**
	 * 
	 * @param point the point which should be removed
	 * @return returns false if the point doesn't exists
	 */
	public boolean removePoint(Point point){
		if(Arrays.asList(points).contains(point)){
			List<Point> list = new ArrayList<Point>();
			
			list.addAll(Arrays.asList(points));
			list.remove(point);
			
			setPoints(list);
		
			return true;
		}else
			return false;
	}
	
	/**
	 * 
	 * @param thickness the thickness of the border
	 */
	public void setBorder(int thickness){
		setBorder(Color.BLACK, thickness);
	}
	
	/**
	 * 
	 * @param color the color of the border
	 * @param thickness the thickness of the border
	 */
	public void setBorder(Color color, int thickness){
		border_color = color;
		border_poly = new Polygon();
		
		final Location center = getCenter();
		
		for(Point point:points){
			Location loc = new Location(point.x, point.y);
			
			if(loc.x < center.x)
				loc.x -= thickness;
			else
				loc.x += thickness;
			
			if(loc.y < center.y)
				loc.y -= thickness;
			else
				loc.y += thickness;
			
			border_poly.addPoint((int) loc.x, (int) loc.y);
		}
	}
	
	/**
	 * 
	 * @return calculates the center using the points on the most outside
	 */
	public Location getCenter(){
		int xMin = Integer.MAX_VALUE, yMin = Integer.MAX_VALUE,
			xMax = Integer.MIN_VALUE, yMax = Integer.MIN_VALUE;
		
		for(Point loc:points){
			if(loc.x < xMin) xMin = loc.x;
			if(loc.x > xMax) xMax = loc.x;
			if(loc.y < yMin) yMin = loc.y;
			if(loc.y > yMax) yMax = loc.y;
		}
		
		return new Location((xMax - xMin) / 2 + xMin, (yMax - yMin) / 2 + yMin);
	}
	
	/**
	 * 
	 * @param g the graphic object for rendering on a jpanel
	 */
	public void draw(Graphics g){
		final Color oldColor = g.getColor();
		
		// border
		if(border_poly != null){
			g.setColor(border_color);
			g.fillPolygon(border_poly);
		}
		
		// polygon
		g.setColor(color);
		g.fillPolygon(poly);
		
		g.setColor(oldColor);
	}
	
	/**
	 * 
	 * @return calculates every point to create a logical line from each point to the next point
	 */
	public List<Line2D> calculateLines(){
		List<Line2D> list = new ArrayList<Line2D>();
		
		if(points.length >= 2){
			
			Point p1 = null, p2 = null;
			
			// add last line
			if(points.length >= 3){
				p1 = points[0];
				p2 = points[points.length - 1];
				
				list.add(new Line2D.Float(p1.x, p1.y, p2.x, p2.y));
			}
			
			for(Point p:points){
				p2 = p1;
				p1 = p;
				
				if(p2 != null)
					list.add(new Line2D.Float(p1.x, p1.y, p2.x, p2.y));
			}
		}
		
		return list;
	}
	
	/**
	 * 
	 * @param loc the coordinates
	 * @return returns the point
	 */
	public Point getPoint(Location loc){
		return getPoint((int) loc.x, (int) loc.y);
	}
	
	/**
	 * 
	 * @param x the x position of the point
	 * @param y the y position of the point
	 * @return returns the point
	 */
	public Point getPoint(int x, int y){
		for(Point p:points){
			if(p.x == x && p.y == y)
				return p;
		}
		
		return null;
	}
	
	/**
	 * 
	 * @return you can recreate a new vector shape using this string. It ONLY saves the points
	 */
	public String toData(){
		String data = "";
		int i=0;
		
		for(Point point:points){
			i++;
			
			data += point.x + "," + point.y + "," + point.in.replace(",", "{c0mma}").replace(";", "{c0mma2}");
			
			if(i != points.length) data += ";";
		}
		
		return data;
	}
	
	private List<Point> fromData(String data){
		List<Point> points = new ArrayList<Point>();
		String[] pointsRaw = data.split(";");
		
		for(String pointRaw:pointsRaw){
			String[] pointData = pointRaw.split(",");
			
			if(pointData.length == 3 && Util.isInteger(pointData[0]) && Util.isInteger(pointData[1]))
				points.add(new Point(this, Integer.valueOf(pointData[0]), Integer.valueOf(pointData[1]), pointData[2].replace("{c0mma}", ",").replace("{c0mma2}", ";")));
		}
		
		return points;
	}
	
	
	
	public static class Point {
		
		private VectorShape shape;
		private int x, y;
		private String in;
		
		/**
		 * 
		 * @param shape the VectorShape it's in
		 * @param x the x position of the point
		 * @param y the y position of the point
		 */
		public Point(VectorShape shape, int x, int y){
			this(shape, x, y, "Default");
		}
		
		/**
		 * 
		 * @param shape the VectorShape it's in
		 * @param x the x position of the point
		 * @param y the y position of the point
		 * @param in the identification name of the point
		 */
		public Point(VectorShape shape, int x, int y, String in){
			this.shape = shape;
			this.x = x;
			this.y = y;
			this.in = in;
		}
		
		/**
		 * 
		 * @param in the identifaction name
		 */
		public void setIdentificationName(String in){
			this.in = in;
		}
		
		/**
		 * 
		 * @param x the new x position
		 */
		public void setX(int x){
			this.x = x;
		}
		
		/**
		 * 
		 * @param y the new y position
		 */
		public void setY(int y){
			this.y = y;
		}
		
		/**
		 * 
		 * @return returns the vectorshape it's in
		 */
		public VectorShape getVectorShape(){
			return this.shape;
		}
		
		/**
		 * 
		 * @return returns the x position of the point
		 */
		public int getX(){
			return this.x;
		}
		
		/**
		 * 
		 * @return returns the y position of the point
		 */
		public int getY(){
			return this.y;
		}
		
		/**
		 * 
		 * @return returns the identification name of the point
		 */
		public String getIdentificationName(){
			return this.in;
		}
		
		/**
		 * 
		 * @returns returns if it still exists in the vectorshape
		 */
		public boolean exists(){
			return Arrays.asList(shape.points).contains(this);
		}
	}
}
