import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Creates 
 * @author Chuck
 *
 */
public class BouncingBalls extends JFrame
{
	//gui components
	private BallPanel ballPanel;
	private JButton clearButton;
	//array of balls to be drawn
	private ArrayList<Ball> ballArray = new ArrayList<Ball>();
	//service to handle threads
	private ExecutorService executor = Executors.newCachedThreadPool();
	//max number of balls on screen
	private final int maxBalls = 20;
	//current number of balls
	private int numBalls;
	private Repainter timer;
	
	/**
	 * Creates window to show bouncing balls
	 */
	public BouncingBalls()
	{
		//initialize layout and number of balls to 0
		super("Bouncing Balls!!");
		setLayout(new BorderLayout());
		numBalls = 0;
		
		//initialize ball panel component
		ballPanel = new BallPanel();
		ballPanel.setBackground(Color.WHITE);
		//create event handler for mouse click
		ballPanel.addMouseListener(
				//adapter to only listen for mouse presses
				new MouseAdapter()
				{
					@Override
					public void mousePressed(MouseEvent event) 
					{
						//create new ball at click location
						Ball newBall = new Ball(event.getX(),event.getY());
						//keep track of overall number, check if too many
						numBalls++;
						if (numBalls>maxBalls)
						{
							ballArray.remove(0);
						}
						//add ball to array and add ball thread to executor
						ballArray.add(newBall);
						executor.execute(newBall);
					}	
				});
		
		//initialize clear button
		clearButton = new JButton("Clear the balls.");
		//create handler for button press
		clearButton.addActionListener(
				new ActionListener()
				{
					//button press triggered event
					public void actionPerformed(ActionEvent event)
					{
						//clear ball array and reset numBalls variable
						ballArray.clear();
						numBalls = 0;
					}
				});
		
		//add gui components to frame
		add(ballPanel, BorderLayout.CENTER);
		add(clearButton, BorderLayout.SOUTH);	
		
		//creates timer object to call repaint
		timer = new Repainter(ballPanel);
		executor.execute(timer);
	}
	
	/**
	 * Panel to draw all balls and shadows
	 * @author Chuck
	 *
	 */
	private class BallPanel extends JPanel
	{
		//draw on screen
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			//draw each ball in ballArray
			for (int i=0; i < ballArray.size(); i++)
			{

				//draw shadow of current ball
				g.setColor(Color.BLACK);
				g.fillOval((int)(ballArray.get(i).getX()-ballArray.get(i).getDiameter()/2.5), 
						   ballPanel.getHeight()-110+(100*ballArray.get(i).getDiameter()/150), 
						   (int)(ballArray.get(i).getDiameter()*(double)(((ballArray.get(i).getY())/(double)(ballPanel.getHeight())+.15))), 
						   (int)(ballArray.get(i).getDiameter()/3*(double)(((ballArray.get(i).getY())/(double)(ballPanel.getHeight())+.15))));				
				//draw current ball
				g.setColor(ballArray.get(i).getBallColor());
				g.fillOval(ballArray.get(i).getX()-ballArray.get(i).getDiameter()/2, 
						   ballArray.get(i).getY()-ballArray.get(i).getDiameter()/2, 
						   ballArray.get(i).getDiameter(), 
						   ballArray.get(i).getDiameter());
				
			}
		}
	}
	
	
	/**
	 * Threadable timer object to handle repainting of BallPanel
	 * @author Chuck
	 *
	 */
	private class Repainter implements Runnable
	{
		private JPanel repaintPanel;
		
		//copy ballpanel
		public Repainter(JPanel panel)
		{
			repaintPanel = panel;
		}
		
		public void run()
		{
			//loop
			while(true)
			{
				//redraw ovals
				repaintPanel.repaint();
				
				//sleep for 42 ms
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {}
			}
		}
	}
	
	
	/**
	 * Inner class, creates a ball for use with multithreading
	 * @author Chuck
	 *
	 */
	private class Ball  implements Runnable
	{
		//location and size variables
		private int x;
		private int y;
		private int diameter;
		
		//speed and direction variables
		private int xSpeed;
		private int ySpeed;
		private double zSpeed;
		private boolean left = false;
		private boolean down = false;
		private boolean away = false;
		
		//to generate and store random ball color
		private Color ballColor;
		private Random rndNum = new Random();
		
		/**
		 * Create new ball with x and y location, random speeds and color
		 * @param clickX x location for ball
		 * @param clickY y location for ball
		 */
		public Ball(int clickX, int clickY)
		{
			setX(clickX);
			setY(clickY);
			setDiameter(rndNum.nextInt(4)*20+40);
			setXSpeed(rndNum.nextInt(5)*4+1);
			setYSpeed(rndNum.nextInt(5)*4+1);
			setZSpeed((rndNum.nextDouble()+9)/10);
			setBallColor(new Color(rndNum.nextInt(256),rndNum.nextInt(256),rndNum.nextInt(256)));
		}

		/**
		 * Update location of ball and repaint screen, 
		 * multithreading handled by executor
		 */
		public void run()
		{
			//run continually until ball is destroyed
			while(true)
			{
				//determine left/right direction
				if (!left)
				{
					//update x coordinate going right
					setX(getX()+getXSpeed());
					if (getX()>ballPanel.getWidth()-getDiameter()-25)
					{	
						left = !left;
					}	
				}
				else
				{
					//update x coordinate going left
					setX(getX()-getXSpeed());
					if (getX()<25)
					{	
						left = !left;
					}	
				}
				//determine up/down direction
				if (down)
				{
					//update y coordinate going down
					setY(getY()+getYSpeed());
					//if (getY()>ballPanel.getHeight()-getDiameter()-15)
					if(getY()>ballPanel.getHeight()-100)
					{	
						down = !down;
					}	
				}
				else
				{
					//update y coordinate going up
					setY(getY()-getYSpeed());
					if (getY()<25)
					{	
						down = !down;
					}	
				}
				//determine away/towards movement
				if (away)
				{
					//update diameter to simulate z coordinate decrease
					setDiameter((int)(getDiameter()*getZSpeed()));
					if (getDiameter()<40)
					{	
						away = !away;
					}	
				}
				else
				{
					//update diameter to simulate z coordinate increase
					setDiameter((int)(getDiameter()/getZSpeed()));
					if (getDiameter()>120)
					{	
						away = !away;
					}	
				}
				
				//update screen
				//repaint();

				//make thread sleep for 42 ms, resulting in about 24 fps
				try {
					Thread.sleep(42);
				} catch (InterruptedException e) {}
				//break;
			}
		}
		
		
		//accessor and mutator methods all below
		private void setX(int val)
		{
			x = val;
		}
		private void setY(int val)
		{
			y = val;
		}
		private void setDiameter(int val)
		{
			diameter = val;
		}
		private void setBallColor(Color c)
		{
			ballColor = c;
		}
		private void setXSpeed(int val)
		{
			xSpeed = val;
		}
		private void setYSpeed(int val)
		{
			ySpeed = val;
		}
		private void setZSpeed(double val)
		{
			zSpeed = val;
		}
		private int getX()
		{
			return x;
		}
		private int getY()
		{
			return y;
		}
		private int getDiameter()
		{
			return diameter;
		}
		private Color getBallColor()
		{
			return ballColor;
		}
		private int getXSpeed()
		{
			return xSpeed;
		}
		private int getYSpeed()
		{
			return ySpeed;
		}
		private double getZSpeed()
		{
			return zSpeed;
		}
	}
}
