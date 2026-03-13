package com.restapi.tasks;

import com.restapi.models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CreateUser implements Task {

    private final User user;

    public CreateUser(Map<String, String> userData) {
        this.user = new User(
                Long.parseLong(userData.get("id")),
                userData.get("username"),
                userData.get("firstName"),
                userData.get("lastName"),
                userData.get("email"),
                userData.get("password"),
                userData.get("phone"),
                Integer.parseInt(userData.get("userStatus"))
        );
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/user")
                        .with(request -> request
                                .contentType("application/json")
                                .body(user))
        );
    }

    public static CreateUser withData(Map<String, String> userData) {
        return instrumented(CreateUser.class, userData);
    }

    public User getUser() {
        return user;
    }
}
