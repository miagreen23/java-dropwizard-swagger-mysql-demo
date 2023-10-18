package org.kainos.ea.api;

import org.kainos.ea.cli.Product;
import org.kainos.ea.cli.ProductRequest;
import org.kainos.ea.client.*;
import org.kainos.ea.core.ProductValidator;
import org.kainos.ea.db.ProductDao;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ProductService {
    private ProductDao productDao = new ProductDao();
    private ProductValidator productValidator = new ProductValidator();
    public List<Product> getAllProducts() throws FailedToGetProductsException {
        try {
            List<Product> productList = productDao.getAllProducts();
            return productList;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetProductsException();
        }

//        List<Product> cheapProducts = productList.stream().filter(product -> product.getPrice() < 10).collect(Collectors.toList());
//
//        cheapProducts.forEach(System.out::println);


    }

    public Product getProductById(int id) throws FailedToGetProductsException, ProductDoesNotExistException {
        try {
            Product product = productDao.getProductById(id);

            if (product == null) {
                throw new ProductDoesNotExistException();
            }
            return product;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetProductsException();
        }
    }

    public int createProduct(ProductRequest product) throws FailedToCreateProductException, InvalidProductException {
        try {
            String validation = productValidator.isValidProduct(product);

            if (validation != null) {
                throw new InvalidProductException(validation);
            }

            int id = productDao.createProduct(product);

            if (id == -1) {
                throw new FailedToCreateProductException();
            }
            return id;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToCreateProductException();
        }
    }

    public void updateProduct(int id, ProductRequest product) throws InvalidProductException, ProductDoesNotExistException, FailedToUpdateProductException {
        try {
            String validation = productValidator.isValidProduct(product);

            if (validation != null) {
                throw new InvalidProductException(validation);
            }

            Product productToUpdate = productDao.getProductById(id);

            if (productToUpdate == null) {
                throw new ProductDoesNotExistException();
            }

            productDao.updateProduct(id, product);
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToUpdateProductException();
        }
    }

    public void deleteProduct(int id) throws ProductDoesNotExistException, FailedToDeleteProductException {
        try {
            Product productToDelete = productDao.getProductById(id);

            if (productToDelete == null) {
                throw new ProductDoesNotExistException();
            }

            productDao.deleteProduct(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToDeleteProductException();
        }
    }
}
