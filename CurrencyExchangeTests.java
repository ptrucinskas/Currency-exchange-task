import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import java.io.IOException;

public class CurrencyExchangeTests{
    @Test
    public void testConvert1() throws Exception {
        System.out.println("test 1");
        String result = CurrencyExchange.convert("50", "USD","EUR");
        assertEquals("40.4776361", result);
    }

    @Test
    public void testConvert2() throws Exception {
        System.out.println("test 2");
        String result = CurrencyExchange.convert("0.999999999999999999999999", "EUR", "GBP");
        String comparableResult = CurrencyExchange.convert("1", "EUR", "GBP");
        assertEquals(comparableResult, result);
    }

    @Test
    public void testConvert3() throws Exception {
        System.out.println("test 3");
        String result = CurrencyExchange.convert("50", "USD", "BTC");
        // This reference number was calculated using the java BigDecimal methods
        assertEquals("0.005801507231578764", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvert4() throws Exception {
        System.out.println("test 4");
        String result = CurrencyExchange.convert("1", "EUR", "EUR");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvert5() throws Exception {
        System.out.println("test 5");
        String result = CurrencyExchange.convert("EUR", "4", "EUR");
    }

    @Test(expected = IOException.class)
    public void testConvert6() throws Exception {
        System.out.println("test 6");
        String result = CurrencyExchange.convert("5", "Dogecoin", "EUR");
    }
}
