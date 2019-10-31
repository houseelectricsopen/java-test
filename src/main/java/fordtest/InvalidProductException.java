package fordtest;

public class InvalidProductException extends Exception{
    public InvalidProductException(String product) {
        super("invalid product " + product);
    }
}
