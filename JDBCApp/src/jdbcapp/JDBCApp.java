/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcapp;

import Model.Appointment;
import Model.City;
import Model.Country;
import Model.Customer;
import Model.DataProvider;
import View.AppointmentController;
import View.EditCustomerController;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import static utils.DBConnection.conn;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static jdbcapp.Query.makeQuery;
import utils.DBConnection;
import static utils.DBConnection.conn;

/**
 *
 * @author Brian
 */
public class JDBCApp extends Application {

    @Override
    public void init() {
        System.out.println("This is the init method. Anything goes her for the startup.");
    }
    
           ObservableList<Appointment> allApt = FXCollections.observableArrayList();
    public ObservableList<Appointment> getAssociatedAppointments() {
        return allApt;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/LoginScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void stop() {
        System.out.println("This is the stop() method");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {

  

        DBConnection.startConnection();
        
        
        
        
        
        
        
        
        
        
        //get all the Customer data.
                try {
            // TODO

            int tCustomerId;
            String tCustomerName;
            String tAddress;
            // String tAddress2;
            String tCity;
            String tCountry;
            String tPostalCode;
            String tPhone;
            ObservableList<Customer> customerList = FXCollections.observableArrayList();

            Statement stmt = null;
            try {
                stmt = conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(EditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }

            String sqlStatement
                    = "SELECT customer.customerId, customer.customerName, address.address, address.address2, address.postalCode, city.cityId, city.city, country.country, address.phone "
                    + "FROM customer, address, city, country "
                    + "WHERE customer.addressId = address.addressId AND address.cityId = city.cityId AND city.countryId = country.countryId "
                    + "ORDER BY customer.customerName";

            ResultSet rs = stmt.executeQuery(sqlStatement);

            while (rs.next()) {
                tCustomerId = rs.getInt("customer.customerId");

                tCustomerName = rs.getString("customer.customerName");

                tAddress = rs.getString("address.address");

                //tAddress2 = rs.getString("address.address2");
                // tCity = new City(rs.getInt("city.cityId"), rs.getString("city.city"));
                tCity = rs.getString("city.city");

                tCountry = rs.getString("country.country");

                tPostalCode = rs.getString("address.postalCode");

                tPhone = rs.getString("address.phone");

                customerList.add(new Customer(tCustomerId, tCustomerName, tAddress, tCity, tCountry, tPostalCode, tPhone));

                DataProvider.setAllCustomers(customerList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
                
        //get all the country data        
               String countryName =""; 
               int countryId; 
              Statement stmt = null;
            try {
                stmt = conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(EditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
             
            String sqlStatement = "SELECT country.countryId, country.country FROM country";
            ResultSet rs = stmt.executeQuery(sqlStatement);
           while (rs.next()) {
               countryName = rs.getString("country.country");  
               countryId = rs.getInt("country.countryId");
               
               DataProvider.getAllCountries().add(new Country(countryId, countryName));
           }
           
          
        //get all the city Data
     
                String cityName =""; 
               int cityId;
               int cityCountryId;
         stmt = null;
            try {
                stmt = conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(EditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String sqlAddCity = "SELECT city.cityId, city.city, city.countryId FROM city";
            rs = stmt.executeQuery(sqlAddCity);
                  while (rs.next()) {
               cityName = rs.getString("city.city");  
               cityId = rs.getInt("city.cityId");
               cityCountryId = rs.getInt("city.countryId");
               
               DataProvider.getAllCities().add(new City(cityId,cityName, cityCountryId));
           }
                  
         //get all of the apt types
         String aptType;
              stmt = null;
            try {
                stmt = conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(EditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String sqlAptTypes = "SELECT appointment.type FROM appointment";
                   rs = stmt.executeQuery(sqlAptTypes);
                  while (rs.next()) {
               aptType = rs.getString("appointment.type");  
                  //    System.out.println(aptType);
               
               
               
             
               
             //  DataProvider.getAllCities().add(new City(cityId,cityName, cityCountryId));
           }
           DataProvider.getAppointmentTypes().add("Interview");
           DataProvider.getAppointmentTypes().add("Medical checkup"); 
           DataProvider.getAppointmentTypes().add("Eye exam"); 
           DataProvider.getAppointmentTypes().add("Radiologist"); 
            
        
                
                
                
     
           
                
          //Get all the appointment data      
          //need help adding appointment data to the customer appointment OL      
             
                                   try {
         ZoneId zid = ZoneId.systemDefault();
            LocalDateTime start;
            LocalDateTime end;
            String title="";
            String type="";
            int customerId=0;
            int aptId=0;
        
            Statement stmt1 = null;
            try {
                stmt1 = conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(EditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }

            String sqlApts = "SELECT  appointment.appointmentId, appointment.customerId, appointment.start, appointment.end, appointment.title, appointment.type " + "FROM appointment";
//               

             rs = stmt1.executeQuery(sqlApts);

            while (rs.next()) {
                
                aptId = rs.getInt("appointment.appointmentId");
                
                customerId = rs.getInt("appointment.customerId");

                start = rs.getTimestamp("appointment.start").toLocalDateTime();
 
                ZonedDateTime newzdtStart = start.atZone(ZoneId.of("UTC"));
        	ZonedDateTime newLocalStart = newzdtStart.withZoneSameInstant(zid);
                LocalDateTime newSTime = newLocalStart.toLocalDateTime();
                
         
           
                 
        
           
           
                
       
                
                end = rs.getTimestamp("appointment.end").toLocalDateTime();
                ZonedDateTime newzdtEnd = end.atZone(ZoneId.of("UTC"));
        	ZonedDateTime newLocalEnd = newzdtEnd.withZoneSameInstant(zid);
                LocalDateTime newETime = newLocalEnd.toLocalDateTime();
               
        	
          
                title = rs.getString("appointment.title");

                type = rs.getString("appointment.type");
                
                
               // Appointment apt = new Appointment(customerId,title,type,start,end);
               // appointmentList.add(new Appointment(customerId,title,type,start,end));
             
               for(Customer cus : DataProvider.getAllCustomers()){
                   if(cus.getId() == customerId){
                       cus.addAppointment(new Appointment(aptId, customerId,title,type,newSTime,newETime));
                   }
               }
               AppointmentController.appointmentList1.add(new Appointment(aptId, customerId,title,type,newSTime,newETime));
               
               
         }
                
        } catch (SQLException ex) {
            Logger.getLogger(EditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
                                   
               
                                   
                                   
       //logically make it so they can't select hours out of business times

        DataProvider.getBusinessHours().add("08:00");
        DataProvider.getBusinessHours().add("09:00");
        DataProvider.getBusinessHours().add("10:00");
        DataProvider.getBusinessHours().add("11:00");
        DataProvider.getBusinessHours().add("12:00");
        DataProvider.getBusinessHours().add("13:00");//1
        DataProvider.getBusinessHours().add("14:00");//2
        DataProvider.getBusinessHours().add("15:00");//3
        DataProvider.getBusinessHours().add("16:00");//4
        DataProvider.getBusinessHours().add("17:00");//5

          ZoneId zid = ZoneId.systemDefault();
        LocalDateTime test1 = null;
        ZonedDateTime test2 = null;
       
       
//        for (int i = 0; i <= 9; i++) {
//             test1 = LocalDateTime.of(LocalDate.now(), LocalTime.of ( 0+i , 00 ));
//        test2 = test1.atZone(zid).withZoneSameInstant(ZoneId.of("UTC"));
//        
//        DataProvider.businessHours.add(test2.toLocalTime().toString());
//        
//        }
//        for (int i = 10; i <= 23; i++) {
//            test1 = LocalDateTime.of(LocalDate.now(), LocalTime.of ( i , 00 ));
//        test2 = test1.atZone(zid).withZoneSameInstant(ZoneId.of("UTC"));
//        DataProvider.businessHours.add(test2.toLocalTime().toString());
//  
//            
//        }
        
    LocalDateTime test3 = LocalDateTime.of(LocalDate.now(), LocalTime.of ( 01 , 00 ));
  ZonedDateTime test4 = test3.atZone(zid).withZoneSameInstant(ZoneId.of("UTC"));

        System.out.println(  test4.toLocalTime());

    
        

                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
        //-------------------------------------------------------------------------------------------------------------------------      
        //Create Statement Object to grab all the data from the database
//        Statement stmt = conn.createStatement();

        //Write SQL statement to select everything from the user table.
//               String sqlStatement = "SELECT * FROM user";
        //Execute Statement and Create ResultSet object
//              ResultSet result = stmt.executeQuery(sqlStatement);
        //Get all records from ResultSet Object and display to the console for User table
//        while(result.next())
//        {
//            System.out.print(result.getInt("userId") + ", " );
//            System.out.print(result.getString("userName") + ", ");
//            System.out.print(result.getString("password") + ", " );
//            System.out.print(result.getInt("active") + ", " );
//            System.out.print(result.getDate("createDate") + " ");
//            System.out.print(result.getTime("createDate") + ", " );       
//            System.out.print(result.getString("createdBy") + ", " );
//            System.out.print(result.getDate("lastUpdate") + " ");
//            System.out.print(result.getTime("lastUpdate") + ", " );
//            System.out.print(result.getString("lastUpdateBy"));
//            System.out.println("");
//        }
        //Create Scanner object
//        Scanner keyboard = new Scanner(System.in);

        //Create a scanner class to show how to update the data in the database
//        System.out.print("Enter userID: ");
//        userID = keyboard.nextInt();
//
//        System.out.print("Enter userName: ");
//        userName = keyboard.next();
//
//        System.out.print("Enter password: ");
//        password = keyboard.next();
//
//        System.out.print("Enter active: ");
//        active = keyboard.nextInt();
//
//        System.out.print("Enter createDate(yyyy-mm-dd): ");
//        createDate = keyboard.next();
//
//        System.out.print("Enter createdBy: ");
//        createdBy = keyboard.next();
//
//        System.out.print("Enter lastUpdate(yyyy-mm-dd): ");
//        lastUpdate = keyboard.next();
//
//        System.out.print("Enter lastUpdateBy: ");
//        lastUpdateBy = keyboard.next();

//        userID = 9;
//        userName = "Briantd6";
//        password = "p@ssword1234";
//        active = 1;
//        createDate = "2019-02-05";
//        createdBy = "Brian";
//        lastUpdate = "2019-02-05";
//        lastUpdateBy = "Brian";

//        //Create SQL instert Statement
//        String sqlStatementI = "INSERT INTO user(userId, userName, password, active, createDate, createdBy, lastUpdate, lastUpdateBy)"
//                + "VALUES("
//                + "'" + userID + "', " + "'" + userName + "', " + "'" + password + "', " + "'" + active + "', "
//                + "'" + createDate + "', " + "'" + createdBy + "', " + "'" + lastUpdate + "', "
//                + "'" + lastUpdateBy + "'" + ")";

        //Execute insert Statement
     //   stmt.executeUpdate(sqlStatementI);
//         result = stmt.executeQuery(sqlStatement);

        //Get all records from ResultSet Object and display to the console for User table
//        while(result.next())
//        {
//            System.out.print(result.getInt("userId") + ", " );
//            System.out.print(result.getString("userName") + ", ");
//            System.out.print(result.getString("password") + ", " );
//            System.out.print(result.getInt("active") + ", " );
//            System.out.print(result.getDate("createDate") + " ");
//            System.out.print(result.getTime("createDate") + ", " );       
//            System.out.print(result.getString("createdBy") + ", " );
//            System.out.print(result.getDate("lastUpdate") + " ");
//            System.out.print(result.getTime("lastUpdate") + ", " );
//            System.out.print(result.getString("lastUpdateBy"));
//            System.out.println("");
//        }
        //-------------------------------------------------------------------------------------------------------------------------          
        

                
              
        launch(args);
        DBConnection.closeConnection();

    }

}
