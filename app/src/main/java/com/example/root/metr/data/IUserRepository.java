package com.example.root.metr.data;


import com.example.root.metr.models.DTO.User;

public interface IUserRepository {
    User getUser();
    void addUser(User user);
    boolean hasUser();
    void deleteUser();
}
