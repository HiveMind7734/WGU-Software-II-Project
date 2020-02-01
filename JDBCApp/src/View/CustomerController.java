/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Appointment;
import Model.Customer;
import Model.DataProvider;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jdbcapp.Query;
import static utils.DBConnection.conn;
import Model.AlertMessage;

/**
 * FXML Controller class
 *
 * @author Brian
 */
public class CustomerController implements Initializable {

    Stage stage;
    Parent scene;
    @FXML
    private TableView<Customer> CustomerTableView;
    @FXML
    private TableColumn<Customer, String> cusNameCol;
    @FXML
    private TableColumn<Customer, String> cusPhoneCol;
    @FXML
    private TableColumn<Customer, String> cusZipCol;
    @FXML
    private TableColumn<Customer, Integer> cusIDCol;
    @FXML
    private TableColumn<Customer, String> cusCountryCol;
    @FXML
    private Label customerLbl;
    @FXML
    private Button customerNewBtn;
    @FXML
    private Button customerEditBtn;
    @FXML
    private Button customerDelBtn;
    @FXML
    private TextField textField;
    @FXML
    private Button mainMenuBtn;
    @FXML
    private Button newAptBtn;
    @FXML
    private Button searchBtn;
    @FXML
    private Button schedule;

    public boolean search(int id) {
        //make an enhanced for loop (don't have to guess how many items are inside)
        for (Customer customer : DataProvider.getAllCustomers()) {
            if (id == customer.getId()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean delete(int id){
        for(Customer cus: DataProvider.getAllCustomers())
        {
            if(cus.getId() == id)
                return DataProvider.getAllCustomers().remove(cus);
        }
        return false;
    }
    
    
    public boolean update(int id, Customer customer){
        int index = -1;
        
        for(Customer cus: DataProvider.getAllCustomers()){
            index++;
            if(cus.getId() == id){
               DataProvider.getAllCustomers().set(index, customer);
               return true;
            }        
        }
        return false;
    }
    
    public Customer selectCustomer(int id){
        for(Customer cus: DataProvider.getAllCustomers()){
            if(cus.getId() == id){
                return cus;
            }
        }
        return null;
    }
    
    public ObservableList<Customer> filter(String text){
        if( !(DataProvider.getAllFilteredCustomers()).isEmpty()){
            DataProvider.getAllFilteredCustomers().clear();
        }
        
        for(Customer cus : DataProvider.getAllCustomers()){
         //   System.out.println(cus.getName());
            if(cus.getName().contains(text)){
                DataProvider.getAllFilteredCustomers().add(cus);
            }
        }
            
            if(DataProvider.getAllFilteredCustomers().isEmpty())
                return DataProvider.getAllCustomers();
            else
                return DataProvider.getAllFilteredCustomers();
         
        
        
    }
    
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
        
        populateTable();
        

        
        
        
        
     //   CustomerTableView.getSelectionModel().select(selectCustomer(3));
   //   CustomerTableView.setItems(filter("Bfdsa"));
     
            
    }

    private void populateTable() {
        //populate the tableView
        CustomerTableView.setItems(DataProvider.getAllCustomers());
        
        //name phone zip Cusid coutnry
        //set cell factory
        cusNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        cusPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cusZipCol.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        cusIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        cusCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        
    }


    @FXML
    private void customerNewBtnHandler(ActionEvent event) throws IOException {
              DataProvider.setNewBtn(1);
        switchScreen(event, "EditCustomer.fxml");
    }

    @FXML
    private void customerEditBtnHandler(ActionEvent event) throws IOException {
                 if(CustomerTableView.getSelectionModel().getSelectedItem()==null){
          alertMsg.sendAlert("Please select a customer to edit.");
      }
         DataProvider.setNewBtn(0);
         FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditCustomer.fxml"));
            loader.load();
            
            EditCustomerController ECController = loader.getController();
            Customer cus = CustomerTableView.getSelectionModel().getSelectedItem();
            ECController.sendCustomer(cus);
            
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
  //      switchScreen(event, "EditCustomer.fxml");
    }

    @FXML
    private void customerDelBtnHandler(ActionEvent event) throws SQLException {
        //Inventory.deletePart(partsTable.getSelectionModel().getSelectedItem());
        
                 if(CustomerTableView.getSelectionModel().getSelectedItem()==null){
          alertMsg.sendAlert("Please select a customer to delete.");
      }
        int rowId = CustomerTableView.getSelectionModel().getSelectedItem().getId();
        
        Customer customer1 = CustomerTableView.getSelectionModel().getSelectedItem();

        Statement stmt = conn.createStatement();
        //  String deleteCus = "DELETE FROM customer, address, city, country WHERE customer.addressId = address.addressId AND address.cityId = city.cityId AND city.countryId = country.countryId";
        
        String sqlAddId = "SELECT addressId FROM customer WHERE customerId = '"+rowId+"'";
        String addId="";
          ResultSet rs = stmt.executeQuery(sqlAddId);
              while (rs.next()) {
                   addId = rs.getString("addressId");
              }
              
         String sqlcityId="SELECT cityId FROM address WHERE addressId = '"+addId+"'";
         String cityId="";
         rs = stmt.executeQuery(sqlcityId);
              while (rs.next()) {
                   cityId = rs.getString("cityId");
              }
              
         String sqlcountryId="SELECT countryId FROM city WHERE cityId = '"+cityId+"'";  
         String countryId="";
          rs = stmt.executeQuery(sqlcountryId);
              while (rs.next()) {
                   countryId = rs.getString("countryId");
              }
        
       



        
       
        
        String deleteCus = "DELETE FROM customer WHERE customer.customerId = " + rowId;
        stmt.executeUpdate(deleteCus);
        String deleteAdd = "DELETE FROM address WHERE addressId = "+addId;
        stmt.executeUpdate(deleteAdd);
        String deleteCity = "DELETE FROM city WHERE cityId = " + cityId;
        stmt.executeUpdate(deleteCity);
        String deleteCountry = "DELETE FROM country WHERE countryId = " + countryId;
  
       int isDeleted = stmt.executeUpdate(deleteCountry);
        
        if(isDeleted == 1){
            delete(rowId);
            populateTable();
        }
    //    get selected deleted item and remove from the DataProvider observable list.
        DataProvider.deleteCustomer(customer1);
        
        



    }

    @FXML
    private void mainMenuBtnHandler(ActionEvent event) throws IOException {
        switchScreen(event, "Menu.fxml");
    }

    private void switchScreen(ActionEvent event, String path) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();   //lets the event handler know that this is a button press
        scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void newAptBtnHandler(ActionEvent event) throws IOException {
              if(CustomerTableView.getSelectionModel().getSelectedItem()==null){
          alertMsg.sendAlert("Please select a customer from the list to make a new appointment for them.");
      }
    DataProvider.setNewBtn(1);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditAppointment.fxml"));
            loader.load();
            
            EditAppointmentController EAController = loader.getController();
            Customer cus = CustomerTableView.getSelectionModel().getSelectedItem();
            EAController.sendCusId(cus);
            
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
        } catch (NullPointerException e) {
            System.out.println("Please select a customer.");
         
            System.out.println("Exception: " + e.getMessage());
        }
        
            
            //java.lang.reflect.InvocationTargetException

    }

    @FXML
    private void searchBtnHandler(ActionEvent event) {
            String searchName = textField.getText();
           // System.out.println(searchName);
        CustomerTableView.setItems(filter(searchName));
    }

    @FXML
    private void scheduleHandler(ActionEvent event) throws IOException {
        //provide an alert if there is no customer selected.

        
      if(CustomerTableView.getSelectionModel().getSelectedItem()==null){
          alertMsg.sendAlert("Please select a customer from the list to view their schedule.");
      }
        
        int currCusId = CustomerTableView.getSelectionModel().getSelectedItem().getId();
        
        
        //list of all the appointments
     ObservableList<Appointment> currSchedule = FXCollections.observableArrayList();
     currSchedule.clear();
     for(Appointment apt:   AppointmentController.getAllAppointments()){
         
         if(apt.getCustomerId() == currCusId){
             currSchedule.add(apt);
             System.out.println("add an appointment");
           
         }
     }
    
         
              FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Appointment.fxml"));
            loader.load();
            
           AppointmentController ACController = loader.getController();
           ACController.sendSchedule(currSchedule);
          //    TableView.getSelectionModel().getSelectedItem().getId();
            System.out.println("here");
            
               stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));   
     }
    
    //The justification for this lamba expression is that using this as a lambda expression makes the code 
    //easier to read when editing the code. It also reduced the number of lines of code that need to repeated.
    //You can easily change the output of the alert without changing the functionality. 
    AlertMessage alertMsg = (message) -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Reminder");
                alert.setContentText(message);
            return  alert.showAndWait();
    
};
        
    

}
