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
    private Response responseOfUserCreation;
    private Response responseOfUserLogin;
    private Response responseOfUserNotRegistrationAndLogin;
    private String json;
    private ArrayList<String> listOfIngredientsID;

    public Steps(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        email = "ds"+ new Random().nextInt(10000) + "@yandex.ru";
        password = "12354";
        name = "ds";
    }

    @Step
    public Response deleteUser(){
        String Bearer = TestHelper.bearerOfRegicteredOrLoginedUser(responseOfUserCreation, responseOfUserLogin);
        if(Bearer != null){
            responseOfUserNotRegistrationAndLogin = given()
            .headers(
                "Authorization",
                "Bearer " + Bearer,
                "Content-Type",
                ContentType.JSON,
                "Accept",
                ContentType.JSON
                )
            .when()
            .delete(StatusesAndUrls.DELETE_USER);

            return responseOfUserNotRegistrationAndLogin;
        } else return null;
    }

    @Step
    public Response registerNewUser(){
        json = "{\"email\":\"" + email + "\", \"password\": \"" + password + "\", \"name\": \"" + name + "\" }";

        responseOfUserCreation = given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post(StatusesAndUrls.REGISTER_USER);

        return responseOfUserCreation;
    }

    @Step
    public Response registerExistUser(){
        json = "{\"email\":\"" + email + "\", \"password\": \"" + password + "\", \"name\": \"ds\" }";

        responseOfUserNotRegistrationAndLogin = given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post(StatusesAndUrls.REGISTER_USER);

            return responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public Response registerUserWOAllData(){
        json = "{\"email\":\"" + email + "\", \"name\": \"ds\" }";

        responseOfUserNotRegistrationAndLogin = given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post(StatusesAndUrls.REGISTER_USER);

            System.out.println(responseOfUserNotRegistrationAndLogin.asString());

            return responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public Response userLogin(){
        json = "{\"email\":\"" + email + "\", \"password\": \"" + password + "\"}";

        responseOfUserLogin = given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post(StatusesAndUrls.LOGIN_USER);

        return responseOfUserLogin;
    }

    @Step
    public Response userLoginWithWrongData(){
        json = "{\"email\":\"" + email + "\", \"password\": \"" + password + "test" + "\"}";

        responseOfUserNotRegistrationAndLogin = given()
        .header("Content-type", "application/json")
        .and()
        .body(json)
        .when()
        .post(StatusesAndUrls.LOGIN_USER);

        return responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public Response changeName(boolean status, int code){
        json = "{\"email\":\"" + email + "\", \"password\": \"" + password + "\", \"name\": \"" + name + "test" + "\" }";
        String Bearer = TestHelper.bearerOfRegicteredOrLoginedUser(responseOfUserCreation, responseOfUserLogin);

        responseOfUserNotRegistrationAndLogin = given()
        .headers(
            "Authorization",
            "Bearer " + Bearer,
            "Content-Type",
            ContentType.JSON,
            "Accept",
            ContentType.JSON
            )
        .and()
        .body(json)
        .when()
        .patch(StatusesAndUrls.DATA_USER);

        return responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public Response changePassword(boolean status, int code){
        json = "{\"email\":\"" + email + "\", \"password\": \"" + password + "test" + "\", \"name\": \"" + name + "\" }";
        String Bearer = TestHelper.bearerOfRegicteredOrLoginedUser(responseOfUserCreation, responseOfUserLogin);

        responseOfUserNotRegistrationAndLogin = given()
        .headers(
            "Authorization",
            "Bearer " + Bearer,
            "Content-Type",
            ContentType.JSON,
            "Accept",
            ContentType.JSON
            )
        .and()
        .body(json)
        .when()
        .patch(StatusesAndUrls.DATA_USER);

        return responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public Response changeEmail(boolean status, int code){
        json = "{\"email\":\"" + email +  "test" + "\", \"password\": \"" + password + "\", \"name\": \"" + name + "\" }";
        String Bearer = TestHelper.bearerOfRegicteredOrLoginedUser(responseOfUserCreation, responseOfUserLogin);

        responseOfUserNotRegistrationAndLogin = given()
        .headers(
            "Authorization",
            "Bearer " + Bearer,
            "Content-Type",
            ContentType.JSON,
            "Accept",
            ContentType.JSON
            )
        .and()
        .body(json)
        .when()
        .patch(StatusesAndUrls.DATA_USER);

        return responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public Response getIngredients(){
        responseOfUserNotRegistrationAndLogin = given()
            .header("Content-type", "application/json")
            .when()
            .get(StatusesAndUrls.INGREDIENTS_USER);

            listOfIngredientsID = TestHelper.ingredients(responseOfUserNotRegistrationAndLogin.asString());

        return responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public Response createOrderForUser(boolean status, int code, String id1, String id2){
        if(!(id1.isEmpty() && id2.isEmpty())){
            json = "{\"ingredients\":[\"" + id1 + "\", \"" + id2 + "\"] }";
        } else {
            json = "";
        }
        String Bearer = TestHelper.bearerOfRegicteredOrLoginedUser(responseOfUserCreation, responseOfUserLogin);

        responseOfUserNotRegistrationAndLogin = given()
        .headers(
            "Authorization",
            "Bearer " + Bearer,
            "Content-Type",
            ContentType.JSON,
            "Accept",
            ContentType.JSON
            )
        .and()
        .body(json)
        .when()
        .post(StatusesAndUrls.ORDERS_USER);

        return responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public Response getAllOrders(){
        responseOfUserNotRegistrationAndLogin = given()
            .header("Content-type", "application/json")
            .when()
            .get(StatusesAndUrls.ORDERS_USER);

        return responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public Response getUsersOrders(boolean status, int code){
        String Bearer = TestHelper.bearerOfRegicteredOrLoginedUser(responseOfUserCreation, responseOfUserLogin);

        responseOfUserNotRegistrationAndLogin = given()
        .headers(
            "Authorization",
            "Bearer " + Bearer,
            "Content-Type",
            ContentType.JSON,
            "Accept",
            ContentType.JSON
            )
        .when()
        .get(StatusesAndUrls.ORDERS_USER);

        return responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public void checkResponse(Response stepResponse, Boolean success, int statusCode){
        if(TestHelper.bearerOfRegicteredOrLoginedUser(responseOfUserCreation, responseOfUserLogin) != null){
            stepResponse
                .then()
                .assertThat()
                .body("success", equalTo(success))
                .and()
                .statusCode(statusCode);
        } else {
            stepResponse
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .and()
                .statusCode(403);
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public Response getResponseOfUserCreation() {
        return responseOfUserCreation;
    }

    public Response getResponseOfUserLogin() {
        return responseOfUserLogin;
    }

    public Response getResponseOfUserNotRegistrationAndLogin() {
        return responseOfUserNotRegistrationAndLogin;
    }

    public String getJson() {
        return json;
    }

    public ArrayList<String> getListOfIngredientsID() {
        return listOfIngredientsID;
    }

}
