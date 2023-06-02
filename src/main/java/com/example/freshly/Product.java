package com.example.freshly;

<<<<<<< Updated upstream
public class Product {
    private String name;
    private String brand;
    private int productId;
    private String information;
    private double price;
    private double point;
    private int numberOfPoints;
    private String group;
    private String image;
    private String comments;
=======
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
>>>>>>> Stashed changes

    public String getName() {
        return name;
    }

<<<<<<< Updated upstream
    public void setName(String name) {
        this.name = name;
    }

=======
>>>>>>> Stashed changes
    public String getBrand() {
        return brand;
    }

<<<<<<< Updated upstream
    public void setBrand(String brand) {
        this.brand = brand;
    }

=======
>>>>>>> Stashed changes
    public int getProductId() {
        return productId;
    }

<<<<<<< Updated upstream
    public void setProductId(int productId) {
        this.productId = productId;
    }

=======
>>>>>>> Stashed changes
    public String getInformation() {
        return information;
    }

<<<<<<< Updated upstream
    public void setInformation(String information) {
        this.information = information;
    }

=======
>>>>>>> Stashed changes
    public double getPrice() {
        return price;
    }

<<<<<<< Updated upstream
    public void setPrice(double price) {
        this.price = price;
    }

=======
>>>>>>> Stashed changes
    public double getPoint() {
        return point;
    }

<<<<<<< Updated upstream
    public void setPoint(double point) {
        this.point = point;
    }

=======
>>>>>>> Stashed changes
    public int getNumberOfPoints() {
        return numberOfPoints;
    }

<<<<<<< Updated upstream
    public void setNumberOfPoints(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Product(String name, String brand, int productId, String information, double price, String group, String image){
        this.name=name;
        this.brand=brand;
        this.productId=productId;
        this.information=information;
        this.price=price;
        this.group=group;
        this.image=image;
    }
    public void addPoint(int newPoint){
        point=((point*numberOfPoints)+newPoint)/(numberOfPoints+1);
        numberOfPoints++;
    }
    public void addComment(String newComment)//customer is needed to
    {}
=======
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

>>>>>>> Stashed changes
}
