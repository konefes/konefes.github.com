import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Creates a window that allows the user to obtain a text file from the server, make changes and save it back to the server
 * @author Chuck
 *
 */
public class ModifyFileClient extends JFrame implements ActionListener
{
	//socket and streams
	private Socket connection;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	//gui components
	private JPanel topPanel;
	private JTextField fileField;
	private JButton openButton;
	private JTextArea textArea;
	private JScrollPane scroller;
	private JPanel bottomPanel;
	private JButton saveButton;
	
	/**
	 * Initializes gui and attempts server connection
	 */
	public ModifyFileClient() 
	{
		super("ModifyFile Client");
		
		//initialize upper gui components 
		topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		fileField = new JTextField(20);
		topPanel.add(fileField);
		openButton = new JButton("Open");
		topPanel.add(Box.createRigidArea(new Dimension(10,0)));
		topPanel.add(openButton);
		topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		//initialize text area component
		textArea = new JTextArea();
		textArea.setBorder(BorderFactory.createTitledBorder("File Contents"));
		scroller = new JScrollPane(textArea);
		
		//initialize lower gui components
		bottomPanel = new JPanel();
		saveButton = new JButton("Save Changes to Server");
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(saveButton, BorderLayout.EAST);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		//add gui components to frame
		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		add(scroller, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		
		//add handlers for button presses and file field events
		fileField.addActionListener(this);
		openButton.addActionListener(this);
		saveButton.addActionListener(this);
		
		try
		{
			//connect through socket 23555 with server on local machine
			connection = new Socket( InetAddress.getLocalHost(), 23555 );
			//initialize output data stream
			output = new ObjectOutputStream(connection.getOutputStream());
	        output.flush();
	        //initialize input data stream
	        input = new ObjectInputStream(connection.getInputStream());
		}
		catch(IOException e)
		{
			//server not running, close client
			JOptionPane.showMessageDialog(getContentPane(), "Server is not running, closing client.");
			System.exit(0);
		}
		
		//when exiting, write a command to server
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					output.writeObject("close");
				} catch (IOException e1) {}
				System.exit(0);
			}
		});
	}

	/**
	 * Event handler for button presses and file field event
	 */
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		try
		{   
			if (!fileField.getText().equals(""))
			{
				//open button or file field event triggered
	            if(event.getSource()==fileField || event.getSource()==openButton)
	    		{	
	            	//send command "filename" to output stream
	            	output.writeObject("filename");
	            	//write name of requested file to output stream
	            	output.writeObject(fileField.getText());
	            	//clear text area
	            	updateDisplay(2,"");
			        //wait for acknowledgement from server
			        String exists = (String)input.readObject();
			        //file exists on server
			        if(exists.equals("exists"))
			        {
				        String fileContents = (String) input.readObject();
				        while(fileContents!=null)
				        {
				        	updateDisplay(1,fileContents +"\n");
				        	fileContents = (String) input.readObject();
				        }
			        }
			        //file does not exist on server
			        else if(exists.equals("!exist"))
			        {
			        	updateDisplay(2,fileField.getText() + " doesn't exist.");
			        }
			        scroller.getVerticalScrollBar().setValue(0);
	    		}
	            //save button event triggered
	            else if(event.getSource()==saveButton)
	    		{
	            	//send command "saving" to output stream
	            	output.writeObject("saving");
	            	//write name of file to be saved
			        output.writeObject(fileField.getText());
			        //write text to save
			        output.writeObject(textArea.getText());
			        JOptionPane.showMessageDialog(getContentPane(), fileField.getText() + " written to file!");
	    		}           
			}
		}//end try
		catch(IOException e1){} 
		catch (ClassNotFoundException e2) {}	
	}
	
	/**
	 * Thread-safe method for updating text area
	 * @param i
	 * @param msg
	 */
	private void updateDisplay(final int i, final String msg)
	{
		SwingUtilities.invokeLater(
				new Runnable()
				{
					public void run()
					{
						if(i==1)
						{
							textArea.append(msg);
						}
						else if (i==2)
						{
							textArea.setText(msg);
						}
					}
				}
		);
	}
}
