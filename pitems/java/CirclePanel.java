import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Creates drawing space for a circle
 * @author Chuck
 *
 */
public class CirclePanel extends JPanel
{
	//set initial circle diameter to 150
	private int diameter=150;
	
	/**
	 * Creates a centered circle in the panel
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//draw an oval with centered coordinates, of size diameter
		g.drawOval((int)getSize().getWidth()/2-diameter/2, 
				   (int)getSize().getHeight()/2-diameter/2, 
				   diameter, diameter);
	}
	
	/**
	 * Set the diameter of the circle
	 * @param size Circle's diameter is set to size
	 */
	protected void setDiameter(int size)
	{
		diameter = size;
	}
	/**
	 * Get the diameter of the circle
	 * @return Diameter of the circle
	 */
	protected int getDiameter()
	{
		return diameter;
	}
}

