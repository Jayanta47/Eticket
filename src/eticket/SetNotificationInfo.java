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
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author USER
 */
public class SetNotificationInfo {
        String UserId;
        MainWindowController mwc;

    public SetNotificationInfo( MainWindowController mwc,String UserId) {
        this.UserId = UserId;
        this.mwc = mwc;
        setfile();
    }
        
    private void setfile(){
        try {
        InetAddress ip= InetAddress.getByName("localhost");
        Socket s= new Socket(ip, 4557);
        if(s.isConnected()){
            System.out.println("connected");
        }
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
         
        String command ="getPurchasedSeatData";
        dos.writeUTF("ClientHandler");
        dos.writeUTF(command);
        dos.writeUTF(UserId);
        BufferedOutputStream bos= new BufferedOutputStream(new FileOutputStream("PurchaseSeatInfo.txt"));
        String size= dis.readUTF();
        int l= Integer.parseInt(size);
            System.out.println(l);
        byte[] contents =new byte[l];
        dis.read(contents);
        bos.write(contents, 0, l);
        bos.close();
        readfile();
        } catch (Exception e) {
        }
    }
    
    private void readfile(){
        try {
            File purchase = new File("PurchaseSeatInfo.txt");
        Scanner scn= new Scanner(purchase);
        while(scn.hasNextLine()){
            String temp=scn.nextLine();
            temp=temp.replace("//", " ");
            StringTokenizer st = new StringTokenizer(temp);
            String DateandTime=st.nextToken();
            String Message=st.nextToken();
            DateandTime=DateandTime.replace("#"," ");
            Message=Message.replace("#", " ");
            NotificationData ndata =new NotificationData(DateandTime, Message);            
            mwc.notificationTableInfo.add(0,ndata);    
        }
        mwc.getDateColumn().setCellValueFactory(new PropertyValueFactory<NotificationData,String>("DateandTime"));
        mwc.getMessagesColumn().setCellValueFactory(new PropertyValueFactory<NotificationData,String>("Message"));
        mwc.getNotificationTable().setItems(mwc.notificationTableInfo);
        
        mwc.getDateColumn().setSortable(false);
        mwc.getMessagesColumn().setSortable(false);
        //mwc.notificationTableInfo.clear();
        } catch (Exception e) {
        }
    }
        
    
    
}
