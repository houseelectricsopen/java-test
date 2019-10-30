package fordtest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Grocery {

public void process() throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in)));
    String line;
    for (;null!=(line = br.readLine());) {
        System.out.println("read " + line);
    }
}

}
