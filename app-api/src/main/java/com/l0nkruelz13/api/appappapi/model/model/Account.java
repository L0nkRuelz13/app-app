package com.l0nkruelz13.api.appappapi.model.model;

import com.fasterxml.jackson.annotation.JsonProperty;

//TODO encrypt user data

/**
 * Represents a user account
 * 
 * JSON FORMAT {"id":d, "username":s, "password":s}
 * 
 * @author L0nkRuelz13
 */
public class Account {
    
    //define variables
    @JsonProperty("id") private final int id;
    @JsonProperty("username") private final String username;
    @JsonProperty("password") private final String password;

    /**
     * Create a new user {@linkplain Account Account}
     * 
     * @param id {@link Account account's} user id
     * @param username {@link Account account's} username
     * @param password {@link Account account's} password
     */
    public Account(@JsonProperty("id") int id, 
                    @JsonProperty("username") String username, 
                    @JsonProperty("password") String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Retrieves the {@linkplain Account account's} username
     * @return the {@link Account account's} username
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Retrieves the {@linkplain Account account's} id
     * @return the {@link Account account's} id
     */
    public int getId(){
        return this.id;
    }

    /**
     * Retrieves the {@linkplain Account account's} password
     * @return the {@link Account account's} password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Account o) {
            return this.id == o.getId() && this.username == o.getUsername() && this.password == o.getPassword();
        }
        return false;
    }
}
