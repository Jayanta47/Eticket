/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eticket;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class MainWindowController implements Initializable {
    
    
    
    @FXML
    private TableView<UserData> perInfoTable;
    @FXML
    private TableView<PurchaseData> LastPurchaseTable;
    @FXML
    private DatePicker journeyDate;
    @FXML
    private Label userEmail;
    @FXML
    private Label userPhoneNumber;
    @FXML
    private TextField userName;
    @FXML
    private DatePicker userDateOfBirth;
    @FXML
    private TextField userIDNumber;
    @FXML
    private TextArea userAddress;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab dashBoard;
    @FXML
    private Tab busRoute;
    @FXML
    private Tab purchaseTicket;
    @FXML
    private Tab purchaseHistory;
    @FXML
    private ComboBox<String> stationFrom;
    @FXML
    private ComboBox<String> stationTo;
    @FXML
    private ComboBox<String> coach;
    @FXML
    private Label invalidInput;

    
    ArrayList<String> stations = new ArrayList();
    ObservableList<String> stationFromList = FXCollections.observableArrayList();
    ObservableList<String> stationToList = FXCollections.observableArrayList();
    ObservableList<String> coachList = FXCollections.observableArrayList();
    ObservableList <UserData> infoTable = FXCollections.observableArrayList();
    ObservableList<PurchaseData> purchaseInfo = FXCollections.observableArrayList();
    ObservableList<PurchaseData> lastPurchaseInfo = FXCollections.observableArrayList();
    public ObservableList<NotificationData>notificationTableInfo= FXCollections.observableArrayList();
    
    
    
    
    @FXML
    private TableColumn<UserData, String> criteriaColumn;
    @FXML
    private TableColumn<UserData, String> infoColumn;
    @FXML
    private TableColumn<PurchaseData, String> slNo;
    @FXML
    private TableColumn<PurchaseData, String> journeyDatePur;
    @FXML
    private TableColumn<PurchaseData, String> stationFromPur;
    @FXML
    private TableColumn<PurchaseData, String> stationToPur;
    @FXML
    private TableColumn<PurchaseData, String> purchaseDatePur;
    @FXML
    private TableColumn<PurchaseData, String> noOfSeatPur;
    @FXML
    private TableView<NotificationData> notificationTable;
    //notification table column
    @FXML
    private TableColumn<NotificationData, String> dateColumn;
    @FXML
    private TableColumn<NotificationData, String> messagesColumn;
    
    public String userId;
    public Stage stage;
    public static MainWindowController mwc2;
//    Socket s;
//    DataInputStream dis;
//    DataOutputStream dos;
    @FXML
    private Label saveInfoNotify;
    @FXML
    private Button buyTicket;
    @FXML
    private TextField bookingCode;
    @FXML
    private PasswordField finalPassword;
    @FXML
    private Label finalSeats;
    @FXML
    private Label finalStationFrom;
    @FXML
    private Label finalStationTo;
    @FXML
    private Label finalTotalFare;
    @FXML
    private Label finalInvalid;
    @FXML
    private Label finalJourneyDate;
    @FXML
    private Label finalSelectedBus;
    @FXML
    private TableView<PurchaseData> purchaseHistoryTable;
    @FXML
    private TableColumn<PurchaseData, String> slNoLast;
    @FXML
    private TableColumn<PurchaseData, String> journeyDatePurLast;
    @FXML
    private TableColumn<PurchaseData, String> stationFromPurLast;
    @FXML
    private TableColumn<PurchaseData, String> stationToPurLast;
    @FXML
    private TableColumn<PurchaseData, String> purchaseDatePurLast;
    @FXML
    private TableColumn<PurchaseData, String> noOfSeatPurLast;

    public Label getFinalSeats() {
        return finalSeats;
    }

    public Label getFinalStationFrom() {
        return finalStationFrom;
    }

    public Label getFinalStationTo() {
        return finalStationTo;
    }

    public Label getFinalTotalFare() {
        return finalTotalFare;
    }

    public TextField getBookingCode() {
        return bookingCode;
    }

    public Label getFinalJourneyDate() {
        return finalJourneyDate;
    }

    public Label getFinalSelectedBus() {
        return finalSelectedBus;
    }

    public Label getFinalInvalid() {
        return finalInvalid;
    }

    
    
    /**
     * Initializes the controller class.
     * @throws java.net.UnknownHostException
     */
    
    
    
    public TableView<NotificationData> getNotificationTable(){
        return notificationTable;
        
    }

    public TableColumn<NotificationData, String> getDateColumn() {
        return dateColumn;
    }

    public TableColumn<NotificationData, String> getMessagesColumn() {
        return messagesColumn;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public Tab getPurchaseTicket() {
        return purchaseTicket;
    }
    
    public void clearDash(){
        try {
            infoTable.clear();
        } catch (Exception e) {
        }
        
    }
    
    
    
    
    
    
    
    
    public void setUserInfo() throws UnknownHostException, IOException {
        
        InetAddress ip= InetAddress.getByName("localhost");
        Socket s= new Socket(ip, 4557);
        if(s.isConnected()){
            System.out.println("connected");
        }
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
         
        String command ="getUserInfo";
        dos.writeUTF("ClientHandler");
        dos.writeUTF(command);
        // read file userinfo and pass in userid
        File file = new File("userInfo.txt");
        if(!file.exists()) file.createNewFile();
        Scanner scan = new Scanner(file);
        userId = scan.next();
        dos.writeUTF(userId);
//        String username =dis.readUTF();
//        System.out.println(username);
        UserData data = new UserData("Username", dis.readUTF());
        infoTable.add(data);
//        String username =dis.readUTF();
//        System.out.println(username);//just to check
        infoTable.add(new UserData("Email", dis.readUTF()));
        infoTable.add(new UserData("Mobile No", dis.readUTF()));
        infoTable.add(new UserData("NID No", dis.readUTF()));
        infoTable.add(new UserData("Address", dis.readUTF()));
        infoTable.add(new UserData("Date of Birth", dis.readUTF()));
        System.out.println("done");
        if(infoTable.contains(data)){
            System.out.println("yes");
        }
        dis.close();
        dos.close();
        criteriaColumn.setCellValueFactory(new PropertyValueFactory<UserData,String>("Criteria"));
        infoColumn.setCellValueFactory(new PropertyValueFactory<UserData,String>("Info"));
        perInfoTable.setItems(infoTable);
    }
    
    private void addStations(){
        stations.add("Dhaka");
        stations.add("Dhamrai");
        stations.add("Manikganj");
        stations.add("Faridpur");
        stations.add("Muksudpur");
        stations.add("GopalGanj");
        stations.add("Khulna");
    }
    
    private void setPurchaseInfo() throws IOException{
        InetAddress ip=  InetAddress.getByName("localhost");
        Socket s= new Socket(ip, 4557);
        if(s.isConnected()){
            System.out.println("connected");
        }else{
            System.out.println("Error in connection");
        }
        
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        
        dos.writeUTF("ClientHandler");
        String command = "getPurchaseInfo";
        dos.writeUTF(command);
//        File file = new File("userInfo.txt");
//        if(!file.exists()) file.createNewFile();
//        Scanner scan = new Scanner(file);
//        String userId = scan.next();
        dos.writeUTF(userId);
        BufferedOutputStream bos= new BufferedOutputStream(new FileOutputStream("purchaseInfo.txt"));
        long filesize = Long.parseLong(dis.readUTF());
        InputStream is = s.getInputStream();
        
        byte contents[] = new byte[10000];
        int bytesRead = 0; 
        long total=0;			//how many bytes read

        while(total<filesize)	//loop is continued until received byte=totalfilesize
        {
                bytesRead=is.read(contents);
                total+=bytesRead;
                bos.write(contents, 0, bytesRead); 
        }
        bos.flush(); 
        readPurchaseInfo();
    }
    
    private void readPurchaseInfo() throws FileNotFoundException{
        File purchase = new File("purchaseInfo.txt");
        Scanner scn= new Scanner(purchase);
        System.out.println(purchase.length());
        int i=1;
        int flag=0;
        while(scn.hasNext()){
            //System.out.println(scn.next());
            
            String SlNo=scn.next();
            System.out.println(SlNo);
            String JourneyDate=scn.next();
            System.out.println(JourneyDate);
            String StationFrom=scn.next();
            System.out.println(StationFrom);
            String StationTo=scn.next();
            System.out.println(StationTo);
            String PurchaseDate=scn.next();
            System.out.println(PurchaseDate);
            String noOfSeat=scn.next();
            System.out.println(noOfSeat);
            PurchaseData pdata =new PurchaseData(SlNo,JourneyDate,StationFrom,StationTo,PurchaseDate,noOfSeat);
            purchaseInfo.add(0, pdata);
            flag=1;
            //purchaseInfoTable.add(pdata);
//              System.out.println(i);
//              i++;
            
        }
        if(flag==1){
            lastPurchaseInfo.add(purchaseInfo.get(0));
        }
        
        
        System.out.println("done in func");
        journeyDatePur.setCellValueFactory(new PropertyValueFactory<PurchaseData,String>("JourneyDate"));
        slNo.setCellValueFactory(new PropertyValueFactory<PurchaseData,String>("SlNo"));
        noOfSeatPur.setCellValueFactory(new PropertyValueFactory<PurchaseData,String>("noOfSeat"));
        stationFromPur.setCellValueFactory(new PropertyValueFactory<PurchaseData,String>("StationFrom"));
        stationToPur.setCellValueFactory(new PropertyValueFactory<PurchaseData,String>("StationTo"));
        purchaseDatePur.setCellValueFactory(new PropertyValueFactory<PurchaseData,String>("PurchaseDate"));
        
        journeyDatePurLast.setCellValueFactory(new PropertyValueFactory<PurchaseData,String>("JourneyDate"));
        slNoLast.setCellValueFactory(new PropertyValueFactory<PurchaseData,String>("SlNo"));
        noOfSeatPurLast.setCellValueFactory(new PropertyValueFactory<PurchaseData,String>("noOfSeat"));
        stationFromPurLast.setCellValueFactory(new PropertyValueFactory<PurchaseData,String>("StationFrom"));
        stationToPurLast.setCellValueFactory(new PropertyValueFactory<PurchaseData,String>("StationTo"));
        purchaseDatePurLast.setCellValueFactory(new PropertyValueFactory<PurchaseData,String>("PurchaseDate"));
        
        
        purchaseHistoryTable.setItems(purchaseInfo);
        LastPurchaseTable.setItems(lastPurchaseInfo);
        
        journeyDatePur.setSortable(false);
        slNo.setSortable(false);
        noOfSeatPur.setSortable(false);
        stationFromPur.setSortable(false);
        stationToPur.setSortable(false);
        purchaseDatePur.setSortable(false);
        
                
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stationFrom.setItems(stationFromList);
        stationTo.setItems(stationToList);
        coach.setItems(coachList);
        
        MainWindowController mwc = this;
        mwc2 = this;
        try {
           setUserInfo();
       } catch (IOException ex) {
           System.out.println(ex);
           
       }
        try {
            setPurchaseInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addStations();
        
        try {
            new SetNotificationInfo(mwc,userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        
        journeyDate.setDayCellFactory(picker -> new DateCell(){
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                LocalDate endDay = today.plusDays(10);
                setDisable(empty || date.compareTo(today)<0 || date.compareTo(endDay)>0);
            }
        });
        
        tabPane.sceneProperty().addListener((obs, oldScene, newScene)->{
            Platform.runLater(()->{
                stage = (Stage) newScene.getWindow();
                stage.setOnCloseRequest(e->{
                    try {
                        InetAddress ip=  InetAddress.getByName("localhost");
                        Socket s= new Socket(ip, 4557);
                        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                        dos.writeUTF("ClientHandler");
                        dos.writeUTF("logout");
                        dos.writeUTF(userId);
                    } catch (UnknownHostException ex) {
                        //Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        //Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.exit(0);
                    
                });
            });
        });
        
        // TODO
    }    

    @FXML
    private void confirmRouteOnAction(ActionEvent event) throws IOException {
        int x=0;
        if(journeyDate.getEditor().getText().equals(""))
            x=1;
        else if(stationFrom.getSelectionModel().getSelectedIndex()==-1)
            x=1;
        else if(stationTo.getSelectionModel().getSelectedIndex()==-1)
            x=1;
        else if(coach.getSelectionModel().getSelectedIndex()==-1)
            x=1;
        if(x==1){
            invalidInput.setText("Please Select all items!!!");
            return;
        }
        else{
            invalidInput.setText("");
        }
        
        
        
        File route = new File("route.txt");
        if(route.exists())
            route.delete();
        route.createNewFile();
        FileWriter writer = new FileWriter(route, true);
        FileWriter writer1 = new FileWriter(route);
        writer1.write("");
        writer1.close();
        writer.write(journeyDate.getEditor().getText() + "\n");
        writer.write(stationFrom.getValue().toString() + "\n");
        writer.write(stationTo.getValue().toString() + "\n");
        writer.write(coach.getValue().toString() + "\n");
        writer.close();
        
        Parent seatStageRoot = FXMLLoader.load(getClass().getResource("SeatSelection.fxml"));
        Scene seatStageScene =new Scene (seatStageRoot);
        Stage seatStage = new Stage();
        seatStage.setScene(seatStageScene);
        seatStage.setTitle("Select Seats");
        seatStage.setResizable(false);
        seatStage.initModality(Modality.APPLICATION_MODAL);
        seatStage.show();
        
    }

    @FXML
    private void saveInformationOnAction(ActionEvent event) {
        //purchaseHistory.setStyle("-fx-background-color:green");
        saveInfoNotify.setText("");
        try {
            int x=0;
            String newUsername=userName.getText();
            if(newUsername.equals(""))x=1;
            String newDateOfBirth=userDateOfBirth.getEditor().getText();
            if(newDateOfBirth.equals(""))x=1;
            String newId=userIDNumber.getText();
            if(newId.matches(".*[a-zA-Z].*") || newId.equals("")){
                saveInfoNotify.setText("Invalid ID No");
                x=1;
            }
            String newAddress=userAddress.getText();
            if(newAddress.equals(""))x=1;
            if(x==1){
                saveInfoNotify.setText("**Fill up all the INFORMATION");
                return;
            }
            
            
            File file = new File("updateUserInfo.txt");
            if(!file.exists()) file.createNewFile();
            PrintWriter pw =new PrintWriter(file);
            pw.println(userId);
            pw.println(newUsername);
            pw.println(newDateOfBirth);
            pw.println(newId);
            pw.println(newAddress);
            pw.close();
            
                
            Parent chngInfoStageRoot = FXMLLoader.load(getClass().getResource("ChangeInfo.fxml"));
            Scene chngInfoScene =new Scene (chngInfoStageRoot);
            Stage chngInfoStage = new Stage();
            chngInfoStage.setScene(chngInfoScene);
            chngInfoStage.setTitle("Change Info");
            chngInfoStage.setResizable(false);
            chngInfoStage.initModality(Modality.APPLICATION_MODAL);
            chngInfoStage.show();
            new ChangeInfoController();
            userName.setText("");
            userDateOfBirth.getEditor().setText("");
            userAddress.setText("");
            userIDNumber.setText("");
              
        } catch (Exception e) {
        }
    }

    @FXML
    private void changePasswordOnAction(ActionEvent event) throws IOException {
        try {
            Parent seatStageRoot = FXMLLoader.load(getClass().getResource("ChangePassword.fxml"));
            Scene seatStageScene =new Scene (seatStageRoot);
            Stage seatStage = new Stage();
            seatStage.setScene(seatStageScene);
            seatStage.setTitle("Change Password");
            seatStage.setResizable(false);
            seatStage.initModality(Modality.APPLICATION_MODAL);
            seatStage.show();
            new ChangePasswordController();

        } catch (LoadException e) {
            e.printStackTrace();
            System.err.println("exception in change password");
        }
        
    }

    public void setBuyTab(){
        tabPane.getSelectionModel().select(purchaseTicket);
    }
    
    @FXML
    private void dateSelectOnAction(ActionEvent event) throws IOException {
        String date = journeyDate.getEditor().getText();
        System.out.println(date);
        try{
            stationFromList.clear();
        }catch(Exception e){
            System.out.println(e);
        }
        for(String i: stations){
            stationFromList.add(i);
        }
        
    }

    @FXML
    private void stationFromSelectionOnAction(ActionEvent event) {
        String from = stationFrom.getSelectionModel().getSelectedItem().toString();
        try{
            stationToList.clear();
        }catch(Exception e){
            System.out.println(e);
        }
        for(String i: stations){
            if(i.equals(from)) continue;
            stationToList.add(i);
        }
    }

    @FXML
    private void stationToSelectionOnAction(ActionEvent event) {
        try{
            coachList.clear();
        }catch(Exception e){
            System.out.println(e);
        }
        coachList.add("AC");
        coachList.add("NonAC");
    }

    @FXML
    private void logoutOnAction(Event event) throws IOException {
        InetAddress ip=  InetAddress.getByName("localhost");
        Socket s= new Socket(ip, 4557);
        if(s.isConnected()){
            System.out.println("connected");
        }else{
            System.out.println("Error in connection");
        }
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        dos.writeUTF("ClientHandler");
        dos.writeUTF("logout");
        dos.writeUTF(userId);
        
        Parent root = FXMLLoader.load(getClass().getResource("WelcomeWindow.fxml"));
        Stage stage = (Stage) tabPane.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.close();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void buyTicketOnAction(ActionEvent event) {
        String bookingId = bookingCode.getText();
        BuyTicketConformation btc= new BuyTicketConformation(bookingId);
    }

    @FXML
    private void searchOnAction(ActionEvent event) {
        String bookingId= bookingCode.getText();
        String password =finalPassword.getText();
        if(bookingId.equals("")|| password.equals("")){
            finalInvalid.setText("The fields cannot be empty!");
            return;
        }
        
        BookingIdQuery biq= new BookingIdQuery(bookingId, password);
        
    }
    
}
