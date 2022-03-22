package com.company.exceptions;

public class FileErrorException extends RuntimeException{
    public FileErrorException(String message) {
        super(message);
    }
}
