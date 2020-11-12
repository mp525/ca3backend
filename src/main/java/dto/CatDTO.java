/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Cat;

/**
 *
 * @author Mathias
 */
public class CatDTO {
    private long id;
    private String name;
    private String race;
    private String owner;

    public CatDTO(String name, String race) {
        this.name = name;
        this.race = race;
    }
    
    public CatDTO(Cat cat) {
        this.name = cat.getName();
        this.race = cat.getRace();
        this.owner = cat.getOwner().getUserName();
        this.id = cat.getId();
    }

    public String getOwner() {
        return owner;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }
    
    
    
}
