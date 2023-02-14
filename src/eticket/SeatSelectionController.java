/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eticket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class SeatSelectionController implements Initializable {

    @FXML
    private Label selectedBusName;
    @FXML
    private Label selectedSeats;
    @FXML
    private Label totalFare;
    @FXML
    private Label numberOfSeats;
    @FXML
    private Label farePerSeat;
    
    
    
    
    @FXML
    private Button A1;
    @FXML
    private Button A2;
    @FXML
    private Button A3;
    @FXML
    private Button A4;
    @FXML
    private Button B1;
    @FXML
    private Button B2;
    @FXML
    private Button B3;
    @FXML
    private Button B4;
    @FXML
    private Button C1;
    @FXML
    private Button C2;
    @FXML
    private Button C3;
    @FXML
    private Button C4;
    @FXML
    private Button D1;
    @FXML
    private Button D2;
    @FXML
    private Button D3;
    @FXML
    private Button D4;
    @FXML
    private Button E1;
    @FXML
    private Button E2;
    @FXML
    private Button E3;
    @FXML
    private Button E4;
    @FXML
    private Button F1;
    @FXML
    private Button F2;
    @FXML
    private Button F3;
    @FXML
    private Button F4;
    @FXML
    private Button G1;
    @FXML
    private Button G2;
    @FXML
    private Button G3;
    @FXML
    private Button G4;
    @FXML
    private Button H1;
    @FXML
    private Button H2;
    @FXML
    private Button H3;
    @FXML
    private Button H4;
    @FXML
    private Button I1;
    @FXML
    private Button I2;
    @FXML
    private Button I3;
    @FXML
    private Button I4;
    @FXML
    private Button J1;
    @FXML
    private Button J2;
    @FXML
    private Button J3;
    @FXML
    private Button J4;
    @FXML
    private AnchorPane pane;
    
    
    ArrayList<Button> seatButtonList = new ArrayList();
    ArrayList<String> stations = new ArrayList();
    
    String userId;
    String from, to, date, coach;
    String direction;
    String journeyType;
    int seatCount=0, fare, totalFareCount;
    Socket socket;
    Socket seatSocket;
    DataInputStream dis, dis2;
    DataOutputStream dos, dos2;
    BufferedReader in;
    Thread t;
    Stage stage;
    MainWindowController mwc2;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        addStations();
        setSeatButtonList();
        InetAddress ip;
        try {
            ip = InetAddress.getByName("localhost");
            socket = new Socket(ip, 4557);
            if(socket.isConnected()){
                System.out.println("connected");
            }
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            String command ="showseats";
            dos.writeUTF("ClientHandler");
            dos.flush();
            dos.writeUTF(command);
            dos.flush();
            
            try {
                File file = new File("route.txt");
                if(!file.exists()) file.createNewFile();
                Scanner scan = new Scanner(file);
                date = scan.nextLine();
                from = scan.nextLine();
                to = scan.nextLine();
                coach = scan.nextLine();
                selectedBusName.setText(coach);
                // TODO
            } catch (IOException ex) {
                Logger.getLogger(SeatSelectionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            countFare();
            
            journeyType = coach.toLowerCase() + direction.toLowerCase() + date.toLowerCase();
            
            dos.writeUTF(date);
            dos.writeUTF(from);
            dos.writeUTF(to);
            dos.writeUTF(coach);
            dos.flush();
            dos.writeUTF(journeyType);
            dos.flush();
            
            int count = Integer.parseInt(dis.readUTF());
            String seats = dis.readUTF();
            disableSeat(seats,"Permanent");
            
            while(true){
                String str = dis.readUTF();
                if(str.equals("Break")) break;
                disableSeat(str, "Temporary");
            }
            
            
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(SeatSelectionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SeatSelectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        mwc2 = MainWindowController.mwc2;
        
        SeatHandler sh = new SeatHandler();
        t = new Thread(sh);
        t.start();
        
        try {
            seatSocket = new Socket("localhost",4557);
            dos2 = new DataOutputStream(seatSocket.getOutputStream());
            //dis2 = new DataInputStream(seatSocket.getInputStream());
            in = new BufferedReader(new InputStreamReader(seatSocket.getInputStream()));
            dos2.writeUTF("SeatHandler");
            dos2.writeUTF(journeyType);
            dos2.flush();
        } catch (IOException ex) {
            Logger.getLogger(SeatSelectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pane.sceneProperty().addListener((obs, oldScene, newScene)->{
            Platform.runLater(()->{
                stage = (Stage) newScene.getWindow();
                stage.setOnCloseRequest(e->{
                    String str = selectedSeats.getText();
                    try {
                        dos2.writeUTF("Temporary");
                        dos2.writeUTF("Remove");
                        dos2.writeUTF(str);
                    } catch (IOException ex) {
                        //Logger.getLogger(SeatSelectionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    t.stop();
                });
            });
        });
        
    }

    public void countFare(){
        int i = stations.indexOf(from);
        int j = stations.indexOf(to);
        if(i<j){
            direction = "Forward";
            fare = (j-i)*50;
        }   
        else{
            direction = "Backward";
            fare = (i-j)*50;
        }
    }
    
    public void addStations(){
        stations.add("Dhaka");
        stations.add("Dhamrai");
        stations.add("Manikganj");
        stations.add("Faridpur");
        stations.add("Muksudpur");
        stations.add("Gopalganj");
        stations.add("Khulna");
        
    }
    
    public void setSeatButtonList(){
        seatButtonList.add(A1);
        seatButtonList.add(A2);
        seatButtonList.add(A3);
        seatButtonList.add(A4);
        
        seatButtonList.add(B1);
        seatButtonList.add(B2);
        seatButtonList.add(B3);
        seatButtonList.add(B4);
        
        seatButtonList.add(C1);
        seatButtonList.add(C2);
        seatButtonList.add(C3);
        seatButtonList.add(C4);
        
        seatButtonList.add(D1);
        seatButtonList.add(D2);
        seatButtonList.add(D3);
        seatButtonList.add(D4);
        
        seatButtonList.add(E1);
        seatButtonList.add(E2);
        seatButtonList.add(E3);
        seatButtonList.add(E4);
        
        seatButtonList.add(F1);
        seatButtonList.add(F2);
        seatButtonList.add(F3);
        seatButtonList.add(F4);
        
        seatButtonList.add(G1);
        seatButtonList.add(G2);
        seatButtonList.add(G3);
        seatButtonList.add(G4);
        
        seatButtonList.add(H1);
        seatButtonList.add(H2);
        seatButtonList.add(H3);
        seatButtonList.add(H4);
        
        seatButtonList.add(I1);
        seatButtonList.add(I2);
        seatButtonList.add(I3);
        seatButtonList.add(I4);
        
        seatButtonList.add(J1);
        seatButtonList.add(J2);
        seatButtonList.add(J3);
        seatButtonList.add(J4);
    }
    
    public void disableSeat(String seats, String type){
        String[] selectedSeats = seats.split(", ");
        for(String seat: selectedSeats){
            for(Button i: seatButtonList){
                if(i.getText().equals(seat)){
                    if(type.equals("Permanent"))
                        i.setStyle("-fx-background-color:yellow");
                    else if(type.equals("Temporary"))
                        i.setStyle("-fx-background-color:pink");
                    i.setDisable(true);
                    break;
                }
            }  
        }
    }
    
    @FXML
    private void selectSeatOnAction(ActionEvent event) throws IOException {
        Button button = (Button)event.getTarget();
        String str = button.getText();
        Color color = (Color) button.getBackground().getFills().get(0).getFill();
        if(color == Color.LIMEGREEN){
            if(seatCount<=3){
                button.setStyle("-fx-background-color:blue");
                selectedSeats.setText(selectedSeats.getText() + str + ", ");
                seatCount++;
                numberOfSeats.setText(String.valueOf(seatCount));
                
                dos2.writeUTF("Temporary");
                dos2.flush();
                dos2.writeUTF("ADD");
                dos2.flush();
                dos2.writeUTF(str);
                dos2.flush();
                
                totalFareCount = seatCount * fare;
                farePerSeat.setText(String.valueOf(fare));
                totalFare.setText(String.valueOf(totalFareCount));
            }
            
        }
        else{
            button.setStyle("-fx-background-color:limegreen");
            selectedSeats.setText(selectedSeats.getText().replace((str+", ") , ""));
            seatCount--;
            numberOfSeats.setText(String.valueOf(seatCount));
            dos2.writeUTF("Temporary");
            dos2.flush();
            dos2.writeUTF("Remove");
            dos2.flush();
            dos2.writeUTF(str);
            
            totalFareCount = seatCount * fare;
            farePerSeat.setText(String.valueOf(fare));
            totalFare.setText(String.valueOf(totalFareCount));
        }
        
    }
    
    void enableSeats(String seats){
        String[] selectedSeats = seats.split(", ");
        for(String seat: selectedSeats){
            for(Button i: seatButtonList){
                if(i.getText().equals(seat)){
                    i.setStyle("-fx-background-color:limegreen");
                    i.setDisable(false);
                    break;
                }
            }  
        }
    }

    @FXML
    private void purchaseNowOnAction(ActionEvent event) throws IOException {
        String seats = selectedSeats.getText();
        dos2.writeUTF("Permanent");
        dos2.writeUTF("ADD");
        dos2.writeUTF(seats);
        
        dos2.writeUTF(direction);
        dos2.writeUTF(from);
        dos2.writeUTF(to);
        dos2.writeUTF(coach);
        dos2.writeUTF(date);
        dos2.writeUTF(String.valueOf(totalFareCount));
        dos2.writeUTF("Booked");
        userId=mwc2.userId;
        dos2.writeUTF(userId);
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            //Logger.getLogger(SeatSelectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        mwc2.getFinalSeats().setText(seats);
        mwc2.getFinalStationFrom().setText(from);
        mwc2.getFinalStationTo().setText(to);
        mwc2.getFinalTotalFare().setText(String.valueOf(totalFareCount));
        mwc2.getFinalJourneyDate().setText(date);
        mwc2.getFinalSelectedBus().setText(coach);
        
        //
        
        
        mwc2.getTabPane().getSelectionModel().select(mwc2.getPurchaseTicket());
        
        Stage stage = (Stage) ((Button)event.getTarget()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void purchaseLaterOnAction(ActionEvent event) {
        try {
            String seats = selectedSeats.getText();
            dos2.writeUTF("Permanent");
            dos2.writeUTF("ADD");
            dos2.writeUTF(seats);

            dos2.writeUTF(direction);
            dos2.writeUTF(from);
            dos2.writeUTF(to);
            dos2.writeUTF(coach);
            dos2.writeUTF(date);
            dos2.writeUTF(String.valueOf(totalFareCount));
            dos2.writeUTF("Booked");
            userId=mwc2.userId;
            dos2.writeUTF(userId);
        } catch (Exception e) {
            System.out.println("purchase later error");
        }
        
        Stage stage = (Stage) ((Button)event.getTarget()).getScene().getWindow();
        stage.close();
        
    }
    
    
    class SeatHandler implements Runnable{

        public SeatHandler() {
            
        }
        
        @Override
        public void run() {
            while(true){
                try {
                    String command = "";
                    String type1 = null;
                    try{
                        type1 = in.readLine();
                        command = in.readLine();
                        
                    }catch(Exception e){
                        
                    }
                    
                    if(command.equals("Temporary")){
                        String operation = in.readLine();
                        String seat = in.readLine();
                        if(type1.equals(journeyType)){
                            if(operation.equals("ADD"))
                                disableSeat(seat, "Temporary");
                            else
                                enableSeats(seat);
                        } 
                    }
                    
                    else if(command.equals("Permanent")){
                        String operation = in.readLine();
                        String seat = in.readLine();
                        if(type1.equals(journeyType)){
                            if(operation.equals("ADD"))
                                disableSeat(seat, "Permanent");
                            else
                                enableSeats(seat);
                        } 
                    }
                    
                    else if (command.equals("BookingId")){
                        String bookingId = type1;
                        mwc2.getBookingCode().setText(bookingId);
                        String dateTime = in.readLine();
                        String msg = in.readLine();
                        
                        mwc2.notificationTableInfo.add(0, new NotificationData(dateTime, msg));
                        
                    }


                } catch (IOException ex) {
                    Logger.getLogger(SeatSelectionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        
    }
    
}

