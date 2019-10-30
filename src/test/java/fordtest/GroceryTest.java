package fordtest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.junit.Test;

public class GroceryTest {

    @Test
    public void test() throws IOException {
        PrintStream initialOut = System.out;
        InputStream initialIn = System.in;
        ByteArrayOutputStream inputBufferStream = new ByteArrayOutputStream();
        PrintStream inputPrintStream = new PrintStream(inputBufferStream);

        inputPrintStream.println("add soup");
        inputPrintStream.println("add soup");
        inputPrintStream.println("add soup");

        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputBufferStream.toByteArray());
        System.setIn(inputStream);
        ByteArrayOutputStream outputBufferStream = new ByteArrayOutputStream();
        PrintStream outputPrintStream = new PrintStream(outputBufferStream);
        System.setOut(outputPrintStream);
        inputPrintStream.flush();
        (new Grocery()).process();
        System.setOut(initialOut);
        System.setIn(initialIn);
        System.out.println("output:" + new String(outputBufferStream.toByteArray()));


    }

}
