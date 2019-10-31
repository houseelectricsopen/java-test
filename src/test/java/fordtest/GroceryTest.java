package fordtest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Test;

public class GroceryTest {

    @Test
    public void test1() throws IOException, InvalidProductException {
        int price = testPriceBasket(
                inputPrintStream -> {
                    inputPrintStream.println("add soup");
                    inputPrintStream.println("add soup");
                    inputPrintStream.println("add soup");
                    inputPrintStream.println("add bread");
                    inputPrintStream.println("add bread");
                    inputPrintStream.println("price " + LocalDate.now());
                }
        );
        Assert.assertEquals(315, price);
    }

    @Test
    public void test2() throws IOException, InvalidProductException {
        int price = testPriceBasket(
                inputPrintStream -> {
                    inputPrintStream.println("add apple");
                    inputPrintStream.println("add apple");
                    inputPrintStream.println("add apple");
                    inputPrintStream.println("add apple");
                    inputPrintStream.println("add apple");
                    inputPrintStream.println("add apple");
                    inputPrintStream.println("add milk");
                    inputPrintStream.println("price " + LocalDate.now());
                }
        );
        Assert.assertEquals(190, price);
    }

    @Test
    public void test3() throws IOException, InvalidProductException {
        int price = testPriceBasket(
                inputPrintStream -> {
                    inputPrintStream.println("add apple");
                    inputPrintStream.println("add apple");
                    inputPrintStream.println("add apple");
                    inputPrintStream.println("add apple");
                    inputPrintStream.println("add apple");
                    inputPrintStream.println("add apple");
                    inputPrintStream.println("add milk");
                    inputPrintStream.println("price " + LocalDate.now().plusDays(5));
                }
        );
        Assert.assertEquals(184, price);
    }


    @Test
    public void test4() throws IOException, InvalidProductException {
        int price = testPriceBasket(
                inputPrintStream -> {
                    inputPrintStream.println("add apple");
                    inputPrintStream.println("add apple");
                    inputPrintStream.println("add apple");
                    inputPrintStream.println("add soup");
                    inputPrintStream.println("add soup");
                    inputPrintStream.println("add bread");
                    inputPrintStream.println("price " + LocalDate.now().plusDays(5));
                }
        );
        Assert.assertEquals(197, price);
    }

    @Test(expected=InvalidProductException.class)
    public void testInvalidProduct() throws IOException, InvalidProductException {
        testPriceBasket(
                inputPrintStream -> {
                    inputPrintStream.println("add tea");
                    inputPrintStream.println("price " + LocalDate.now().plusDays(5));
                }
        );
    }


    private Grocery target = new Grocery();

    private int testPriceBasket(Consumer<PrintStream> commandLineInputer) throws IOException, InvalidProductException {
        PrintStream initialOut = System.out;
        InputStream initialIn = System.in;
        ByteArrayOutputStream inputBufferStream = new ByteArrayOutputStream();
        PrintStream inputPrintStream = new PrintStream(inputBufferStream);

        commandLineInputer.accept(inputPrintStream);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputBufferStream.toByteArray());
        //redirect system in
        System.setIn(inputStream);
        ByteArrayOutputStream outputBufferStream = new ByteArrayOutputStream();
        PrintStream outputPrintStream = new PrintStream(outputBufferStream);
        //redirect system out
        System.setOut(outputPrintStream);
        inputPrintStream.flush();

        target.priceFromSystemIn();

        //restore system in and out
        System.setOut(initialOut);
        System.setIn(initialIn);
        String strPrice = new String(outputBufferStream.toByteArray()).trim();
        return Integer.valueOf(strPrice);
    }

}
