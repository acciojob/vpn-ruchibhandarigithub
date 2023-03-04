package com.driver.services.impl;

import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.model.User;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Override
    public User register(String username, String password, String countryName) throws  Exception{
        List<String> countries = listOfCountry();
        if(countries.contains(countryName.toUpperCase())==false) {
          throw new Exception("Country not found");
        }
        Country country = new Country();
        CountryName countryName1 = CountryName.valueOf(countryName.toUpperCase());
        country.setCountryName(countryName1);
        country.setCode(countryName1.toCode());

        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setCountry(country);
        country.setUser(user);
        country = countryRepository3.save(country);
        user.setConnected(false);
        user.setOriginalIp(country.getCode()+"."+user.getId());
        userRepository3.save(user);
        return user;
    }

    @Override
    public User subscribe(Integer userId, Integer serviceProviderId) {
       User user = userRepository3.findById(userId).get();
       ServiceProvider serviceProvider = serviceProviderRepository3.findById(serviceProviderId).get();
       user.getServiceProviders().add(serviceProvider);
       serviceProvider.getUser().add(user);
       serviceProviderRepository3.save(serviceProvider);
       userRepository3.save(user);
       return user;




    }
    private List<String> listOfCountry(){
        List<String> countryName = new ArrayList<>();
        countryName.add(CountryName.AUS.toString());
        countryName.add(CountryName.CHI.toString());
        countryName.add(CountryName.IND.toString());
        countryName.add(CountryName.JPN.toString());
        countryName.add(CountryName.USA.toString());
        return countryName;
    }
}
