package com.bluedot.stock_manager.product.service;

import com.bluedot.stock_manager.audit.model.AuditLogEvent;
import com.bluedot.stock_manager.product.dto.ProductRequestDTO;
import com.bluedot.stock_manager.product.dto.ProductResponseDTO;
import com.bluedot.stock_manager.product.mapper.ProductMapper;
import com.bluedot.stock_manager.product.model.Product;
import com.bluedot.stock_manager.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

  private final ApplicationEventPublisher eventPublisher;
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public ProductService(
    ApplicationEventPublisher eventPublisher,
    ProductRepository productRepository,
    ProductMapper productMapper
  ) {
    this.eventPublisher = eventPublisher;
    this.productRepository = productRepository;
    this.productMapper = productMapper;
  }

  private Long getLoggedUserId() {
    return (Long) SecurityContextHolder.getContext()
      .getAuthentication()
      .getPrincipal();
  }

  public List<ProductResponseDTO> getAllProducts() {
    return productRepository
      .findAll()
      .stream()
      .map(productMapper::toResponseDTO)
      .collect(Collectors.toList());
  }

  public ProductResponseDTO getProductById(Long id) {
    Product product = productRepository
      .findById(id)
      .orElseThrow(() ->
        new EntityNotFoundException("Product not found with id: " + id)
      );

    return productMapper.toResponseDTO(product);
  }

  @Transactional
  public ProductResponseDTO createProduct(ProductRequestDTO dto) {
    if (productRepository.findBySku(dto.getSku()).isPresent()) {
      throw new IllegalArgumentException(
        "Product with SKU already exists: " + dto.getSku()
      );
    }

    Product product = productMapper.toEntity(dto);

    Product productSaved = productRepository.save(product);

    ProductResponseDTO nextProductDTO = productMapper.toResponseDTO(
      productSaved
    );
    eventPublisher.publishEvent(
      new AuditLogEvent(getLoggedUserId(), "CREATE", null, nextProductDTO)
    );

    return productMapper.toResponseDTO(productSaved);
  }

  @Transactional
  public ProductResponseDTO updateProduct(ProductRequestDTO productDTO) {
    Product productFound = productRepository
      .findById(productDTO.getId())
      .orElseThrow(() ->
        new EntityNotFoundException(
          "Product not found with id: " + productDTO.getId()
        )
      );

    Product productUpdated = productMapper.updateEntity(
      productFound,
      productDTO
    );

    Product productSaved = productRepository.save(productUpdated);

    ProductResponseDTO prevProductDTO = productMapper.toResponseDTO(
      productFound
    );
    ProductResponseDTO nextProductDTO = productMapper.toResponseDTO(
      productSaved
    );

    eventPublisher.publishEvent(
      new AuditLogEvent(
        getLoggedUserId(),
        "UPDATE",
        prevProductDTO,
        nextProductDTO
      )
    );

    return productMapper.toResponseDTO(productSaved);
  }

  @Transactional
  public void deleteProduct(Long id) {
    Product product = productRepository
      .findById(id)
      .orElseThrow(() ->
        new EntityNotFoundException("Product not found with id: " + id)
      );

    productRepository.delete(product);

    ProductResponseDTO dto = productMapper.toResponseDTO(product);
    eventPublisher.publishEvent(
      new AuditLogEvent(getLoggedUserId(), "DELETE", dto, null)
    );
  }
}
