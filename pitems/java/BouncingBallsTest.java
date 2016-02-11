import javax.swing.JFrame;

/**
 * Test class for BouncingBalls class
 * @author Chuck
 *
 */
public class BouncingBallsTest {

	public static void main(String[] args) 
	{
		BouncingBalls bb = new BouncingBalls();
		bb.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		bb.setSize(500,500);
		//center window
		bb.setLocationRelativeTo(null);
		bb.setVisible(true);

	}

}
