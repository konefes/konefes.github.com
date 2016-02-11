import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Math.PI;
import java.text.DecimalFormat;

/**
 * Creates a window with a circle whose size is controlled by a slider.  
 * Also displays diameter, area, and circumference data. 
 * @author Chuck
 */
public class CircleWithSlider extends JFrame
{
	//create a CirclePanel to display the circle
	private CirclePanel cPanel = new CirclePanel();
	//create panel to hold slider
	private JPanel sliderPanel = new JPanel();
	//create horizontal slider with values ranging from 100-200, initially set to cPanel's diameter value
	private JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL, 100, 200, cPanel.getDiameter());
	//create text area to hold info string
	private JTextArea info = new JTextArea();
	//create string to hold diameter, area, and circumference data
	private String infoString = "";
	//create DecimalFormat to format displayed numbers
	private DecimalFormat df = new DecimalFormat("###,###.##");
	
	/**
	 * Initializes the CircleWithSlider frame
	 */
	public CircleWithSlider()
	{
		//set window text and layout
		super("Use Slider to Change Size");
		setLayout(new BorderLayout());
		
		//set background color of circle panel and add to frame
		cPanel.setBackground(Color.WHITE);
		add(cPanel, BorderLayout.CENTER);
		
		//slider will have no ticks
		sizeSlider.setPaintTicks(false);
		//add listener to slider 
		sizeSlider.addChangeListener(
				//anonymous inner class for change events from slider
				new ChangeListener()
				{
					public void stateChanged(ChangeEvent event)
					{
						cPanel.setDiameter(sizeSlider.getValue());
						cPanel.repaint();
						setInfo();
						info.setText(getInfo());
					}
				}
		);
		//add nested layout to sliderPanel and add the slider
		sliderPanel.setLayout(new BorderLayout());;
		sliderPanel.add(sizeSlider, BorderLayout.NORTH);
		
		//initialize info area and add to sliderPanel
		info.setRows(5);
		info.setEditable(false);
		setInfo();
		info.setText(getInfo());
		info.setBackground(getBackground());
		sliderPanel.add(info, BorderLayout.SOUTH);
		add(sliderPanel, BorderLayout.SOUTH);	
	}
	
	//method to set infoString
	private void setInfo()
	{	
		infoString = "\n   Diameter = " + cPanel.getDiameter() + 
				     "\n   Area = " + df.format(PI*cPanel.getDiameter()*cPanel.getDiameter()/4) +
				     "\n   Circumference = " + df.format(PI*cPanel.getDiameter());
	}
	//method to get infoString
	private String getInfo()
	{
		return infoString;
	}

	
}
