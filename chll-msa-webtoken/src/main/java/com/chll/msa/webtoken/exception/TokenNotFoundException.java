package com.chll.msa.webtoken.exception;

public class TokenNotFoundException extends ErrorException{
    public TokenNotFoundException(String token) {
        super(
                "CTR-0003", String.format("Error, the token [%s] is not registered in the system.", token)
        );
    }
}