package com.springbootjpa;

import com.springbootjpa.domain.Category;
import com.springbootjpa.domain.Product;
import com.springbootjpa.service.CategoryService;
import com.springbootjpa.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryProductTest {
  @Autowired
  private CategoryService categoryService;

  @Autowired
  private ProductService productService;

  // step 1:
  @Test
  public void saveCategory() {
    Category category = new Category();
    category.setName("电器");
    category.setDescription("电器:需要电");

    categoryService.save(category);
    // 以上可对商品类别进行 CRUD 操作
  }

  // step 2:
  @Test
  public void saveProduct() {
    Category category = categoryService.findById(6).get();
    Product product = new Product();
    product.setName("美的空调");
    product.setPrice(5200d);
    product.setDescription("省电");
    // 设置商品与商品类别的关系
    product.setCategory(category);

    productService.save(product);
  }

  // step 3:
  @Test
  public void list() {
    for (Product product : productService.findAll()) {
      System.out.println(product.getName() + " " + product.getCategory().getName());
    }
  }
}
