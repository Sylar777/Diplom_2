package com.yandex.diplom2.Steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import com.yandex.diplom2.StatusesAndUrls;
import com.yandex.diplom2.TestHelper;

public class OrderSteps extends BaseSteps {

    @Step
    public Response createOrderForUser(boolean status, int code, String id1, String id2){
        if(!(id1.isEmpty() && id2.isEmpty())){
            json = "{\"ingredients\":[\"" + id1 + "\", \"" + id2 + "\"] }";
        } else {
            json = "";
        }
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
        .post(StatusesAndUrls.ORDERS_USER);

        return BaseSteps.responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public Response getAllOrders(){
        BaseSteps.responseOfUserNotRegistrationAndLogin = given()
            .header("Content-type", "application/json")
            .when()
            .get(StatusesAndUrls.ORDERS_USER);

        return BaseSteps.responseOfUserNotRegistrationAndLogin;
    }

    @Step
    public Response getUsersOrders(boolean status, int code){
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
        .when()
        .get(StatusesAndUrls.ORDERS_USER);

        return BaseSteps.responseOfUserNotRegistrationAndLogin;
    }
}
