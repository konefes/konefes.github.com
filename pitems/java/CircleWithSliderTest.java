import javax.swing.JFrame;
import java.awt.Dimension;

/**
 * Test for class CircleWithSlider
 * @author Chuck
 *
 */
public class CircleWithSliderTest {

	public static void main(String[] args) 
	{
		//create circle window and initialize
		CircleWithSlider circleWindow = new CircleWithSlider();
		circleWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		circleWindow.setMinimumSize(new Dimension(150,250));
		
		//set initial size and location
		circleWindow.pack();
		circleWindow.setSize(250,375);
		circleWindow.setLocationRelativeTo(null);
		//make window visible
		circleWindow.setVisible(true);
	}

}
