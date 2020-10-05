package com.example.mobileapp.models;

public class ContactItems {
    //data member
    private int ImageId;
    private String ContactName;
    private String ContactNumber;
    private String ContactEmail;

    // contructor
    public ContactItems(int img,String name,String phone,String email){
        this.ImageId=img;
        this.ContactName=name;
        this.ContactNumber=phone;
        this.ContactEmail=email;
    }
    //getter member
    public int getImageId(){
        return ImageId;
    }
    public String getContactName(){
        return ContactName;
    }
    public String getContactNumber(){
        return ContactNumber;
    }

    public String getContactEmail() {
        return ContactEmail;
    }
}
