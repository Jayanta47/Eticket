/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eticket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class ChangeInfoController implements Initializable {

    String UserId;
    String newUsername;
    String newDateOfBirth;
    String newId;
    String newAddress="";
    
    MainWindowController mwc;

    public ChangeInfoController() {
        
          try {
            File file = new File("updateUserInfo.txt");
            if(!file.exists()) file.createNewFile();
            Scanner scan = new Scanner(file);
            UserId = scan.nextLine();
            newUsername= scan.nextLine();
            newDateOfBirth= scan.nextLine();
            newId=scan.nextLine();
            newAddress = scan.nextLine();
            System.out.println(newAddress);
            scan.close();
            mwc = MainWindowController.mwc2;
            //file.delete();
        } catch (Exception e) {
        }
    }
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void confirmChangeOnAction(ActionEvent event) {
        try {
            InetAddress ip = InetAddress.getByName("localhost");
            Socket socket = new Socket(ip, 4557);
            if(socket.isConnected()){
                System.out.println("connected");
            }
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String command = "resetInfo";
            dos.writeUTF("ClientHandler");
            dos.flush();
            dos.writeUTF(command);
            dos.flush();
            System.out.println(newDateOfBirth);
            dos.writeUTF(UserId);
            dos.writeUTF(newUsername);
            dos.writeUTF(newDateOfBirth);
            dos.writeUTF(newId);
            dos.writeUTF(newAddress);
            Stage stage = (Stage) ((Button)event.getTarget()).getScene().getWindow();
            stage.close();
            Thread.sleep(500);
            mwc.clearDash();
            mwc.setUserInfo();
        } catch (Exception e) {
            
        }
    }
    
}
