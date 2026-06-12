package com.bluedot.stock_manager.product.controller;

import com.bluedot.stock_manager.config.ApiResponse;
import com.bluedot.stock_manager.product.dto.ProductRequestDTO;
import com.bluedot.stock_manager.product.dto.ProductResponseDTO;
import com.bluedot.stock_manager.product.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/all")
  public ResponseEntity<
    ApiResponse<List<ProductResponseDTO>>
  > getAllProducts() {
    List<ProductResponseDTO> responseDTO = productService.getAllProducts();

    return ResponseEntity.ok(
      ApiResponse.<List<ProductResponseDTO>>builder()
        .success(true)
        .message("Products retrieved successfully")
        .data(responseDTO)
        .status(HttpStatus.OK.value())
        .build()
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ProductResponseDTO>> getProductById(
    @PathVariable Long id
  ) {
    ProductResponseDTO responseDTO = productService.getProductById(id);

    return ResponseEntity.ok(
      ApiResponse.<ProductResponseDTO>builder()
        .success(true)
        .message("Product retrieved successfully")
        .data(responseDTO)
        .status(HttpStatus.OK.value())
        .build()
    );
  }

  @PostMapping
  public ResponseEntity<ApiResponse<ProductResponseDTO>> create(
    @Valid @RequestBody ProductRequestDTO request
  ) {
    ProductResponseDTO responseDTO = productService.createProduct(request);

    return ResponseEntity.ok(
      ApiResponse.<ProductResponseDTO>builder()
        .success(true)
        .message("Product created successfully")
        .data(responseDTO)
        .status(HttpStatus.CREATED.value())
        .build()
    );
  }

  @PutMapping
  public ResponseEntity<ApiResponse<ProductResponseDTO>> update(
    @Valid @RequestBody ProductRequestDTO request
  ) {
    ProductResponseDTO responseDTO = productService.updateProduct(request);
    return ResponseEntity.ok(
      ApiResponse.<ProductResponseDTO>builder()
        .success(true)
        .message("Product updated successfully")
        .data(responseDTO)
        .status(HttpStatus.OK.value())
        .build()
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    productService.deleteProduct(id);

    return ResponseEntity.status(HttpStatus.OK).body(
      ApiResponse.<Void>builder()
        .success(true)
        .message("Product deleted successfully")
        .data(null)
        .status(HttpStatus.OK.value())
        .build()
    );
  }
}
