package com.codeforinca.bytsoftapi.exceptions;

public class ForbidenException
        extends RuntimeException
{
    public ForbidenException(String message) {
        super(message);
    }

    public ForbidenException(String message, Throwable cause) {
        super(message, cause);
    }

}
