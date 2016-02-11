import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Creates an interface to enter strings in postfix notation and evaluate the string
 * @author Chuck
 *
 */
public class PostfixEvaluatorTest 
{
	public static void main(String[] args) 
	{
		PostfixEvaluatorGUI peGUI = new PostfixEvaluatorGUI();
		peGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		peGUI.setSize(350,225);
		peGUI.setMinimumSize(new Dimension(300,225));
		peGUI.setLocationRelativeTo(null);
		peGUI.setVisible(true);

	}
}
