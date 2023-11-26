package com.automates.automates.interfaces;

import com.automates.automates.Model.User;

public interface UserDAO extends AutoCloseable {
    public boolean Register(User newUser);
    public int Login(User user);
    public boolean IsUsernameAlreadyExist(String username);
    public User GetUserById(int id);
    public int getMyClients(int userId);
}
