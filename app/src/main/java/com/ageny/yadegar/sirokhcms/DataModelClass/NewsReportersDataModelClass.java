package com.ageny.yadegar.sirokhcms.DataModelClass;

public class NewsReportersDataModelClass {
    	String id;
    	String first_name;
    	String last_name;
    	String Email;
    	String Image;
    	String Bio;
    	String news_category_id;

    public NewsReportersDataModelClass(String id, String first_name, String last_name, String email, String image, String bio, String news_category_id) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        Email = email;
        Image = image;
        Bio = bio;
        this.news_category_id = news_category_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getNews_category_id() {
        return news_category_id;
    }

    public void setNews_category_id(String news_category_id) {
        this.news_category_id = news_category_id;
    }
}
