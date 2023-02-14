/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eticket;

/**
 *
 * @author USER
 */
public class NotificationData {
    private String DateandTime;
    private String Message;
    
    public NotificationData(String DateandTime, String Message ){
        this.DateandTime= DateandTime;
        this.Message = Message;
    }
    
    public String getDateandTime(){
        return DateandTime;
    }
    public String getMessage(){
        return Message;
    }
}
