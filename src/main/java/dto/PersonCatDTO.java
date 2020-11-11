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
public class PersonCatDTO {
    private String name;
    private String address;
    private String phone;
    private String[] data;

    public PersonCatDTO(PersonDTO person, CatFactDTO catfact) {
        this.name = person.getName();
        this.address = person.getAddress();
        this.phone = person.getPhone_h();
        this.data = catfact.getData();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
    
    
    
    
}
