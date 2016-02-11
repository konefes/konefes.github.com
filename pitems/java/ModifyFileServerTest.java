import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Test class for ModifyFileServer class
 * @author Chuck
 *
 */
public class ModifyFileServerTest 
{
	public static void main(String args[])
	{
		ModifyFileServer mfs = new ModifyFileServer();
		mfs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mfs.setSize(400,400);
		mfs.setMinimumSize(new Dimension(275,275));
		//mfs.setLocationRelativeTo(null);
		mfs.setVisible(true);
		
		mfs.runServer();
	}
}
