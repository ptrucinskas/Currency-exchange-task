/*
    This program converts the given amount of currency and prints the result.

    3 arguments required:
        1) amount of currency to exchange
        2) currency name to exchange from
        3) currency name to exchange into

    For currency exhange rates it uses provided "data.csv" file.
 */

import java.math.BigDecimal;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CurrencyExchange {

    public static void main(String[] args){
        try {
            // Exceptions for illegal provided program arguments.
            if (args.length != 3)
                throw new IllegalArgumentException("There should be 3 arguments:" +
                        " amount to exchange, currency name to exchange from and" +
                        " name to exchange into.");

            System.out.println(convert(args[0], args[1], args[2]));
        } // try
        catch (NumberFormatException exception){
            System.err.println("The inputed amount was illegal.");
        } // catch
        catch (IllegalArgumentException exception) {
            System.err.println("The input was illegal.");
            System.err.println(exception.getMessage());
        } // catch
        catch(IOException exception){
            System.err.println("The data file reading failed.");
            System.err.println(exception.getMessage());
        } // catch
        catch (Exception exception){
            System.err.println("There was an error.");
            System.err.println(exception);
            System.err.println(exception.getMessage());
        } // catch
    } // main()


    // This method returns the converted given amount of currency as a String.
    // 3 arguments required:
    //   1) amount of currency to exchange
    //   2) currency name to exchange from
    //   3) currency name to exchange into
    //For currency exchange rates it uses provided "data.csv" file.
    public static String convert(String _amount, String _name1, String _name2) throws Exception{
        if(_name1.equals(_name2))
            throw new IllegalArgumentException(_name1 + " into " + _name2
                + " is not a conversion.");

        // Calculating the exchange.
        BigDecimal amount = new BigDecimal(_amount);

        amount = amount.multiply(retrieveRate(_name1, _name2));
        amount = amount.setScale(18, BigDecimal.ROUND_HALF_UP);
        amount = amount.stripTrailingZeros();

        // Returning the result.
        return amount.toString();
    } // convert



    // Method that returns the exchange rate of BigDecimal type.
    // Given parameters:
    // name of currency to convert from,
    // name of currency to convert to.
    // Reads data from given "data.csv" file.
    private static BigDecimal retrieveRate(String start, String end) throws Exception{

        // The rate that will be returned.
        BigDecimal rate = new BigDecimal(1);

        // Opening the given data file.
        BufferedReader input = new BufferedReader(new FileReader("data.csv"));
        // Local variables to read data.
        String currentLine;
        String[] splitLine;
        // Boolean variables to check whether both required currencies were found.
        Boolean startFound = false;
        Boolean endFound = false;
        // Reading lines from file and checking if relevant to required conversion.
        // Looping if next line exists and both currencies have not been found.
        while((currentLine = input.readLine()) != null && !(startFound && endFound)){
            // Split line into name and number.
            splitLine = currentLine.split(",");
            // If line is not split into required parts break the loop.
            if(splitLine.length != 2)
                break;

            // Checking
            //   if it is the starting currency
            //   if it is the ending currency
            if(!startFound && splitLine[0].equals(start)){
                // Multiply by the rate of the starting currency.
                rate = rate.multiply(new BigDecimal(splitLine[1])).setScale(20, BigDecimal.ROUND_HALF_UP);
                startFound = true;
            } // if
            else if(!endFound && splitLine[0].equals(end)){
                // Divide by the rate of the ending currency.
                rate = rate.divide(new BigDecimal(splitLine[1]), 20, BigDecimal.ROUND_HALF_UP);
                endFound = true;
            } // else if
        } // while

        // Throw exception if one of the exchange rates was not found.
        if(!startFound || !endFound)
            throw new IOException("One or both currency names were not found.");

        // Close input file.
        input.close();

        // Returning what was read.
        return rate;
    } // retrieveRate()
} // CurrencyExchange
