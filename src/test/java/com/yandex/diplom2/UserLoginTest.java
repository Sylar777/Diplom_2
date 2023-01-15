package com.yandex.diplom2;
import org.junit.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

public class UserLoginTest {

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
    @DisplayName("Check that user can login")
    @Description("Send POST request to login user")
    public void userCanLoginTest(){
        steps.registerNewUser();
        steps.checkResponse(steps.getResponseOfUserCreation(),true,StatusesAndUrls.STATUS_OK);
        steps.userLogin();
        steps.checkResponse(steps.getResponseOfUserLogin(),true,StatusesAndUrls.STATUS_OK);
    }

    @Test
    @DisplayName("Check that user can't be login with wrong data")
    @Description("Send POST request to login user")
    public void userCantLoginWithWrongDataTest(){
        steps.registerNewUser();
        steps.checkResponse(steps.getResponseOfUserCreation(),true,StatusesAndUrls.STATUS_OK);
        steps.userLoginWithWrongData();
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),false,StatusesAndUrls.STATUS_UNAUTHORIZED);

    }
}
