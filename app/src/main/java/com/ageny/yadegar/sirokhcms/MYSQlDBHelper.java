package com.ageny.yadegar.sirokhcms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

//import com.ageny.yadegar.sirokhcms.DataModelClass.UserReferralDataModel;

import com.ageny.yadegar.sirokhcms.DataModelClass.CitiesDataModelClass;
import com.ageny.yadegar.sirokhcms.DataModelClass.MarginDataModelClass;
import com.ageny.yadegar.sirokhcms.DataModelClass.NewsCategoriesDataModel;
import com.ageny.yadegar.sirokhcms.DataModelClass.NewsReportersDataModelClass;
import com.ageny.yadegar.sirokhcms.DataModelClass.OrgansDataModelClass;
import com.ageny.yadegar.sirokhcms.DataModelClass.PeopleDataModelClass;
import com.ageny.yadegar.sirokhcms.DataModelClass.PreNewsUpdateDataModelClass;
import com.ageny.yadegar.sirokhcms.DataModelClass.ProvincesDataModelClass;
import com.ageny.yadegar.sirokhcms.DataModelClass.UserDataModelClass;
import com.ageny.yadegar.sirokhcms.DataModelClass.UserReferralDataModel;
import com.mohamadamin.persianmaterialdatetimepicker.Utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class MYSQlDBHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "";
    private static final int DATABASE_VERSION = 1;
    private static String DB_NAME = "sirokhcms.db";
    //Table names
    private static String UserRefferalDataModel_Table_Name = "UserReferral";
    private static String PeopleDataModel_Table_Name = "People";
    private static String OrgansDataModel_Table_Name = "Organs";
    private static String MarginsDataModel_Table_Name = "Margins";
    private static String PreNewsDataModel_Table_Name = "PreNews";
    private static String ProvincesDataModel_Table_Name = "Provinces";
    private static String CitiesDataModel_Table_Name = "Cities";
    private static String NewsCategoriesDataModel_Table_Name = "NewsCategories";
    private static String AttachmentFileDataModel_Table_Name = "AttachmentFile";
    private static String UserDataModel_Table_Name = "User";
    private static String NewsReportersDataModel_Table_Name = "NewsReporters";
    private static String GetPreNewsDataModel_Table_Name= "GetPreNews";

    //User Column Names
    private static final String USER_ID="id";
    private static final String USER_EMAIL="email";
    private static final String USER_FIRST_NAME="first_name";
    private static final String USER_LAST_NAME="last_name";
    private static final String USER_IMAGE="Image";
    private static final String USER_IS_LOGEDIN="is_logedin";

    //UserReferral Column Names
    private static final String USERREFERAL_ID="id";
    private static final String USERREFERAL_SUBJECT="Subject";
    private static final String USERREFERAL_DESCRIPTION="Description";
    private static final String USERREFERAL_FROM_USER_ID="from_user_id";
    private static final String USERREFERAL_TO_USER_ID="to_user_id";
    private static final String USERREFERAL_REFERRAL_FOLDER_STATE_ID="referral_folder_state_id";
    private static final String USERREFERAL_URGENCY_ID="urgency_id";
    private static final String USERREFERAL_SEEN="Seen";
    private static final String USERREFERAL_NEWS_TYPE_ID="news_type_id";
    private static final String USERREFERAL_ACTION_DATE="ActionDate";
    private static final String USERREFERAL_CREATE_AT="created_at";
    private static final String USERREFERAL_UPDATED_AT="updated_at";
    private static final String USERREFERAL_USERREFERRAL_STATE_TITLE="ReferralFolderStateTitle";
    private static final String USERREFERAL_URGENCY_TITLE="UrgencyTitle";
    private static final String USERREFERAL_COLOR="Color";
    private static final String USERREFERAL_STRAT_INTERVIEW="start_interview";
    private static final String USERREFERAL_END_INTERVIEW="end_interview";
    private static final String USERREFERAL_REFERRAL_FOLDER_RETURN_DESCRIPTION="ReferralFolderReturnDescription";
    private static final String USERREFERAL_REFERRAL_FOLDER_RETURN_REASON_ID="ReferralFolderReturnReasonID";
    private static final String USERREFERAL_PEOPLE_ID="People_Id";
    private static final String USERREFERAL_ORGAN_ID="Organ_ID";

    //Province Column Names
    private static final String PROVINCE_ID="Id";
    private static final String PROVINCE_TITLE="Title";
    private static final String PROVINCE_TELEGRAM_CHANNEL_NAME="TelegramChannelName";
    private static final String PROVINCE_DISABLED="Disabled";

    //NewsCategory Column names
    private static final String NEWSCATEGORY_ID="Id";
    private static final String NEWSCATEGORY_Title="Title";

    //People Column Names
    private static final String PEOPLE_ID="id";
    private static final String PEOPLE_FIRST_NAME="First_Name";
    private static final String PEOPLE_LAST_NAME="Last_Name";
    private static final String PEOPLE_PEOPLE="Phone";
    private static final String PEOPLE_MOBILE="Mobile";
    private static final String PEOPLE_ADDRESS="Address";
    private static final String PEOPLE_IMAGES="Image";

    //Organ Column Names
    private static final String ORGAN_ID="Id";
    private static final String ORGAN_TITLE="Title";
    private static final String ORGAN_ADDRESS="Address";
    private static final String ORGAN_PHONE="Phone";
    private static final String ORGAN_FAX="Fax";

    //Margin Column Names
    private static final String MARGIN_CREATED_AT="created_at";
    private static final String MARGIN_REMEMBER_DATE="RememberDate";
    private static final String MARGIN_TITLE="Title";
    private static final String MARGIN_FIRST_NAME="first_name";
    private static final String MARGIN_LAST_NAME="last_name";
    private static final String MARGIN_RID="RID";
    private static final String MARGIN_UID="UID";
    private static final String MARGIN_REFERRAL_FOLDER_MARGIN_DESCRIPTION="ReferralFolderMarginDescription";
    private static final String MARGIN_REFERRAL_FOLDER_MARGIN_ID="ReferralFolderMarginStateID";
    private static final String MARGIN_NEWS_ID="News_Id";
    private static final String MARGIN_SUBJECT="Subject";


    //GETPRENEWS Column Names
    private static final String GETPRENEWS_REFERRAL_FOLDER_ID="referral_folder_id";
    private static final String GETPRENEWS_STARTINTERVIEW="StartInterview";
    private static final String GETPRENEWS_ENDINTERVIEW="EndInterview";
    private static final String GETPRENEWS_TOPTITLE="TopTitle";
    private static final String GETPRENEWS_TITLE="Title";
    private static final String GETPRENEWS_BOTTOMTITLE="BottomTitle";
    private static final String GETPRENEWS_CONTENTSUMMARY="ContentSummary";
    private static final String GETPRENEWS_MAINCONTENT="MainContent";
    private static final String GETPRENEWS_NEWS_ID="news_id";
    private static final String GETPRENEWS_IMAGE="Image";
    private static final String GETPRENEWS_NEWS_CATEGORY_ID="news_category_id";
    private static final String GETPRENEWS_SEEN="Seen";
    private static final String GETPRENEWS_PRE_NEWS_STATE_ID="pre_news_state_id";
    private static final String GETPRENEWS_NEWS_TYPE_ID="news_type_id";
    private static final String GETPRENEWS_PROVINCE_ID="province_id";
    private static final String GETPRENEWS_CITY_ID="city_id";
    private static final String GETPRENEWS_REPORTER_ID="reporter_id";
    private static final String GETPRENEWS_CREATED_AT="created_at";
    private static final String GETPRENEWS_UPDATED_AT="updated_at";
    private static final String GETPRENEWS_NEWSTYPETITLE="NewsTypeTitle";
    private static final String GETPRENEWS_PRENEWSSTATETITLE="PreNewsStateTitle";
    private static final String GETPRENEWS_PROVINCETITLE="ProvinceTitle";
    private static final String GETPRENEWS_CITYTITLE="CityTitle";
    private static final String GETPRENEWS_FIRST_NAME="first_name";
    private static final String GETPRENEWS_LAST_NAME="last_name";


    //PreNews Column Names
    private static final String PRENEWS_USER_ID="User_Id";
    private static final String PRENEWS_REFERRAL_ID="Referral_Id";
    private static final String PRENEWS_NEWS_TITLE="News_Title";
    private static final String PRENEWS_NEWS_MAINPIC_FILE="News_MainPic_File";
    private static final String PRENEWS_TOP_TITLE="Top_Title";
    private static final String PRENEWS_SUB_TITLE="Sub_Title";
    private static final String PRENEWS_NEWS_SUMMARY="News_Summary";
    private static final String PRENEWS_NEWS_CATEGORY_ID="News_Category_Id";
    private static final String PRENEWS_NEWS_TYPE_ID="News_Type_Id";
    private static final String PRENEWS_PROVINCE_ID="Province_Id";
    private static final String PRENEWS_CITY_ID="City_Id";
    private static final String PRENEWS_MAINCONTENT="MainContent";
    private static final String PRENEWS_REPORTER_ID="Reporter_Id";

    //Attachment Column Names
    private static final String ATTACHMENT_REFFERAL_ID="Refferal_Id";
    private static final String ATTACHMENT_USER_ID="User_Id";
    private static final String ATTACHMENT_FIRST_NAME="First_Name";
    private static final String ATTACHMENT_LAST_NAME="Last_Name";
    private static final String ATTACHMENT_FILE_PATH="File_Path";
    private static final String ATTACHMENT_FILE_DESCRIPTION="File_Description";
    private static final String ATTACHMENT_CREATED_AT="Created_At";


    //City Column Names
    private static final String CITY_ID="Id";
    private static final String CITY_TITLE="Title";
    private static final String CITY_PROVINCE_ID="province_id";

    //News Reporter Column Names
    private static final String REPORTER_ID="id";
    private static final String REPORTER_FIRST_NAME="first_name";
    private static final String REPORTER_LAST_NAME="last_name";
    private static final String REPORTER_EMAIL="Email";
    private static final String REPORTER_IMAGE="Image";
    private static final String REPORTER_BIO="Bio";
    private static final String REPORTER_NEWS_CATEGORY_ID="news_category_id";


    private static final String CREATE_NEWSREPORTER_TABLE= "CREATE TABLE "+NewsReportersDataModel_Table_Name+"("
            + REPORTER_ID + " TEXT,"
            + REPORTER_FIRST_NAME + " TEXT,"
            + REPORTER_LAST_NAME + " TEXT,"
            + REPORTER_EMAIL + " TEXT,"
            + REPORTER_IMAGE + " TEXT,"
            + REPORTER_BIO + " TEXT,"
            + REPORTER_NEWS_CATEGORY_ID + " TEXT"
            +")";

    private static final String CREATE_CITY_TABLE = "CREATE TABLE "+CitiesDataModel_Table_Name+"("
            +CITY_ID+" TEXT,"
            +CITY_TITLE+" TEXT,"
            +CITY_PROVINCE_ID+" TEXT"
            +")";

    private static final String CREATE_ATTACHMENT_TABLE="CREATE TABLE "+AttachmentFileDataModel_Table_Name+"("
            + ATTACHMENT_REFFERAL_ID + " TEXT,"
            + ATTACHMENT_USER_ID + " TEXT,"
            + ATTACHMENT_FIRST_NAME + " TEXT,"
            + ATTACHMENT_LAST_NAME + " TEXT,"
            + ATTACHMENT_FILE_PATH + " TEXT,"
            + ATTACHMENT_FILE_DESCRIPTION + " TEXT,"
            + ATTACHMENT_CREATED_AT + " TEXT"
            +")";


    private static final String CREATE_PRENEWS_TABLE="CREATE TABLE "+PreNewsDataModel_Table_Name+"("
            + PRENEWS_USER_ID + " TEXT,"
            + PRENEWS_REFERRAL_ID + " TEXT,"
            + PRENEWS_NEWS_TITLE + " TEXT,"
            + PRENEWS_NEWS_MAINPIC_FILE + " TEXT,"
            + PRENEWS_TOP_TITLE + " TEXT,"
            + PRENEWS_SUB_TITLE + " TEXT,"
            + PRENEWS_NEWS_SUMMARY + " TEXT,"
            + PRENEWS_NEWS_CATEGORY_ID + " TEXT,"
            + PRENEWS_NEWS_TYPE_ID + " TEXT,"
            + PRENEWS_PROVINCE_ID + " TEXT,"
            + PRENEWS_CITY_ID + " TEXT,"
            + PRENEWS_MAINCONTENT + " TEXT,"
            + PRENEWS_REPORTER_ID + " TEXT"
            +")";
    private static final String CREATE_GETPRENEWS_TABLE="CREATE TABLE "+GetPreNewsDataModel_Table_Name+"("
            + GETPRENEWS_REFERRAL_FOLDER_ID + " TEXT,"
            + GETPRENEWS_STARTINTERVIEW + " TEXT,"
            + GETPRENEWS_ENDINTERVIEW + " TEXT,"
            + GETPRENEWS_TOPTITLE + " TEXT,"
            + GETPRENEWS_TITLE + " TEXT,"
            + GETPRENEWS_BOTTOMTITLE + " TEXT,"
            + GETPRENEWS_CONTENTSUMMARY + " TEXT,"
            + GETPRENEWS_MAINCONTENT + " TEXT,"
            + GETPRENEWS_NEWS_ID + " TEXT,"
            + GETPRENEWS_IMAGE + " TEXT,"
            + GETPRENEWS_NEWS_CATEGORY_ID + " TEXT,"
            + GETPRENEWS_SEEN + " TEXT,"
            + GETPRENEWS_PRE_NEWS_STATE_ID + " TEXT,"
            + GETPRENEWS_NEWS_TYPE_ID + " TEXT,"
            + GETPRENEWS_PROVINCE_ID + " TEXT,"
            + GETPRENEWS_CITY_ID + " TEXT,"
            + GETPRENEWS_REPORTER_ID + " TEXT,"
            + GETPRENEWS_CREATED_AT + " TEXT,"
            + GETPRENEWS_UPDATED_AT + " TEXT,"
            + GETPRENEWS_NEWSTYPETITLE + " TEXT,"
            + GETPRENEWS_PRENEWSSTATETITLE + " TEXT,"
            + GETPRENEWS_PROVINCETITLE + " TEXT,"
            + GETPRENEWS_CITYTITLE + " TEXT,"
            + GETPRENEWS_FIRST_NAME + " TEXT,"
            + GETPRENEWS_LAST_NAME + " TEXT"

            +")";

    private static final String CREATE_MARGIN_TABLE="CREATE TABLE "+MarginsDataModel_Table_Name+"("
            + MARGIN_CREATED_AT + " TEXT,"
            + MARGIN_REMEMBER_DATE + " TEXT,"
            + MARGIN_TITLE + " TEXT,"
            + MARGIN_FIRST_NAME + " TEXT,"
            + MARGIN_LAST_NAME + " TEXT,"
            + MARGIN_RID + " TEXT,"
            + MARGIN_UID + " TEXT,"
            + MARGIN_REFERRAL_FOLDER_MARGIN_DESCRIPTION + " TEXT,"
            + MARGIN_REFERRAL_FOLDER_MARGIN_ID + " TEXT,"
            + MARGIN_NEWS_ID + " TEXT,"
            + MARGIN_SUBJECT + " TEXT"
            +")";


    private static final String CREATE_ORGAN_TABLE="CREATE TABLE "+OrgansDataModel_Table_Name+"("
            + ORGAN_ID + " TEXT,"
            + ORGAN_TITLE + " TEXT,"
            + ORGAN_ADDRESS + " TEXT,"
            + ORGAN_PHONE + " TEXT,"
            + ORGAN_FAX + " TEXT"
            +")";
    private static final String CREATE_PEOPLE_TABLE="CREATE TABLE "+PeopleDataModel_Table_Name+"("
            + PEOPLE_ID + " TEXT,"
            + PEOPLE_FIRST_NAME + " TEXT,"
            +  PEOPLE_LAST_NAME+ " TEXT,"
            + PEOPLE_PEOPLE + " TEXT,"
            + PEOPLE_MOBILE + " TEXT,"
            + PEOPLE_ADDRESS + " TEXT,"
            + PEOPLE_IMAGES + " TEXT"
            +")";


    private static final String CREATE_NEWSCATEGORY_TABLE="CREATE TABLE "+NewsCategoriesDataModel_Table_Name+"("
            +NEWSCATEGORY_ID + " TEXT,"
            +NEWSCATEGORY_Title + " TEXT"
            +")";

    private static final String CREATE_PROVINCE_TABLE="CREATE TABLE "+ProvincesDataModel_Table_Name + "("
            + PROVINCE_ID + " TEXT,"
            +PROVINCE_TITLE+" TEXT,"
            +PROVINCE_TELEGRAM_CHANNEL_NAME+" TEXT,"
            +PROVINCE_DISABLED+" TEXT"
            +")";

    private static final String CREATE_USER_TABLE="CREATE TABLE "+UserDataModel_Table_Name + "("
            + USER_ID + " TEXT,"
            +USER_EMAIL+" TEXT,"
            +USER_FIRST_NAME+" TEXT,"
            +USER_LAST_NAME+" TEXT,"
            +USER_IMAGE+" TEXT,"
            +USER_IS_LOGEDIN+" TEXT"
            +")";

    private static final String CREATE_USERREFERRAL_TABLE="CREATE TABLE "+UserRefferalDataModel_Table_Name + "("
            + USERREFERAL_ID + " TEXT,"
            + USERREFERAL_SUBJECT + " TEXT,"
            + USERREFERAL_DESCRIPTION + " TEXT,"
            + USERREFERAL_FROM_USER_ID + " TEXT,"
            + USERREFERAL_TO_USER_ID + " TEXT,"
            + USERREFERAL_REFERRAL_FOLDER_STATE_ID + " TEXT,"
            + USERREFERAL_URGENCY_ID + " TEXT,"
            + USERREFERAL_SEEN + " TEXT,"
            + USERREFERAL_NEWS_TYPE_ID + " TEXT,"
            + USERREFERAL_ACTION_DATE + " TEXT,"
            + USERREFERAL_CREATE_AT + " TEXT,"
            + USERREFERAL_UPDATED_AT + " TEXT,"
            + USERREFERAL_USERREFERRAL_STATE_TITLE + " TEXT,"
            + USERREFERAL_URGENCY_TITLE + " TEXT,"
            + USERREFERAL_COLOR + " TEXT,"
            + USERREFERAL_STRAT_INTERVIEW + " TEXT,"
            + USERREFERAL_END_INTERVIEW + " TEXT,"
            + USERREFERAL_REFERRAL_FOLDER_RETURN_DESCRIPTION + " TEXT,"
            + USERREFERAL_REFERRAL_FOLDER_RETURN_REASON_ID + " TEXT,"
            + USERREFERAL_PEOPLE_ID + " TEXT,"
            + USERREFERAL_ORGAN_ID + " TEXT"
            +")";





    private SQLiteDatabase sqlDB;
    private Context mCtx = null;

    public MYSQlDBHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        db.disableWriteAheadLogging();
    }


    /////////////////////////////////////////////////////
    /////////////User Referral Table Handler/////////////
    /////////////////////////////////////////////////////
    public void InsertUserRefferal(ContentValues cv){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(UserRefferalDataModel_Table_Name, null, cv);
        db.close();
    }

    public UserReferralDataModel GetURRecordByID( String IDValue){
        UserReferralDataModel TempList=null;// = new ArrayList<UserReferralDataModel>();
        SQLiteDatabase db = this.getWritableDatabase();
        PersianDateFormat persianDateFormat=new PersianDateFormat("yyyy-MM-dd HH:mm:ss");
        PersianDate pdate;String ActionDate=null, CreateAT=null, UpdateAt =null;
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM UserReferral WHERE Id = "+IDValue, null);
            if (c == null)
                return null;
            c.moveToFirst();
            do{
                try {
                    pdate=persianDateFormat.parseGrg(c.getString(9),"yyyy-MM-dd HH:mm:ss");
                    ActionDate =(pdate.toString());
                    pdate=persianDateFormat.parseGrg(c.getString(10),"yyyy-MM-dd HH:mm:ss");
                    CreateAT =(pdate.toString());
                    pdate=persianDateFormat.parseGrg(c.getString(11),"yyyy-MM-dd HH:mm:ss");
                    UpdateAt =(pdate.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                UserReferralDataModel pr =  new UserReferralDataModel(
                        ((c.getString(0))),
                        (c.getString(1)),
                        (c.getString(2)),
                        (c.getString(3)),
                        ((c.getString(4))),
                        (c.getString(5)),
                        (c.getString(6)),
                        (c.getString(7)),
                        (c.getString(8)),
                        (ActionDate),
                        (CreateAT),
                        (UpdateAt),
                        (c.getString(12)),
                        (c.getString(13)),
                        (c.getString(14)));
                Log.i("SQL Says:",((c.getString(0)))+(c.getString(1))+(c.getString(2))+(c.getString(3))
                        +((c.getString(4)))+(c.getString(5))+(c.getString(6))+(c.getString(7))+(c.getString(8))
                        +(c.getString(9))+(c.getString(10))+(c.getString(11))+(c.getString(12))+(c.getString(13))+(c.getString(14)));
                TempList = pr;
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){Log.i("GetRecordByID Says:", e.toString());}
        db.close();
        return TempList;
    }

    public ArrayList<UserReferralDataModel> GetAllRecords(){
        ArrayList<UserReferralDataModel> TempList = new ArrayList<UserReferralDataModel>();
        SQLiteDatabase db = this.getWritableDatabase();
        PersianDateFormat persianDateFormat=new PersianDateFormat("yyyy-MM-dd HH:mm:ss");
        PersianDate pdate;String ActionDate=null, CreateAT=null, UpdateAt =null;
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM UserReferral", null);
            if (c == null)
                return null;
            c.moveToFirst();
            do{
                try {
                    pdate=persianDateFormat.parseGrg(c.getString(9),"yyyy-MM-dd HH:mm:ss");
                    ActionDate =(pdate.toString());
                    pdate=persianDateFormat.parseGrg(c.getString(10),"yyyy-MM-dd HH:mm:ss");
                    CreateAT =(pdate.toString());
                    pdate=persianDateFormat.parseGrg(c.getString(11),"yyyy-MM-dd HH:mm:ss");
                    UpdateAt =(pdate.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                UserReferralDataModel pr =  new UserReferralDataModel(
                        ((c.getString(0))),
                        (c.getString(1)),
                        (c.getString(2)),
                        (c.getString(3)),
                        ((c.getString(4))),
                        (c.getString(5)),
                        (c.getString(6)),
                        (c.getString(7)),
                        (c.getString(8)),
                        (ActionDate),
                        (CreateAT),
                        (UpdateAt),
                        (c.getString(12)),
                        (c.getString(13)),
                        (c.getString(14)));
                Log.i("SQL Says:",((c.getString(0)))+(c.getString(1))+(c.getString(2))+(c.getString(3))
                        +((c.getString(4)))+(c.getString(5))+(c.getString(6))+(c.getString(7))+(c.getString(8))
                        +(c.getString(9))+(c.getString(10))+(c.getString(11))+(c.getString(12))+(c.getString(13))+(c.getString(14)));
                TempList.add(pr);
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){Log.i("GetAllRecords Says:", e.toString());}
        db.close();
        return TempList;
    }

//    public void UpdateUserRefferal(ContentValues cv, String ConditionStr){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.update(CitiesDataModel_Table_Name, cv, ConditionStr, null );
//        db.close();
//    }




    /////////////////////////////////////////////////////
    /////////////People Table Handler////////////////////
    /////////////////////////////////////////////////////
    public void InsertPeople(ContentValues cv){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(PeopleDataModel_Table_Name, null, cv);
        db.close();
    }

    public PeopleDataModelClass GetPeopleByID(String IDValue){
        PeopleDataModelClass TempList=null;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM People WHERE Id = "+IDValue, null);
            if (c == null)
                return null;
            c.moveToFirst();
            do{

                PeopleDataModelClass pr =  new PeopleDataModelClass(
                        ((c.getString(0))),
                        (c.getString(1)),
                        (c.getString(2)),
                        (c.getString(3)),
                        ((c.getString(4))),
                        (c.getString(5)),
                        (c.getString(6)));

                TempList = pr;
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){Log.i("GetPeopleByID Says:", e.toString());}
        db.close();
        return TempList;
    }




    /////////////////////////////////////////////////////
    /////////////Organs Table Handler////////////////////
    /////////////////////////////////////////////////////
    public void InsertOrgans(ContentValues cv){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(OrgansDataModel_Table_Name, null, cv);
        db.close();
    }

    public OrgansDataModelClass GetOrgansByID(String IDValue){
        OrgansDataModelClass TempList=null;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM Organs WHERE Id = "+IDValue, null);
            if (c == null)
                return null;
            c.moveToFirst();
            do{
                OrgansDataModelClass pr =  new OrgansDataModelClass(
                        ((c.getString(0))),
                        (c.getString(1)),
                        (c.getString(2)),
                        (c.getString(3)),
                        ((c.getString(4))));

                TempList = pr;
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){Log.i("Get Organ ByID Says:", e.toString());}
        db.close();
        return TempList;
    }



    /////////////////////////////////////////////////////
    /////////////Margins Table Handler///////////////////
    /////////////////////////////////////////////////////
    public void DeleteMargins(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Margins",null);
        db.close();
    }

    public void InsertMargins(ContentValues cv){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(MarginsDataModel_Table_Name, null, cv);
        db.close();
    }

    public ArrayList<MarginDataModelClass> GetMarginByID(String RefferalID){
        ArrayList<MarginDataModelClass> TempList=new ArrayList<MarginDataModelClass>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM Margins WHERE RID = "+RefferalID, null);
            if (c == null)
                return null;
            c.moveToFirst();
            do{
                MarginDataModelClass pr =  new MarginDataModelClass(
                        (c.getString(0)),
                        (c.getString(1)),
                        (c.getString(2)),
                        (c.getString(3)),
                        (c.getString(4)),
                        (c.getString(5)),
                        (c.getString(6)),
                        (c.getString(7)),
                        (c.getString(8)),
                        (c.getString(9)));

                TempList.add(pr);
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){Log.i("Get Margins Says:", e.toString());}
        db.close();
        return TempList;
    }




    /////////////////////////////////////////////////////
    /////////////PreNews Table Handler///////////////////
    /////////////////////////////////////////////////////
    public void InsertPreNews(ContentValues cv){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(PreNewsDataModel_Table_Name, null, cv);
        db.close();
    }

    public PreNewsUpdateDataModelClass GetPreNewsByID(String Referral_Id){
        PreNewsUpdateDataModelClass TempList=null;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM PreNews WHERE Referral_Id = "+Referral_Id, null);
            if (c == null)
                return null;
            c.moveToFirst();
            do{
                PreNewsUpdateDataModelClass pr =  new PreNewsUpdateDataModelClass(
                        (c.getString(0)),
                        (c.getString(1)),
                        (c.getString(2)),
                        (c.getString(3)),
                        (c.getString(4)),
                        (c.getString(5)),
                        (c.getString(6)),
                        (c.getString(7)),
                        (c.getString(8)),
                        (c.getString(9)),
                        (c.getString(10)),
                        (c.getString(11)),
                        (c.getString(12))
                );

                TempList = pr;
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){Log.i("Get PreNew Says:", e.toString());}
        db.close();
        return TempList;
    }



    /////////////////////////////////////////////////////
    /////////////Provinces Table Handler/////////////////
    /////////////////////////////////////////////////////
    public void InsertProvinces(ContentValues cv){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ProvincesDataModel_Table_Name, null, cv);
        db.close();
    }

    public void DeleteProvinces(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Provinces");
        db.close();
    }

    public ProvincesDataModelClass GetProvincesByID(String Id){
        ProvincesDataModelClass TempList=null;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM Provinces WHERE Id = "+Id, null);
            if (c == null)
                return null;
            c.moveToFirst();
            do{
                ProvincesDataModelClass pr =  new ProvincesDataModelClass(
                        (c.getString(0)),
                        (c.getString(1)),
                        (c.getString(2)),
                        (c.getString(3)));

                TempList = pr;
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){Log.i("Get Provinces Says:", e.toString());}
        db.close();
        return TempList;
    }

    public String[][] GetProvincesList(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[][] Str=null;
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM Provinces ", null);
            if (c == null)
                return null;
            Str = new String[c.getCount()][2];
            int counter = 0;
            c.moveToFirst();
            do{
                ProvincesDataModelClass pr =  new ProvincesDataModelClass(
                        (c.getString(0)),
                        (c.getString(1)),
                        (c.getString(2)),
                        (c.getString(3)));

                Str[counter][0]=c.getString(0);
                Str[counter][1]=c.getString(1);
                counter++;
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){Log.i("Get Provinces Says:", e.toString());}
        db.close();
        return Str;
    }





    /////////////////////////////////////////////////////
    /////////////NewsReporters Table Handler/////////////////
    /////////////////////////////////////////////////////
    public void InsertNewsReporters(ContentValues cv){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(NewsReportersDataModel_Table_Name, null, cv);
        db.close();
    }

    public void DeleteNewsReporters(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from NewsReporters");
        db.close();
    }

    public String[][] GetNewsReportersList(){
        SQLiteDatabase db = this.getWritableDatabase();

        String[][]str = null;
        Cursor c;
        try {
            //c = db.rawQuery("SELECT * FROM NewsReporters WHERE id = " + NewsReporters_ID, null);
            c = db.rawQuery("SELECT * FROM NewsReporters " , null);
            if (c == null)
                return null;

            str = new String[c.getCount()][2];
            int counter = 0;
            c.moveToFirst();
            do{
                Log.i("hhh","Get NewsReporters Log:"+ c.toString());
                NewsReportersDataModelClass pr =  new NewsReportersDataModelClass(
                        (c.getString(0)),
                        (c.getString(1)),
                        (c.getString(2)),
                        (c.getString(3)),
                        (c.getString(4)),
                        (c.getString(5)),
                        (c.getString(6)));

                //TempList.add(c.getString(1) + " " + c.getString(2));
                str[counter][0] = c.getString(1) + " " + c.getString(2);
                str[counter][1] = c.getString(0);
                counter++;

            }while (c.moveToNext());
            c.close();
        }catch (Exception e){Log.i("Get NewsReporters Cach:", e.toString());}
        db.close();
        //return TempList;
        return str;
    }



    /////////////////////////////////////////////////////
    /////////////Cities Table Handler/////////////////
    /////////////////////////////////////////////////////
    public void InsertCities(ContentValues cv){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(CitiesDataModel_Table_Name, null, cv);
        db.close();
    }

    public void DeleteCities(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Cities");
        db.close();
    }

    public String[][] GetCitiesList(String Province_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        String[][]str = null;
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM Cities WHERE Province_ID = " + Province_ID, null);

            if (c == null)
                return null;
            str = new String[c.getCount()][2];
            int counter = 0;
            c.moveToFirst();
            do{
                Log.i("Get Cities Says:", c.toString());
                CitiesDataModelClass pr =  new CitiesDataModelClass(
                        (c.getString(0)),
                        (c.getString(1)),
                        (c.getString(2)));
                str[counter][0] = c.getString(0);
                str[counter][1] = c.getString(1);
                counter++;
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){Log.i("Get Cities Says:", e.toString());}
        db.close();
        return str;
    }

    public List<String> GetCitiesIDList(String Province_ID){
        List<String> TempList=new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM Cities WHERE Province_ID = " + Province_ID, null);
            if (c == null)
                return null;
            c.moveToFirst();
            do{
                Log.i("Get Cities Says:", c.toString());
                CitiesDataModelClass pr =  new CitiesDataModelClass(
                        (c.getString(0)),
                        (c.getString(1)),
                        (c.getString(2)));
                TempList.add(c.getString(0));
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){Log.i("Get Cities Says:", e.toString());}
        db.close();
        return TempList;
    }



    /////////////////////////////////////////////////////
    /////////////NewsCategories Table Handler////////////
    /////////////////////////////////////////////////////
    public void InsertNewsCategories(ContentValues cv){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(NewsCategoriesDataModel_Table_Name, null, cv);
        db.close();
    }

    public void DeleteNewsCategories(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from NewsCategories");
        db.close();
    }

    public NewsCategoriesDataModel GetNewsCategorieById(String Id){
        NewsCategoriesDataModel TempList=null;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM NewsCategories WHERE Id="+Id, null);
            if (c == null)
                return null;
            c.moveToFirst();
            do{
                NewsCategoriesDataModel pr =  new NewsCategoriesDataModel(
                        (c.getString(0)),
                        (c.getString(1)));

                TempList=pr;
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){Log.i("Get NewsCategors Says:", e.toString());}

        db.close();
        return TempList;
    }

    public String[][] GetALLNewsCategoriesList(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[][]str = null;

        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM NewsCategories", null);
            if (c == null)
                return null;
            str = new String[c.getCount()][2];
            int counter = 0;
            c.moveToFirst();
            do{
                NewsCategoriesDataModel pr =  new NewsCategoriesDataModel(
                        (c.getString(0)),
                        (c.getString(1).toString()));
                Log.i("Get NewsCategors hhh:",c.getString(1));
                str[counter][0]=c.getString(0);
                str[counter][1]=c.getString(1);
                counter++;
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){Log.i("Get NewsCategors Says:", e.toString());}

        db.close();

        return str;
    }

    public Cursor GetALLNewsCategoriesCursor(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c=null;
        try {
            c = db.rawQuery("SELECT Title FROM NewsCategories", null);
            if (c == null)
                return null;

            c.close();
        }catch (Exception e){Log.i("Get NewsCategors Says:", e.toString());}

        db.close();

        return c;
    }



    /////////////////////////////////////////////////////
    /////////////AttachmentFile Table Handler////////////
    /////////////////////////////////////////////////////
    public void InsertAttachmentFile(ContentValues cv){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(AttachmentFileDataModel_Table_Name, null, cv);
        db.close();
    }

    public NewsCategoriesDataModel GetNewsCategoriesByTitle(String Title){
        NewsCategoriesDataModel TempList=null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM NewsCategories WHERE Title='"+Title+"'", null);
            Log.d("hhh", "GetCitiesIDList: "+DatabaseUtils.dumpCursorToString(c));
            if (c == null)
                return null;
            c.moveToFirst();
            do{
                NewsCategoriesDataModel pr =  new NewsCategoriesDataModel(
                        (c.getString(0)),
                        (c.getString(1)));

                TempList = pr;
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){Log.i("Get NewsCategors Says:", e.toString());}

        db.close();
        return TempList;
    }

    /////////////////////////////////////////////////////
    /////////////Margins Table Handler///////////////////
    /////////////////////////////////////////////////////
    public void InsertUser(ContentValues cv){
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("delete from User");
        //db.update(UserDataModel_Table_Name, " ", null, null );
        //db.close();
        //db = this.getWritableDatabase();
        Long lng = db.insert(UserDataModel_Table_Name, null, cv);
        db.update(UserDataModel_Table_Name, cv, null, null );


        Log.d("hhhK", "insert user: " + lng);

        db.close();
    }

    public UserDataModelClass GetCurrentUser(){
        UserDataModelClass TempList=null;
        this.close();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM User ", null);
            if (c == null)
                return null;
            c.moveToFirst();
            do{
                UserDataModelClass pr =  new UserDataModelClass(
                        (c.getString(0)),
                        (c.getString(1)),
                        (c.getString(2)),
                        (c.getString(3)),
                        (c.getString(4)),
                        (c.getString(5)));
                Log.d("hhh Get User Says:", c.toString());
                TempList = pr;
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){Log.d("hhh Get User cacht:", e.toString());}
        db.close();
        return TempList;
    }

    public String GetCurrentUserId(){
        UserDataModelClass TempList=null;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM User ", null);
            if (c == null)
                return null;
            c.moveToFirst();
            do{
                UserDataModelClass pr =  new UserDataModelClass(
                        (c.getString(0)),
                        (c.getString(1)),
                        (c.getString(2)),
                        (c.getString(3)),
                        (c.getString(4)),
                        (c.getString(5)));

                TempList = pr;
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){Log.i("Get User Says:", e.toString());}
        db.close();
        return TempList.getId();
    }

    public void DeleteUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from User");
        //db.update(UserDataModel_Table_Name,  null, null );

        db.close();
    }



    //////////////////////////////////////////////////////////////////
    @Override
    public synchronized void close() {
        if(sqlDB != null)
            sqlDB.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_USERREFERRAL_TABLE);
        db.execSQL(CREATE_PROVINCE_TABLE);
        db.execSQL(CREATE_NEWSCATEGORY_TABLE);
        db.execSQL(CREATE_PEOPLE_TABLE);
        db.execSQL(CREATE_ORGAN_TABLE);
        db.execSQL(CREATE_MARGIN_TABLE);
        db.execSQL(CREATE_GETPRENEWS_TABLE);
        db.execSQL(CREATE_PRENEWS_TABLE);
        db.execSQL(CREATE_ATTACHMENT_TABLE);
        db.execSQL(CREATE_CITY_TABLE);
        db.execSQL(CREATE_NEWSREPORTER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+UserDataModel_Table_Name);
        db.execSQL("DROP TABLE IF EXISTS "+UserRefferalDataModel_Table_Name);
        db.execSQL("DROP TABLE IF EXISTS "+ProvincesDataModel_Table_Name);
        db.execSQL("DROP TABLE IF EXISTS "+NewsCategoriesDataModel_Table_Name);
        db.execSQL("DROP TABLE IF EXISTS "+PeopleDataModel_Table_Name);
        db.execSQL("DROP TABLE IF EXISTS "+OrgansDataModel_Table_Name);
        db.execSQL("DROP TABLE IF EXISTS "+MarginsDataModel_Table_Name);
        db.execSQL("DROP TABLE IF EXISTS "+GetPreNewsDataModel_Table_Name);
        db.execSQL("DROP TABLE IF EXISTS "+PreNewsDataModel_Table_Name);
        db.execSQL("DROP TABLE IF EXISTS " +AttachmentFileDataModel_Table_Name);
        db.execSQL("DROP TABLE IF EXISTS "+CitiesDataModel_Table_Name);
        db.execSQL("DROP TABLE IF EXISTS "+NewsReportersDataModel_Table_Name);
    }
}

