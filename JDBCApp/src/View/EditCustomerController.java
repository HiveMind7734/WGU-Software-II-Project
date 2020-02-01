/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.City;
import Model.Customer;
import Model.DataProvider;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jdbcapp.Query;
import static utils.DBConnection.conn;

/**
 * FXML Controller class
 *
 * @author Brian
 */
public class EditCustomerController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField countryField;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Label customerDetailsLbl;
    @FXML
    private TextField cityField;
    @FXML
    private Label customerIdLbl;

    String name;
    String address;
    String city;
    String country;
    String zip;
    String phone;
    int id;
    int active;

    //Create a date/time stamp to enter into the database
    DateTimeFormatter dtf;
    LocalDateTime now;
    String createDate;
    String createdBy;
    String lastUpdate;
    String lastUpdateBy;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public boolean cusExists(int id) {
        for (Customer cus : DataProvider.getAllCustomers()) {
            if (cus.getId() == id) {
                return true;
            }
        }
        return false;
    }

    @FXML
    private void saveBtnHandler(ActionEvent event) throws IOException, SQLException {
        //tCustomerId, tCustomerName, tAddress, tCity, tCountry, tPostalCode, tPhone
        //String = Integer.parseInt(customerIdField.getText());
        name = nameField.getText();
        address = addressField.getText();
        city = cityField.getText();
        country = countryField.getText();
        zip = postalCodeField.getText();
        phone = phoneField.getText();
     //   int id=0;
        active = 1;
        
        System.out.println("name:"+name);
        
        if(name.equals("")){
            System.out.println("You left the name field blank.");
        } else if(address.equals("")){
            System.out.println("You left the address field blank.");
        } else if(city.equals("")){
            System.out.println("You left the city field blank");
        } else if(country.equals("")){
            System.out.println("Youf left the counttry field blank");
        } else if(zip.equals("")){
            System.out.println("You left the postal code field blank");
        } else if(phone.equals("")){
            System.out.println("You left the phone field blank");
        } else{
        
            System.out.println("entered");

        //Create a date/time stamp to enter into the database
        dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        now = LocalDateTime.now();

        createDate = dtf.format(now);
        createdBy = "NULL"; //replace with user later
        lastUpdate = dtf.format(now);
        lastUpdateBy = "NULL";// replace with user object later.

        if (DataProvider.getNewBtn()) {
            
            //Create a connection to the database
            Statement stmt = conn.createStatement();
            String getCountryIdSQL = "SELECT country.countryId FROM country WHERE country.country = '"+country+"'";
            String getCityIdSQL = "SELECT city.cityId   FROM city WHERE city.city = '"+city+"'";
            String getAddressIdSQL = "SELECT address.addressId FROM address WHERE address.address = '"+address+"'";
            String getCustomerIdSQL = "SELECT customer.customerId FROM customer WHERE customer.customerId = (SELECT MAX(customer.customerId) FROM customer)";
            String getAddressId=""; 
            String getCityId=""; 
            String getCountryId=""; 
            String getCustomerId="";
            

 
                      
            //SQL query to save to the Country table in the database--country city address customer
            String sqlCountry = "insert into country( country, createDate, CreatedBy, lastUpdate, LastUpdateBy)"
                    + "values('" + country + "', " + "'" + createDate + "', " + "'" + createdBy + "', " + "'" + lastUpdate + "', " + "'" + lastUpdateBy + "'" + ")";
            stmt.executeUpdate(sqlCountry);
                       ResultSet rs = stmt.executeQuery(getCountryIdSQL);
              while (rs.next()) {
                   getCountryId = rs.getString("countryId");
                 
              }
            //SQL query to save to the City table in the database (cityId, city, 4)
            String sqlCity = "insert into city(city, countryId, createDate, CreatedBy, lastUpdate, LastUpdateBy)"
                    + "values('" + city + "', " + "'" + getCountryId + "', " + "'" + createDate + "', " + "'" + createdBy + "', " + "'" + lastUpdate + "', " + "'" + lastUpdateBy + "'" + ")";
            stmt.executeUpdate(sqlCity);
                rs = stmt.executeQuery(getCityIdSQL);
              while (rs.next()) {
                      getCityId = rs.getString("cityId");
                 
              }
            
       
            String sqlAddress = "insert into address( address, address2, cityId, postalCode, phone, createDate, CreatedBy, lastUpdate, LastUpdateBy)"
                    + "values('" + address + "', " + "'" + address + "', " + "'" + getCityId + "', " + "'" + zip + "', " + "'" + phone + "', " + "'" + createDate + "', " + "'" + createdBy + "', " + "'" + lastUpdate + "', " + "'" + lastUpdateBy + "'" + ")";
            stmt.executeUpdate(sqlAddress);
                    rs = stmt.executeQuery(getAddressIdSQL);
              while (rs.next()) {
                       getAddressId = rs.getString("addressId");
                 
              }
              
    
            
            
            //SQL query to save a customer to the database
            String sqlCustomer = "insert into customer( customerName, addressId, active, createDate, CreatedBy, lastUpdate, LastUpdateBy)"
                    + "values('" + name + "', " + "'" + getAddressId + "', " + "'" + active + "', " + "'" + createDate + "', " + "'" + createdBy + "', " + "'" + lastUpdate + "', " + "'" + lastUpdateBy + "'" + ")";
            System.out.println(sqlCustomer);
            stmt.executeUpdate(sqlCustomer);
            
                 rs = stmt.executeQuery(getCustomerIdSQL);
              while (rs.next()) {
                       getCustomerId = rs.getString("customerId");
                 
              }
            
            System.out.println(getCustomerId);
            Customer newCustomer = new Customer(Integer.parseInt(getCustomerId), name, address, city, country, zip, phone);
            DataProvider.addCustomer(newCustomer);
          //  DataProvider.setNewBtn(0);

        }

        if (!DataProvider.getNewBtn()) {
            int idCus = Integer.parseInt(customerIdLbl.getText());
            System.out.println(idCus);
            Query.makeQuery("UPDATE customer SET customerName = '" + name + "' WHERE customerId = '" + idCus + "'");
            Query.makeQuery("UPDATE address, customer SET address.address = '" + address + "', address.phone = '"+phone+"',address.postalCode = '"+zip+"' WHERE customer.customerId = '" + idCus + "'" +" AND address.addressId = customer.addressId");
            Query.makeQuery("UPDATE city, address, customer SET city.city = '" + city + "' WHERE customer.customerId = '" + idCus + "'" +" AND address.addressId = customer.addressId AND city.cityId = address.cityId");
            Query.makeQuery("UPDATE country, city, address, customer SET country.country = '" + country + "' WHERE customer.customerId = '" + idCus + "'" +" AND address.addressId = customer.addressId AND city.cityId = address.cityId AND country.countryId = city.countryId");
            //Query.makeQuery("UPDATE address, customer SET address = '" + address + "', postalCode = '" + zip + "', phone = '" + phone + "' WHERE address.addressId = customer.customerId AND customer.customerId = '" + idCus + "'");
            //Query.makeQuery("UPDATE city,address, customer,country SET city = '" + city + "', country = '" + country + "' WHERE country.countryId = city.cityId AND city.cityId = address.addressId AND address.addressId = customer.customerId AND customer.customerId = '" + idCus + "'");
            editCustomer(idCus);

        }

//                 String sqlStatementI = "INSERT INTO user(userId, userName, password, active, createDate, createdBy, lastUpdate, lastUpdateBy)"
//                + "VALUES("
//                + "'" + userID + "', " + "'" + userName + "', " + "'" + password + "', " + "'" + active + "', "
//                + "'" + createDate + "', " + "'" + createdBy + "', " + "'" + lastUpdate + "', "
//                + "'" + lastUpdateBy + "'" + ")";
        }
        switchScreen(event, "Customer.fxml");

    }

    public Customer editCustomer(int id) {
        for (Customer cus : DataProvider.getAllCustomers()) {
            if (cus.getId() == id) {
                cus.setAddress(address);
                cus.setCity(city);
                cus.setCountry(country);
                cus.setPhone(phone);
                cus.setZipCode(zip);
                cus.setName(name);
                return cus;
            }
        }
        return null;
    }

    @FXML
    private void cancelBtnHandler(ActionEvent event) throws IOException {
        switchScreen(event, "Customer.fxml");
    }

    //Method to switchScreen.
    private void switchScreen(ActionEvent event, String path) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();   //lets the event handler know that this is a button press
        scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void sendCustomer(Customer cus) {

        customerIdLbl.setText(String.valueOf(cus.getId()));
        nameField.setText(String.valueOf(cus.getName()));
        addressField.setText(String.valueOf(cus.getAddress()));
        cityField.setText(String.valueOf(cus.getCity()));
        countryField.setText(String.valueOf(cus.getCountry()));
        postalCodeField.setText(String.valueOf(cus.getZipCode()));
        phoneField.setText(String.valueOf(cus.getPhone()));
//        if (part instanceof InHouse) {
//            ModifyPartInHouseRBtn.setSelected(true);
//            ModifyPartMachIDTxtField.setText(String.valueOf(((InHouse) part).getMachineId()));
//        } else {
//            ModifyPartOutsourcedRBtn.setSelected(true);
//            ModifyPartMachIDTxtField.setText(((Outsourced) part).getCompanyName());
//        }
    }

}
