package com.yandex.diplom2;

public class StatusesAndUrls {

    public static final String
        REGISTER_USER = "/api/auth/register",
        DELETE_USER = "/api/auth/user",
        LOGIN_USER = "/api/auth/login",
        DATA_USER = "/api/auth/user",
        INGREDIENTS_USER = "api/ingredients",
        ORDERS_USER = "api/orders";

    public static final int
        STATUS_OK = 200,
        STATUS_CREATED = 201,
        STATUS_ACCEPTED = 202,
        STATUS_NON_AUTHORITATIVE_INFO = 203,
        STATUS_BAD_REQUEST = 400,
        STATUS_UNAUTHORIZED = 401,
        STATUS_FORBIDDEN = 403;
}
