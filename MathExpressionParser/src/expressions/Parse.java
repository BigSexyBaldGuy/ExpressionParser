package expressions;

import java.text.*;
import java.util.Arrays;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

// This class is an arithmetic expression parser that accepts a string
// parameter as input. It parses the arithmetic expression and returns
// the results as a string array. If a parsing error occurs, a
// ParseException is thrown.The main purpose of this class being
// declared as final is to prevent this class from being subclassed.
// If a class is marked as final, then no class can inherit any
// feature from the final class. You cannot extend a final class.
public final class Parse {

	// We want to prevent the creation of an instance of this class in
	// any place other than within this class, so we will make our
	// constructor private.
   private Parse() {
   }

	public static String[] parse(String expression) throws ParseException {
		int splitIndex;
		int tokenIndex = 0;
		String[] tokenArray;
		String[] splitArray;
		String splitArrayItem = "";
		String[] resizedTokenArray;
		char expressionCharacter;
		String expressionValue = "";

		if (expression == null || expression.length() == 0){
			throw new ParseException("Input expression is empty " +
					                 "or null. ", 0);
		}

		// We will not know how long the results array needs to be
		// until we have finished our parsing. Since we need to have
		// an initial size, we will set it to the length of the
		// passed expression.
		tokenArray = new String[expression.length()];

		// Since the input expression could have multiple characters
		// in succession (Ex: 123, a2, ++, etc), it will make it
		// easier to parse the character(s) if we create a new array
		// that splits the input expression on spaces before we start
		// the actual parsing.
		splitArray = expression.split(" ");

		for (splitIndex = 0; splitIndex < splitArray.length;
			 splitIndex++) {
			if (splitArray[splitIndex].length() == 1) {
				tokenArray[tokenIndex] = splitArray[splitIndex];
				tokenIndex++;
			} else {
				splitArrayItem = splitArray[splitIndex];
				// loop through splitArrayItem
				for (int splitArrayItemIndex = 0;
					 splitArrayItemIndex < splitArrayItem.length();
					 splitArrayItemIndex++) {
					 expressionCharacter =
					 splitArrayItem.charAt(splitArrayItemIndex);
					 if (!isDigit(expressionCharacter) &&
				   	 	 !isLetter(expressionCharacter)) {
						 if (expressionValue.length() > 0) {
							tokenArray[tokenIndex] = expressionValue;
							tokenIndex++;
							expressionValue = "";
						}
						tokenArray[tokenIndex] =
						String.valueOf(expressionCharacter);
						tokenIndex++;
					} else {
						expressionValue +=
						String.valueOf(expressionCharacter);
					}
				}
				if (expressionValue.length() > 0) {
					// Only letters and digits can be combined, so if
					// any other non-alphanumeric characters are
					// consecutive, separate them. Ex: ++ would be
					// treated as one + then another +.

					// If a character set within the splitArray
					// contains an alphabetic character, we need to
					// make sure the character set starts with an
					// alphabetic character. If it starts with a
					// number, it is invalid we throw an error.
					if (checkForAlphaCharacters(expressionValue)) {
						expressionCharacter =
						expressionValue.charAt(0);
						if (isDigit(expressionCharacter)) {
							throw new ParseException("A character " +
									"set within the input " +
									"expression contains an " +
									"alphabetic character. \nIf a " +
									"character set contains an " +
									"alphabetic character, then " +
									"the character set MUST begin " +
									"with an alphabetic character. " +
									"\nThis character set " +
									"is invalid - " +
									splitArray[splitIndex],
									splitIndex);
						}
					}
					tokenArray[tokenIndex] = expressionValue;
					tokenIndex++;
					expressionValue = "";
				}
			}
		}
		// Since our tokenArray was set to the length of the input
		// expression, we need to resize it since our tokenIndex
		// length is smaller due to the parsing we performed.
		resizedTokenArray = Arrays.copyOf(tokenArray, tokenIndex);
		return resizedTokenArray;
	}

   // Created this method to loop through all the characters of
   // a string provided and return true if any alphabetic characters
   // are found.
   private static boolean checkForAlphaCharacters(String valueToCheck){
	   char c;

	   if (valueToCheck == null || valueToCheck.length() == 0) {
		   return false;
	   }

	   for (int checkAlphabeticIndex = 0;
			    checkAlphabeticIndex < valueToCheck.length();
			    checkAlphabeticIndex++) {
		   c = valueToCheck.charAt(checkAlphabeticIndex);
		   if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
			   return true;
		   }
	   }
	   return false;
   }

    // This is our unit test area. We have an array of expressions
	// that can be changed or added to as we make changes to ensure
	// they all still work correctly.  To add another expression
	// to test, you will need to add the expression to the end of the
	// unitTestExpression array below and add a new unitTestResults(x)
	// with the expected results and add a new switch value to test
	// the expected results to the actual results.
	public static void main(String[] args) {
	   int resultsIndex;
	   int testIndex;
	   String[] results;

	   String[] testExpressions = {"1 + 5 + (.b5+",
			                       "2 + 4 + b4",
			                       "b2bt + 4 + b4",
			                       "($@+ + 4 + b4",
			   	                   "2 + 4b + 4"};

	   String[] expectedResults0 = {"1","+","5","+","(",".","b5","+"};
	   String[] expectedResults1 = {"2","+","4","+","b4"};
	   String[] expectedResults2 = {"b2bt","+","4","+","b4"};
	   String[] expectedResults3 = {"(","$","@","+","+","4","+","b4"};
	   // expectedResults4 should fail with a ParseException
	   String[] expectedResults4 = {"2","+","4b","+","4"};

	   Parse parseTest = new Parse();

		try{
			for (testIndex = 0;
				 testIndex < testExpressions.length;
				 testIndex++) {

				System.out.println("===============================");
				System.out.println("Testing expression: " +
						           testExpressions[testIndex]);
				System.out.println("===============================");

				results = parseTest.parse(testExpressions[testIndex]);
				for (resultsIndex = 0;
					 resultsIndex < results.length;
					 resultsIndex++) {
					 System.out.println(results[resultsIndex]);
				}

				switch(testIndex) {
					case 0:
						if (Arrays.equals(results,expectedResults0))
							System.out.println("PASSED!");
						else
							System.out.println("*** FAILED! ***");
						break;
					case 1:
						if (Arrays.equals(results,expectedResults1))
							System.out.println("PASSED!");
						else
							System.out.println("*** FAILED! ***");
						break;
					case 2:
						if (Arrays.equals(results,expectedResults2))
							System.out.println("PASSED!");
						else
							System.out.println("*** FAILED! ***");
						break;
					case 3:
						if (Arrays.equals(results,expectedResults3))
							System.out.println("PASSED!");
						else
							System.out.println("*** FAILED! ***");
						break;
					case 4:
						// Unit test 4 should not be equal. The parse
						// exception should catch it, but if something
						// happens and the exception does not catch it
						// we need to make sure we alert the developer
						// that the test failed.
						if (Arrays.equals(results,expectedResults4))
							System.out.println("*** FAILED! ***");
						else
							System.out.println("PASSED!");
						break;
					}
			}
		} catch (ParseException ex) {
			System.out.print(ex.getMessage() + " Error Offset = " +
					         ex.getErrorOffset());
		}
	}
}
