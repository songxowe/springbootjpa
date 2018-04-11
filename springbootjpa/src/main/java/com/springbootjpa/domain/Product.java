package com.springbootjpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT")
// 在表名称与类名称不同时使用,表名与类名相同可以省略
public class Product implements java.io.Serializable {
  private static final long serialVersionUID = -4454713877705834026L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "NAME", nullable = false, length = 200)
  // 字段名与属性名不同时使用，相同时可以省略
  private String name;

  @Column(name = "PRICE")
  private Double price;

  @Column(name = "DESCRIPTION")
  private String description;

  // FetchType.EAGER 及时加载: 不管是否需要都加载关联数据
  // FetchType.LAZY 延迟加载: 当需要关联的数据时则加载;不需要则不加载
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "CATEGORY_ID")
  private Category category;

  public Product() {
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Category getCategory() {
    return this.category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return this.price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
