package com.nguyen.demo.product;

import com.nguyen.demo.core.entity.Product;
import com.nguyen.demo.core.repository.ProductRepository;
import com.nguyen.demo.error.DataNotFoundException;
import com.nguyen.demo.rsql.RSQLSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductDto create(CreateProductReq createProductReq) {
        Product product = ProductMapper.INSTANCE.of(createProductReq);
        Product createdProduct = productRepository.save(product);
        return ProductMapper.INSTANCE.of(createdProduct);
    }

    @Transactional
    public ProductDto update(Long id, UpdateProductReq updateProductReq) {
        Product product = getById(id);
        Product updatedProduct = ProductMapper.INSTANCE.of(product, updateProductReq);
        return ProductMapper.INSTANCE.of(updatedProduct);
    }

    @Transactional
    public void delete(Long id) {
        Product product = getById(id);
        productRepository.delete(product);
    }


    public Page<Product> search(String query, PageRequest pageRequest) {
        Specification<Product> specification = RSQLSupport.rsql(query);
        return productRepository.findAll(specification, pageRequest);
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Not found user with id = " + id));
    }
}
