package com.epam.mjc.io;

public class FileFormatException extends RuntimeException {
    public FileFormatException(String message) {
        super(message);
    }
    public FileFormatException(Throwable cause){
        super(cause);
    }

    public FileFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
