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
        List<ServiceProvider> serviceProviders = user.getServiceProviderList();
        if(serviceProviders.size()==0){
            throw new Exception("Unable to connect");
        }
        // finding serviceProvide
        boolean check = false;
        for(ServiceProvider serviceProvider : serviceProviders){
            if(serviceProvider.getCountryList().contains(countryName)){
                check = true;
            }
        }
        if(check == false){
            throw new Exception("Unable to connect");
        }
        ServiceProvider serviceProvider = null;

        for(ServiceProvider serviceProvider1 : serviceProviders){
            if(serviceProvider.getCountryList().contains(countryName) ){
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
        user.getConnectionList().add(connection);
        Country country = new Country();
        country.setCountryName(countryName1);
        user.setCountry(country);
        serviceProvider.getConnectionList().add(connection);
        connectionRepository2.save(connection);
        serviceProviderRepository2.save(serviceProvider);
        userRepository2.save(user);


        return user;

    }
    @Override
    public User disconnect(int userId) throws Exception {
       User user = userRepository2.findById(userId).get();
       if(user.isConnected()==false){
           throw new Exception("Already disconnected");
       }
       user.setConnected(false);
       user.setMaskedIp(null);
       userRepository2.save(user);


       return user;
    }
    @Override
    public User communicate(int senderId, int receiverId) throws Exception {
      User sender = userRepository2.findById(senderId).get();
//      User receiver = userRepository2.findById(receiverId).get();
//      if(sender.getCountry().equals(sender.getCountry())==false){
//
//      }

      return sender;
    }
}
