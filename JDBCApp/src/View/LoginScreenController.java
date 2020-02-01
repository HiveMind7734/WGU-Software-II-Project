/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.DataProvider;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdbcapp.JDBCApp;
import static utils.DBConnection.conn;
import java.io.*;
import java.time.LocalDateTime;

/**
 * FXML Controller class
 *
 * @author Brian
 */
public class LoginScreenController implements Initializable {

    private JDBCApp mainApp;
    Stage stage;
    Parent scene;
    String login;
    String pass;
    @FXML
    private TextField loginTextFeild;
    @FXML
    private TextField passTextField;
    @FXML
    private Text loginTxtLbl;
    @FXML
    private Text passwordTxtLbl;
    @FXML
    private Button lginBtn;
    @FXML
    private Button exitBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
            try {
                String password = null;
                
              //  Locale.setDefault(new Locale("fr", "FR"));  //Test to see what happens in French
                rb = ResourceBundle.getBundle("jdbcapp/Nat", Locale.getDefault());
                if (Locale.getDefault().getLanguage().equals("fr")) {
                  
                    password = rb.getString("password");
                    passwordTxtLbl.setText(password);
                    loginTxtLbl.setText(rb.getString("User"));
                    lginBtn.setText(rb.getString("Login"));
                    exitBtn.setText(rb.getString("Exit"));
                }
                //grab the user information
                
                Statement stmt1 = null;
                try {
                    stmt1 = conn.createStatement();
                } catch (SQLException ex) {
                    Logger.getLogger(EditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                String sqlUP = "SELECT  user.userName, user.password FROM user WHERE user.userId = '1'";


ResultSet rs = stmt1.executeQuery(sqlUP);

while (rs.next()) {
    
    
    login = rs.getString("user.userName");
    
    pass = rs.getString("user.password");
}
            } catch (SQLException ex) {
                Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            System.out.println(login+ " and " + pass);
            
        }//end of initialize
    
    
    

    @FXML
    private void loginBtnHandler(ActionEvent event) throws IOException {
       
        String username =loginTextFeild.getText();
        String password =passTextField.getText();

        System.out.println(username + " and " + password);
        if(username.equals(login) && password.equals(pass)){
        LocalDateTime lastLogin = LocalDateTime.now();
         //document login times for a user.
         
         
         FileWriter fwLog = new FileWriter("log.txt", true);
         PrintWriter toLog = new PrintWriter(fwLog);
            toLog.println("User: "+username+ ", last login time: "+lastLogin.toString());
            toLog.close();
            
            
            
        
               switchScreen(event, "Menu.fxml");
        }else{
           //   Locale.setDefault(new Locale("fr", "FR"));
         ResourceBundle rb = ResourceBundle.getBundle("jdbcapp/Nat", Locale.getDefault());
          
          
           if (Locale.getDefault().getLanguage().equals("fr")) {
               Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("Error") + " " + rb.getString("Message"));
                alert.setContentText(rb.getString("Invalid") + " " + rb.getString("user") + " " + rb.getString("name") +" " +rb.getString("or") + " " + rb.getString("password")+".");
                alert.showAndWait();
           }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Invalid user name or password.");
                alert.showAndWait();
           }
             
        }
        
        
       
     
    }

    @FXML
    private void exitBtnHandler(ActionEvent event) throws IOException {
         //switchScreen(event, "Menu.fxml");
    }

    //Method to switchScreen.
    private void switchScreen(ActionEvent event, String path) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();   //lets the event handler know that this is a button press
        scene = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}
