import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.text.DecimalFormat;

/**
 * Creates a basic calculator with full functionality
 * @author Chuck
 *
 */
public class Calculator extends JFrame
{
	//panel for calculator 
	private JPanel buttonPanel;
	//calculator display
	private JTextField display;
	//array of calculator buttons
	private JButton buttons[], clearButton;
	private static final String[] buttonText = {"7", "8","9",
		"/","4","5","6","*","1","2","3","-","0",".","=","+"};
	
	//variable to store number button presses
	private String number = "";
	//variables to store calculation numbers
	private double tempNum1, tempNum2, result;
	//boolean variables to keep track of state
	private boolean firstOp = true, opToggle, numToggle;
	//store which operation to perform
	private int operation;
	//create formatting for display
	private final DecimalFormat df = new DecimalFormat("0.##########");
	
	/**
	 * Construct the Calculator object
	 */
	public Calculator()
	{
		//call constructor of superclass and set layout
		super("Calculator");
		//setLayout(new FlowLayout());
		
		//initialize the display text field and add to frame
		display = new JTextField(16);
		display.setEditable(false);
		display.setBackground(Color.white);
		display.setHorizontalAlignment(JTextField.RIGHT);
		add(display, BorderLayout.NORTH);
		
		//create button array and add to buttonPanel as a grid
		buttons = new JButton[16];		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4,4,0,0));
		//create handler for button presses
		ButtonHandler handler = new ButtonHandler();
		//add text and handler to each button, add buttons to jpanel
		for(int i=0; i<buttons.length; i++)
		{
			buttons[i] = new JButton(buttonText[i]);
			buttonPanel.add(buttons[i]);
			buttons[i].addActionListener(handler);
		}
		//add the button panel to the frame
		add(buttonPanel, BorderLayout.CENTER);
		
		//create big clear button, add handler and add to bottom of frame
		clearButton = new JButton("Clear");
		add(clearButton, BorderLayout.SOUTH);
		clearButton.addActionListener(handler);
	}
	
	
	//inner class for handling button press actions
	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			//pressing buttons 1-9
			for(int i=0; i<3; i++)
			{
				//pressing buttons 7-9
				if(event.getSource()==buttons[i])
				{
					numButtonPressed(String.valueOf(i+7));
				}
				//pressing buttons 4-6
				else if(event.getSource()==buttons[i+4])
				{
					numButtonPressed(String.valueOf(i+4));
				}
				//pressing buttons 1-4
				else if(event.getSource()==buttons[i+8])
				{
					numButtonPressed(String.valueOf(i+1));
				}
			}
			//press division button
			if(event.getSource()==buttons[3])
			{
				opButtonPressed(" /",1);
			}
			//press multiplication button
			if(event.getSource()==buttons[7])
			{
				opButtonPressed(" *",2);
			}
			//press subtraction button
			if(event.getSource()==buttons[11])
			{
				opButtonPressed(" -",3);
			}
			//press button 0
			if(event.getSource()==buttons[12])
			{
				numButtonPressed("0");
			}
			//press decimal point button
			if(event.getSource()==buttons[13])
			{
				numButtonPressed(".");
			}
			//press equals button
			if(event.getSource()==buttons[14])
			{
				//not the first operation and an operator wasn't last button press
				if (!firstOp && !opToggle)
				{
					//compute the result and reset variables
					computeResult();
					number = df.format(result);
					if (result<999999999||result>-999999999)
						display.setText(df.format(result));
					else display.setText("" + result);
					operation = 0;
					firstOp = true;	//1st operator has not been pressed
					numToggle = true; //a first number has not yet been entered
					tempNum1 = 0;
					tempNum2 = 0;
					result = 0;
				}
				//an operator was the last button press 
				else
					//remove operator from end of string by reprinting result
					if (result<999999999||result>-999999999)
						display.setText(df.format(result));
					else display.setText("" + result);
				
			}
			//press addition button
			if(event.getSource()==buttons[15])
			{
				opButtonPressed(" +",4);
					
			}
			//press clear button
			if(event.getSource()==clearButton)
			{
				//reset needed variables and clear screen
				number = "";
				display.setText(number);
				firstOp = true;	//1st operator has not been pressed
				numToggle = false; 
				result = 0;
				tempNum1 = 0;
				tempNum2 = 0;
				opToggle = false; //operator was not last button press
			}
		}
	}
	
	//method runs when a number button or decimal point is pressed
	private void numButtonPressed(String s)
	{
		//appending digits to current number
		if (!numToggle) {
			//append number onto current number and display
			number = number + s;
			display.setText(number);
		}
		//new number
		else {
			//reset to first digit 
			number = s;
			display.setText(number);
			numToggle = false; //now currently entering a number
		}
		//set to be ready for another operation
		opToggle = false; //operator was not last button press
	}
	
	
	/*
	 * 
	 * method runs when on operator button is pressed
	 * 
	 */
	private void opButtonPressed(String s, int i)
	{
		//check if first operator and a number has been entered
		if (firstOp && number != "") {
			//store current number
			tempNum1 = Double.parseDouble(number);
			//update display
			display.setText(number + s);
			//flag that 1st number is finished
			numToggle = true;
			//flag that it is no longer the first operator
			firstOp = false;	
			//flag that an operator was last button press
			opToggle = true;
			//store what operation is to be performed
			operation = i;
			//update result in case equals is next button press
			result = tempNum1;
		}
		//if a number has been entered and last button press was not an operator
		else if (number != "" && !opToggle) {
			//get result from last operator and display
			computeResult();
			//store what operation is to be performed
			operation = i;
			//store current number after calculation
			tempNum1 = result;
			//reset number string
			number = "";
			//flag that an operator was last button press
			opToggle = true;
			//display result of last operation and append string with new operator
			if (result<999999999||result>-999999999)
				display.setText(df.format(result) + s);
			else display.setText("" + result + s);
		}
		else
		{
			//if last button press was an operator, but not 1st operator
			if(!firstOp) 
			{
				//replace last operator button press with current one
				operation = i;
				if (result<999999999||result>-999999999)
					display.setText(df.format(result) + s);
				else display.setText("" + result + s);
			}
		}
	}
	
	/*
	 * 
	 * method to perform calculation
	 * 
	 */
	private void computeResult()
	{
		
		//check if a second number exists for calculation
		if (number != "")
		{
			//convert to double
			tempNum2 = Double.parseDouble(number);
		
			//decide which operation to perform from operation variable and store result
			if (operation == 1)
				result = tempNum1 / tempNum2;
			else if (operation == 2)
				result = tempNum1 * tempNum2;
			else if (operation == 3)
				result = tempNum1 - tempNum2;
			else if (operation == 4)
				result = tempNum1 + tempNum2;
		}
	}
}
