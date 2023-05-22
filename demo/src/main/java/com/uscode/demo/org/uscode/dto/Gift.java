package com.uscode.demo.org.uscode.dto;

import java.time.LocalDateTime;

public class Gift {
    private Long id;
    private String name;
    private Double price;
    private LocalDateTime createdDate;

    public Gift() {
        super();
    }

    public Gift(Long id, String name, Double price) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Gift(Long id, String name, Double price, LocalDateTime createdDate) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Gift{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", createdDate=" + createdDate +
                '}';
    }

}
