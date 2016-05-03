import java.io.Serializable;

/**
 * Created by madsbjoern on 11/04/16.
 */
public class NegativeTimeException extends Exception implements Serializable {
    public NegativeTimeException(String message) {
        super(message);
    }
}
