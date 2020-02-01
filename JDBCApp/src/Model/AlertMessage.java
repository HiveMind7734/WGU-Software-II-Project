/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Brian
 */
public interface AlertMessage {
    //value returning abstract mehod
    Optional<ButtonType> sendAlert(String n);
   // int addNums(int n);
    
    
    
    
    
//           Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Appointment Reminder");
//                alert.setContentText(message);
//                alert.showAndWait();
//   
}
