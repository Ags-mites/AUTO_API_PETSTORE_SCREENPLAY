package com.restapi.questions;

import io.restassured.module.jsv.JsonSchemaValidator;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.io.InputStream;

public class SchemaValidation implements Question<Boolean> {

    private final String schemaFile;

    public SchemaValidation(String schemaFile) {
        this.schemaFile = schemaFile;
    }

    public static SchemaValidation matches(String schemaFile) {
        return new SchemaValidation(schemaFile);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        try {
            InputStream schemaStream = getClass().getClassLoader().getResourceAsStream(schemaFile);
            if (schemaStream == null) {
                throw new RuntimeException("Schema file not found: " + schemaFile);
            }
            SerenityRest.lastResponse().then()
                    .body(JsonSchemaValidator.matchesJsonSchema(schemaStream));
            return true;
        } catch (Exception e) {
            throw new AssertionError("Schema validation failed: " + e.getMessage());
        }
    }
}
