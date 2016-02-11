import java.util.Random;


public class HugeIntegerTest 
{

	public static void main(String[] args) 
	{
		HugeInteger h1 = new HugeInteger();
		String h1String = "";
		HugeInteger h2 = new HugeInteger();
		String h2String = "";
		
		for(int i = (int)(Math.random()*40); i<40; i++)
		{
			h1String += (int)(Math.random()*10);
		}
		for(int i = (int)(Math.random()*40); i<40; i++)
		{
			h2String += (int)(Math.random()*10);
		}
		
		h1.parse(h1String);
		h2.parse(h2String);
		
		if(1+(int)(Math.random()*2)>1)
			h1.setPositive(false);
		if(1+(int)(Math.random()*2)>1)
			h2.setPositive(false);
		
		System.out.println("h1 init to: " + h1.toString());
		System.out.println("h2 init to: " + h2.toString());
		
		System.out.println("h1 is zero: " + h1.isZero());
		System.out.println("h1 is equal to h2: " + h1.isEqualTo(h2));
		System.out.println("h1 is less than h2: " + h1.isLessThan(h2));
		System.out.println("h1 is greater than h2: " + h1.isGreaterThan(h2));
		
		System.out.println("h1 + h2 is: " + h1.add(h2).toString());
		System.out.println("h1 - h2 is: " + h1.subtract(h2).toString());
	}
}
