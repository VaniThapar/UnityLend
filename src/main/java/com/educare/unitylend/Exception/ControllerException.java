package com.educare.unitylend.Exception;

/**
 * Exception to be thrown at controller level methods
 */
public class ControllerException extends Exception{
    public ControllerException(String message, Throwable cause){
        super(message, cause);
    }
}
