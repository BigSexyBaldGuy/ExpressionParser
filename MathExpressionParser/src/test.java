import expressions.Parse;
import java.text.*;
import java.lang.*;

public class test {
    public test() throws ParseException {


    }

    public static void main(String[] args) throws ParseException {
        String[] testParse = Parse.parse("123");

        System.out.println(testParse.toString());
    }
}
