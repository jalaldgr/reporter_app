package com.ageny.yadegar.sirokhcms.DataModelClass;

public class OrgansDataModelClass {
    String Id;
    String Title;
    String Address;
    String Phone;
    String Fax;

    public OrgansDataModelClass(String id, String title, String address, String phone, String fax) {
        this.Id = id;
        Title = title;
        Address = address;
        Phone = phone;
        Fax = fax;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }
}
