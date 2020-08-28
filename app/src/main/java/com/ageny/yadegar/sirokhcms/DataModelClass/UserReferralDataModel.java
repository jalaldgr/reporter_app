package com.ageny.yadegar.sirokhcms.DataModelClass;

import android.database.Cursor;
import android.renderscript.ScriptIntrinsicYuvToRGB;

import java.text.ParseException;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class UserReferralDataModel  {
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
     String ReferralFolderStateTitle;
     String UrgencyTitle;
     String Color;
     String start_interview;
     String end_interview;
     String ReferralFolderReturnDescription;
     String ReferralFolderReturnReasonID;
     String People_Id;
     String Organ_ID;
    ///////Data Model Class



    public UserReferralDataModel(String id, String subject, String description, String from_user_id, String to_user_id, String referral_folder_state_id, String urgency_id, String seen, String news_type_id, String actionDate, String created_at, String updated_at, String referralFolderStateTitle, String urgencyTitle, String color) {
        this.id = id;
        this.Subject = subject;
        this.Description = description;
        this.from_user_id = from_user_id;
        this.to_user_id = to_user_id;
        this.referral_folder_state_id = referral_folder_state_id;
        this.urgency_id = urgency_id;
        this.Seen = seen;
        this.news_type_id = news_type_id;
        this.ActionDate = actionDate;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.ReferralFolderStateTitle = referralFolderStateTitle;
        this.UrgencyTitle = urgencyTitle;
        this.Color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return Subject;
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

    public String getNews_type_title() {
        String news_type_title=news_type_id;
        switch (news_type_title){
            case    "1":
                news_type_title= "پوششی";
                break;
            case   "2":
                news_type_title="تولیدی";
                break;
            case    "3":
                news_type_title="دریافتی";
                break;
            case    "4":
                news_type_title="ارسالی";
                break;
            case "5":
                news_type_title= "نقلی";


        }
        return news_type_title;
    }

    public void setNews_type_id(String news_type_id) {
        this.news_type_id = news_type_id;
    }
    public String getNews_type_id(String news_type_id) {
        return news_type_id;
    }
    public String getActionDate() {
        PersianDateFormat persianDateFormat=new PersianDateFormat("yyyy-MM-dd HH:mm:ss");
        PersianDate pdate;
        try {
            pdate=persianDateFormat.parseGrg(ActionDate,"yyyy-MM-dd HH:mm:ss");
            return pdate.toString();
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setActionDate(String actionDate) {
        this.ActionDate = actionDate;
    }

    public String getCreated_at() {
        PersianDateFormat persianDateFormat=new PersianDateFormat("yyyy-MM-dd HH:mm:ss");
        PersianDate pdate;
        try {
            pdate=persianDateFormat.parseGrg(created_at,"yyyy-MM-dd HH:mm:ss");
            return pdate.toString();
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setCreated_at(String created_At) {
        this.created_at = created_At;
    }

    public String getUpdated_at() {
        PersianDateFormat persianDateFormat=new PersianDateFormat("yyyy-MM-dd HH:mm:ss");
        PersianDate pdate;
        try {
            pdate=persianDateFormat.parseGrg(updated_at,"yyyy-MM-dd HH:mm:ss");
            return pdate.toString();
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setUpdated_at(String updated_At) {
        this.updated_at = updated_At;
    }

    public String getReferralFolderStateTitle() {
        return ReferralFolderStateTitle;
    }

    public void setReferralFolderStateTitle(String referralFolderStateTitle) {
        ReferralFolderStateTitle = referralFolderStateTitle;
    }

    public String getUrgencyTitle() {
        return UrgencyTitle;
    }

    public void setUrgencyTitle(String urgencyTitle) {
        UrgencyTitle = urgencyTitle;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getStart_interview() {
        return start_interview;
    }

    public void setStart_interview(String start_interview) {
        this.start_interview = start_interview;
    }

    public String getEnd_interview() {
        return end_interview;
    }

    public void setEnd_interview(String end_interview) {
        this.end_interview = end_interview;
    }

    public String getReferralFolderReturnDescription() {
        return ReferralFolderReturnDescription;
    }

    public void setReferralFolderReturnDescription(String referralFolderReturnDescription) {
        ReferralFolderReturnDescription = referralFolderReturnDescription;
    }

    public String getReferralFolderReturnReasonID() {
        return ReferralFolderReturnReasonID;
    }

    public void setReferralFolderReturnReasonID(String referralFolderReturnReasonID) {
        ReferralFolderReturnReasonID = referralFolderReturnReasonID;
    }

    public String getNews_type_id() {
        return news_type_id;
    }

    public String getPeople_Id() {
        return People_Id;
    }

    public void setPeople_Id(String people_Id) {
        People_Id = people_Id;
    }

    public String getOrgan_ID() {
        return Organ_ID;
    }

    public void setOrgan_ID(String organ_ID) {
        Organ_ID = organ_ID;
    }
}