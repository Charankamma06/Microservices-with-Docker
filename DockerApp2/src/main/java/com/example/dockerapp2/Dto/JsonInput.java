package com.example.dockerapp2.Dto;

public class JsonInput {
    private String file;
    private String product;

    public JsonInput() {
    }

    public JsonInput(String file, String product) {
        this.file = file;
        this.product = product;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}