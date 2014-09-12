import java.awt.Point;
import java.util.ArrayList;

public class yAxis {
        
         int x = Main.screenX/2 ;
     int y = 0;
     
      ArrayList <Point> points = new ArrayList<Point> ();
         
        
          public yAxis (int startY)
      {
              calculatePoints (startY);
      }
      
      public void calculatePoints (int startY)
      {
      points.clear();
              y = startY;
              Point newPoint = new Point (x + Main.finalOffsetX ,y + Main.finalOffsetY);
              points.add(newPoint); 
             
              for (int i = 0 ; i < 50; i ++)
              {
                      nextPoint();
              }                  
      }
         
         
        
        
     
     public void nextPoint ()
     {          
         y+= 100;
         Point newPoint = new Point (x + Main.finalOffsetX ,y + Main.finalOffsetY);
         points.add(newPoint); 
            
        
      
                        
             
             
     }
     
     void update (int x, int y)
     {
             for (int i = 0 ; i < points.size(); i ++)
             {
                     points.get(i).translate ( x , y);
                     
                     
             }
     }

}
