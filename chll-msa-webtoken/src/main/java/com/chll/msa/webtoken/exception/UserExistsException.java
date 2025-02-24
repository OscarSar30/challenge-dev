package com.chll.msa.webtoken.exception;

public class UserExistsException extends ErrorException{
    public UserExistsException() {
        super(
                "CTR-0001", "Error, user exists. Please contact your administrator."
        );
    }
}