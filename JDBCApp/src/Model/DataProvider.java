/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Brian
 */
public class DataProvider {
    private static ObservableList<User> allUsres = FXCollections.observableArrayList();
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<Customer> allFilteredCustomers = FXCollections.observableArrayList();
    private static ObservableList<City> allCities = FXCollections.observableArrayList();
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private static ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
    public static ObservableList<String> businessHours = FXCollections.observableArrayList();
    public static ObservableList<Appointment> filteredBusinessHours = FXCollections.observableArrayList();
    public static ObservableList<ZonedDateTime> businessHours1 = FXCollections.observableArrayList();
    //private static 
    private static String lastLoggedInUser;
    private static boolean newBtn;

    public static ObservableList<Appointment> getFilteredBusinessHours() {
        return filteredBusinessHours;
    }
    
    public static void addFilteredApt(Appointment apt) {
        filteredBusinessHours.add(apt);
    }
    public static ObservableList<String> getAppointmentTypes() {
        return appointmentTypes;
    }

    public static void setAppointmentTypes(ObservableList<String> appointmentTypes) {
        DataProvider.appointmentTypes = appointmentTypes;
    }
    
    
    
    
    public static void setNewBtn(int switch1){
        if(switch1 == 1){
            newBtn = true;
          
        
        }else
            newBtn = false;
      
 
    }

    public static boolean getNewBtn() {
        return newBtn;
    }
    
    public static void addAppointmentType(String type){
        appointmentTypes.add(type);
    }
    
    public static void addCustomer(Customer customer){
        allCustomers.add(customer);
    }
    
    public static int count(){
        return allCustomers.size();
    }

    public static ObservableList<User> getAllUsres() {
        return allUsres;
    }

    public static void setAllUsres(ObservableList<User> allUsres) {
        DataProvider.allUsres = allUsres;
    }

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static void setAllCustomers(ObservableList<Customer> allCustomers) {
        DataProvider.allCustomers = allCustomers;
    }

    public static ObservableList<City> getAllCities() {
        return allCities;
    }

    public static void setAllCities(ObservableList<City> allCities) {
        DataProvider.allCities = allCities;
    }

    public static ObservableList<Country> getAllCountries() {
        return allCountries;
    }

    public static void setAllCountries(ObservableList<Country> allCountries) {
        DataProvider.allCountries = allCountries;
    }

//    public static ArrayList<String> getAppointmentTypes() {
//        return appointmentTypes;
//    }
//
//    public static void setAppointmentTypes(ArrayList<String> appointmentTypes) {
//        DataProvider.appointmentTypes = appointmentTypes;
//    }

    public static ObservableList<String> getBusinessHours() {
        return businessHours;
    }

    public static void setBusinessHours(ObservableList<String> businessHours) {
        DataProvider.businessHours = businessHours;
    }

    public static String getLastLoggedInUser() {
        return lastLoggedInUser;
    }

    public static void setLastLoggedInUser(String lastLoggedInUser) {
        DataProvider.lastLoggedInUser = lastLoggedInUser;
    }

    public static ObservableList<Customer> getAllFilteredCustomers() {
        return allFilteredCustomers;
    }
    
    public static void deleteCustomer(Customer cus){
        allCustomers.remove(cus);
    }
    
        
    public static void deleteAppointment(Appointment apt){
      
         
        //cycle through the ol of customers to find and remove the appointment?
       for(Customer cus : getAllCustomers()){
         
               cus.getAllAppointments().remove(apt);
       }
    }
    
    
    
    
}
