import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.FlowLayout;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.lang.NumberFormatException;

/**
 * Creates a window with text fields for both arabic values and roman numerals.  
 * As one is changed, the other is updated immediately with converted values.
 * @author Chuck
 *
 */
public class ArabicToRoman extends JFrame 
{
	private JLabel aTitle, rTitle;
	private JTextField aDisplay, rDisplay;
	private FlowLayout layout;
	
	/**
	 * Initializes the ArabicToRoman frame
	 */
	public ArabicToRoman()
	{
		super("Arabic/Roman Converter");
		
		//set frame layout
		layout = new FlowLayout();
		layout.setAlignment(FlowLayout.CENTER);
		setLayout(layout);
		
		//configure arabic title
		aTitle = new JLabel();		
		aTitle.setHorizontalAlignment(JTextField.CENTER);
		aTitle.setText("Arabic Value");
		add(aTitle);
		
		//configure arabic entry field
		aDisplay = new JTextField(10);
		aDisplay.setEditable(true);
		aDisplay.setBackground(Color.white);
		aDisplay.setHorizontalAlignment(JTextField.RIGHT);
		add(aDisplay);
		
		//configure roman title
		rTitle = new JLabel();		
		rTitle.setHorizontalAlignment(JTextField.CENTER);
		rTitle.setText("Roman Numeral");
		add(rTitle);
		
		//configure roman entry field
		rDisplay = new JTextField(10);
		rDisplay.setEditable(true);
		rDisplay.setBackground(Color.white);
		rDisplay.setHorizontalAlignment(JTextField.RIGHT);
		add(rDisplay);
		
		//create handler for button presses
		KeyHandler handler = new KeyHandler();
		aDisplay.addKeyListener(handler);
		rDisplay.addKeyListener(handler);
	}

	/**
	 * Inner class to listen for key presses.
	 * @author Chuck
	 *
	 */
	private class KeyHandler implements KeyListener
	{
		//not used
		public void keyTyped(KeyEvent e)
		{}	
		//not used
		public void keyPressed(KeyEvent e)
		{}
		
		//triggered by key release
		public void keyReleased(KeyEvent e)
		{
			//event from arabic entry field
			if (e.getSource()==aDisplay)
			{
				try{
					//call toRoman method to update romann entry field
					rDisplay.setText(toRoman(Integer.parseInt(aDisplay.getText())));
				}
				//invalid entries in arabic field--set roman field blank
				catch(NumberFormatException exception){
					rDisplay.setText("");
				}
			}
			//event from roman entry field
			else if (e.getSource()==rDisplay)
			{
				try{
					//call toArabic method to update arabic entry field
					aDisplay.setText(toArabic(rDisplay.getText()));
					rDisplay.setText(toRoman(Integer.parseInt(aDisplay.getText())));
				}
				//invalid
				catch(NumberFormatException exception){
					aDisplay.setText("");
				}
			}
		}
	}
	
	/**
	 * Converts a number to Roman numerals
	 * @param arabic
	 * @return a string of roman numerals
	 */
	private String toRoman(int arabic)
	{
		//initalize string to store roman numerals
		String romanString = "";
		
		//check for max value
		if(arabic>3999)
		{
			JOptionPane.showMessageDialog(getContentPane(), "Too big!");
			aDisplay.setText("");
			return "";
		}
		//check if any M's are needed
		if (arabic >= 1000)
		{
			for(int i=arabic/1000; i>0; i--)
			{
				romanString = romanString + "M";
			}
			arabic = arabic%1000;	
		}
		//check if CM is needed
		if (arabic >= 900)
		{
			romanString = romanString + "CM";
			arabic = arabic-900;	
		}
		//check if D's are needed
		if (arabic >= 500)
		{
			romanString = romanString + "D";
			arabic = arabic-500;	
		}
		//check if CD is needed
		if (arabic >= 400)
		{
			romanString = romanString + "CD";
			arabic = arabic-400;	
		}
		//check if any C's are needed
		if (arabic >= 100)
		{
			for(int i=arabic/100;i>0;i--)
			{
				romanString = romanString + "C";
			}
			arabic = arabic%100;	
		}
		//check if XC is needed
		if (arabic >= 90)
		{
			romanString = romanString + "XC";
			arabic = arabic-90;	
		}
		//check if any L's are needed
		if (arabic >= 50)
		{
			romanString = romanString + "L";
			arabic = arabic-50;	
		}
		//check if XL is needed
		if (arabic >= 40)
		{
			romanString = romanString + "XL";
			arabic = arabic-40;	
		}
		//check if any X's are needed
		if (arabic >= 10)
		{
			for(int i=arabic/10;i>0;i--)
			{
				romanString = romanString + "X";
			}
			arabic = arabic%10;	
		}
		//check if IX is needed
		if (arabic >= 9)
		{
			romanString = romanString + "IX";
			arabic = arabic-9;	
		}
		//check if any V's are needed
		if (arabic >= 5)
		{
			romanString = romanString + "V";
			arabic = arabic-5;	
		}
		//check if IV is needed
		if (arabic >= 4)
		{
			romanString = romanString + "IV";
			arabic = arabic-4;	
		}
		//check if any I's are needed
		for(int i=arabic;i>0;i--)
		{
			romanString = romanString + "I";
		}
		//return equivalent string of roman numerals
		return romanString;
	}
	
	/**
	 * Convert roman numeral string to a number
	 * @param roman
	 * @return a String containing the converted number
	 */
	private String toArabic(String roman)
	{
		//initialize variable to keep track of total
		int arabic = 0;
		
		//loop until entire roman numeral string has been analyzed
		while(roman != null)
		{
			//I in rightmost position of string
			if (roman.endsWith("I")||roman.endsWith("i"))
			{
				if (arabic<5) arabic = arabic+1;
				else arabic = arabic-1;
			}
			//V in rightmost position of string
			else if (roman.endsWith("V")||roman.endsWith("v"))
			{
				arabic = arabic+5;
			}
			//X in rightmost position of string
			else if (roman.endsWith("X")||roman.endsWith("x"))
			{
				if (arabic<50) arabic = arabic+10;
				else arabic = arabic-10;
			}
			//L in rightmost position of string
			else if (roman.endsWith("L")||roman.endsWith("l"))
			{
				if(arabic<50) arabic = arabic+50;
				else JOptionPane.showMessageDialog(getContentPane(), "Invalid Input.");
			}
			//C in rightmost position of string
			else if (roman.endsWith("C")||roman.endsWith("c"))
			{
				if (arabic<500) arabic = arabic+100;
				else arabic = arabic-100;
			}
			//D in rightmost position of string
			else if (roman.endsWith("D")||roman.endsWith("d"))
			{
				if(arabic<500) arabic = arabic+500;
				else JOptionPane.showMessageDialog(getContentPane(), "Invalid Input.");
			}
			//M in rightmost position of string
			else if (roman.endsWith("M")||roman.endsWith("m"))
			{
				arabic = arabic+1000;
			}
			//character found that does not represent a roman numeral
			else
			{ 
				JOptionPane.showMessageDialog(getContentPane(), "Invalid Input.");;
			}
			//remove last character of roman numeral string
			if(roman.length()>1)
			{
				roman = roman.substring(0, roman.length()-1);
			}
			else
			{
				roman = null;
			}
		}
		return "" + arabic;
	}
}




