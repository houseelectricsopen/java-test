package fordtest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Test;

public class GroceryTest {



    private int testPriceBasket(Consumer<PrintStream> commandLineInputer) throws IOException {
        PrintStream initialOut = System.out;
        InputStream initialIn = System.in;
        ByteArrayOutputStream inputBufferStream = new ByteArrayOutputStream();
        PrintStream inputPrintStream = new PrintStream(inputBufferStream);

        commandLineInputer.accept(inputPrintStream);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputBufferStream.toByteArray());
        System.setIn(inputStream);
        ByteArrayOutputStream outputBufferStream = new ByteArrayOutputStream();
        PrintStream outputPrintStream = new PrintStream(outputBufferStream);
        System.setOut(outputPrintStream);
        inputPrintStream.flush();
        System.setOut(initialOut);
        System.setIn(initialIn);
        return Integer.valueOf(new String(outputBufferStream.toByteArray()));
    }

    @Test
    public void test1() throws IOException {
        int price = testPriceBasket(
                inputPrintStream -> {
                    inputPrintStream.println("add soup");
                    inputPrintStream.println("add soup");
                    inputPrintStream.println("add soup");
                    inputPrintStream.println("add loaf");
                    inputPrintStream.println("add loaf");
                    inputPrintStream.println("price " + LocalDate.now());
                }
        );
        Assert.assertEquals(price, 315);
    }


}
