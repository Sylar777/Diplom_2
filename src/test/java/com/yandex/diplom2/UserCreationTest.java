package com.yandex.diplom2;

import org.junit.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

public class UserCreationTest {

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
        steps.checkResponse(steps.getResponseOfUserCreation(),true,StatusesAndUrls.STATUS_OK);
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
