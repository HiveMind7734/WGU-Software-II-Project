/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.AlertMessage;
import Model.Appointment;
import Model.Customer;
import Model.DataProvider;
import Model.Loop;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jdbcapp.Query;
import utils.DBConnection;
import static utils.DBConnection.conn;

/**
 * FXML Controller class
 *
 * @author Brian
 */
public class AppointmentController implements Initializable {
  Stage stage;
  Parent scene;
    @FXML
    private TableView<Appointment> TableView;
    @FXML
    private TableColumn<Appointment, String> aptStartCol;
    @FXML
    private TableColumn<Appointment, String> aptEndCol;
    @FXML
    private TableColumn<Appointment, String> aptTitleCol;
    @FXML
    private TableColumn<Appointment, String> aptTypeCol;
    @FXML
    private TableColumn<Appointment, Integer> aptCusId;
    @FXML
    private Button editAptBtn;
    @FXML
    private Button deleteAptBtn;
    @FXML
    private RadioButton monthRadioButton;
    @FXML
    private RadioButton weekRadioButton;
    @FXML
    private Button mainMenuBtn;
    private static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    public static ObservableList<Appointment> appointmentList1 = FXCollections.observableArrayList();
    private static ObservableList<LocalDateTime> aptTimes = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Appointment, String> dateCol;
    private ToggleGroup apptToggleGroup;
    
    
   private final DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
 ObservableList<Appointment> filteredData = FXCollections.observableArrayList();
 ObservableList<Appointment> filteredData1;
    @FXML
    private Button typeMonth;
    @FXML
    private ComboBox<String> typeComboBox;

    public ObservableList<Appointment> getFilteredData() {
        return filteredData;
    }
 

  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO     
        typeComboBox.setItems((ObservableList<String>) DataProvider.getAppointmentTypes());
        
        
         apptToggleGroup = new ToggleGroup();
        this.weekRadioButton.setToggleGroup(apptToggleGroup);
        this.monthRadioButton.setToggleGroup(apptToggleGroup);
        

    if (  !(appointmentList1.isEmpty())  || !(appointmentList.isEmpty())  ){
      appointmentList.clear();
      appointmentList1.clear();
       }
    
    if( !(aptTimes.isEmpty())){
        aptTimes.clear();
    }
             
             

  ZoneId zid = ZoneId.systemDefault();
  LocalDateTime start = null;
  ZonedDateTime newzdtStart = null;
  ZonedDateTime newLocalStart = null;
 LocalDateTime newSTime = null;
 
 LocalDateTime end = null;
 ZonedDateTime newzdtEnd = null;
 ZonedDateTime newLocalEnd = null;
 LocalDateTime newETime = null;
            
   //extract the appointments from the customers into an OL
     for(Customer cus : DataProvider.getAllCustomers()){
     
      appointmentList = cus.getAllAppointments();
       for (Appointment apt : appointmentList){
             start = apt.getStart();
             newzdtStart = start.atZone(ZoneId.of("UTC"));
             newLocalStart = newzdtStart.withZoneSameInstant(zid);
             newSTime = newLocalStart.toLocalDateTime();
             apt.setStart(newSTime);
             end = apt.getEnd();
             newzdtEnd = end.atZone(ZoneId.of("UTC"));
             newLocalEnd = newzdtEnd.withZoneSameInstant(zid);
             newETime = newLocalEnd.toLocalDateTime();
             apt.setEnd(newETime);
            
           appointmentList1.add(apt);
           aptTimes.add(apt.getStart());
       }        
     }
             aptReminder();

            
   //     DataProvider.getAllCustomers().get(aptCusId).getId();
        TableView.setItems(getAllAppointments());
        aptStartCol.setCellValueFactory(new PropertyValueFactory<>("startDisplay"));
        aptEndCol.setCellValueFactory(new PropertyValueFactory<>("endDisplay"));
        aptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        aptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        aptCusId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("DateDisplay"));

