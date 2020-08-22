package com.ageny.yadegar.sirokhcms.DataModelClass;

import java.text.ParseException;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class MarginDataModelClass {

    String	created_at;
    String	RememberDate;
    String	Title;
    String	first_name;
    String	last_name;
    String	RID;
    String	UID;
    String	ReferralFolderMarginDescription;
    String	ReferralFolderMarginStateID;
    String  News_Id;
    String  Subject;

    public MarginDataModelClass(String created_at, String rememberDate, String title, String first_name, String last_name, String RID, String UID,
                                String referralFolderMarginDescription, String referralFolderMarginStateID, String subject) {
        this.created_at = created_at;
        RememberDate = rememberDate;
        Title = title;
        this.first_name = first_name;
        this.last_name = last_name;
        this.RID = RID;
        this.UID = UID;
        ReferralFolderMarginDescription = referralFolderMarginDescription;
        ReferralFolderMarginStateID = referralFolderMarginStateID;
        Subject = subject;
    }

    public MarginDataModelClass(String referralFolderMarginDescription, String rememberDate, String created_at, String title, String first_name,
                                String last_name) {
        this.created_at = created_at;
        RememberDate = rememberDate;
        Title = title;
        this.first_name = first_name;
        this.last_name = last_name;
        ReferralFolderMarginDescription = referralFolderMarginDescription;
        ReferralFolderMarginStateID = "";
        this.RID = "";
        this.UID = "";
        News_Id = "";
    }

    public String getCreated_at() {
        PersianDateFormat persianDateFormat=new PersianDateFormat("yyyy-MM-dd HH:mm:ss");
        PersianDate pdate = null;
        try {
            pdate=persianDateFormat.parseGrg(created_at,"yyyy-MM-dd HH:mm:ss");
            return pdate.toString();
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getRememberDate() {
        PersianDateFormat persianDateFormat=new PersianDateFormat("yyyy-MM-dd HH:mm:ss");
        PersianDate pdate = null;
        try {
            pdate=persianDateFormat.parseGrg(RememberDate,"yyyy-MM-dd HH:mm:ss");
            return pdate.toString();

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
       //return RememberDate;
    }

    public void setRememberDate(String rememberDate) {
        RememberDate = rememberDate;
    }

    public String getDescription() {
        return ReferralFolderMarginDescription;
    }

    public void setDescription(String description) {
        ReferralFolderMarginDescription = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
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

    public String getRID() {
        return RID;
    }

    public void setRID(String RID) {
        this.RID = RID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getReferralFolderMarginDescription() {
        return ReferralFolderMarginDescription;
    }

    public void setReferralFolderMarginDescription(String referralFolderMarginDescription) {
        ReferralFolderMarginDescription = referralFolderMarginDescription;
    }

    public String getReferralFolderMarginStateID() {
        return ReferralFolderMarginStateID;
    }

    public void setReferralFolderMarginStateID(String referralFolderMarginStateID) {
        ReferralFolderMarginStateID = referralFolderMarginStateID;
    }

    public String getNews_Id() {
        return News_Id;
    }

    public void setNews_Id(String news_Id) {
        News_Id = news_Id;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }
}
