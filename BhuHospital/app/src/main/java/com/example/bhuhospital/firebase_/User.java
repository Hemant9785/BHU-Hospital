package com.example.bhuhospital.firebase_;

public class User {
    public String name,email,age,blood_group,gender;
    public  User(){

    }
    public  User(String name,String email,String age,String blood_group,String gender)
    {
        this.name = name;
        this.email = email;
        this.age = age;
        this.blood_group = blood_group;
        this.gender = gender;
    }
}
