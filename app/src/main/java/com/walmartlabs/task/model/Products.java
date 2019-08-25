package com.walmartlabs.task.model;

/**
 * Created by Bipul on 20-06-2019.
 */

public class Products {
    private String productId, productName, shortDescription, longDescription, price, productImage;
    private String reviewRatings, reviewCounts;
    private Boolean inStock;

    public Products() {

    }

    public Products(String productId, String productName, String shortDescription, String longDescription, String price, String productImage, String reviewRatings, String reviewCounts, Boolean inStock) {
        this.productId = productId;
        this.productName = productName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.price = price;
        this.productImage = productImage;
        this.reviewRatings = reviewRatings;
        this.reviewCounts = reviewCounts;
        this.inStock = inStock;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getReviewRatings() {
        return reviewRatings;
    }

    public void setReviewRatings(String reviewRatings) {
        this.reviewRatings = reviewRatings;
    }

    public String getReviewCounts() {
        return reviewCounts;
    }

    public void setReviewCounts(String reviewCounts) {
        this.reviewCounts = reviewCounts;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }
}
