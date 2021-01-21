package com.nguyen.demo.product;

import com.nguyen.demo.authentication.IsAdmin;
import com.nguyen.demo.request.PageResponse;
import com.nguyen.demo.request.PageResponseBuilder;
import com.nguyen.demo.request.PagingAndSorting;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.nguyen.demo.request.RequestConstants.QUERY;

/**
 * Controller to login users.
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @IsAdmin
    public ProductDto create(@Valid @RequestBody CreateProductReq createProductReq) {
        return productService.create(createProductReq);
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ProductDto update(@PathVariable("id") Long id,
                             UpdateProductReq updateProductReq) {
        return productService.update(id, updateProductReq);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<ProductDto> search(@RequestParam(value = QUERY, required = false) String query,
                                           @PagingAndSorting PageRequest pageRequest) {
        return PageResponseBuilder.build(productService.search(query, pageRequest), ProductMapper.INSTANCE::of);

    }
}
