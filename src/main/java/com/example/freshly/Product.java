package com.example.freshly;

import javafx.scene.image.Image;

public class Product {
    private  String name;
    private  String brand;
    private int productId;
    private String information;
    private  double price;
    private double point;
    private int numberOfPoints;
    private String type;
    private Image image;
    private String[] comments;

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

    public Image getImage() {
        return image;
    }

    public String[] getComments() {
        return comments;
    }


    public Product(String name, String brand, int productId, String information, double price, String group, Image image, double point, int numberOfPoints, String[] comments) {
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
    }

    public Product(String name, String brand, double price, Image image, double point) {
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
