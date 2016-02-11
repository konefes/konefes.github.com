import javax.swing.JFrame;

/**
 * Test class for Arabic to Roman GUI
 * @author Chuck
 *
 */
public class ArabicToRomanTest {

	public static void main(String[] args) {
		ArabicToRoman ar = new ArabicToRoman();
		ar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ar.setSize(140,140);
		ar.setLocationRelativeTo(null);
		ar.setResizable(false);
		ar.setVisible(true);

	}

}
