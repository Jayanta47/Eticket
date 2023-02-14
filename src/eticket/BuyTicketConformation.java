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
public class BuyTicketConformation {
    MainWindowController mwc;
    String bookingId;

    public BuyTicketConformation(String bookingId) {
        this.bookingId = bookingId;
        System.out.println(bookingId + " 1");
        mwc = MainWindowController.mwc2;
        Confirm();
        
    }
    
     
    public void Confirm(){
         try {
             InetAddress ip = InetAddress.getByName("localhost");
            Socket socket = new Socket(ip, 4557);
            if(socket.isConnected()){
                System.out.println("connected");
            }
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String command = "confirmBuy";
            dos.writeUTF("ClientHandler");
            dos.flush();
            dos.writeUTF(command);
            dos.flush();
            dos.writeUTF(bookingId);
            dos.flush();
            
            String str = dis.readUTF();
            String str1 = dis.readUTF();
            System.out.println(str + str1);
            
            mwc.notificationTableInfo.add(0, new NotificationData(str, str1));
            
            mwc.purchaseInfo.add(0, new PurchaseData(dis.readUTF(),dis.readUTF(),dis.readUTF(),dis.readUTF(),dis.readUTF(),dis.readUTF()));
            mwc.lastPurchaseInfo.set(0, mwc.purchaseInfo.get(0));
            //dos.writeUTF(booking id);         ekhane booking id pass kora lagbe
         } catch (Exception e) {
             e.printStackTrace();
             System.out.println("error in buying");
         }
     }
}
