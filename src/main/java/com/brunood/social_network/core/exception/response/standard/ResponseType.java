package com.brunood.social_network.core.exception.response.standard;

public enum ResponseType {

    SUCCESS("Success"),
    WARNING("Warning"),
    ERROR("Error");

    private final String text;
    ResponseType(String text){
        this.text = text;
    }
}