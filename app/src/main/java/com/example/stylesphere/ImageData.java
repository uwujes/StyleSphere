package com.example.stylesphere;

public class ImageData {
    private String category, imageURI;

    public ImageData(){

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public ImageData(String category, String imageURI) {
        this.category = category;
        this.imageURI = imageURI;
    }
}
