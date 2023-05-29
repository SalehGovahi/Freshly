package com.example.freshly;

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

        public int getNumberOfPoints() {
            return numberOfPoints;
        }

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
            numberOfPoints++;
            point=((point*numberOfPoints)+newPoint)/(numberOfPoints);

        }
        public void addComment(String newComment)//customer is needed to
        {}

    public static void main(String[] args) {

    }
    }


