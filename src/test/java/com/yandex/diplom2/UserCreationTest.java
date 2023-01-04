package com.yandex.diplom2;

import org.junit.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Random;

public class UserCreationTest {

    private String email;
    private String password;
    private Response responseOfCourierCreation;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        email = "ds"+ new Random().nextInt(10000) + "@yandex.ru";
        password = "12354";
    }

    @After
    public void clear(){

    }

    @Test
    @DisplayName("Check that user can be created")
    @Description("Check that user can be created")
    @Step("Send POST request to create user")
    public void userCanBeCreatedTest(){
        String json = "{\"email\":\"" + email + "\", \"password\": \"" + password + "\", \"name\": \"ds\" }";

        responseOfCourierCreation = given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("api/auth/register");

        responseOfCourierCreation
            .then()
            .assertThat()
            .body("success", equalTo(true))
            .and()
            .statusCode(200);
    }


    @Test
    @DisplayName("Check that user with the same credentials can't be created")
    @Description("Check that user with the same credentials can't be created")
    @Step("Send POST request to create user second time")
    public void cantBeCreatedTheSameUserTest(){
        String json = "{\"email\":\"" + email + "\", \"password\": \"" + password + "\", \"name\": \"ds\" }";

        given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("api/auth/register")
            .then()
            .assertThat()
            .body("success", equalTo(true))
            .and()
            .statusCode(200);

        responseOfCourierCreation = given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("api/auth/register");

        responseOfCourierCreation
            .then()
            .assertThat()
            .body("success", equalTo(false))
            .and()
            .statusCode(403);
    }

    @Test
    @DisplayName("Check that user can't be created w/o all fields")
    @Description("Check that user can't be created w/o all fields")
    @Step("Send POST request to create user")
    public void userCantBeCreatedWOAllInfoTest(){
        String json = "{\"email\":\"" + email + "\", \"name\": \"ds\" }";

        responseOfCourierCreation = given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("api/auth/register");

        responseOfCourierCreation
            .then()
            .assertThat()
            .body("success", equalTo(false))
            .and()
            .statusCode(403);
    }
}
