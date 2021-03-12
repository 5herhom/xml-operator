package cn.com.sherhom.xml.operator.common.exceptions;

/**
 * @author Sherhom
 * @date 2021/3/12 11:04
 */
public class SherhomException extends RuntimeException {
    public SherhomException() {
    }

    public SherhomException(String message) {
        super(message);
    }

    public SherhomException(String message, Throwable cause) {
        super(message, cause);
    }

    public SherhomException(Throwable cause) {
        super(cause);
    }

    public SherhomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
