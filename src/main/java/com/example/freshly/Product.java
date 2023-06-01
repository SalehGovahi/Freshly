<<<<<<< Updated upstream
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

=======
package com.example.freshly;

import java.util.ArrayList;

public class Product {
    private String name;
    private String brand;
    private int productId;
    private String information;
    private double price;
    private double point;
    private int numberOfPoints;
    private String type;
    private String image;
    private String uniqueProperties;
    private ArrayList<String>  comments;
    private String productSeller;
    private int stock;

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public String getProductSeller() {
        return productSeller;
    }

    public void setProductSeller(String productSeller) {
        this.productSeller = productSeller;
    }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }

    public void setNumberOfPoints(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public String getUniqueProperties() {
        return uniqueProperties;
    }

    public void setUniqueProperties(String uniqueProperties) {
        this.uniqueProperties = uniqueProperties;
    }

    public Product(String name, String brand, int productId, String information, double price, String type, String image, int stock, ArrayList<String>  comments, String productSeller){
        this.name=name;
        this.brand=brand;
        this.productId=productId;
        this.information=information;
        this.price=price;
        this.type = type;
        this.image=image;
        this.stock=stock;
        this.comments=comments;
        this.productSeller=productSeller;
    }
    public void addPoint(int newPoint){
        point=((point*numberOfPoints)+newPoint)/(numberOfPoints+1);
        numberOfPoints++;
    }
    public void addComment(String newComment)//customer is needed to
    {}
}
>>>>>>> Stashed changes
