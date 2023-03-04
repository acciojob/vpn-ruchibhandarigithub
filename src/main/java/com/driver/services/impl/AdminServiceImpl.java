package com.driver.services.impl;

import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;

    @Override
    public Admin register(String username, String password) {
        Admin admin = new Admin();
        admin.setUserName(username);
        admin.setPassword(password);
        adminRepository1.save(admin);
        return admin;
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) {
            ServiceProvider serviceProvider = new ServiceProvider();
            serviceProvider.setName(providerName);
            Admin admin = adminRepository1.findById(adminId).get();
           admin.getServiceProviders().add(serviceProvider);
           serviceProvider.setAdmin(admin);
           serviceProvider= serviceProviderRepository1.save(serviceProvider);
           adminRepository1.save(admin);
           return admin;

    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception{
        List<String> countries = listOfCountry();
        if(countries.contains(countryName.toUpperCase())==false) {
          throw new Exception("Country not found");
        }
        Country country = new Country();
        CountryName countryName1 = CountryName.valueOf(countryName.toUpperCase());
        country.setCountryName(countryName1);
        country.setCode(countryName1.toCode());

        ServiceProvider serviceProvider = serviceProviderRepository1.findById(serviceProviderId).get();
        country.setServiceProvider(serviceProvider);
        serviceProvider.getCountries().add(country);
        country =countryRepository1.save(country);
        serviceProvider = serviceProviderRepository1.save(serviceProvider);

        return serviceProvider;

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
