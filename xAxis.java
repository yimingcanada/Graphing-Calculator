import java.awt.Point;
import java.util.ArrayList;

public class xAxis {
	int x = 0 ;
	int y = Main.screenY/2 ;

	ArrayList <Point> points = new ArrayList<Point> (); //points of the x Axis


	public xAxis (int startX)
	{
		calculatePoints (startX);
	}

	public void calculatePoints (int startX)
	{
		points.clear(); //clears all points and re-makes them
		x = startX; //reset the starting x value

		Point newPoint1 = new Point (x + Main.finalOffsetX,y + Main.finalOffsetY);
		//finalOffsets are used if the graph is dragged. It is the total distance dragged
		points.add(newPoint1); 

		for (int i = 0 ; i < 50; i ++)
		{
			nextPoint();
		}                   
	}





	public void nextPoint ()
	{         

		x+= 100;
		//create points in increments of 100 pixels or 1 unit
		Point newPoint2 = new Point (x + Main.finalOffsetX,y + Main.finalOffsetY);
		points.add(newPoint2); 

	}

	void update (int x, int y)
	{
		for (int i = 0 ; i < points.size(); i ++)
		{
			points.get(i).translate ( x , y);
			//if function is dragged, translate these points

		}
	}
}
