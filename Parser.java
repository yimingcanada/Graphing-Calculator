import java.util.ArrayList;
import java.util.Arrays;



public class Parser {

	public static void main(String args[])
	{
		//evaluate ("154 + 4*5^2 + 2^2 - 8/4 - 2^2^3");
		System.out.println(     evaluate ("(4*5^2 + 2^2) ((2-5) + 5(5))"));
		//note: if you do: evaluate ("(4*5^2 + 2^2) (2-5) + 5(5)");, the program will NOT work since it can't deal with negative signs and reads it as a subtraction operator
	}

	static double evaluate (String input)
	{
		

		input = input.replaceAll("\\s", ""); //removes whitespace


		if(input.contains("("))
		{
			if( input.contains(")("))
			{
				input = input.replace(")(", ")*(");
			}

			for (int i = 0 ; i <= 9;  i++) 
			{//replaces all "n(" with "n*(" where n is a number
				String search = i + "(";
				if (input.contains(search))
				{
					input = input.replace(search, i+"*(");
				}
			}

		

			int startBracket = input.indexOf("(");
			int bracketCount = 0;
			int endBracket = 0; // used to find the associated bracket. ie: in the string "( ( ) )", the leftmost bracket is associated with the rightmost bracket
			//it counts how many brackets have passed and when it is 0 again, the associated bracket is found
			boolean done = false;// stays false until all the brackets have been processed

			while (!done)
			{
				for (int i= startBracket; i < input.length() ; i ++) //starts at the index of the first bracket found
				{
					if (input.charAt(i) == '(')
					{
						bracketCount ++;//if another left bracket passed, we must find the right bracket after the next one in order to find the one we're looking for
					}
					else if (input.charAt(i) == ')')
					{
						bracketCount --;
						if (bracketCount ==0)//if the associated bracket is found
						{
							endBracket = i ;//the end bracket is found
						
							if(input.indexOf('(', i)==-1) //if no more brackets are left, this is the last loop 
							{
								
								done = true;

							}                       

							String strToEvaluate = input.substring(startBracket +1, endBracket);// the string is within the two associated brackets
							input = input.replace("("+strToEvaluate+")", Double.toString(evaluate(strToEvaluate)));//evaluate the string within the associated brackets
							startBracket = input.indexOf('(');//goes to the beginning again to search for the first bracket since the length of the string input has most likely shortened

							break;
						}
					}
				}
			}
		}

		//after brackets have been dealt with, begin precedence climbing
		ArrayList<Double> numbers = new ArrayList <Double>();
		ArrayList<String> operators = new ArrayList<String> ();

	
		input = input.replaceAll("\\s", ""); //removes whitespace
		input = input.replaceAll("\\+", " + "); //add spaces between all of the operators in order to split any inputs that contains a number over 9 properly
		input = input.replaceAll("\\-", " + -");//the negative sign loses its ability to become an operator and becomes a modifier
		//it is ok to do this negative multiplication and division since the code ignores the second operator
		input = input.replaceAll("\\*", " * ");
		input = input.replaceAll("/", " / ");
		input = input.replaceAll("\\^", " ^ ");


	
		if (input.length()>2)
		{
			if (input.substring(0, 3).equals(" + ")) //checks if the input starts with a +, if it does, remove it
			{
				input = input.replaceFirst(" \\+ ", "");
			}
		}

		//System.out.println(input);

		String[] splitInput = ((input.split(" ")));
		
		boolean prevOperator = false;


		for (int i = 0 ; i < splitInput.length ; i ++)
		{
			if (!splitInput [i].equals(""))
			{
				if(splitInput [i].matches ("[-+]?\\d+(\\.\\d+)?")) //if it's a number, add it to the numbers arrayList
				{
					numbers.add( Double.parseDouble(splitInput [i]));
					prevOperator= false;
				} 
				else //if not, add it to the operators arrayList
				{
					if (!prevOperator)//checks if a previous operator exists right in front of this one without a number in between
						//if an operator does exist, before this one, ignore this operator
						operators.add(splitInput[i]);
					prevOperator = true;
				}
			}
		}
		while(operators.contains("^"))
		{
		
			for (int i = operators.size() -1 ; i >=0 ; i --) //goes through the operators backwards because exponents are the only operations which are RIGHT ASSOCIATIVE, meaning 2^3^4 = 2 ^(3 ^4) 
			{
				if (operators.get(i).contains("^")) //if the exponent sign exists within the operators list, do calculations 
				{       // the base to which the power will be applied to is numbers.get (i)

					numbers.set(i,Math.pow(numbers.get(i),numbers.get(i+1))); //changes the base to base^power
					numbers.remove (i+1); //removes the power since it has already been applied to the base
					operators.remove(i);//removes the operator that was just applied
				}
			}
		}


		while (operators.contains("*") || operators.contains("/"))
		{
			

			for (int i = 0 ; i < operators.size() ; i ++) //goes through the operators and tries to find the multiplication sign from left to right since all operations except for ^ are LEFT ASSOCIATIVE, meaning 2+4+5 = (2+4)+5
			{
				if (operators.get(i).contains("*"))
				{
					numbers.set(i, numbers.get(i) * numbers.get(i+1)); //sets numbers[i] to itself times the number succeeding it
					numbers.remove(i+1);//removes the number succeeding numbers [i] since the operation is finished
					operators.remove(i);//removes the operator that was just applied
				}
			}
			//NOTE: all the operations below work the same way as this one, except with different symbols
			for (int i = 0 ; i < operators.size() ; i ++)
			{
				if (operators.get(i).contains("/"))//goes through the operators and tries to find the division sign
				{
					numbers.set(i, numbers.get(i) / numbers.get(i+1));
					numbers.remove(i+1);
					operators.remove(i);
				}
			}
		}
		while(operators.contains("+") )
		{
		

			for (int i = 0 ; i < operators.size() ; i ++)
			{
				if (operators.get(i).contains("+"))//goes through the operators and tries to find the addition sign
				{
					numbers.set(i, numbers.get(i) + numbers.get(i+1));
					numbers.remove(i+1);
					operators.remove(i);
				}
			}
		
		}

		return numbers.get(0); //all the operators should be used up at this point

	}

}
