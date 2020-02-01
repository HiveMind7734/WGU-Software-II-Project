/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Brian
 */
public class MenuController implements Initializable {
  Stage stage;
    Parent scene;
    @FXML
    private Button CustomerBtn;
    @FXML
    private Button aptBtn;
    @FXML
    private Button userBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private Text mainMenuText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onClickCustomer(ActionEvent event) throws IOException {
        switchScreen(event, "Customer.fxml");
    }

    @FXML
    private void onClickAptBtn(ActionEvent event) throws IOException {
         switchScreen(event, "Appointment.fxml");
    }

    @FXML
    private void onClickUsrBtn(ActionEvent event) throws IOException {// you will need to change the *user* field for this to work. You will also need to be on a Windows computer.
            File file = new File ("C:\\Users\\Brian\\Documents\\NetBeansProjects\\JDBCApp\\log.txt");
            Desktop desktop = Desktop.getDesktop();
             desktop.open(file);
    
    }

    @FXML
    private void onClickExitBtn(ActionEvent event) throws IOException {
         switchScreen(event, "LoginScreen.fxml");
    }
    
        //Method to switchScreen.
    private void switchScreen(ActionEvent event, String path) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();   //lets the event handler know that this is a button press
        scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
}
