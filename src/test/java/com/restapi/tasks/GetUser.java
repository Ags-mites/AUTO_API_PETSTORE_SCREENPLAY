package com.restapi.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.interactions.Put;
import net.serenitybdd.screenplay.rest.interactions.Delete;

import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GetUser implements Task {

    private final String username;

    public GetUser(String username) {
        this.username = username;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource("/user/{username}")
                        .with(request -> request.pathParam("username", username))
        );
    }

    public static GetUser byUsername(String username) {
        return instrumented(GetUser.class, username);
    }

    public String getUsername() {
        return username;
    }
}
