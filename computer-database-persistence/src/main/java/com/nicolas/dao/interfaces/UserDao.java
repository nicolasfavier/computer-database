package com.nicolas.dao.interfaces;

import com.nicolas.models.User;

public interface UserDao {
    
    public User getUser(String login);
 
}