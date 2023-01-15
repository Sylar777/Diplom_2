package com.yandex.diplom2;

import org.junit.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

public class OrderTest {

    private Steps steps;

    @Before
    public void setUp() {
        steps = new Steps();
    }

    @After
    public void clear(){
        steps.deleteUser();
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),true,StatusesAndUrls.STATUS_ACCEPTED);
    }

    @Test
    @DisplayName("Check that logined User can't create Order w/o ingredients")
    @Description("Send POST request to create Order")
    public void loginedUserCantCreateOrderWOIngredientsTest(){
        steps.registerNewUser();
        steps.checkResponse(steps.getResponseOfUserCreation(),true,StatusesAndUrls.STATUS_OK);
        steps.userLogin();
        steps.checkResponse(steps.getResponseOfUserLogin(),true,StatusesAndUrls.STATUS_OK);
        steps.createOrderForUser(false,StatusesAndUrls.STATUS_BAD_REQUEST,"","");
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),false,StatusesAndUrls.STATUS_BAD_REQUEST);
    }

    @Test
    @DisplayName("Check that logined User can create Order with ingredients")
    @Description("Send POST request to create Order")
    public void loginedUserCanCreateOrderWithIngredientsTest(){
        steps.registerNewUser();
        steps.checkResponse(steps.getResponseOfUserCreation(),true,StatusesAndUrls.STATUS_OK);
        steps.userLogin();
        steps.checkResponse(steps.getResponseOfUserLogin(),true,StatusesAndUrls.STATUS_OK);
        steps.getIngredients();
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),true,StatusesAndUrls.STATUS_OK);
        steps.createOrderForUser(true,StatusesAndUrls.STATUS_OK,steps.getListOfIngredientsID().get(0),steps.getListOfIngredientsID().get(1));
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),true,StatusesAndUrls.STATUS_OK);
    }

    @Test
    @DisplayName("Check that logined User can't create Order with wrond ingredient ids")
    @Description("Send POST request to create Order")
    public void loginedUserCantCreateOrderWithWrongIngredientIdsTest(){
        steps.registerNewUser();
        steps.checkResponse(steps.getResponseOfUserCreation(),true,StatusesAndUrls.STATUS_OK);
        steps.userLogin();
        steps.checkResponse(steps.getResponseOfUserLogin(),true,StatusesAndUrls.STATUS_OK);
        steps.createOrderForUser(false,StatusesAndUrls.STATUS_BAD_REQUEST,"60d3b41abdacab0026a733c6","609646e4dc916e00276b2870");
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),false,StatusesAndUrls.STATUS_BAD_REQUEST);
    }

    @Test
    @DisplayName("Check that not logined User can't create Order w/o ingredients")
    @Description("Send POST request to create Order")
    public void notLoginedUserCantCreateOrderWOIngredientsTest(){
        steps.registerNewUser();
        steps.checkResponse(steps.getResponseOfUserCreation(),true,StatusesAndUrls.STATUS_OK);
        steps.createOrderForUser(false,StatusesAndUrls.STATUS_BAD_REQUEST,"","");
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),false,StatusesAndUrls.STATUS_BAD_REQUEST);
    }

    @Test
    @DisplayName("Check that not logined User can create Order with ingredients")
    @Description("Send POST request to create Order")
    public void notLoginedUserCanCreateOrderWithIngredientsTest(){
        steps.registerNewUser();
        steps.checkResponse(steps.getResponseOfUserCreation(),true,StatusesAndUrls.STATUS_OK);
        steps.getIngredients();
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),true,StatusesAndUrls.STATUS_OK);
        steps.createOrderForUser(true,StatusesAndUrls.STATUS_OK,steps.getListOfIngredientsID().get(0),steps.getListOfIngredientsID().get(1));
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),true,StatusesAndUrls.STATUS_OK);
    }

    @Test
    @DisplayName("Check that not logined User can't create Order with wrond ingredient ids")
    @Description("Send POST request to create Order")
    public void notLoginedUserCantCreateOrderWithWrongIngredientIdsTest(){
        steps.registerNewUser();
        steps.checkResponse(steps.getResponseOfUserCreation(),true,StatusesAndUrls.STATUS_OK);
        steps.createOrderForUser(false,StatusesAndUrls.STATUS_BAD_REQUEST,"60d3b41abdacab0026a733c6","609646e4dc916e00276b2870");
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),false,StatusesAndUrls.STATUS_BAD_REQUEST);
    }

    @Test
    @DisplayName("Check that logined User can get list of orders")
    @Description("Send GET request to get Orders")
    public void getListOfOrdersForLoginedUserTest(){
        steps.registerNewUser();
        steps.checkResponse(steps.getResponseOfUserCreation(),true,StatusesAndUrls.STATUS_OK);
        steps.userLogin();
        steps.checkResponse(steps.getResponseOfUserLogin(),true,StatusesAndUrls.STATUS_OK);
        steps.getIngredients();
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),true,StatusesAndUrls.STATUS_OK);
        steps.createOrderForUser(true,StatusesAndUrls.STATUS_OK,steps.getListOfIngredientsID().get(0),steps.getListOfIngredientsID().get(1));
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),true,StatusesAndUrls.STATUS_OK);
        steps.getUsersOrders(true,StatusesAndUrls.STATUS_OK);
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),true,StatusesAndUrls.STATUS_OK);
    }

    @Test
    @DisplayName("Check that not logined User can get list of orders")
    @Description("Send GET request to get Orders")
    public void getListOfOrdersForNotLoginedUserTest(){
        steps.registerNewUser();
        steps.checkResponse(steps.getResponseOfUserCreation(),true,StatusesAndUrls.STATUS_OK);
        steps.getIngredients();
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),true,StatusesAndUrls.STATUS_OK);
        steps.createOrderForUser(true,StatusesAndUrls.STATUS_OK,steps.getListOfIngredientsID().get(0),steps.getListOfIngredientsID().get(1));
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),true,StatusesAndUrls.STATUS_OK);
        steps.getUsersOrders(true,StatusesAndUrls.STATUS_OK);
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),true,StatusesAndUrls.STATUS_OK);
    }
}
