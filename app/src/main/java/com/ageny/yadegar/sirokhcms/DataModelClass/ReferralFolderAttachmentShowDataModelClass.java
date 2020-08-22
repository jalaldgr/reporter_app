package com.ageny.yadegar.sirokhcms.DataModelClass;

import java.text.ParseException;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class ReferralFolderAttachmentShowDataModelClass {
    String Refferal_Id;
    String User_Id;
    String First_Name;
    String Last_Name;
    String File_Path;
    String File_Description;
    String Created_At;

    public ReferralFolderAttachmentShowDataModelClass(String file_Path, String file_Description,
                                                      String created_At, String first_Name, String last_Name) {
        this.First_Name = first_Name;
        this.Last_Name = last_Name;
        this.File_Path = file_Path;
        this.File_Description = file_Description;
        this.Created_At = created_At;
    }


    public String getRefferal_Id() {
        return Refferal_Id;
    }

    public void setRefferal_Id(String refferal_Id) {
        Refferal_Id = refferal_Id;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getFile_Path() {
        return File_Path;
    }

    public void setFile_Path(String file_Path) {
        File_Path = file_Path;
    }

    public String getFile_Description() {
        return File_Description;
    }

    public void setFile_Description(String file_Description) {
        File_Description = file_Description;
    }

    public String getCreated_At() {
        PersianDateFormat persianDateFormat=new PersianDateFormat("yyyy-MM-dd HH:mm:ss");
        PersianDate pdate = null;
        try {
            pdate=persianDateFormat.parseGrg(Created_At,"yyyy-MM-dd HH:mm:ss");
            return pdate.toString();
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setCreated_At(String created_At) {
        Created_At = created_At;
    }
}
