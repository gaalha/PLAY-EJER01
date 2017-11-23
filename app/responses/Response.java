package responses;

import java.io.Serializable;

/**
 * Created by educacion on 17/11/2017.
 */
public class Response<T> implements Serializable {
    public boolean success;
    public String message;
    public T data;

    public Response(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
