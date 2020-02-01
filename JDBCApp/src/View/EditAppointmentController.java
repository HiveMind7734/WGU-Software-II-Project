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
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.DBConnection;
import static utils.DBConnection.conn;

/**
 * FXML Controller class
 *
 * @author Brian
 */
public class EditAppointmentController implements Initializable {

    Stage stage;
    Parent scene;
    ZoneId zid = ZoneId.systemDefault();
    @FXML
    private TextField titleField;
    @FXML
    private ComboBox<String> startComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private ComboBox<String> endComboBox;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private AnchorPane cusIDTextField;
    @FXML
    private Label cusIdLbl;

//        private final DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
//    private final DateTimeFormatter dateDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    Appointment appointment = null;
    @FXML
    private Label aptId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //typeComboBox.setItems(list);

        typeComboBox.setItems((ObservableList<String>) DataProvider.getAppointmentTypes());
        startComboBox.setItems(DataProvider.getBusinessHours());
        endComboBox.setItems(DataProvider.getBusinessHours());

    }

    @FXML
    private void typeComboHandler(ActionEvent event) {

    }

    private void switchScreen(ActionEvent event, String path) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();   //lets the event handler know that this is a button press
        scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    private static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }

    @FXML
    private void saveBtnHandler(ActionEvent event) throws IOException, SQLException {

        int apptId = Integer.parseInt(aptId.getText());//get the appointment id
        java.util.Date today = new java.util.Date();//grab todays date 

        //Take start and end strings, make whole again
        String strTime = startComboBox.getSelectionModel().getSelectedItem().substring(0, 5);
        String date = datePicker.getValue().toString();
        String strtDateTime = date + " " + strTime + ":00";
        String endTime = endComboBox.getSelectionModel().getSelectedItem().substring(0, 5);
        String endDateTime = date + " " + endTime + ":00";
        System.out.println(strtDateTime);
        
        //OffsetTime test1 = OffsetTime.now(); 
        
        
        //   System.out.println(strtDateTime);
        //Convert to LocalDateTime Objects
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime sdt = LocalDateTime.parse(strtDateTime, formatter);
        LocalDateTime edt = LocalDateTime.parse(endDateTime, formatter);
        
         //Convert to ZDT objects
        ZonedDateTime startUTC = sdt.atZone(zid).withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime endUTC = edt.atZone(zid).withZoneSameInstant(ZoneId.of("UTC"));
        
        //ZoneId.of("America/New_York")
        
        //Convert back to local time objects.
        LocalDateTime sdt1 = startUTC.toLocalDateTime();
        LocalDateTime edt1 = endUTC.toLocalDateTime();

        //  System.out.println(startUTC);
        //Convert the start and end times into Timestamp objects to save to the database.
        Timestamp sqlS = Timestamp.valueOf(startUTC.toLocalDateTime());
        Timestamp sqlE = Timestamp.valueOf(endUTC.toLocalDateTime());
        
        

        //Check to see if LDT start and end overlap
        ObservableList<Appointment> customersAppointments = FXCollections.observableArrayList();
        //get a list of appointment times that the customer already has.
        for (Customer cus1 : DataProvider.getAllCustomers()) {
            if (cus1.getId() == Integer.parseInt(cusIdLbl.getText())) {
                customersAppointments = cus1.getAllAppointments();
            }
        }
        String errorMessage = "";
             LocalTime timeStart =  sdt.toLocalTime();
          LocalTime timeEnd =  edt.toLocalTime();
          LocalTime busStart = LocalTime.of(8, 00);
          LocalTime busEnd = LocalTime.of(17,00);
            System.out.println(timeStart);
            System.out.println(busStart);
            System.out.println(timeStart.isBefore(busStart));
        for (Appointment apt : customersAppointments) {
            //check for overlap
      if(timeStart.isBefore(busStart)  || timeEnd.isAfter(busEnd)){
                  errorMessage += "You can't make an appointment outside of business hours.\n";
                }
           if(timeStart.isAfter(timeEnd)  || timeEnd.isBefore(timeStart)){
                  errorMessage += "You can't make an appointment start time after the end time.\n";
                } 
            
            if ((apt.getId() != apptId || (DataProvider.getNewBtn() == true))) {
                //check to make sure they are within business hours
               
                
               
                //Start times can't be the same:
                if (sdt.isEqual(apt.getStart())) {
                    errorMessage += "Sorry, you have another appointment scheduled at this start time.\n";

                }

                //End times can't be the same:
                if (edt.equals(apt.getEnd())) {
                    errorMessage += "This appointment is ending at the same time another appointment is ending.\n";
                }

                //is the start appointment inbetween?
                if (sdt.isAfter(apt.getStart()) && sdt.isBefore(apt.getEnd()))//8-12  
                {
                    errorMessage += "This appointment begins during another apt time\n";
                }

                //is the start appointment inbetween?
                if (edt.isAfter(apt.getStart()) && edt.isBefore(apt.getEnd())) {
                    errorMessage += "This appointment is ending during another apt time\n";
                }
                
             

                
                //is an appointment inside of this appointment
                if(apt.getStart().isAfter(sdt) && apt.getEnd().isBefore(edt)){
                    errorMessage += "You have an overlapping appointment Time.\n";
                }

            }
        }



        //Enter this if statement if you are updating an apointment  
        if (!DataProvider.getNewBtn()) {
            if ((errorMessage == "")) {
                //Create a connection object.
                Statement stmt = null;
                try {
                    stmt = conn.createStatement();
                } catch (SQLException ex) {
                    Logger.getLogger(EditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }

                PreparedStatement pst = DBConnection.startConnection().prepareStatement("UPDATE appointment SET "
                        + "customerId = ?, userId = '1', title = ?, description = ?, location = ?, contact = '1', type = ?, url = ?,  start = ?, end = ?, createDate = ?, createdBy = 'Brian', lastUpdate = ?, lastUpdateBy = 'Brian'"
                        + "WHERE appointment.appointmentId = " + "'" + apptId + "'" + "");

                pst.setString(1, cusIdLbl.getText());
                pst.setString(2, titleField.getText());
                pst.setString(3, typeComboBox.getValue());
                pst.setString(4, "");
                pst.setString(5, typeComboBox.getValue());
                //  pst.setString(6, "");
                pst.setString(6, typeComboBox.getValue());
                pst.setTimestamp(7, sqlS);
                pst.setTimestamp(8, sqlE);
//                pst.setTimestamp(8, endsqlts);
                pst.setTimestamp(9, getCurrentTimeStamp());
                pst.setTimestamp(10, getCurrentTimeStamp());
                int result = pst.executeUpdate();

                //int id, int customerId, String title, String type, LocalDateTime start, LocalDateTime end
                //   System.out.println(result);
                Appointment a = new Appointment(apptId, Integer.parseInt(cusIdLbl.getText()), titleField.getText(), typeComboBox.getValue(), sdt1, edt1);
                int index = 0;
                for (Customer cus : DataProvider.getAllCustomers()) {
                    for (Appointment apt : cus.getAllAppointments()) {
                        if (apt.getId() == apptId) {
                            apt.setCustomerId(a.getCustomerId());
                            apt.setTitle(a.getTitle());
                            apt.setType(a.getType());
                            apt.setStart(sdt);
                            apt.setEnd(edt);

                        }
                    }

                }
            } else {//end of error message 
                System.out.println(errorMessage);
                errorMessage = "";
            }
        }//end of update appointment

        //Add a new Appointment to the database.
        //Enter this if statement if you are adding an apointment  
        if (DataProvider.getNewBtn()) {
            if ((errorMessage == "")) {
                //Create a connection object.
                Statement stmt = null;
                try {
                    stmt = conn.createStatement();
                } catch (SQLException ex) {
                    Logger.getLogger(EditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                PreparedStatement pst = DBConnection.startConnection().prepareStatement("INSERT INTO appointment "
                        + "(customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy)"
                        + "VALUES (?, '1', ?, ?, ?, '1', ?, ?, ?, ?, ?, 'Brian', ?, 'Brian')");

                pst.setString(1, cusIdLbl.getText());
                pst.setString(2, titleField.getText());
                pst.setString(3, typeComboBox.getValue());
                pst.setString(4, "");
                pst.setString(5, typeComboBox.getValue());
                //  pst.setString(6, "");
                pst.setString(6, typeComboBox.getValue());
                pst.setTimestamp(7, sqlS);
                pst.setTimestamp(8, sqlE);
//                pst.setTimestamp(8, endsqlts);
                pst.setTimestamp(9, getCurrentTimeStamp());
                pst.setTimestamp(10, getCurrentTimeStamp());
                int result = pst.executeUpdate();

                if (result == 1) {
                        //Create a connection object.
                stmt = null;
                try {
                    stmt = conn.createStatement();
                } catch (SQLException ex) {
                    Logger.getLogger(EditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    String sqlNewAptId = "SELECT MAX(appointment.appointmentId) FROM appointment"; 
                    int newAptId=0;
                    ResultSet rs = stmt.executeQuery(sqlNewAptId);

                            while (rs.next()) {
                            newAptId = rs.getInt("MAX(appointment.appointmentId)");
                                System.out.println(newAptId);
                            }
                            
                    Appointment a = new Appointment(newAptId, Integer.parseInt(cusIdLbl.getText()), titleField.getText(), typeComboBox.getValue(), sdt1, edt1);
                    for (Customer cus : DataProvider.getAllCustomers()) {
                        if (cus.getId() == Integer.parseInt(cusIdLbl.getText())) {
                            cus.addAppointment(a);
                        }
                    }
                }
            } else {
                System.out.println(errorMessage);
                errorMessage = "";
            }

        }//end of add new appointment

        switchScreen(event, "Appointment.fxml");
    }

    @FXML
    private void cancelBtnHandler(ActionEvent event) throws IOException {
        switchScreen(event, "Appointment.fxml");
    }

    public void sendPart(Appointment apt) {
        cusIdLbl.setText(String.valueOf(apt.getCustomerId()));
        titleField.setText(String.valueOf(apt.getTitle()));
        //startComboBox.getSelectionModel().select(apt.getStartDisplay().substring(0,5));
        //endComboBox.getSelectionModel().select(apt.getEndDisplay().substring(0,5));
        typeComboBox.getSelectionModel().select(apt.getType());
        datePicker.setValue(apt.getDateDisplay());
        aptId.setText(String.valueOf(apt.getId()));
    }

    public void sendCusId(Customer cus) {
        cusIdLbl.setText(String.valueOf(cus.getId()));
        aptId.setText("00");
    }

}
