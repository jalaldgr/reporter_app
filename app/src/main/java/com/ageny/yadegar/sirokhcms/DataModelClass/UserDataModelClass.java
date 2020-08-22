package com.ageny.yadegar.sirokhcms.DataModelClass;

public class UserDataModelClass {
    String id;
    String email;
    String first_name;
    String last_name;
    String Image;
    String is_logedin;

    public UserDataModelClass(String id, String email, String first_name, String last_name, String image ,String is_logedin) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.Image = image;
        this.is_logedin =is_logedin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getIs_logedin() {
        return is_logedin;
    }

    public  Boolean IsUserLogedIn(){
        Boolean result=false;
        if(is_logedin=="true")result=true;
        else if (is_logedin=="false") result= false;
        return result;
    }
}
