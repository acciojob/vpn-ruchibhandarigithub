package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ConnectionRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    UserRepository userRepository2;
    @Autowired
    ServiceProviderRepository serviceProviderRepository2;
    @Autowired
    ConnectionRepository connectionRepository2;

    @Override
    public User connect(int userId, String countryName) throws Exception
    {
         User user = userRepository2.findById(userId).get();
         if(user.isConnected()==true){
             throw new Exception("Already connected");
         }
        if(user.getCountry().getCountryName().equals(countryName)){
           return user;
        }
        List<ServiceProvider> serviceProviders = user.getServiceProviders();
        if(serviceProviders.size()==0){
            throw new Exception("Unable to connect");
        }
        // finding serviceProvide
        boolean check = false;
        for(ServiceProvider serviceProvider : serviceProviders){
            if(serviceProvider.getCountries().contains(countryName)){
                check = true;
            }
        }
        if(check == false){
            throw new Exception("Unable to connect");
        }
        ServiceProvider serviceProvider = null;

        for(ServiceProvider serviceProvider1 : serviceProviders){
            if(serviceProvider.getCountries().contains(countryName) ){
                if((serviceProvider == null) || (serviceProvider1.getId() > serviceProvider1.getId())){
                 serviceProvider= serviceProvider1;
                }
            }
        }
        CountryName countryName1 = CountryName.valueOf(countryName);
        user.setMaskedIp(countryName1.toCode()+"."+serviceProvider.getId()+"."+user.getId());
        user.setConnected(true);
        Connection connection = new Connection();
        connection.setServiceProvider(serviceProvider);
        connection.setUser(user);
        user.getConnections().add(connection);
        serviceProvider.getConnections().add(connection);
        connectionRepository2.save(connection);
        serviceProviderRepository2.save(serviceProvider);
        userRepository2.save(user);


        return user;

    }
    @Override
    public User disconnect(int userId) throws Exception {
       User user = new User();
       return user;
    }
    @Override
    public User communicate(int senderId, int receiverId) throws Exception {
      User user = new User();
      return user;
    }
}
