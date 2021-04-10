package com.example.mobileapp.models;

public class ContactItems {
    //data member
    private String ImageURL;
    private int ImageId;
    private String ContactName;
    private String ContactNumber;
    private String ContactEmail;

    // contructor
    public ContactItems(String imageURL,String name,String phone,String email){
        this.ImageURL=imageURL;
        this.ContactName=name;
        this.ContactNumber=phone;
        this.ContactEmail=email;
    }
    public ContactItems(int imgId,String name,String phone,String email){
        this.ImageId=imgId;
        this.ContactName=name;
        this.ContactNumber=phone;
        this.ContactEmail=email;
    }
    //getter member
    public String getImageURL(){
        return ImageURL;
    }
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
