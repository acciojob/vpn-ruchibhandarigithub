package com.driver.services;

import com.driver.model.User;

public interface ConnectionService{
    @Override
    public User connect(int userId, String countryName) throws Exception;
    @Override
    public User disconnect(int userId) throws Exception;
    @Override
    public User communicate(int senderId, int receiverId) throws Exception;

}