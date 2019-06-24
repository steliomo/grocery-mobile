package mz.co.commandline.grocery.product.service;

import java.util.List;

import mz.co.commandline.grocery.listner.ResponseListner;
import mz.co.commandline.grocery.product.model.Product;

public interface ProductService {

    void findAllProducts(ResponseListner<List<Product>> listner);
}
