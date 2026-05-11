package com.test.gitradar.exceptions;

public class UserNotFoundException extends BaseException{
    public UserNotFoundException() {
        super("User not found");
    }
}
