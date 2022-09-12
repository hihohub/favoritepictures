package com.example.favoritepictures;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name="picture")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="username",nullable=false)
    private String username;
    @Column(name="encoding",length=100000,nullable=false)
    private String encoding;
    @Column(name="extension")
    private String extension;
    @Column(name="filename")
    private String filename;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUserName(){
        return this.username;
    }
    public void setUserName(String name){
        this.username = name;
    }
    public String getExtension(){
        return this.extension;
    }
    public void setExtension(String ext){
        this.extension = ext;
    }
    public String getEncoding(){
        return this.encoding;
    }
    public void setEncoding(String enc){
        this.encoding = enc;
    }
    public String getFileName(){
        return this.filename;
    }
    public void setFileName(String name){
        this.filename = name;
    }
 
}