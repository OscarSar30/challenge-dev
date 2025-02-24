package com.chll.msa.webtoken.exception;

public class UserException extends ErrorException{
    public UserException(String userId) {
        super(
                "CTR-0002", String.format("Error, the user with id [%s] is not registered in the system.", userId)
        );
    }
}