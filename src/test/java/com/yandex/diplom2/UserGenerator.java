package com.yandex.diplom2;
import java.util.Random;

public class UserGenerator {
    private String email;
    private String password;
    private String name;

    public UserGenerator(){
        this.email = "ds"+ new Random().nextInt(10000) + "@yandex.ru";
        this.password = "12354";
        this.name = "ds";
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }


}
