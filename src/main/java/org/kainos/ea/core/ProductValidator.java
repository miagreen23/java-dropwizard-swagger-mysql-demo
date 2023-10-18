package org.kainos.ea.core;

import org.kainos.ea.cli.ProductRequest;

public class ProductValidator {
    public String isValidProduct(ProductRequest product) {
        if (product.getName().length() > 50) {
            return "Name is too long, must be shorter than 50 characters";
        }

        if (product.getDescription().length() > 500) {
            return "Description is too long, must be shorter than 500 characters";
        }

        if (product.getPrice() < 10) {
            return "Price too low, must be greater than 10";
        }
        return null;
    }
}
