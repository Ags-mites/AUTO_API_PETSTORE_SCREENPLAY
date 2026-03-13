package com.restapi.tasks;

import com.restapi.models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;

import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class UpdateUser implements Task {

    private final String username;
    private final Map<String, String> updateData;

    public UpdateUser(String username, Map<String, String> updateData) {
        this.username = username;
        this.updateData = updateData;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Put.to("/user/{username}")
                        .with(request -> request
                                .pathParam("username", username)
                                .contentType("application/json")
                                .body(updateData))
        );
    }

    public static UpdateUser withData(String username, Map<String, String> updateData) {
        return instrumented(UpdateUser.class, username, updateData);
    }

    public String getUsername() {
        return username;
    }
}
