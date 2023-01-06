package com.yandex.diplom2;

import org.junit.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

public class UserCreationTest {
    Steps steps;

    @Before
    public void setUp() {
        steps = new Steps();
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
    }

    @Test
    @DisplayName("Check that user with the same credentials can't be created")
    @Description("Send POST request to create user second time")
    public void cantBeCreatedTheSameUserTest(){
        steps.registerNewUser()
            .registerExistUser();
    }

    @Test
    @DisplayName("Check that user can't be created w/o all fields")
    @Description("Send POST request to create user")
    public void userCantBeCreatedWOAllInfoTest(){
        steps.registerUserWOAllData();
    }
}
