import java.util.Arrays;

/**
 * Stores an array of integers and provides quicksort functionality for that array
 * @author Chuck
 *
 */
public class QuickSort 
{
	//keep track of array as entered
	private int[] array;
	//keep track of sorted array
	private int[] sortedArray;
	
	/**
	 * Create new QuickSort object, initialize integer array to size 0
	 */
	public QuickSort()
	{
		array = new int[0];
		sortedArray = new int[0];
	}
	
	/**
	 * Add an integer to the quicksort array
	 * @param i integer to be added to the array
	 */
	public void add(int i)
	{
		array = Arrays.copyOf(array, array.length+1);
		array[array.length-1] = i;
	}
	
	/**
	 * Clear the quicksort array
	 */
	public void clear()
	{
		array = new int[0];
	}
	
	/**
	 * Perform quicksort and return string of sorted array
	 * @return sorted array in string form
	 */
	public String sortedString()
	{
		//check if array is longer than 1 element
		if (array.length>0)
		{
			//copy array
			sortedArray = array;
			//call recursive quicksort method
			qs(0, sortedArray.length-1);
			
		}
		
		return arrayToString(sortedArray);
	}
	
	/**
	 * Recursive quicksort method
	 * @param i beginning index of array
	 * @param j end index of array
	 */
	private void qs(int i, int j)
	{
		//check progress
		if(j-i <= 0)
		{
			return ;
		}
		//store pivot and indexes
		int pivotValue = sortedArray[i];
		int left = i;
		int right = j;
		
		//sort pivot into position
		while(left<=right)
		{
			//start from upper index values, find first value smaller than pivot
			while(pivotValue<sortedArray[right])
			{
				right--;
			}
			//start from lower index, find first value smaller than pivot
			while(pivotValue>sortedArray[left])
			{
				left++;
			}
			//check that left index is still to left of or at right index
			if (left<=right)
			{
				//swap elements at right and left indexes
				int tmp = sortedArray[right];
				sortedArray[right] = sortedArray[left];
				sortedArray[left] = tmp;
				//increase/decrease indexes
				left++;
				right--;
			}
		}
		
		//sort numbers lower than pivot
		qs(i, right);
		//sort numbers larger than pivot
		qs(left, j);
	}
	
	/**
	 * Returns string of unsorted array elements
	 * @return a string of the unsorted array's elements
	 */
	public String unsortedString()
	{
		return arrayToString(array);
	}
	
	/**
	 * Returns string of array elements
	 * @param arr array to be output as a string
	 * @return a string of the array's elements
	 */
	private String arrayToString(int[] arr)
	{
		//loop through elements and add to string
		String arrayString = "";
		if (arr.length != 0)
		{
			arrayString += "{";
			
			for(int i=0; i<arr.length; i++)
			{
				if (i == arr.length-1)
					arrayString +=  arr[i];
				else
					arrayString += arr[i] + ", ";
			}
			
			arrayString += "}";
		}
		return arrayString;
	}
}
