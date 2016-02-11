/**
 * Object that represents an Integer of up to 40 digits
 * @author Chuck
 *
 */
public class HugeInteger
{
	private int[] digits;
	private boolean positive;
	
	/**
	 * Create array to hold 40 digits, set to positive
	 */
	public HugeInteger()
	{
		digits = new int[40];
		positive = true;
	}
	
	
	/**
	 * Get a digit
	 * @param index Index of digit
	 * @return Value of digit
	 */
	public int getDigit(int index)
	{
		return digits[index];
	}
	/**
	 * Set a digit
	 * @param index Index of digit
	 * @param val Value of digit
	 */
	public void setDigit(int index, int val)
	{
		digits[index] = val;
	}
	/**
	 * Determine if HugeInteger is positive/negative
	 * @return true if positive, false if negative
	 */
	public boolean getPositive()
	{
		return positive;
	}
	/**
	 * Set HugeInteger to positive/negative
	 * @param b True for positive, false for negative
	 */
	public void setPositive(boolean b)
	{
		positive = b;
	}
	/**
	 * Add another HugeInteger to HugeInteger
	 * @param h1 HugeInteger to add to this
	 * @return HugeInteger object storing the result
	 */
	public HugeInteger add(HugeInteger h1)
	{
		HugeInteger h2 = new HugeInteger();
		//both positive or both negative
		if( (getPositive()&&h1.getPositive()) || (!getPositive()&&!h1.getPositive()) )
				h2 = addPos(h1);
		//positive, adding negative
		else if (getPositive()&&!h1.getPositive())
		{
			h1.setPositive(true);
			//bigger than the negative being added
			if(isGreaterThan(h1))
				h2=subPos(h1);
			//smaller than the negative being added
			else
			{
				h2=h1.subPos(this);
				h2.setPositive(false);
			}
			h1.setPositive(false);
		}
		//negative, adding positive
		else if(!getPositive()&&h1.getPositive())
		{
			h1.setPositive(false);
			//bigger than positive being added
			if(isGreaterThan(h1))
				h2=h1.subPos(this);
			//smaller than the positive being added
			else
			{
				h2=subPos(h1);
				h2.setPositive(false);
			}
			h1.setPositive(true);
		}
		
		return h2;
	}
	/**
	 * Add two HugeInts
	 * @param h1 HugeInt to add
	 * @return new HugeInt of result
	 */
	private HugeInteger addPos(HugeInteger h1)
	{
		HugeInteger h2 = new HugeInteger();
		int temp=0;
		//loop from right to left
		for(int i=39; i>=0; i--)
		{
			h2.setDigit(i, temp+getDigit(i)+h1.getDigit(i));
			//check if carry is needed
			if(h2.getDigit(i)>=10)
			{
				h2.setDigit(i, h2.getDigit(i)%10);
				temp=1;
			}
			else
				temp=0;
		}
		h2.setPositive(getPositive());
		return h2;
	}
	/**
	 * Subtract another HugeInteger from HugeInteger
	 * @param h1 HugeInteger to subtract from this
	 * @return HugeInteger object storing the result
	 */
	public HugeInteger subtract(HugeInteger h1)
	{
		HugeInteger h2 = new HugeInteger();
		//opposite signs
		if( (getPositive()&&!h1.getPositive()) || (!getPositive()&&h1.getPositive()) )
			h2=addPos(h1);
		//both positive
		if(getPositive()&&h1.getPositive())
		{
			if(isLessThan(h1))
			{
				h2=h1.subPos(this);
				h2.setPositive(false);
			}
			else
				h2=subPos(h1);
		}
		//both negative
		if(!getPositive()&&!h1.getPositive())
		{
			if(isLessThan(h1))
			{
				h2=subPos(h1);
				h2.setPositive(false);
			}	
			else
				h2=h1.subPos(this);
		}
		return h2;
	}
	/**
	 * Subtract two HugeInts
	 * @param h1 HugeInt to subtract
	 * @return new HugeInt of result
	 */
	private HugeInteger subPos(HugeInteger h1)
	{
		HugeInteger h2 = new HugeInteger();
		int temp = 0;
		//loop from right to left
		for(int i=39; i>=0; i--)
		{
			//check if borrow is needed
			if(getDigit(i)<h1.getDigit(i))
			{
				//add 10 to stay positive, set borrow
				h2.setDigit(i, getDigit(i)+10-temp-h1.getDigit(i));
				temp = 1;
			}
			else
			{
				//no borrow needed
				h2.setDigit(i, getDigit(i)-temp-h1.getDigit(i));
				temp = 0;
			}
		}
		return h2;
	}
	
