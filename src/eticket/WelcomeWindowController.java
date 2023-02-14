/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eticket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class WelcomeWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField userName;
    @FXML
    private PasswordField passWord;
    @FXML
    private Label invalidLogin;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void signInOnAction(ActionEvent event) throws IOException {
        System.out.println(userName.getText()+" "+passWord.getText());
        InetAddress ip= InetAddress.getByName("localhost");
        Socket s= new Socket(ip, 4557);
        //Socket s= new Socket("192.168.1.100", 4557);
        if(s.isConnected()){
            System.out.println("connected");
        }
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            String command ="userloginquery";
            dos.writeUTF("ClientHandler");
            dos.writeUTF(command);
            dos.writeUTF(userName.getText());
            dos.writeUTF(passWord.getText());

            String decision =dis.readUTF();
            System.out.println(decision);
            if(decision.equals("okay")){
                invalidLogin.setText("");
                String userID= dis.readUTF();
                s.close();
                dis.close();
                dos.close();
                File file = new File("userInfo.txt");
                if(!file.exists()) file.createNewFile();
                PrintWriter pw =new PrintWriter(file);
                pw.println(userID);
                pw.close();
                Parent mainWindowParent=  FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
                Scene mainWindowScene =new Scene (mainWindowParent);
                Stage mainStage = (Stage)  ( (Node) event.getSource()).getScene().getWindow();
                mainStage.close();
                mainStage.setScene(mainWindowScene);
                mainStage.setResizable(true);
                mainStage.setTitle("eTicket :: Reserve Ticket Faster");
                mainStage.show();
                 
            }
            else if(decision.equals("Logged in")){
                invalidLogin.setText("Already Logged In!");
            }
            else{
                invalidLogin.setText("Invalid Login");
            }
        
        
    }

    @FXML
    private void createNewAccountOnAction(ActionEvent event) throws IOException {
        Parent createAccount = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
        Scene createScene = new Scene(createAccount);
        Stage createStage = new Stage();
        createStage.setScene(createScene);
        createStage.setResizable(false);
        createStage.setTitle("Create New Account");
        createStage.initModality(Modality.APPLICATION_MODAL);
        createStage.show();
    }
    
}
