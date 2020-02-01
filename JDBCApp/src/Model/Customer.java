/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Brian
 */
public class Customer {
    
    private int id;
    private String name;
    private String address;
    private String city;
    private String country;
    private String zipCode;  // why would you make that a sting?
    private String phone;
    private String createDate;
    private String createdBy;
    private String lastUpdateBy;
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public Customer(int id, String name, String address, String city, String country, String zipCode, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
        this.phone = phone;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
    
    public void addAppointment( Appointment newAppointment){
        allAppointments.add(newAppointment);
    }
    
    
    public ObservableList<Appointment> getAllAppointments(){
        return allAppointments;
    }
    
    public void removeAppointments(Appointment apt){
     allAppointments.remove(apt);
            
        }
    
//    
//    public Appointment getAppointment(ObservableList<Appointment> list){
//        for(Appointment apt : list){
//            
//        }
//    }
    
    
    
}
