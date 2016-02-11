import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Server program to provide client with text files and save client's changes
 * @author Chuck
 *
 */
public class ModifyFileServer extends JFrame 
{	
	//socket and streams
	private ServerSocket server;
	private Socket connection;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	//file object to access files
	private File file;

	//gui components
	private JTextArea msgArea;
	
	/**
	 * Initialize window 
	 */
	public ModifyFileServer()
	{
		super("ModifyFile Server");

		//initialize text area to display operation 
		msgArea = new JTextArea();
		msgArea.setEditable(false);
		msgArea.setBorder(BorderFactory.createTitledBorder("Server Info"));
		add(new JScrollPane(msgArea));
		
		//initialize ServerSocket to wait for connection
		try
		{
			server = new ServerSocket(23555, 10);
		}
		catch(IOException e){}
	}

	/**
	 * Set up server to wait for commands from client
	 */
	public void runServer()
	{	
		while(true)//infinite loop
		{
			try
			{
				//wait for and accept a connection
				msgArea.append("Waiting for Connection...\n");
				connection = server.accept();
				updateDisplay("Connected to client.");
	
				//initialize input and output streams
				output = new ObjectOutputStream(connection.getOutputStream());
				output.flush();
				input = new ObjectInputStream(connection.getInputStream());
				updateDisplay("Successful I/O initialization.");
				
				//loop while connection is still connected
				while(connection.isConnected())
				{
					//get command string from client
					String clientCmd = (String)input.readObject();
					
					//client wants to access a file
					if(clientCmd.equals("filename"))
					{
						readFilename();
					} 
					//client wants to save a file
					else if(clientCmd.equals("saving"))
					{
						saveFile();
					}
				}//end while
			}//end try
			catch (IOException | ClassNotFoundException  e1) {}
			finally 
			{
				//connection from client lost, close connection
				try 
				{
					output.close(); // close output stream
					input.close(); // close input stream
					connection.close(); // close socket
					updateDisplay("Connection closed.\n");
				} 
				catch ( IOException ioException ) {} 
			}//end finally
		}//end while	      
	} // end method runServer

	/**
	 * Client wants to save a file to the server
	 * Accept text from client and write to file
	 */
	private void saveFile() 
	{
		try
		{
			//get file name from client
			String filename = (String) input.readObject();
			updateDisplay("Attempting to save " + filename + ".");
	
			//initialize file with file name from client
			file = new File(filename);
			//create new BufferedWriter for writing text to file
			BufferedWriter bf = new BufferedWriter(new FileWriter(filename));
			//write the input from client to file
			bf.write((String) input.readObject());
			//close BufferedWriter
			bf.close();
			updateDisplay("Saved " + filename + ".");	
		}
		catch(IOException | ClassNotFoundException e1){}	
	}

	/**
	 * Client wants to read a file from the server
	 * Determine if file exists and write to client
	 */
	private void readFilename() 
	{
		try
		{
			//read filename from client
			String filename = (String) input.readObject();
	
			//create new file object with filename
			file = new File(filename);
			//determine if file exists
			if (file.exists())
			{
				//acknowledge that file exists
				output.writeObject("exists");
				//create scanner to read file contents
				Scanner fileContents = new Scanner(file);
				//write all lines from file to client
				while(fileContents.hasNextLine())
				{
					output.writeObject(fileContents.nextLine());
				}
				//close scanner
				fileContents.close();
				//signal end of file
				output.writeObject(null);
				updateDisplay(filename + " successfully sent!");
			}
			//file does not exist
			else
			{
				//tell client file does not exist
				output.writeObject("!exist");
				updateDisplay(filename + " doesn't exist.");
			}  
		}
		catch(IOException | ClassNotFoundException e1){}		
	}	
	
	/**
	 * Thread-safe method for updating text area
	 * @param msg
	 */
	private void updateDisplay(final String msg)
	{
		SwingUtilities.invokeLater(
				new Runnable()
				{
					public void run()
					{
						msgArea.append(msg +"\n");
					}
				}
		);
	}
}
