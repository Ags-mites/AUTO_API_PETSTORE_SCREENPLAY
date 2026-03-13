package com.restapi.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class DeleteUser implements Task {

    private final String username;

    public DeleteUser(String username) {
        this.username = username;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Delete.from("/user/{username}")
                        .with(request -> request.pathParam("username", username))
        );
    }

    public static DeleteUser byUsername(String username) {
        return instrumented(DeleteUser.class, username);
    }
}
