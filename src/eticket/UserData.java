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
public class UserData {
    private String criteria;
    private String info;

    public UserData(String criteria, String info) {
        this.criteria = criteria;
        this.info = info;
    }

    public String getCriteria() {
        return criteria;
    }

    public String getInfo() {
        return info;
    }
    
    
}
