package mz.co.commandline.grocery.product.delegate;

import java.util.List;

import mz.co.commandline.grocery.product.dto.ProductDTO;

public interface ProductDelegate {

    void selectProduct();

    List<ProductDTO> getProductsDTO();

    void selectedProduct(ProductDTO productDTO);
}
