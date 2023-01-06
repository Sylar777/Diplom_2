package com.yandex.diplom2;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Random;

public class Steps {
    private String email;
    private String password;
    private String name;
    public String bearerToken = "";
    private Response responseOfUserCreation;
    public String json;
    public ArrayList<String> listOfIngredientsID;

    public Steps(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        email = "ds"+ new Random().nextInt(10000) + "@yandex.ru";
        password = "12354";
        name = "ds";
    }

    @Step
    public void deleteUser(){
        if(!bearerToken.isEmpty()){
            given()
            .headers(
                "Authorization",
                "Bearer " + bearerToken,
                "Content-Type",
                ContentType.JSON,
                "Accept",
                ContentType.JSON
                )
            .when()
            .delete("/api/auth/user")
            .then()
            .assertThat()
            .body("success", equalTo(true))
            .and()
            .statusCode(202);

            bearerToken = "";
        }
    }

    @Step
    public Steps registerNewUser(){
        json = "{\"email\":\"" + email + "\", \"password\": \"" + password + "\", \"name\": \"" + name + "\" }";

        responseOfUserCreation = given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("api/auth/register");

        responseOfUserCreation
            .then()
            .assertThat()
            .body("success", equalTo(true))
            .and()
            .statusCode(200);

        bearerToken = TestHelper.bearerToken(responseOfUserCreation.asString());

        return this;
    }

    @Step
    public Steps registerExistUser(){
        json = "{\"email\":\"" + email + "\", \"password\": \"" + password + "\", \"name\": \"ds\" }";

        responseOfUserCreation = given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("api/auth/register");

        responseOfUserCreation
            .then()
            .assertThat()
            .body("success", equalTo(false))
            .and()
            .statusCode(403);

            return this;
    }

    @Step
    public Steps registerUserWOAllData(){
        json = "{\"email\":\"" + email + "\", \"name\": \"ds\" }";

        responseOfUserCreation = given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("api/auth/register");

        responseOfUserCreation
            .then()
            .assertThat()
            .body("success", equalTo(false))
            .and()
            .statusCode(403);

            return this;
    }

    @Step
    public Steps userLogin(){
        json = "{\"email\":\"" + email + "\", \"password\": \"" + password + "\"}";

        responseOfUserCreation = given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("api/auth/login");

        responseOfUserCreation
            .then()
            .assertThat()
            .body("success", equalTo(true))
            .and()
            .statusCode(200);

        bearerToken = TestHelper.bearerToken(responseOfUserCreation.asString());

        return this;
    }

    @Step
    public Steps userLoginWithWrongData(){
        json = "{\"email\":\"" + email + "\", \"password\": \"" + password + "test" + "\"}";

        given()
        .header("Content-type", "application/json")
        .and()
        .body(json)
        .when()
        .post("api/auth/login")
        .then()
        .assertThat()
        .body("success", equalTo(false))
        .and()
        .statusCode(401);

        return this;
    }

    @Step
    public Steps changeName(boolean status, int code){
        json = "{\"email\":\"" + email + "\", \"password\": \"" + password + "\", \"name\": \"" + name + "test" + "\" }";

        given()
        .headers(
            "Authorization",
            "Bearer " + bearerToken,
            "Content-Type",
            ContentType.JSON,
            "Accept",
            ContentType.JSON
            )
        .and()
        .body(json)
        .when()
        .patch("api/auth/user")
        .then()
        .assertThat()
        .body("success", equalTo(status))
        .and()
        .statusCode(code);

        return this;
    }

    @Step
    public Steps changePassword(boolean status, int code){
        json = "{\"email\":\"" + email + "\", \"password\": \"" + password + "test" + "\", \"name\": \"" + name + "\" }";

        given()
        .headers(
            "Authorization",
            "Bearer " + bearerToken,
            "Content-Type",
            ContentType.JSON,
            "Accept",
            ContentType.JSON
            )
        .and()
        .body(json)
        .when()
        .patch("api/auth/user")
        .then()
        .assertThat()
        .body("success", equalTo(status))
        .and()
        .statusCode(code);

        return this;
    }

    @Step
    public Steps changeEmail(boolean status, int code){
        json = "{\"email\":\"" + email +  "test" + "\", \"password\": \"" + password + "\", \"name\": \"" + name + "\" }";

        given()
        .headers(
            "Authorization",
            "Bearer " + bearerToken,
            "Content-Type",
            ContentType.JSON,
            "Accept",
            ContentType.JSON
            )
        .and()
        .body(json)
        .when()
        .patch("api/auth/user")
        .then()
        .assertThat()
        .body("success", equalTo(status))
        .and()
        .statusCode(code);

        return this;
    }

    @Step
    public Steps getIngredients(){
        responseOfUserCreation = given()
            .header("Content-type", "application/json")
            .when()
            .get("api/ingredients");

        responseOfUserCreation
            .then()
            .assertThat()
            .body("success", equalTo(true))
            .and()
            .statusCode(200);

            listOfIngredientsID = TestHelper.ingredients(responseOfUserCreation.asString());

        return this;
    }

    @Step
    public Steps createOrderForUser(boolean status, int code, String id1, String id2){
        if(!(id1.isEmpty() && id2.isEmpty())){
            json = "{\"ingredients\":[\"" + id1 + "\", \"" + id2 + "\"] }";
        } else {
            json = "";
        }

        given()
        .headers(
            "Authorization",
            "Bearer " + bearerToken,
            "Content-Type",
            ContentType.JSON,
            "Accept",
            ContentType.JSON
            )
        .and()
        .body(json)
        .when()
        .post("api/orders")
        .then()
        .assertThat()
        .body("success", equalTo(status))
        .and()
        .statusCode(code);

        return this;
    }

    @Step
    public Steps getAllOrders(){
        responseOfUserCreation = given()
            .header("Content-type", "application/json")
            .when()
            .get("api/orders");

        responseOfUserCreation
            .then()
            .assertThat()
            .body("success", equalTo(true))
            .and()
            .statusCode(200);


        return this;
    }

    @Step
    public Steps getUsersOrders(boolean status, int code){
        given()
        .headers(
            "Authorization",
            "Bearer " + bearerToken,
            "Content-Type",
            ContentType.JSON,
            "Accept",
            ContentType.JSON
            )
        .when()
        .get("api/orders")
        .then()
        .assertThat()
        .body("success", equalTo(status))
        .and()
        .statusCode(code);
        return this;
    }
}
