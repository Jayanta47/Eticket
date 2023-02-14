/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eticket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author USER
 */
public class BookingIdQuery {
    String bookingId;
    String password;

    public BookingIdQuery(String bookingId, String password) {
        this.bookingId = bookingId;
        this.password = password;
        Connect();
    
    }
    
    public void Connect(){
        try {
            InetAddress ip= InetAddress.getByName("localhost");
            Socket s= new Socket(ip, 4557);
            if(s.isConnected()){
                System.out.println("connected");
            }
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            String command ="BookingId";
            dos.writeUTF("ClientHandler");
            dos.writeUTF(command);
            dos.writeUTF(bookingId);
            dos.writeUTF(password);

            MainWindowController mwc = MainWindowController.mwc2;
            command = dis.readUTF();
            if(command.equals("yes")){

                mwc.getFinalStationFrom().setText(dis.readUTF());
                mwc.getFinalStationTo().setText(dis.readUTF());
                mwc.getFinalSelectedBus().setText(dis.readUTF());
                mwc.getFinalJourneyDate().setText(dis.readUTF());
                mwc.getFinalSeats().setText(dis.readUTF());
                mwc.getFinalTotalFare().setText(dis.readUTF());   
            }
            else{
                mwc.getFinalInvalid().setText("Booking ID incorrect!");
            }
        } catch (Exception e) {
        }
    }
    
    
}
