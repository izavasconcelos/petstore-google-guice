package com.izavasconcelos.cloud.tema02.exceptions;

public class EmptyOrNullFieldException extends RuntimeException {
    public EmptyOrNullFieldException(String e) {
        super(e);
    }
}
