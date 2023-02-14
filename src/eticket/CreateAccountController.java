/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eticket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class CreateAccountController implements Initializable {

    @FXML
    private TextArea address;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private PasswordField confirmedPassword;
    @FXML
    private PasswordField password;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private TextField mobileNo;
    @FXML
    private TextField NIDno;
    @FXML
    private Label invalidPassword;
    @FXML
    private Label invalidEmail;
    @FXML
    private Label invalidMobile;
    @FXML
    private Label invalidNID;
    @FXML
    private Label invalidUsername;
    @FXML
    private Label invalidAddress;
    @FXML
    private Label invalidDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void SignUpOnAction(ActionEvent event) throws IOException {
        //email
        int x=0;
        if(username.getText().equals("")){
            invalidUsername.setText("Invalid Username!");
            x=1;
        }
        if(address.getText().equals("")){
            invalidAddress.setText("Invalid Adress!");
            x=1;
        }
        if(dateOfBirth.getEditor().getText().equals("")){
            invalidDate.setText("Invalid Date!");
            x=1;
        }
        if(!email.getText().contains("@") || email.getText().equals("")){
            invalidEmail.setText("Invalid Email!");
            x=1;
        }
        if(mobileNo.getText().matches(".*[a-zA-Z].*") || mobileNo.getText().equals("")){
            invalidMobile.setText("Invalid Mobile No!");
            x=1;
        }
        if(NIDno.getText().matches(".*[a-zA-Z].*") || NIDno.getText().equals("")){
            invalidNID.setText("Invalid NID No!");
            x=1;
        }
        if(!password.getText().equals(confirmedPassword.getText())){
            invalidPassword.setText("Passwords are not matched!");
            x=1;
        }
        if(password.getText().equals("") && confirmedPassword.getText().equals("")){
            invalidPassword.setText("Password fields are empty!");
            x=1;
        }
        if(x==1) return;
        else{
//            String userID;
//            File file = new File("userInfo.txt");
//            if(!file.exists()) file.createNewFile();
//            PrintWriter pw =new PrintWriter(file);
//            pw.println(userID);
//            pw.close();
            Socket socket = new Socket("localhost",4557);
            try {
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("ClientHandler");
                dos.flush();
                dos.writeUTF("CreateAccount");
                dos.flush();
                dos.writeUTF(username.getText());
                dos.flush();
                dos.writeUTF(password.getText());
                dos.flush();
                dos.writeUTF(email.getText());
                dos.flush();
                dos.writeUTF(dateOfBirth.getEditor().getText());
                dos.flush();
                dos.writeUTF(mobileNo.getText());
                dos.flush();
                dos.writeUTF(address.getText());
                dos.flush();
                dos.writeUTF(NIDno.getText());
                dos.flush();
                dos.close();
            }
            catch(Exception e){
            }
//            ConnectDatabase1 ob =new ConnectDatabase1(username.getText(), 
//                    password.getText(), email.getText(), dateOfBirth.getEditor().getText(), 
//                    mobileNo.getText(), address.getText(), NIDno.getText());
//            ob.saveData();


//            Parent mainWindowParent=  FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
//            Scene mainWindowScene =new Scene (mainWindowParent);
            Stage mainStage = (Stage)  ( (Node) event.getSource()).getScene().getWindow();
            //Stage mainStage = new Stage();
            mainStage.close();
//            mainStage.setScene(mainWindowScene);
//            mainStage.setResizable(true);
//            mainStage.setTitle("eTicket :: Reserve Ticket Faster");
//            mainStage.show();
        }
    }

    @FXML
    private void ResetOnAction(ActionEvent event) {
        username.clear();
        email.clear();
        mobileNo.clear();
        NIDno.clear();
        address.clear();
        password.clear();
        confirmedPassword.clear();
        dateOfBirth.getEditor().clear();
        invalidEmail.setText("");
        invalidMobile.setText("");
        invalidNID.setText("");
        invalidPassword.setText("");
        invalidUsername.setText("");
        invalidDate.setText("");
        invalidAddress.setText("");
        
    }
    
}
