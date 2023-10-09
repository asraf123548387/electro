//package com.shopping.electroshopping.service.ProductOfferService;
//
////import com.shopping.electroshopping.dto.ProductOfferDTO;
//import com.shopping.electroshopping.model.product.Product;
//import com.shopping.electroshopping.model.productOffer.ProductOffer;
//import com.shopping.electroshopping.repository.ProductOfferRepository;
//import com.shopping.electroshopping.repository.ProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//
//@Service
//public class ProductOfferServiceImpl implements ProductOfferService{
//    @Autowired
//    ProductRepository productRepository;
//    @Autowired
//    ProductOfferRepository productOfferRepository;
//
//    @Override
//    public ProductOffer addProductOffer(ProductOffer productOffer) {
//
//        productOffer.setDiscountAmount(productOffer.getDiscountAmount());
//        productOffer.setCreateDate(productOffer.getCreateDate());
//        productOffer.setExpirationDate(productOffer.getExpirationDate());
//        if (productOffer.getProduct() == null) {
//            throw new IllegalArgumentException("Product is required.");
//        }
//
//        // Retrieve the associated product by its ID from the database
//        Product product = productRepository.findById(productOffer.getProduct().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productOffer.getProduct().getId()));
//
//        // Set the product association
//        productOffer.setProduct(product);
//
//        return productOfferRepository.save(productOffer);
//    }
//
//    public void editProductOffer(Long id, ProductOffer productOffer) {
//        ProductOffer productOffer1=productOfferRepository.findById(id).orElse(null);
//        if(productOffer1!=null)
//        {
//            productOffer1.setExpirationDate(productOffer.getExpirationDate());
//            productOffer1.setDiscountAmount(productOffer.getDiscountAmount());
//            productOffer1.setCreateDate(productOffer.getCreateDate());
//
//        }
//        productOfferRepository.save(productOffer1);
//    }
//}
