/**
 * This class represents a set of integers that can include numbers
 * from 0 to 100.
 * @author Chuck
 *
 */
public class IntegerSet 
{
	//create array of boolean values, each entry defaults to 0
	private boolean[] setOfInts = new boolean[101];
	
	/**
	 * Creates an empty IntegerSet.
	 */
	public IntegerSet(){}
	
	/**
	 * Returns the union of two IntegerSets
	 * @param intSet1 an IntegerSet object
	 * @param intSet2 another IntegerSet object
	 * @return An IntegerSet that represents the union of
	 * intSet1 and intSet2. The returned IntegerSet includes all integers
	 * that exist in either intSet1 of intSet2.
	 * true. 
	 */
	public static IntegerSet union(IntegerSet intSet1, IntegerSet intSet2)
	{
		//create new IntegerSet to store union--initially all false
		IntegerSet unionSet = new IntegerSet();
		
		//cycle through each index
		for(int i=0; i<=100; i++)
		{
			//check if either value is true
			if(intSet1.getValue(i) || intSet2.getValue(i))
			{
				//insert true at corresponding element in IntegerSet to be returned
				unionSet.insertElement(i);
			}
		}
		return unionSet;
	}
	
	/**
	 * Returns the intersection of two IntegerSets
	 * @param intSet1 an IntegerSet object
	 * @param intSet2 another IntegerSet object
	 * @return An IntegerSet that represents the intersection of
	 * intSet1 and intSet2. The returned IntegerSet includes all integers
	 * that exist in both intSet1 and intSet2.
	 */
	public static IntegerSet intersection(IntegerSet intSet1, IntegerSet intSet2)
	{
		//create new IntegerSet to store intersection--initially all false
		IntegerSet intersectSet = new IntegerSet();
		
		//cycle through each index
		for(int i=0; i<=100; i++)
		{
			//check if both values are true
			if(intSet1.getValue(i) && intSet2.getValue(i))
			{
				//insert true at corresponding element in IntegerSet to be returned
				intersectSet.insertElement(i);
			}
		}
		return intersectSet;
	}
	
	/**
	 * Inserts i into the IntegerSet
	 * @param i Inserts an integer of value i into the IntegerSet if it is not
	 * already included
	 */
	public void insertElement(int i)
	{
		//check for valid index
		if(i>=0 && i<=100)
		{
			setOfInts[i]=true;
		}
	}
	
	/**
	 * Removes i from the IntegerSet
	 * @param i Makes sure i is not included in IntegerSet
	 */
	public void deleteElement(int i)
	{
		//check for valid index
		if(i>=0 && i<=100)
		{
			setOfInts[i]=false;
		}
	}
	
	/**
	 * Returns a string of the integers in the IntegerSet
	 * 
	 * @return String consisting of each number stored in the IntegerSet in 
	 * the form "{ 1, 2, 3 }
	 */
	@Override
	public String toString()
	{
		//create string of values
		String listOfNumbers = "{";
		
		//cycle through each index
		for(int i=0; i<=100; i++)
		{
			//check if index value of setOfInts is true
			if(getValue(i))
			{
				//concatenate a space and the integer to the string
				listOfNumbers = listOfNumbers + " " + i;
			}
		}
		
		//finish up and return string
		listOfNumbers = listOfNumbers + " }";
		return listOfNumbers;
	}
	
	/**
	 * Check if i exists in the IntegerSet
	 * @param i Checks if the integer i exists in this IntegerSet
	 * @return True if the integer is part of the IntegerSet, or false if it is not.
	 */
	public boolean getValue(int i)
	{
		//check for valid index
		if(i>=0 && i<=100)
		{
			return setOfInts[i];
		}
		return false;
	}
	
}
