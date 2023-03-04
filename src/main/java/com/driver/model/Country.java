package com.driver.model;

import javax.persistence.*;

// Note: Do not write @Enumerated annotation above CountryName in this model.
public class Country
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   @Enumerated(value= EnumType.STRING)
   CountryName countryName;
   String code;
   @ManyToOne
   @JoinColumn
   private ServiceProvider serviceProvider;
   @ManyToOne
   @JoinColumn
   private User user;

   public Country() {
   }

   public Country(int id, CountryName countryName, String code) {
      this.id = id;
      this.countryName = countryName;
      this.code = code;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public CountryName getCountryName() {
      return countryName;
   }

   public void setCountryName(CountryName countryName) {
      this.countryName = countryName;
   }

   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public ServiceProvider getServiceProvider() {
      return serviceProvider;
   }

   public void setServiceProvider(ServiceProvider serviceProvider) {
      this.serviceProvider = serviceProvider;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }
}