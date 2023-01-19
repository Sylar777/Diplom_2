package com.yandex.diplom2;

import org.junit.*;

import com.yandex.diplom2.Steps.UserSteps;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

public class UserCreationTest {

    private UserSteps steps;

    @Before
    public void setUp() {
        steps = new UserSteps();
    }

    @After
    public void clear(){
        steps.deleteUser();
    }

    @Test
    @DisplayName("Check that user can be created")
    @Description("Send POST request to create user")
    public void userCanBeCreatedTest(){
        steps.registerNewUser();
        steps.checkResponse(steps.getResponseOfUserCreation(),true,StatusesAndUrls.STATUS_OK);
    }

    @Test
    @DisplayName("Check that user with the same credentials can't be created")
    @Description("Send POST request to create user second time")
    public void cantBeCreatedTheSameUserTest(){
        steps.registerNewUser();
        steps.registerExistUser();
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),false,StatusesAndUrls.STATUS_FORBIDDEN);
    }

    @Test
    @DisplayName("Check that user can't be created w/o all fields")
    @Description("Send POST request to create user")
    public void userCantBeCreatedWOAllInfoTest(){
        steps.registerUserWOAllData();
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),false,StatusesAndUrls.STATUS_FORBIDDEN);
    }
}
