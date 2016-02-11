import javax.swing.JFrame;
import java.awt.Dimension;

/**
 * Test for the class Calculator
 * @author Chuck
 *
 */
public class CalculatorTest 
{

	public static void main(String[] args)
	{
		//create the calculator object and initialize
		Calculator calculator = new Calculator();
		calculator.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		calculator.setSize(180,217);
		//set minimum window size to show text on buttons
		calculator.setMinimumSize(new Dimension(180,217));
		//center window
		calculator.setLocationRelativeTo(null);
		calculator.setVisible(true);
		
	}
}
