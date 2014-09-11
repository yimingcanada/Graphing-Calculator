import java.awt.Point;
import java.util.ArrayList;

public class xAxis {
	 int x = 0 ;
     int y = Main.screenY/2 ;
     
      ArrayList <Point> points = new ArrayList<Point> ();
	 
	
	  public xAxis (int startX)
      {
              calculatePoints (startX);
      }
      
      public void calculatePoints (int startX)
      {
      points.clear();
              x = startX;
              
              Point newPoint1 = new Point (x + Main.finalOffsetX,y + Main.finalOffsetY);
              points.add(newPoint1); 
             
              for (int i = 0 ; i < 50; i ++)
              {
                      nextPoint();
              }                   
      }
	 
	 
	
        
     
     public void nextPoint ()
     {         
    	 
          
    	  x+= 100;
         Point newPoint2 = new Point (x + Main.finalOffsetX,y + Main.finalOffsetY);
         points.add(newPoint2); 
            
       
       
                        
             
             
     }
     
     void update (int x, int y)
     {
             for (int i = 0 ; i < points.size(); i ++)
             {
                     points.get(i).translate ( x , y);
                     
                     
             }
     }
}
