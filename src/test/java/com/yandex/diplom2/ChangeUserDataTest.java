package com.yandex.diplom2;
import org.junit.*;

import com.yandex.diplom2.Steps.UserSteps;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
public class ChangeUserDataTest {

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
    @DisplayName("Check that logined User can change all data")
    @Description("Send PATCH request to change user's data")
    public void loginedUserCanChangeNameDataTest(){
        steps.registerNewUser();
        steps.userLogin();
        steps.changeName(true, StatusesAndUrls.STATUS_OK);
        steps.changePassword(true, StatusesAndUrls.STATUS_OK);
        steps.changeEmail(true, StatusesAndUrls.STATUS_OK);
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),true,StatusesAndUrls.STATUS_OK);
    }

    /*
     * Из Документация API
     *  PATCH https://stellarburgers.nomoreparties.site/api/auth/user — эндпоинт обновления данных о пользователе.
        Если выполнить запрос без авторизации, вернётся код ответа 401 Unauthorized.

        возвразается 200 - соответственно, то, что падают тесты
        - notLoginedUserCantChangeNameDataTest
        - notLoginedUserCantChangePasswordDataTest
        - notLoginedUserCantChangeEmailDataTest
        это найденные автотестами баги.
     */
    @Test
    @DisplayName("Check that not logined User can't change name Data")
    @Description("Send PATCH request to change user's data")
    public void notLoginedUserCantChangeNameDataTest(){
        steps.registerNewUser();
        steps.changeName(false, StatusesAndUrls.STATUS_UNAUTHORIZED);
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),false,StatusesAndUrls.STATUS_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Check that not logined User can't change password Data")
    @Description("Send PATCH request to change user's data")
    public void notLoginedUserCantChangePasswordDataTest(){
        steps.registerNewUser();
        steps.changePassword(false, StatusesAndUrls.STATUS_UNAUTHORIZED);
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),false,StatusesAndUrls.STATUS_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Check that not logined User can't change email Data")
    @Description("Send PATCH request to change user's data")
    public void notLoginedUserCantChangeEmailDataTest(){
        steps.registerNewUser();
        steps.changeEmail(false, StatusesAndUrls.STATUS_UNAUTHORIZED);
        steps.checkResponse(steps.getResponseOfUserNotRegistrationAndLogin(),false,StatusesAndUrls.STATUS_UNAUTHORIZED);
    }
}
