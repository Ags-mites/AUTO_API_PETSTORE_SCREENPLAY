package com.restapi.stepdefinitions;

import com.restapi.models.User;
import com.restapi.questions.StatusCode;
import com.restapi.questions.UserResponse;
import com.restapi.tasks.*;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import java.util.Map;
import java.util.UUID;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserStepDefinitions {

    private Map<String, String> userData;
    private String currentUsername;

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("el actor está configurado para interactuar con la API de PetStore")
    public void actorConfiguredForPetStoreApi() {
        OnStage.theActorCalled("Tester").whoCan(CallAnApi.at("https://petstore.swagger.io/v2"));
    }

    @Given("el actor crea un usuario con los siguientes datos:")
    public void actorCreatesUserWithData(Map<String, String> userData) {
        Map<String, String> dynamicData = generateDynamicData(userData);
        this.userData = dynamicData;
        this.currentUsername = dynamicData.get("username");
        
        theActorInTheSpotlight().remember("username", currentUsername);
        
        theActorInTheSpotlight().attemptsTo(
                CreateUser.withData(dynamicData)
        );
    }

    private Map<String, String> generateDynamicData(Map<String, String> templateData) {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String baseUsername = templateData.getOrDefault("username", "user");
        String dynamicUsername = baseUsername + "_" + uniqueId;
        
        return Map.of(
            "id", templateData.getOrDefault("id", String.valueOf(System.currentTimeMillis())),
            "username", dynamicUsername,
            "firstName", templateData.getOrDefault("firstName", "Test"),
            "lastName", templateData.getOrDefault("lastName", "User"),
            "email", templateData.getOrDefault("email", dynamicUsername + "@example.com"),
            "password", templateData.getOrDefault("password", "Password123"),
            "phone", templateData.getOrDefault("phone", "+573001234567"),
            "userStatus", templateData.getOrDefault("userStatus", "1")
        );
    }

    @Then("el actor recibe una respuesta con código de estado {int}")
    public void actorShouldReceiveStatusCode(int expectedStatusCode) {
        theActorInTheSpotlight().should(
                seeThat("Status code", StatusCode.response(), equalTo(expectedStatusCode))
        );
    }

    @When("el actor consulta el usuario por su username")
    public void actorConsultsUserByUsername() {
        String username = (String) theActorInTheSpotlight().recall("username");
        
        theActorInTheSpotlight().attemptsTo(
                GetUser.byUsername(username)
        );
    }

    @And("el actor debería verificar que los datos del usuario sean:")
    public void actorShouldVerifyUserData(Map<String, String> expectedData) {
        User user = UserResponse.body().answeredBy(theActorInTheSpotlight());
        String storedUsername = (String) theActorInTheSpotlight().recall("username");
        
        expectedData.forEach((field, expectedValue) -> {
            switch (field) {
                case "username":
                    theActorInTheSpotlight().should(
                            seeThat("username", u -> user.getUsername(), equalTo(storedUsername))
                    );
                    break;
                case "firstName":
                    theActorInTheSpotlight().should(
                            seeThat("firstName", u -> user.getFirstName(), equalTo(expectedValue))
                    );
                    break;
                case "lastName":
                    theActorInTheSpotlight().should(
                            seeThat("lastName", u -> user.getLastName(), equalTo(expectedValue))
                    );
                    break;
                case "email":
                    theActorInTheSpotlight().should(
                            seeThat("email", u -> user.getEmail(), equalTo(expectedValue))
                    );
                    break;
            }
        });
    }

    @When("el actor actualiza el usuario con los siguientes datos:")
    public void actorUpdatesUserWithData(Map<String, String> updateData) {
        String username = (String) theActorInTheSpotlight().recall("username");
        
        theActorInTheSpotlight().attemptsTo(
                UpdateUser.withData(username, updateData)
        );
    }

    @When("el actor consulta el usuario actualizado")
    public void actorConsultsUpdatedUser() {
        String username = (String) theActorInTheSpotlight().recall("username");
        
        theActorInTheSpotlight().attemptsTo(
                GetUser.byUsername(username)
        );
    }

    @When("el actor elimina el usuario")
    public void actorDeletesUser() {
        String username = (String) theActorInTheSpotlight().recall("username");
        
        theActorInTheSpotlight().attemptsTo(
                DeleteUser.byUsername(username)
        );
    }

    @When("el actor intenta consultar el usuario eliminado")
    public void actorTriesToConsultDeletedUser() {
        String username = (String) theActorInTheSpotlight().recall("username");
        
        theActorInTheSpotlight().attemptsTo(
                GetUser.byUsername(username)
        );
    }
}
