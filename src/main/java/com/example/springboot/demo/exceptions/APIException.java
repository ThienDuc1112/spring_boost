package com.example.springboot.demo.exceptions;

public class APIException extends Exception{
    private static final long serialVersionUID = 1L;

    public  APIException(){}

    public APIException(String message){
        super(message);
    }
}
