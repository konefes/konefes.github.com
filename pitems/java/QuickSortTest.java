import javax.swing.JFrame;
import java.awt.Dimension;

/**
 * Test for QuickSort Project
 * @author Chuck
 *
 */
public class QuickSortTest
{

	public static void main(String[] args) 
	{
		QuickSortGui gui = new QuickSortGui();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(400,400);
		gui.setMinimumSize(new Dimension(275,275));
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);

	}

}
