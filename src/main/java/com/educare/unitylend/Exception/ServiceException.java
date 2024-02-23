package com.educare.unitylend.Exception;

/**
 * Exception thrown from Service level methods
 */
public class ServiceException extends Exception{
    public ServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
