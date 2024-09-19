package com.yandex.diplom2.Steps;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;
import com.yandex.diplom2.StatusesAndUrls;
import com.yandex.diplom2.TestHelper;
import com.yandex.diplom2.UserGenerator;

public class UserSteps extends BaseSteps {
    private ArrayList<String> listOfIngredientsID;
    private UserGenerator userGenerator;

    public UserSteps(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        userGenerator = new UserGenerator();
    }

    @Step
    public Response deleteUser(){
        String bearer = TestHelper.bearerOfRegicteredOrLoginedUser(BaseSteps.responseOfUserCreation, BaseSteps.responseOfUserLogin);

        if(bearer != null){
            BaseSteps.responseOfUserNotRegistrationAndLogin = given()
            .headers(
                "Authorization",
                "Bearer " + bearer,
                "Content-Type",
                ContentType.JSON,
                "Accept",
                ContentType.JSON
                )
            .when()
            .delete(StatusesAndUrls.DELETE_USER);
            BaseSteps.responseOfUserCreation=null;
            BaseSteps.responseOfUserLogin=null;
            BaseSteps.responseOfUserNotRegistrationAndLogin=null;

            return BaseSteps.responseOfUserNotRegistrationAndLogin;
        } else return null;
    }

    @Step
    public Response registerNewUser(){
        json = "{\"email\":\"" + userGenerator.getEmail() + "\", \"password\": \"" + userGenerator.getPassword() + "\", \"name\": \"" + userGenerator.getName() + "\" }";

        BaseSteps.responseOfUserCreation = given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post(StatusesAndUrls.REGISTER_USER);

        return BaseSteps.responseOfUserCreation;
    }

    @Step
    public Response registerExistUser(){
        json = "{\"email\":\"" + userGenerator.getEmail() + "\", \"password\": \"" + userGenerator.getPassword() + "\", \"name\": \"ds\" }";

        BaseSteps.responseOfUserNotRegistrationAndLogin = given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post(StatusesAndUrls.REGISTER_USER);

            return BaseSteps.responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public Response registerUserWOAllData(){
        json = "{\"email\":\"" + userGenerator.getEmail() + "\", \"name\": \"ds\" }";

        BaseSteps.responseOfUserNotRegistrationAndLogin = given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post(StatusesAndUrls.REGISTER_USER);

            return BaseSteps.responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public Response userLogin(){
        json = "{\"email\":\"" + userGenerator.getEmail() + "\", \"password\": \"" + userGenerator.getPassword() + "\"}";

        BaseSteps.responseOfUserLogin = given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post(StatusesAndUrls.LOGIN_USER);

        return BaseSteps.responseOfUserLogin;
    }

    @Step
    public Response userLoginWithWrongData(){
        json = "{\"email\":\"" + userGenerator.getEmail() + "\", \"password\": \"" + userGenerator.getPassword() + "test" + "\"}";

        BaseSteps.responseOfUserNotRegistrationAndLogin = given()
        .header("Content-type", "application/json")
        .and()
        .body(json)
        .when()
        .post(StatusesAndUrls.LOGIN_USER);

        return BaseSteps.responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public Response changeName(boolean status, int code){
        json = "{\"email\":\"" + userGenerator.getEmail() + "\", \"password\": \"" + userGenerator.getPassword() + "\", \"name\": \"" + userGenerator.getName() + "test" + "\" }";
        String bearer = TestHelper.bearerOfRegicteredOrLoginedUser(BaseSteps.responseOfUserCreation, BaseSteps.responseOfUserLogin);

        BaseSteps.responseOfUserNotRegistrationAndLogin = given()
        .headers(
            "Authorization",
            "Bearer " + bearer,
            "Content-Type",
            ContentType.JSON,
            "Accept",
            ContentType.JSON
            )
        .and()
        .body(json)
        .when()
        .patch(StatusesAndUrls.DATA_USER);

        return BaseSteps.responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public Response changePassword(boolean status, int code){
        json = "{\"email\":\"" + userGenerator.getEmail() + "\", \"password\": \"" + userGenerator.getPassword() + "test" + "\", \"name\": \"" + userGenerator.getName() + "\" }";
        String bearer = TestHelper.bearerOfRegicteredOrLoginedUser(BaseSteps.responseOfUserCreation, BaseSteps.responseOfUserLogin);

        BaseSteps.responseOfUserNotRegistrationAndLogin = given()
        .headers(
            "Authorization",
            "Bearer " + bearer,
            "Content-Type",
            ContentType.JSON,
            "Accept",
            ContentType.JSON
            )
        .and()
        .body(json)
        .when()
        .patch(StatusesAndUrls.DATA_USER);

        return BaseSteps.responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public Response changeEmail(boolean status, int code){
        json = "{\"email\":\"" + userGenerator.getEmail() +  "test" + "\", \"password\": \"" + userGenerator.getPassword() + "\", \"name\": \"" + userGenerator.getName() + "\" }";
        String bearer = TestHelper.bearerOfRegicteredOrLoginedUser(BaseSteps.responseOfUserCreation, BaseSteps.responseOfUserLogin);

        BaseSteps.responseOfUserNotRegistrationAndLogin = given()
        .headers(
            "Authorization",
            "Bearer " + bearer,
            "Content-Type",
            ContentType.JSON,
            "Accept",
            ContentType.JSON
            )
        .and()
        .body(json)
        .when()
        .patch(StatusesAndUrls.DATA_USER);

        return BaseSteps.responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public Response getIngredients(){
        BaseSteps.responseOfUserNotRegistrationAndLogin = given()
            .header("Content-type", "application/json")
            .when()
            .get(StatusesAndUrls.INGREDIENTS_USER);

            listOfIngredientsID = TestHelper.ingredients(BaseSteps.responseOfUserNotRegistrationAndLogin.asString());

        return BaseSteps.responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public void checkResponse(Response stepResponse, Boolean success, int statusCode){
        if(TestHelper.bearerOfRegicteredOrLoginedUser(BaseSteps.responseOfUserCreation, BaseSteps.responseOfUserLogin) != null){
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

    public Response getResponseOfUserCreation() {
        return BaseSteps.responseOfUserCreation;
    }

    public Response getResponseOfUserLogin() {
        return BaseSteps.responseOfUserLogin;
    }

    public Response getResponseOfUserNotRegistrationAndLogin() {
        return BaseSteps.responseOfUserNotRegistrationAndLogin;
    }

    public String getJson() {
        return json;
    }

    public ArrayList<String> getListOfIngredientsID() {
        return listOfIngredientsID;
    }

}
