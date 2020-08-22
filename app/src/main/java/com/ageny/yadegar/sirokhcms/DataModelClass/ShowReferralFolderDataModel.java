package com.ageny.yadegar.sirokhcms.DataModelClass;

import java.text.ParseException;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class ShowReferralFolderDataModel {

    String id;
    String Subject;
    String Description;
    String from_user_id;
    String to_user_id;
    String referral_folder_state_id;
    String urgency_id;
    String Seen;
    String news_type_id;
    String ActionDate;
    String created_at;
    String updated_at;
    String people_id;
    String people_first_name;
    String people_last_name;
    String people_Phone;
    String people_Mobile;
    String people_Address;
    String people_Image;
    String organ_id;
    String organ_Title;
    String organ_Address;
    String organ_Phone;
    String organ_Fax;

    public ShowReferralFolderDataModel(String id, String subject, String description, String from_user_id, String to_user_id, String referral_folder_state_id, String urgency_id, String seen, String news_type_id, String actionDate, String created_at, String updated_at,
                                       String people_id, String people_first_name, String people_last_name, String people_Phone, String people_Mobile, String people_Address, String people_Image, String organ_id, String organ_Title, String organ_Address, String organ_Phone, String organ_Fax) {
        this.id = id;
        Subject = subject;
        Description = description;
        this.from_user_id = from_user_id;
        this.to_user_id = to_user_id;
        this.referral_folder_state_id = referral_folder_state_id;
        this.urgency_id = urgency_id;
        Seen = seen;
        this.news_type_id = news_type_id;
        ActionDate = actionDate;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.people_id = people_id;
        this.people_first_name = people_first_name;
        this.people_last_name = people_last_name;
        this.people_Phone = people_Phone;
        this.people_Mobile = people_Mobile;
        this.people_Address = people_Address;
        this.people_Image = people_Image;
        this.organ_id = organ_id;
        this.organ_Title = organ_Title;
        this.organ_Address = organ_Address;
        this.organ_Phone = organ_Phone;
        this.organ_Fax = organ_Fax;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        if(Subject != null)
            return Subject;
        else
            return "";
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getReferral_folder_state_id() {
        return referral_folder_state_id;
    }

    public void setReferral_folder_state_id(String referral_folder_state_id) {
        this.referral_folder_state_id = referral_folder_state_id;
    }

    public String getUrgency_id() {
        return urgency_id;
    }

    public void setUrgency_id(String urgency_id) {
        this.urgency_id = urgency_id;
    }

    public String getSeen() {
        return Seen;
    }

    public void setSeen(String seen) {
        Seen = seen;
    }

    public String getNews_type_id() {
        return news_type_id;
    }

    public void setNews_type_id(String news_type_id) {
        this.news_type_id = news_type_id;
    }

    public String getActionDate() {

        PersianDateFormat persianDateFormat=new PersianDateFormat("yyyy-MM-dd HH:mm:ss");
        PersianDate pdate = null;
        try {
            pdate=persianDateFormat.parseGrg(ActionDate,"yyyy-MM-dd HH:mm:ss");
            return pdate.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setActionDate(String actionDate) {
        ActionDate = actionDate;
    }

    public String getCreated_at() {
        PersianDateFormat persianDateFormat=new PersianDateFormat("yyyy-MM-dd HH:mm:ss");
        PersianDate pdate = null;
        try {
            pdate=persianDateFormat.parseGrg(created_at,"yyyy-MM-dd HH:mm:ss");
            return pdate.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        PersianDateFormat persianDateFormat=new PersianDateFormat("yyyy-MM-dd HH:mm:ss");
        PersianDate pdate = null;
        try {
            pdate=persianDateFormat.parseGrg(updated_at,"yyyy-MM-dd HH:mm:ss");
            return pdate.toString();
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getPeople_id() {
        return people_id;
    }

    public void setPeople_id(String people_id) {
        this.people_id = people_id;
    }

    public String getPeople_first_name() {
        if(people_first_name != null)
            return people_first_name;
        else
            return " ";
    }

    public void setPeople_first_name(String people_first_name) {
        this.people_first_name = people_first_name;
    }

    public String getPeople_last_name() {
        if(people_last_name != null)
            return people_last_name;
        else
            return " ";
    }

    public void setPeople_last_name(String people_last_name) {
        this.people_last_name = people_last_name;
    }

    public String getPeople_Phone() {
        return people_Phone;
    }

    public void setPeople_Phone(String people_Phone) {
        this.people_Phone = people_Phone;
    }

    public String getPeople_Mobile() {
        return people_Mobile;
    }

    public void setPeople_Mobile(String people_Mobile) {
        this.people_Mobile = people_Mobile;
    }

    public String getPeople_Address() {
        return people_Address;
    }

    public void setPeople_Address(String people_Address) {
        this.people_Address = people_Address;
    }

    public String getPeople_Image() {
        return people_Image;
    }

    public void setPeople_Image(String people_Image) {
        this.people_Image = people_Image;
    }

    public String getOrgan_id() {
        return organ_id;
    }

    public void setOrgan_id(String organ_id) {
        this.organ_id = organ_id;
    }

    public String getOrgan_Title() {
        return organ_Title;
    }

    public void setOrgan_Title(String organ_Title) {
        this.organ_Title = organ_Title;
    }

    public String getOrgan_Address() {
        return organ_Address;
    }

    public void setOrgan_Address(String organ_Address) {
        this.organ_Address = organ_Address;
    }

    public String getOrgan_Phone() {
        return organ_Phone;
    }

    public void setOrgan_Phone(String organ_Phone) {
        this.organ_Phone = organ_Phone;
    }

    public String getOrgan_Fax() {
        return organ_Fax;
    }

    public void setOrgan_Fax(String organ_Fax) {
        this.organ_Fax = organ_Fax;
    }
}
