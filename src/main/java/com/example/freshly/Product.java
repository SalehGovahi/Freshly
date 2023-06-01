package com.example.freshly;

import java.sql.Date;

public class Product {
    private final String name;
    private final String brand;
    private int productId;
    private String information;
    private final double price;
    private double point;
    private int numberOfPoints;
    private String type;
    private final String image;
    private String[] comments;
    private Date date;

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public int getProductId() {
        return productId;
    }

    public String getInformation() {
        return information;
    }

    public double getPrice() {
        return price;
    }

    public double getPoint() {
        return point;
    }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public String[] getComments() {
        return comments;
    }

    public Date getDate() {
        return date;
    }

    public Product(String name, String brand, int productId, String information, double price, String group, String image, double point, int numberOfPoints, String[] comments, Date date) {
        this.name = name;
        this.brand = brand;
        this.productId = productId;
        this.information = information;
        this.price = price;
        this.type = group;
        this.image = image;
        this.point = point;
        this.numberOfPoints = numberOfPoints;
        this.comments = comments;
        this.date = date;
    }

    public Product(String name, String brand, double price, String image, double point) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.image = image;
        this.point = point;
    }

    public void addPoint(int newPoint) {
        numberOfPoints++;
        point = ((point * numberOfPoints) + newPoint) / (numberOfPoints);

    }

    public void addComment(String newComment)//customer is needed to
    {
    }

}

