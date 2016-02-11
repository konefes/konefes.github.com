import java.util.Stack;

/**
 * Object to hold a String in postfix format and provide functionality to evaluate it
 * @author Chuck
 *
 */
public class PostfixEvaluator 
{
	//StringBuffer object for postfix string
	private StringBuffer s;
	//stack used to evaluate postfix string
	private Stack<Double> peStack = new Stack<Double>();
	//doubles to hold intermediate evaluation values
	private double x, y;
	private char op;
	
	/**
	 * Initialize PostfixEvaluator object with empty postfix string
	 */
	public PostfixEvaluator()
	{
		s = new StringBuffer(" )");
	}

	/**
	 * Evaluate the postfix string 
	 * @return double value of evaluation result
	 */
	public double evaluate() 
	{
		//String to store current postfix string element
		String current = "";
		int numCount = 0;
		int i = 1;
		
		//look for end marking character
		while(s.charAt(i) != ')')
		{
			//check for spaces to separate postfix elements
			if(s.charAt(i) == ' ' && s.length() > 2)
			{
				try
				{
					//attempt to push a double onto the stack
					peStack.push(Double.parseDouble(current));
					numCount++;
				}
				//tried to push operator to stack
				catch(NumberFormatException e)
				{
					//get operator
					op = s.charAt(i-1);
					//get values to calculate
					y = peStack.pop();
					x = peStack.pop();
					//call calculate method
					calculate();
				}
				current = "";
			}
			//still filling current element
			else
			{
				current = current + s.charAt(i);
			}
			i++;
		}
		//check if anything is in the stack
		if(peStack.isEmpty())
		{
			return 0;
		}
		else
		{
			return peStack.pop();
		}
	}
	
	/**
	 * Perform calculation for current operator and x and y values,
	 * push result back on stack
	 */
	private void calculate() 
	{
		if(op=='+')
		{
			peStack.push(x+y);
		}
		else if(op=='-')
		{
			peStack.push(x-y);
		}
		else if(op=='/')
		{
			peStack.push(x/y);
		}
		else if(op=='*')
		{
			peStack.push(x*y);
		}
		else if(op=='^')
		{
			peStack.push(Math.pow(x,y));
		}
		else if(op=='%')
		{
			peStack.push(x%y);
		}
	}

	/**
	 * Append integer to postfix string
	 * @param newInt
	 */
	public void add(int newInt) 
	{
		s.insert(s.length()-2, " ");
		s.insert(s.length()-2, newInt);			
	}

	/**
	 * Append operator to postfix string
	 * @param newOp
	 */
	public void add(String newOp) 
	{
		s.insert(s.length()-2, " ");
		s.insert(s.length()-2, newOp);
	}

	/**
	 * Formats and returns postfix string
	 * @return
	 */
	public String currentString() 
	{
		return "(" + s.toString();
	}

	/**
	 * Clear postfix string
	 */
	public void clear() 
	{
		s.delete(0,s.length());
		s.append(" )");
	}
	
}