//    
        
        
    }    

    private void newAptBtnHandler(ActionEvent event) throws IOException {
            
            switchScreen(event, "EditAppointment.fxml");
    }

    @FXML
    private void editAptBtnHandler(ActionEvent event) throws IOException {
            DataProvider.setNewBtn(0);
             FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditAppointment.fxml"));
            loader.load();
            
             EditAppointmentController EAController = loader.getController();
           EAController.sendPart(TableView.getSelectionModel().getSelectedItem());
          //    TableView.getSelectionModel().getSelectedItem().getId();
            
            
               stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
    }

    @FXML
    private void deleteAptBtnHandler(ActionEvent event) {
        
                try{           
            PreparedStatement pst = DBConnection.startConnection().prepareStatement("DELETE appointment.* FROM appointment WHERE appointment.appointmentId = ?");
            pst.setInt(1, TableView.getSelectionModel().getSelectedItem().getId()); 
            pst.executeUpdate();  
                
        } catch(SQLException e){
            e.printStackTrace();
        } 
//           


//        for(Customer cus : DataProvider.getAllCustomers()){
//            for(Appointment apt : cus.getAllAppointments()){
//                if(apt.getId() == TableView.getSelectionModel().getSelectedItem().getId()){
//                    cus.removeAppointments(apt);
//                    
//                    Appointment aptt = TableView.getSelectionModel().getSelectedItem();
//                 
//                }
//            }
//        }
        
    

       Appointment appointment1 = TableView.getSelectionModel().getSelectedItem();
          DataProvider.deleteAppointment(TableView.getSelectionModel().getSelectedItem());
       appointmentList1.remove(appointment1);
        
       
       
//              if (  !(appointmentList.isEmpty()) || !(appointmentList1.isEmpty())  ){
//            appointmentList.clear();
//            appointmentList1.clear();
//        }
//
//     for(Customer cus : DataProvider.getAllCustomers()){
//      appointmentList = cus.getAllAppointments();
//       for (Appointment apt : appointmentList){
//           appointmentList1.add(apt);
//       }        
//     }
     
       TableView.setItems(getAllAppointments());
    }
    


    @FXML
    private void monthRadioButtonHandle(ActionEvent event) {
        LocalDate now = LocalDate.now();
        LocalDate nowPlus1Month = now.plusMonths(1);

//        if (!(DataProvider.getFilteredBusinessHours().isEmpty())){
//            DataProvider.getFilteredBusinessHours().clear();
//        }
                
            loopArray.loop(DataProvider.getFilteredBusinessHours());
        
       
          for(Appointment apt : getAllAppointments()){             
               if(apt.getDateDisplay().isAfter(now.minusDays(1)) && apt.getDateDisplay().isBefore(nowPlus1Month)){
                   

                   DataProvider.addFilteredApt(apt);

               }
          }
 TableView.setItems(DataProvider.getFilteredBusinessHours());
            
            

    
    }
    //this lambda expression is used multiple times on this page. I have reduced the number of lines of code.
        Loop loopArray = (OLArray) -> {
          if (!(OLArray.isEmpty())){
                    OLArray.clear();
        }
              
            return  OLArray;
    
};

    @FXML
    private void weekRadioButtonHandle(ActionEvent event) {
         LocalDate now = LocalDate.now();
        LocalDate nowPlus7 = now.plusDays(7);    
        
        if (!(DataProvider.getFilteredBusinessHours().isEmpty())){
            DataProvider.getFilteredBusinessHours().clear();
        }
        
       
          for(Appointment apt : getAllAppointments()){             
               if(apt.getDateDisplay().isAfter(now.minusDays(1)) && apt.getDateDisplay().isBefore(nowPlus7)){
                   DataProvider.addFilteredApt(apt);
               }
          }
          
     
       
            TableView.setItems(DataProvider.getFilteredBusinessHours());
          
          
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
        
            public static ObservableList<Appointment> getAllAppointments() {
        return appointmentList1;
    }
        
   private void aptReminder(){
       long timeDifference=0;
       String message="";
       boolean switchLoop=false;
            LocalDateTime now =  LocalDateTime.now();
        System.out.println("Current time: " + now);
        for(LocalDateTime ldt : aptTimes){
             timeDifference = ChronoUnit.MINUTES.between(now, ldt);
          // System.out.println("Time differences: " + timeDifference);
            
           if(timeDifference > 0 && timeDifference <= 15){
                message += "You have an appointment in: " +timeDifference + "minute(s)\n";
                switchLoop = true;
           }
                
        }
        if(switchLoop){
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Reminder");
                alert.setContentText(message);
                alert.showAndWait();
        }
    
   }

    @FXML
    private void typeMonthHandler(ActionEvent event) {
         String type = typeComboBox.getSelectionModel().getSelectedItem();
            LocalDate now = LocalDate.now();
        LocalDate nowPlus1Month = now.plusMonths(1);
        
            if (!filteredData.isEmpty()){
            filteredData.clear();
        }
      
         //getAllAppointments()
        for(Appointment apt : appointmentList1){
            if((apt.getType().equals(type)) && (apt.getDateDisplay().isAfter(now.minusDays(1)) && apt.getDateDisplay().isBefore(nowPlus1Month))    ){
               
                filteredData.add(apt);
            }
        }
        //getFilteredData()
         TableView.setItems(filteredData);
        
    }

    @FXML
    private void allAptHandler(ActionEvent event) {
             TableView.setItems(appointmentList1);
    }
    
    public void sendSchedule(ObservableList<Appointment> aptList){
           for(Appointment apt: aptList){
      
           }
           TableView.setItems(aptList);
    
    }


    
}
