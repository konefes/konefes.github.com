import javax.swing.JFrame;

import java.awt.Dimension;

/**
 * Test class for ModifyFileClient class
 * @author Chuck
 *
 */
public class ModifyFileClientTest 
{

	public static void main(String[] args) 
	{
		ModifyFileClient mfc = new ModifyFileClient();
		//mfc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mfc.setSize(500,600);
		mfc.setLocationRelativeTo(null);
		mfc.setVisible(true);
	}

}
