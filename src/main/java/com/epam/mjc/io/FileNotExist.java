package com.epam.mjc.io;

public class FileNotExist extends RuntimeException{
    public FileNotExist(Throwable cause){
        super(cause);
    }
}
