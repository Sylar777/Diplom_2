package com.yandex.diplom2;
import org.junit.*;
import com.yandex.diplom2.Steps.UserSteps;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

public class UserLoginTest {

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
    @DisplayName("Check that user can login")
    @Description("Send POST request to login user")
    public void userCanLoginTest(){
        steps.registerNewUser();
        steps.userLogin();
        steps.checkResponse(steps.getResponseOfUserLogin(),true,StatusesAndUrls.STATUS_OK);
    }

    @Test
    @DisplayName("Check that user can't be login with wrong data")
    @Description("Send POST request to login user")
    public void userCantLoginWithWrongDataTest(){
        steps.registerNewUser();
        steps.userLoginWithWrongData();
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),false,StatusesAndUrls.STATUS_UNAUTHORIZED);

    }
}
