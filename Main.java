import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JApplet;




@SuppressWarnings("serial")
public class Main extends Applet implements Runnable, ActionListener{

	
	   static int screenX = 800;
	    static int screenY = 600;
	    static int scale = 100; // 100 pixels = 1 unit

	private    double dScale = 1.1;// delta scale, or change in scale

	private   Button Clear, Submit;
	private   TextField function; 
	private   Label function_Label;

	private   Graph g1;
	private   xAxis xAxis;
	private   yAxis yAxis;
	//private  xAxis xScale;
	//private  yAxis yScale;
        
        private boolean running = true;

        private int posX;
        private int posY;

        int mousePressX =0, mousePressY = 0;


        Image dbImage;
        Graphics dbg;


        static int offsetX = 0;
        static int offsetY = 0;
        static int finalOffsetX = 0, finalOffsetY =0;

        public void init ()
        {
                //Color white = new Color (11);
                // this.setBackground(white);
                setSize(screenX, screenY);
                g1 = new Graph (0, "x^3");
                xAxis = new xAxis (0);
                yAxis = new yAxis (0);

               function_Label = new Label ("Function: ");
               function_Label.setBounds(546, 537, 29, 14);
               add (function_Label);
               
           	function = new TextField();
           	function.setBounds(585, 534, 86, 20);
    		add(function);
    		function.setColumns(10);
               
           	Submit = new Button("Submit");
           	Submit.setBounds(681, 533, 89, 23);
    		add(Submit);
    		Submit.addActionListener(this);
    		
    		Clear = new Button("Clear");    		
    		Clear.setBounds(681, 507, 89, 23);
    		add(Clear);
    		
                this.addMouseListener(new MouseAdapter()
                {


                        public void mousePressed(MouseEvent e)
                        {
                                posX=e.getXOnScreen(); // the initial place where the mouse is clicked so that mouseDragged and released can calculate the offset
                                posY=e.getYOnScreen();

                                mousePressX = e.getXOnScreen();
                                mousePressY = e.getYOnScreen();

                        }
                        public void mouseReleased (MouseEvent e)
                        {
                                offsetX = e.getXOnScreen() - mousePressX; //calculate the offset values after each drag
                                offsetY = e.getYOnScreen() - mousePressY;
                                finalOffsetX += offsetX;
                                finalOffsetY += offsetY;


                                g1.calculatePoints(-finalOffsetX);//redraw the points according to the offset so that you can never see the "end" of the function
                                xAxis.calculatePoints(-finalOffsetX);
                                yAxis.calculatePoints(-finalOffsetY);
                           

                                repaint();

                        }
                });
                this.addMouseMotionListener(new MouseAdapter()
                {
                        public void mouseDragged(MouseEvent evt)
                        {
                        	
                                g1.update(evt.getXOnScreen()- posX , evt.getYOnScreen() - posY );  
                                xAxis.update(evt.getXOnScreen()- posX , evt.getYOnScreen() - posY );
                                yAxis.update(evt.getXOnScreen()- posX , evt.getYOnScreen() - posY );
           
                                repaint ();

                                offsetX = evt.getXOnScreen()- posX ;
                                offsetY = evt.getXOnScreen()- posY ;


                                posX=evt.getXOnScreen(); 
                                posY=evt.getYOnScreen();



                        }


                });
                this.addMouseWheelListener(new MouseAdapter()
                {
                        public void mouseWheelMoved(MouseWheelEvent e) {
                                String message = null;
                                int notches = e.getWheelRotation();
                                if (notches < 0) {
                                        //Mouse wheel moved UP
                                       scale *= dScale;
                                        //zoom in
                                } 
                                else 
                                {
                                        //Mouse wheel moved DOWN   
                                      scale /= dScale;
                                        //zoom out
                                }
                                if 
                                (
                                                e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                                   
                                }
                            
                                //System.out.println(dScale);
                                g1.calculatePoints(0);;


                                repaint();

                        }
                });
        }
        public void start ()
        {
                Thread thread = new Thread(this);
                thread.start();
                repaint();
              
        }
        public void paint (Graphics g)
        {
g.drawString("("+(posX - finalOffsetX-screenX/2) + " ," + -(posY- finalOffsetY- screenY/2)+ ")", posX, posY);
                for (int i = 0 ; i < 80 ; i ++)
                {

                        g.drawLine((g1.points.get(i+1).x) , g1.points.get(i+1).y, (g1.points.get(i ).x), g1.points.get(i).y );

                }

                g1.x = 0;
                g.drawLine(xAxis.points.get(0).x , xAxis.points.get(0).y, xAxis.points.get(9 ).x , xAxis.points.get(9).y );
                g.drawLine(yAxis.points.get(0).x , yAxis.points.get(0).y, yAxis.points.get(7).x , yAxis.points.get(7).y );
              
                //  drawing x_scale & y_scale 
            	int n = -25;
          
            
              


             try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }

        public void update (Graphics g)
        {
                dbImage = createImage(getWidth(),getHeight()); //double buffering
                dbg = dbImage.getGraphics();
                paint (dbg);
                g.drawImage(dbImage,0,0,this);

        }

        public void run ()
        {       

                while (running )
                {

                        repaint();

                }

        }
        
        public void  actionPerformed(ActionEvent e) 
        {
    		if (e.getSource() == Submit)
    		{
    		g1.changeFunction (function.getText()); //give the graph a new function
    		g1.calculatePoints(0);
    		}
    		
    		
        }
        public void destroy ()
        {
                running = false;
        }
}

