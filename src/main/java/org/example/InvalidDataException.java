package org.example;

public class InvalidDataException extends RuntimeException{
    InvalidDataException(String message){
        super(message);
    }
}
