package org.kainos.ea.client;

public class FailedToUpdateProductException extends Throwable {
    @Override
    public String getMessage() {
        return "Unable to update product";
    }
}
