package com.example.dockerapp1.Dto;

public class JsonOutput {

    public JsonOutput() {
    }

    private String file;
    private String error;

    public JsonOutput(String file, String error) {
        this.file = file;
        this.error = error;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getError() {
        return error;
    }

    public void setError(String product) {
        this.error = product;
    }

    @Override
    public String toString() {
        return "{\n" +
                "file='" + file + "\'\n" +
                ", error='" + error + "\'\n" +
                '}';
    }
}