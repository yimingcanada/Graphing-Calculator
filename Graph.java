

import java.awt.Point;
import java.util.ArrayList;

public class Graph {

	int x =0 ;
	double y ;
	double currentPoint = 0;
	private String function = "x^3"; //starts off with this function
	ArrayList <Point> points = new ArrayList<Point> (); //stores all points in an array list


	public Graph (int startX, String function)
	{
		calculatePoints (startX);
		this.function = function;
	}

	public void calculatePoints (int startX)
	{
		points.clear(); //clears all points and re-makes them
		x = startX; //reset the starting x value
		for (int i = 0 ; i < 81 ; i ++)//  calculates all the points of the function by starting at a certain x value on the screen
		{
			nextPoint();
		}
	}

	public void nextPoint ()
	{
		x+= 10;
		y = (int) ((int)- (Parser.evaluate(function.replace("x", Double.toString(x-Main.screenX/2)))) /Main.scale + Main.screenY/2);  
		//translates the function to x = 0 at the center of the screen
		//replaces x with its value

		Point newPoint = new Point ((int)x + Main.finalOffsetX,(int)y + Main.finalOffsetY); 
		//finalOffsets are used if the graph is dragged. It is the total distance dragged
		
		points.add(newPoint); 
		currentPoint ++;
	}

	void update (int x, int y)
	{
		for (int i = 0 ; i < points.size(); i ++)
		{
			if(x!= 0 || y != 0)
			{
				points.get(i).translate ( x , y); //move if translated or else it will reset to initial position
			}
		
		}
	}

	public void changeFunction(String text) {
		function =text;

	}


}
