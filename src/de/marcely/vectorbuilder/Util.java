package de.marcely.vectorbuilder;

import java.awt.geom.Line2D;
import java.util.List;

import de.marcely.vectorbuilder.VectorShape.Point;

public class Util {
	public static Line2D getCollidingLine(List<Line2D> lines, Location loc){
		for(Line2D line:lines){
			if(line.intersects(loc.x - 5, loc.y - 5, 10, 10))
				return line;
		}
		
		return null;
	}
	
	public static double distance(double d1, double d2){
		return d1 > d2 ? d1 - d2 : d2 - d1;
	}
	
	public static int distance(Point p1, Point p2){
		return (p1.getX() > p2.getX() ? p1.getX() - p2.getX() : p2.getX() - p1.getX()) + (p1.getY() > p2.getY() ? p1.getY() - p2.getY() : p2.getY() - p1.getY());
	}
	
	public static double distance(Location loc, Point p){
		return (loc.x > p.getX() ? loc.x - p.getX() : p.getX() - loc.x) + (loc.y > p.getY() ? loc.y - p.getY() : p.getY() - loc.y);
	}
	
	public static boolean isInteger(String str){
		try{
			Integer.valueOf(str);
			
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public static boolean isDouble(String str){
		try{
			Double.valueOf(str);
			
			return true;
		}catch(Exception e){
			return false;
		}
	}
}
