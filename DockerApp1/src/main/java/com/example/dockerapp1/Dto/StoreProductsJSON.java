package com.example.dockerapp1.Dto;

public class StoreProductsJSON {
    private String file;
    private String data;

    public StoreProductsJSON() {
    }

    public StoreProductsJSON(String file, String data) {
        this.file = file;
        this.data = data;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
