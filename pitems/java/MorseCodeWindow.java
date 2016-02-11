import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;

/**
 * Creates a window to convert plain text to Morse code and vice versa
 * @author Chuck
 *
 */
public class MorseCodeWindow extends JFrame
{
	//initialize frame components
	private JTextArea messageArea;
	private JTextArea convertedArea;
	private JButton textToMorse;
	private JButton morseToText;
	//initialize arrays with morse codes corresponding to characters
	private final String letters[] = {"A","B","C","D","E","F","G","H","I",
									"J","K","L","M","N","O","P","Q","R",
									"S","T","U","V","W","X","Y","Z","1",
									"2","3","4","5","6","7","8","9","0"};
	private final String morse[] = {".-","-...","-.-.","-..",".","..-.","--.",
									"....","..",".---","-.-",".-..","--","-.",
									"---",".--.","--.-",".-.","...","-","..-",
									"...-",".--","-..-","-.--","--..",".----",
									"..---","...--","....-",".....","-....",
									"--...","---..","----.","-----"};
	
	/**
	 * Initialize the MorseCodeWindow frame
	 */
	public MorseCodeWindow()
	{
		super("Morse Code Encoder/Decoder");
		//create box to store window components
		Box box = Box.createHorizontalBox();
		
		//create text area for text
		String startMessage = "Enter a message to convert here";
		messageArea = new JTextArea(startMessage, 10, 15);
		messageArea.setBorder(BorderFactory.createTitledBorder("Text"));
		messageArea.setLineWrap(true);
		messageArea.setWrapStyleWord(true);
		box.add(new JScrollPane(messageArea));
		
		//create box to hold buttons
		Box buttonBox = Box.createVerticalBox();
		//create buttons and add to box
		textToMorse = new JButton("Text to Morse >>");
		morseToText = new JButton("<< Morse to Text");
		buttonBox.add(textToMorse);
		buttonBox.add(Box.createRigidArea(new Dimension(0,10)));
		buttonBox.add(morseToText);
		buttonBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		box.add(buttonBox);  //add button box to box
		
		//create text area for morse code
		convertedArea = new JTextArea( 10, 15);
		convertedArea.setBorder(BorderFactory.createTitledBorder("Morse"));
		convertedArea.setLineWrap(true);
		convertedArea.setWrapStyleWord(true);
		box.add(new JScrollPane(convertedArea));
		
		//create handler to listen for button presses
		ButtonHandler handler = new ButtonHandler();
		textToMorse.addActionListener(handler);
		morseToText.addActionListener(handler);
		
		//create border and add box to frame
		box.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(box);
	}
	
	/**
	 * Inner class to handle events from button presses
	 * @author Chuck
	 *
	 */
	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{	
			//text to morse button pressed
			if (event.getSource() == textToMorse)
			{
				//call toMorse method to convert text to morse
				convertedArea.setText(toMorse(messageArea.getText().toUpperCase()));
			}
			//morse to text button press
			if (event.getSource() == morseToText)
			{
				//call toText method to convert morse to text
				messageArea.setText(toText(convertedArea.getText()));
			}
		}
	}
	
	/**
	 * Converts a string to the equivalent in Morse code
	 * @param msg
	 * @return a string of Morse code
	 */
	private String toMorse(String msg)
	{
		//create new string to store morse
		String converted = new String();
		//boolean to mark the end of a word
		Boolean word = false;
		
		//split the string by spaces, store as array of strings
		String[] wordTokens = msg.split(" ");
		//create arraylist of string arrays to store letters
		ArrayList<String[]> letterTokens = new ArrayList<String[]>();
		//store each letter from each word
		for (int i=0; i<wordTokens.length; i++)
		{
			//split each word into letters
			letterTokens.add(wordTokens[i].split(""));
		}
		
		//loop through words
		for (int j=0; j<letterTokens.size(); j++)
		{
			//loop through letters
			for (int k=0; k<letterTokens.get(j).length; k++)
			{
				//find match in final letters array
				for (int m=0; m<letters.length; m++)
				{
					if (letterTokens.get(j)[k].equals(letters[m]))
					{
						converted += morse[m] + " ";	
						word = true;
						break;
					}
				}
			}
			//word finished, add 3 spaces into morse string
			if (word)
				converted += "   ";
			word = false;
		}
		
		return converted;
	}
	
	/**
	 * converts a Morse string to the equivalent in plain text
	 * @param msg
	 * @return converted plain text string
	 */
	private String toText(String msg)
	{
		//create string to store converted text
		String converted = new String();
		
		//split morse code words by finding chains of 3 spaces
		String[] wordTokens = msg.split("   ");
		ArrayList<String[]> letterTokens = new ArrayList<String[]>();
		//store morse letters by finding spaces
		for (int i=0; i<wordTokens.length; i++)
		{
			letterTokens.add(wordTokens[i].split(" "));
		}
		//loop through letters and convert to text
		for (int j=0; j<letterTokens.size(); j++)
		{
			for (int k=0; k<letterTokens.get(j).length; k++)
			{
				for (int m=0; m<morse.length; m++)
				{
					if (letterTokens.get(j)[k].equals(morse[m]))
					{
						converted += letters[m];	
						break;
					}
				}
			}
			//append a space to converted string
			converted += " ";
		}
		
		return converted;
	}
}
