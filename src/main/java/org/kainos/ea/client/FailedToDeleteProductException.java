package org.kainos.ea.client;

public class FailedToDeleteProductException extends Throwable {
    @Override
    public String getMessage() {
        return "Unable to delete product";
    }
}
