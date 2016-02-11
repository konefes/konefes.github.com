import javax.swing.JFrame;

/**
 * Test for class MorseCode
 * @author Chuck
 *
 */
public class MorseCodeTest {

	public static void main(String[] args) 
	{
		MorseCodeWindow mc = new MorseCodeWindow();
		mc.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		mc.setSize(425,200);
		//center window
		mc.setLocationRelativeTo(null);
		mc.setVisible(true);
		
	}

}