	/**
	 * Check if equal to another HugeInteger
	 * @param h1 HugeInteger to be compared
	 * @return result of comparison
	 */
	public boolean isEqualTo(HugeInteger h1)
	{
		//not equal if opposite signs
		boolean b = getPositive()==h1.getPositive();
		//if same sign
		if(b)
		{
			//check each digit
			for(int i=0; i<40; i++)
			{
				if(getDigit(i)!=h1.getDigit(i))
				{
					b=false;
					break;
				}
			}
		}
		
		return b;
	}
	/**
	 * Check if not equal to another HugeInteger
	 * @param h1 HugeInteger to be compared
	 * @return result of comparison
	 */
	public boolean isNotEqualTo(HugeInteger h1)
	{
		return !isEqualTo(h1);
	}
	/**
	 * Check if less than another HugeInteger
	 * @param h1 HugeInteger to be compared
	 * @return result of comparison
	 */
	public boolean isLessThan(HugeInteger h1)
	{
		//check if same sign
		boolean b = getPositive()==h1.getPositive();
		//different sign
		if(!b)
		{
			b = !getPositive();
		}
		//same sign
		else
		{	//check until fist nonzero digit
			for(int i=0; i<40; i++)
			{
				if(getDigit(i)!=h1.getDigit(i))
				{
					if(getPositive())
					{
						if(getDigit(i)>h1.getDigit(i))
						{
							b=false;
							break;
						}
						else
						{
							b=true;
							break;
						}
					}	
					else
					{
						if(getDigit(i)<h1.getDigit(i))
						{
							b=false;
							break;
						}
						else
						{
							b=true;
							break;
						}
					}
				}
			}//end for loop
		}//end out if
		
		return b && !isEqualTo(h1);
	}
	/**
	 * Check if less than or equal to another HugeInteger
	 * @param h1 HugeInteger to be compared
	 * @return result of comparison
	 */
	public boolean isLessThanOrEqualTo(HugeInteger h1)
	{
		return (isLessThan(h1) || isEqualTo(h1));
	}
	/**
	 * Check if greater than another HugeInteger
	 * @param h1 HugeInteger to be compared
	 * @return result of comparison
	 */
	public boolean isGreaterThan(HugeInteger h1)
	{
		return !isLessThan(h1) && !isEqualTo(h1);
	}
	/**
	 * Check if greater than or equal to another HugeInteger
	 * @param h1 HugeInteger to be compared
	 * @return result of comparison
	 */
	public boolean isGreaterThanOrEqualTo(HugeInteger h1)
	{
		return !isLessThan(h1);
	}
	
	/**
	 * Determine Whether the object is zero or not
	 * @return 
	 */
	public boolean isZero()
	{
		boolean b = true;
		for(int i=0; i<40; i++)
		{
			if(getDigit(i)!=0)
			{
				b=false;
				break;
			}
		}
		return b;
	}
	
	/**
	 * Converts a String representing an integer into a HugeInteger representation
	 * @param intString String of digits
	 */
	public void parse(String intString)
	{
		if(intString.startsWith("-"))
		{
			setPositive(false);
			intString = intString.substring(1, intString.length()-1);
		}
		//loop through, get value at 
		for(int i=40-intString.length(); i<40; i++)
		{
			setDigit(i, Character.getNumericValue(intString.charAt(i+intString.length()-40)));
		}
	}
	
	/**
	 * Returns the HugeInteger represented number as a string
	 */
	public String toString()
	{
		String hugeIntString = "";
		if(!positive)
			hugeIntString += "-";
		
		boolean leadingZeroes = true;
		for(int i=0; i<40; i++)
		{
			if(!(getDigit(i)==0))
				leadingZeroes=false;
			if(!leadingZeroes)
				hugeIntString = hugeIntString + getDigit(i);	
		}		
		if(leadingZeroes)
			hugeIntString = hugeIntString + "0";
		return hugeIntString;		
	}//end toString method	
}
