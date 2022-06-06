package com.example.bhuhospital.Room;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Appoinment")
public class appoinment_entity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name_of_doc;
    private String name_of_patinet;
    private String email;
    private String symptoms;
    private String date;   //year month date constructor
    int isPending;

    public appoinment_entity(String name_of_doc, String name_of_patinet, String email, String symptoms, String date, int isPending) {
        this.name_of_doc = name_of_doc;
        this.name_of_patinet = name_of_patinet;
        this.email = email;
        this.symptoms = symptoms;
        this.date = date;
        this.isPending = isPending;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName_of_doc() {
        return name_of_doc;
    }

    public String getName_of_patinet() {
        return name_of_patinet;
    }

    public String getEmail() {
        return email;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getDate() {
        return date;
    }

    public int isPending() {
        return isPending;
    }
}
