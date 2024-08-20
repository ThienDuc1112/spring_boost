package com.example.springboot.demo.models;

import jakarta.persistence.*;

import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "tblProduct")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false,unique = true,length = 300)
    private String productName;
    private int productYear;
    private Double price;
    private String url;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Image> images;

    // Add a default constructor
    public Product() {}

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    //not add to database
    @Transient
    private int age;
    public int getAge() {
        return Calendar.getInstance().get(Calendar.YEAR) - productYear;
    }

    public Product( String productName, int productYear, Double price, String url) {

        this.productName = productName;
        this.productYear = productYear;
        this.price = price;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName=" + productName + '\'' +
                ", year=" + productYear +
                ", price=" + price +
                ", url=" + url + '\'' +
                "}";

    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductYear() {
        return productYear;
    }

    public void setProductYear(int productYear) {
        this.productYear = productYear;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
