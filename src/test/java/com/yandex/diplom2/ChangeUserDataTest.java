package com.yandex.diplom2;
import org.junit.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
public class ChangeUserDataTest {

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
    @DisplayName("Check that logined User can change all data")
    @Description("Send PATCH request to change user's data")
    public void loginedUserCanChangeNameDataTest(){
        steps.registerNewUser()
            .userLogin()
            .changeName(true, 200)
            .changePassword(true, 200)
            .changeEmail(true, 200);
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
        steps.registerNewUser()
            .changeName(false, 401);
    }

    @Test
    @DisplayName("Check that not logined User can't change password Data")
    @Description("Send PATCH request to change user's data")
    public void notLoginedUserCantChangePasswordDataTest(){
        steps.registerNewUser()
            .changePassword(false, 401);
    }

    @Test
    @DisplayName("Check that not logined User can't change email Data")
    @Description("Send PATCH request to change user's data")
    public void notLoginedUserCantChangeEmailDataTest(){
        steps.registerNewUser()
            .changeEmail(false, 401);
    }
}
