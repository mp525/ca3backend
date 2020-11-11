/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Mathias
 */
public class PersonDTO {
    private String name;
    private String phone_h;
    private String address;

    public PersonDTO(String name, String phone_h, String address) {
        this.name = name;
        this.phone_h = phone_h;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_h() {
        return phone_h;
    }

    public void setPhone_h(String phone_h) {
        this.phone_h = phone_h;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
    
}
