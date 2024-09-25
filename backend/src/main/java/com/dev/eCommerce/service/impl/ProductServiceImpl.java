package com.dev.eCommerce.service.impl;

import com.dev.eCommerce.dto.ProductDto;
import com.dev.eCommerce.dto.Response;
import com.dev.eCommerce.entity.Category;
import com.dev.eCommerce.entity.Product;
import com.dev.eCommerce.exception.NotFoundException;
import com.dev.eCommerce.mapper.EntityDtoMapper;
import com.dev.eCommerce.repository.CategoryRepo;
import com.dev.eCommerce.repository.ProductRepo;
import com.dev.eCommerce.service.AwsS3Service;
import com.dev.eCommerce.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private  final CategoryRepo categoryRepo;
    private final EntityDtoMapper entityDtoMapper;
    private final AwsS3Service awsS3Service;

    @Override
    public Response createProduct(Long categoryId, MultipartFile image, String name, String description, BigDecimal price) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->new NotFoundException("Category Not Found."));
        String productImageUrl = awsS3Service.saveImageToS3(image);

        Product product = new Product();
        product.setCategory(category);
        product.setPrice(price);
        product.setName(name);
        product.setDescription(description);
        product.setImageUrl(productImageUrl);

        productRepo.save(product);
        return Response.builder()
                .status(200)
                .message("Product successfully created.")
                .build();
    }

    @Override
    public Response updateProduct(Long productId, Long categoryId, MultipartFile image, String name, String description, BigDecimal price) {
        Product product = productRepo.findById(productId).orElseThrow(()->new NotFoundException("Product not found."));

        Category category = null;
        String productImageUrl = null;

        if(category !=null){
             category = categoryRepo.findById(categoryId).orElseThrow(()->new NotFoundException("Category Not Found."));
        }
        if (image != null && !image.isEmpty()) {
            productImageUrl = awsS3Service.saveImageToS3(image);
        }

        if (category != null ) {product.setCategory(category);}
        if (name != null ) {product.setName(name);}
        if (price != null ) {product.setPrice(price);}
        if (description != null ) {product.setDescription(description);}
        if (productImageUrl != null ) {product.setImageUrl(productImageUrl);}

        productRepo.save(product);

        return Response.builder()
                .status(200)
                .message("Product updated successfully.")
                .build();
    }

    @Override
    public Response deleteProduct(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(()->new NotFoundException("Product not found."));

        productRepo.delete(product);

        return Response.builder()
                .status(200)
                .message("Product deleted successfully.")
                .build();
    }

    @Override
    public Response getProductById(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(()->new NotFoundException("Product not found."));
        ProductDto productDto = entityDtoMapper.mapProductToDtoBasic(product);

        return Response.builder()
                .status(200)
                .product(productDto)
                .build();
    }

    @Override
    public Response getAllProducts() {
        List<ProductDto> productList = productRepo.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productList)
                .build();
    }

    @Override
    public Response getProductsByCategory(Long categoryId) {
        List<Product> products = productRepo.findByCategoryId(categoryId);

        if (products.isEmpty()) {
            throw new NotFoundException("Product Not found for this category.");
        }
        List<ProductDto> productList = products.stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productList)
                .build();
    }

    @Override
    public Response searchProduct(String searchValue) {
        List<Product> products = productRepo.findByNameOrDescriptionContaining(searchValue, searchValue);

        if (products.isEmpty()) {
            throw new NotFoundException("No Products Found.");
        }
        List<ProductDto> productList= products.stream()
                .map(entityDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productList)
                .build();
    }
}
