package com.ageny.yadegar.sirokhcms.DataModelClass;

public class PeopleDataModelClass {
        String id;
        String First_Name;
        String Last_Name;
        String Phone;
        String Mobile;
        String Address;
        String Image;

    public PeopleDataModelClass(String id, String first_name, String last_name,
                                String phone, String mobile, String address, String image) {
        this.id = id;
        this.First_Name = first_name;
        this.Last_Name = last_name;
        this.Phone = phone;
        this.Mobile = mobile;
        this.Address = address;
        this.Image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return First_Name;
    }

    public void setFirst_name(String first_name) {
        this.First_Name = first_name;
    }

    public String getLast_name() {
        return Last_Name;
    }

    public void setLast_name(String last_name) {
        this.Last_Name = last_name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
