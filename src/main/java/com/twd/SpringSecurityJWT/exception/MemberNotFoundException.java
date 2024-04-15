package com.twd.SpringSecurityJWT.exception;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(Integer id){
        super("Could not found the member with id "+ id);
    }
}
