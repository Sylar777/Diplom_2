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

public class UserLoginTest {

    private String email;
    private String password;
    private Response responseOfCourierCreation;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        email = "ds"+ new Random().nextInt(10000) + "@yandex.ru";
        password = "12354";

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
    }

    @After
    public void clear(){

    }

    @Test
    @DisplayName("Check that user can login")
    @Description("Check that user can login")
    @Step("Send POST request to login user")
    public void userCanLoginTest(){
        String json = "{\"email\":\"" + email + "\", \"password\": \"" + password + "\"}";

        given()
        .header("Content-type", "application/json")
        .and()
        .body(json)
        .when()
        .post("api/auth/login")
        .then()
        .assertThat()
        .body("success", equalTo(true))
        .and()
        .statusCode(200);
    }

    @Test
    @DisplayName("Check that user can't be login with wrong data")
    @Description("Check that user can't be login with wrong data")
    @Step("Send POST request to login user")
    public void userCantLoginWithWrongDataTest(){
        String json = "{\"email\":\"" + email + "\", \"password\": \"" + password + "test" + "\"}";

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
    }
}
