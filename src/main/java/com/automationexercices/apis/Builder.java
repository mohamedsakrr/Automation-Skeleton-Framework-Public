package com.automationexercices.apis;

import com.automationexercices.utils.dataReader.PropertyReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class Builder {
    private Builder() {
    }

    public static RequestSpecification getRequestSpecification(Map<String, ?> formParams) {
        return new RequestSpecBuilder().setBaseUri(PropertyReader.getProperty("baseUrlApi"))
                .setContentType(ContentType.URLENC)
                .addFormParams(formParams)
                .build();
    }

    public static RequestSpecification getJsonRequestSpecification() {
        return new RequestSpecBuilder().setBaseUri(PropertyReader.getProperty("baseUrlApi"))
                .setContentType(ContentType.JSON)
                .build();
    }
}
