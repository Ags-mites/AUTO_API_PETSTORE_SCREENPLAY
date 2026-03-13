package com.restapi.questions;

import com.restapi.models.User;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class UserResponse implements Question<User> {

    public static UserResponse body() {
        return new UserResponse();
    }

    @Override
    public User answeredBy(Actor actor) {
        return SerenityRest.lastResponse().as(User.class);
    }
}
