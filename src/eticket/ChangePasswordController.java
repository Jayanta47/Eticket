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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class ChangePasswordController implements Initializable {

    String UserId;
    @FXML
    private PasswordField prevPass;
    @FXML
    private PasswordField newPass;
    @FXML
    private PasswordField confNewPass;
    @FXML
    private Button applyChangeBtn;
    @FXML
    private Label misfitPass;
    @FXML
    private Label passDntMatch;
    /**
     * Initializes the controller class.
     * @param UserId
     */
    public ChangePasswordController(){
        
    }
    
    public ChangePasswordController(String UserId) {
        this.UserId=UserId;
    }    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("on");
    }    

    @FXML
    private void applyChangeOnAction(ActionEvent event) {
        try{
             InetAddress ip = InetAddress.getByName("localhost");
            Socket socket = new Socket(ip, 4557);
            if(socket.isConnected()){
                System.out.println("connected");
            }
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            misfitPass.setText("");
            passDntMatch.setText("");
            String command = "Change Password";
            dos.writeUTF("ClientHandler");
            dos.flush();
            dos.writeUTF(command);
            dos.flush();
            // read userId from text file in the client class
             File file = new File("userInfo.txt");
            if(!file.exists()) file.createNewFile();
            Scanner scan = new Scanner(file);
             String userId = scan.next();
            dos.writeUTF(userId);
            String pass= dis.readUTF();
            int x=0;
            if(!pass.equals(prevPass.getText())){
                x=1;
                misfitPass.setText("*Wrong Password");
            }
            if(!confNewPass.getText().equals(newPass.getText())){
                x=1;
                passDntMatch.setText("*Your password does not match");
            }
            
            if(x==1){
                dos.writeUTF("finished");
                return;
            }
            else{
                dos.writeUTF("update");
                dos.writeUTF(confNewPass.getText());
                Stage stage = (Stage) ((Button)event.getTarget()).getScene().getWindow();
                stage.close();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
}
