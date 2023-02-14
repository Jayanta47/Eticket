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
public class PurchaseData {
    String SlNo;
    String JourneyDate;
    String StationFrom;
    String StationTo;
    String PurchaseDate;
    String noOfSeat;

    public PurchaseData(String SlNo, String JourneyDate, String StationFrom, String StationTo, String PurchaseDate, String noOfSeat) {
        this.SlNo = SlNo;
        this.JourneyDate = JourneyDate;
        this.StationFrom = StationFrom;
        this.StationTo = StationTo;
        this.PurchaseDate = PurchaseDate;
        this.noOfSeat = noOfSeat;
        System.out.println("done in purchase data class");
            
//            System.out.println(SlNo);
//            
//            System.out.println(JourneyDate);
//            
//            System.out.println(StationFrom);
//       
//            System.out.println(StationTo);
//            
//            System.out.println(PurchaseDate);
//
//            System.out.println(noOfSeat);
    }

    public String getSlNo() {
        return SlNo;
    }
    
    public String getJourneyDate() {
        return JourneyDate;
    }

    public String getNoOfSeat() {
        return noOfSeat;
    }

    public String getPurchaseDate() {
        return PurchaseDate;
    }

    public String getStationFrom() {
        return StationFrom;
    }

    public String getStationTo() {
        return StationTo;
    }
    
    
}
