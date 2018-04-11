package com.springbootjpa.service;

import com.springbootjpa.domain.Category;
import com.springbootjpa.domain.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CategoryService {
  @Autowired
  private CategoryRepository categoryRepository;

  public void save(Category category) {
    categoryRepository.save(category);
  }

  public Optional<Category> findById(Integer id) {
    return categoryRepository.findById(id);
  }
}
