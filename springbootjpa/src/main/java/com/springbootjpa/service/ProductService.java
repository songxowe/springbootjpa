package com.springbootjpa.service;

import com.springbootjpa.domain.Product;
import com.springbootjpa.domain.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {
  @Autowired
  private ProductRepository productRepository;

  public void save(Product product) {
    productRepository.save(product);
  }

  public List<Product> findAll() {
    return productRepository.findAll();
  }
}
