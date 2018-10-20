package com.example.abdel.loginapplication;

public class ModelClass {
    String name, code;

    public ModelClass(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public ModelClass() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountryName() {

        return name;
    }

    public void setCountryName(String name) {
        this.name = name;
    }

}
