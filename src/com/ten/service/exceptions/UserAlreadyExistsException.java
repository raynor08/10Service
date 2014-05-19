package com.ten.service.exceptions;

public class UserAlreadyExistsException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -8130823732746240470L;

    public UserAlreadyExistsException(String username) {
        super("The username '" + username + "' is already in use.");
    }
}
