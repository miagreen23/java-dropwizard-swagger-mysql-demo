package org.kainos.ea.client;

public class FailedToCreateOrderException extends Throwable {
    @Override
    public String getMessage() {
        return "Unable to create new order";
    }
}
