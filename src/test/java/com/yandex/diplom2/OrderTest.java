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
    }

    @Test
    @DisplayName("Check that logined User can't create Order w/o ingredients")
    @Description("Send POST request to create Order")
    public void loginedUserCantCreateOrderWOIngredientsTest(){
        steps.registerNewUser()
            .userLogin()
            .createOrderForUser(false,400,"","");
    }

    @Test
    @DisplayName("Check that logined User can create Order with ingredients")
    @Description("Send POST request to create Order")
    public void loginedUserCanCreateOrderWithIngredientsTest(){
        steps.registerNewUser()
            .userLogin()
            .getIngredients()
            .createOrderForUser(true,200,steps.getListOfIngredientsID().get(0),steps.getListOfIngredientsID().get(1));
    }

    @Test
    @DisplayName("Check that logined User can't create Order with wrond ingredient ids")
    @Description("Send POST request to create Order")
    public void loginedUserCantCreateOrderWithWrongIngredientIdsTest(){
        steps.registerNewUser()
            .userLogin()
            .createOrderForUser(false,400,"60d3b41abdacab0026a733c6","609646e4dc916e00276b2870");
    }

    @Test
    @DisplayName("Check that not logined User can't create Order w/o ingredients")
    @Description("Send POST request to create Order")
    public void notLoginedUserCantCreateOrderWOIngredientsTest(){
        steps.createOrderForUser(false,400,"","");
    }

    @Test
    @DisplayName("Check that not logined User can create Order with ingredients")
    @Description("Send POST request to create Order")
    public void notLoginedUserCanCreateOrderWithIngredientsTest(){
        steps.getIngredients()
            .createOrderForUser(true,200,steps.getListOfIngredientsID().get(0),steps.getListOfIngredientsID().get(1));
    }

    @Test
    @DisplayName("Check that not logined User can't create Order with wrond ingredient ids")
    @Description("Send POST request to create Order")
    public void notLoginedUserCantCreateOrderWithWrongIngredientIdsTest(){
        steps.createOrderForUser(false,400,"60d3b41abdacab0026a733c6","609646e4dc916e00276b2870");
    }

    @Test
    @DisplayName("Check that logined User can get list of orders")
    @Description("Send GET request to get Orders")
    public void getListOfOrdersForLoginedUserTest(){
        steps.registerNewUser()
            .userLogin()
            .getIngredients()
            .createOrderForUser(true,200,steps.getListOfIngredientsID().get(0),steps.getListOfIngredientsID().get(1))
            .getUsersOrders(true,200);
    }

    @Test
    @DisplayName("Check that not logined User can get list of orders")
    @Description("Send GET request to get Orders")
    public void getListOfOrdersForNotLoginedUserTest(){
        steps.registerNewUser()
            .getIngredients()
            .createOrderForUser(true,200,steps.getListOfIngredientsID().get(0),steps.getListOfIngredientsID().get(1))
            .getUsersOrders(true,200);
    }
}
