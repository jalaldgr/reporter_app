package com.ageny.yadegar.sirokhcms.DataModelClass;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PreNewsUpdateDataModelClass {

    String User_Id;
    String Referral_Id;
    String News_Title;
    String News_MainPic_File;
    String Top_Title;
    String Sub_Title;
    String News_Summary;
    String News_Category_Id;
    String News_Type_Id; // 1->tolidi  2->posheshi 3->ersali  4->naghli
    String Province_Id;
    String City_Id;
    String MainContent;
    String Reporter_Id;
    public PreNewsUpdateDataModelClass() {

    }



    public PreNewsUpdateDataModelClass(String user_Id, String referral_Id, String news_Title, String news_MainPic_File, String top_Title, String sub_Title, String news_Summary,

                                       String news_Category_Id, String news_Type_Id, String province_Id, String city_Id, String mainContent,String reporter_Id) {
        this.User_Id = user_Id;
        this.Referral_Id = referral_Id;
        this.News_Title = news_Title;
        this.News_MainPic_File = news_MainPic_File;
        this.Top_Title = top_Title;
        this.Sub_Title = sub_Title;
        this.News_Summary = news_Summary;
        this.News_Category_Id = news_Category_Id;
        this.News_Type_Id = news_Type_Id;
        this.Province_Id = province_Id;
        this.City_Id = city_Id;
        this.MainContent = mainContent;
        this.Reporter_Id = reporter_Id;
    }


    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getReferral_Id() {
        return Referral_Id;
    }

    public void setReferral_Id(String referral_Id) {
        Referral_Id = referral_Id;
    }

    public String getNews_Title() {
        return News_Title;
    }

    public void setNews_Title(String news_Title) {
        News_Title = news_Title;
    }

    public String getNews_MainPic_File() {
        return News_MainPic_File;
    }

    public void setNews_MainPic_File(String news_MainPic_File) {
        News_MainPic_File = news_MainPic_File;
    }

    public String getTop_Title() {
        return Top_Title;
    }

    public void setTop_Title(String top_Title) {
        Top_Title = top_Title;
    }

    public String getSub_Title() {
        return Sub_Title;
    }

    public void setSub_Title(String sub_Title) {
        Sub_Title = sub_Title;
    }

    public String getNews_Summary() {
        return News_Summary;
    }

    public void setNews_Summary(String news_Summary) {
        News_Summary = news_Summary;
    }

    public String getNews_Category_Id() {
        return News_Category_Id;
    }

    public void setNews_Category_Id(String news_Category_Id) {
        News_Category_Id = news_Category_Id;
    }

    public String getNews_Type_Id() {
        return News_Type_Id;
    }

    public void setNews_Type_Id(String news_Type_Id) {
        News_Type_Id = news_Type_Id;
    }

    public String getProvince_Id() {
        return Province_Id;
    }

    public void setProvince_Id(String province_Id) {
        Province_Id = province_Id;
    }

    public String getCity_Id() {
        return City_Id;
    }

    public void setCity_Id(String city_Id) {
        City_Id = city_Id;
    }

    public String getMainContent() {
        return MainContent;
    }

    public void setMainContent(String mainContent) {
        MainContent = mainContent;
    }

    public String getReporter_Id() {
        return Reporter_Id;
    }

    public void setReporter_Id(String reporter_Id) {
        Reporter_Id = reporter_Id;
    }

    public ArrayList<String> getallrecords(){
            ArrayList<String> ReturnList=new ArrayList<String>() {{
            add(getUser_Id());
            add(getReferral_Id());
            add(getNews_Title());
            add(getNews_MainPic_File());
            add(getTop_Title());
            add(getSub_Title());
            add(getNews_Summary());
            add(getNews_Category_Id());
            add(getNews_Type_Id());
            add(getProvince_Id());
            add(getCity_Id());
            add(getMainContent());
            add(getReporter_Id());
            }};
            ////////////Return All variable in String List////////////////
        return ReturnList;
}
    public Boolean isanyUnset() {
////////////////////check All Fields Not NUll////////////////
        if (  User_Id == null) return true;
        if (Referral_Id == null) return true;
        if (News_Title == null) return true;
//        if (News_MainPic_File == null) return true;
//        if (Top_Title == null) return true;
//        if (Sub_Title == null) return true;
        if (News_Summary == null) return true;
        if (News_Category_Id == null) return true;
        if (News_Type_Id == null) return true;
        if (Province_Id == null) return true;
        if (City_Id == null) return true;
        if (MainContent == null) return true;
        if (Reporter_Id == null) return true;

        return false;
    }

    @Override
    public String toString() {
        return "PreNewsUpdateDataModelClass{" +
                "User_Id='" + User_Id + '\'' +
                ", Referral_Id='" + Referral_Id + '\'' +
                ", News_Title='" + News_Title + '\'' +
                ", News_MainPic_File='" + News_MainPic_File + '\'' +
                ", Top_Title='" + Top_Title + '\'' +
                ", Sub_Title='" + Sub_Title + '\'' +
                ", News_Summary='" + News_Summary + '\'' +
                ", News_Category_Id='" + News_Category_Id + '\'' +
                ", News_Type_Id='" + News_Type_Id + '\'' +
                ", Province_Id='" + Province_Id + '\'' +
                ", City_Id='" + City_Id + '\'' +
                ", MainContent='" + MainContent + '\'' +
                ", Reporter_Id='" + Reporter_Id + '\'' +
                '}';
    }
}
