package com.shopping.electroshopping.service.productService;

import com.shopping.electroshopping.dto.ProductDto;
import com.shopping.electroshopping.model.product.Product;

public interface ProductService {
    Product addProduct(ProductDto productDto);
    public void deleteProductById(long id);

    void editproduct (Long id, ProductDto productDto);

    Product saveProduct(Product product);
}
