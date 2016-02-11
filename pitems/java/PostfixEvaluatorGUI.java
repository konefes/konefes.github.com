import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

/**
 * Create window to enter postfix strings and evaluate them
 * @author Chuck
 *
 */
public class PostfixEvaluatorGUI extends JFrame implements ActionListener
{
	//gui components
	private JPanel topPanel = new JPanel();
	private JTextField newEntryField = new JTextField(20);
	private JButton newEntryButton = new JButton("<<-- Add to Postfix String");
	private JPanel centerPanel = new JPanel();
	private JTextArea currentArea = new JTextArea(5,20);
	private JPanel bottomButtonPanel = new JPanel();
	private JButton evalButton = new JButton("Evaluate");
	private JButton clearButton = new JButton("Clear Array");
	private JPanel bottomPanel = new JPanel();
	private JTextField evalField = new JTextField(10);
	private final int borderSize = 10;
	private DecimalFormat formatter;
	
	//PostfixEvaluator object
	private PostfixEvaluator pe;
	
	public PostfixEvaluatorGUI()
	{
		super("Postfix Evaluator");
		//set layout to hold other components
		setLayout(new BorderLayout());
		
		//setup components in top panel
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.add(newEntryField);
		topPanel.add(Box.createRigidArea(new Dimension(borderSize,0)));
		topPanel.add(newEntryButton);
		topPanel.setBorder(BorderFactory.createEmptyBorder(borderSize,borderSize,borderSize,borderSize));
		
		//setup center panel
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
		centerPanel.add(currentArea);
		currentArea.setEditable(false);
		currentArea.setWrapStyleWord(true);
		currentArea.setLineWrap(true);
		currentArea.setBorder(BorderFactory.createTitledBorder("Current String"));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(0,borderSize,borderSize,borderSize));
		
		//setup bottom panel
		bottomButtonPanel.setLayout(new BoxLayout(bottomButtonPanel, BoxLayout.X_AXIS));
		bottomButtonPanel.add(evalButton);
		bottomButtonPanel.add(Box.createRigidArea(new Dimension(borderSize,0)));
		bottomButtonPanel.add(clearButton);
		evalField.setEditable(false);
		evalField.setBackground(Color.WHITE);
		evalField.setBorder(BorderFactory.createTitledBorder("Evaluation"));
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bottomPanel.add(bottomButtonPanel);
		bottomPanel.add(Box.createRigidArea(new Dimension(borderSize,0)));
		bottomPanel.add(evalField);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(0,borderSize,borderSize,borderSize));
		
		//add panels to frame
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		
		//add event handler for buttons and text field
		newEntryField.addActionListener(this);
		newEntryButton.addActionListener(this);
		evalButton.addActionListener(this);
		clearButton.addActionListener(this);
		
		//initialize PostfixEvaluator object and double formatting
		pe = new PostfixEvaluator();
		formatter = new DecimalFormat("###,###.###");
	}

	/**
	 * Event handler for button presses and text field events
	 */
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		//new entry button of text field event triggered
		if (event.getSource() == newEntryButton || event.getSource() == newEntryField)
		{
			//copy contents of text field
			String contents = newEntryField.getText();
			try
			{
				//add contents of text field to pe postfix string
				pe.add(Integer.parseInt(contents));
			}
			catch(NumberFormatException e)
			{
				//contents of text field was not an integer
				//check if operator
				if (contents.equals("+")||contents.equals("-")||contents.equals("/")||
					contents.equals("*")||contents.equals("^")||contents.equals("%"))
				{
					//add operator to pe postfix string
					pe.add(contents);
				}
				// notify user of incorrect input
				else
				{
					JOptionPane.showMessageDialog(getContentPane(), "That is not a valid entry.");
				}	
			}
			//reset gui
			newEntryField.setText("");
			newEntryField.requestFocus();
			currentArea.setText(pe.currentString());
			
		}
		//evaluate button event triggered
		else if (event.getSource() == evalButton)
		{
			//run and format pe object evaluation of postfix string
			evalField.setText(formatter.format(pe.evaluate()));
			//reset gui
			newEntryField.setText("");
			newEntryField.requestFocus();
		}
		//clear button event triggered
		else if (event.getSource() == clearButton)
		{
			//clear pe postfix string
			pe.clear();
			//reset gui
			currentArea.setText("");
			evalField.setText("");
			newEntryField.setText("");
			newEntryField.requestFocus();		
		}	
	}	
}
