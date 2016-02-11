import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;

/**
 * Create window to create arrays and run the QuickSort algorithm on them
 * @author Chuck
 *
 */
public class QuickSortGui extends JFrame implements ActionListener
{
	//initialize frame components
	private JPanel topPanel = new JPanel();
	private JTextField newNumberField = new JTextField(20);
	private JButton newNumberButton = new JButton("<<-- Add Integer to Array");
	private JPanel bottomPanel = new JPanel();
	private JTextArea currentArrayArea = new JTextArea(5,20);
	private JPanel bottomButtonPanel = new JPanel();
	private JButton sortButton = new JButton("QuickSort!");
	private JButton clearButton = new JButton("Clear Array");
	private JTextArea sortedArrayArea = new JTextArea(5,20);
	//variable to adjust size of all borders
	private final int borderSize = 10;
	//create QuickSort object
	private QuickSort q;
	
	/**
	 * Create frame to hold Quicksort GUI elements
	 */
	public QuickSortGui()
	{
		super("Quicksorter!");
		//set frame layout to borderlayout
		setLayout(new BorderLayout());
		
		//set layout of top panel to horizontal boxlayout, add elements
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.add(newNumberField);
		topPanel.add(Box.createRigidArea(new Dimension(borderSize,0)));
		topPanel.add(newNumberButton);
		topPanel.setBorder(BorderFactory.createEmptyBorder(borderSize,borderSize,borderSize,borderSize));
		
		//set layout of bottom panel to vertical, add elements
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));
		bottomPanel.add(currentArrayArea);
		currentArrayArea.setEditable(false);
		currentArrayArea.setWrapStyleWord(true);
		currentArrayArea.setLineWrap(true);
		currentArrayArea.setBorder(BorderFactory.createTitledBorder("Current Array"));
		bottomPanel.add(Box.createRigidArea(new Dimension(0,borderSize)));
		bottomButtonPanel.setLayout(new BoxLayout(bottomButtonPanel, BoxLayout.LINE_AXIS));
		bottomButtonPanel.add(sortButton);
		bottomButtonPanel.add(clearButton);
		bottomPanel.add(bottomButtonPanel);
		bottomPanel.add(Box.createRigidArea(new Dimension(0,borderSize)));
		bottomPanel.add(sortedArrayArea);
		sortedArrayArea.setEditable(false);
		sortedArrayArea.setWrapStyleWord(true);
		sortedArrayArea.setLineWrap(true);
		sortedArrayArea.setBorder(BorderFactory.createTitledBorder("Sorted Array"));
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(0,borderSize,borderSize,borderSize));
		
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.CENTER);
		
		//quicksortGUI class will handle events
		//set it to handle events from buttons and number field
		newNumberField.addActionListener(this);
		newNumberButton.addActionListener(this);
		sortButton.addActionListener(this);
		clearButton.addActionListener(this);
		
		//initialize quicksort object
		q = new QuickSort();
	}
	
	/**
	 * Event handler for button presses and text field
	 */
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		//new number button or new number field generate event
		if (event.getSource() == newNumberButton || event.getSource() == newNumberField)
		{
			try
			{
				//add number from text field to quicksort array
				q.add(Integer.parseInt(newNumberField.getText()));
			}
			//check for integer
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(getContentPane(), "That is not an integer.");
			}
			//update gui
			newNumberField.setText("");
			newNumberField.requestFocus();
			currentArrayArea.setText(q.unsortedString());	
		}
		//sort button pressed
		else if (event.getSource() == sortButton)
		{
			//call Quicksort object's sort method
			sortedArrayArea.setText(q.sortedString());
			//update gui
			newNumberField.setText("");
			newNumberField.requestFocus();
		}
		//clear button pressed
		else if (event.getSource() == clearButton)
		{
			//clear quicksort array
			q.clear();
			//update gui
			currentArrayArea.setText(q.unsortedString());
			sortedArrayArea.setText(q.sortedString());
			newNumberField.setText("");
			newNumberField.requestFocus();	
		}
	}
}
