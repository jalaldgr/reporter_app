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
    private static String DB_NAME = "sirokhcms.db";
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

    private SQLiteDatabase sqlDB;
    private Context mCtx = null;

    public MYSQlDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        if(Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        mCtx = context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        db.disableWriteAheadLogging();
    }

    private boolean CheckDataBase(){
        SQLiteDatabase tempsqlDB = null;
        try {
            String path = DB_PATH + DB_NAME;
            tempsqlDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        }catch (Exception e){Log.i("CheckDataBase Says:", e.toString());}
        if (tempsqlDB != null)
            tempsqlDB.close();
        return tempsqlDB!=null?true:false;
    }

    public void CopyDataBase(){
        try {
            InputStream myinput = mCtx.getAssets().open(DB_NAME);
            //path to create database
            String OutFileName = DB_PATH + DB_NAME;
            OutputStream myoutput = new FileOutputStream(OutFileName);
            byte[] buffer=new byte[1024];
            int lenght;
            while((lenght = myinput.read(buffer)) > 0) {
                myoutput.write(buffer, 0, lenght);
            }
            myoutput.flush();
            myoutput.close();
            myinput.close();
        }catch (IOException e){
            e.printStackTrace();
            Log.i("Copy DB Says:", e.toString());
        }


    }

    public void OpenDataBase(){
        String path = DB_PATH+DB_NAME;
        sqlDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void CreateDataBase(){
        boolean isDBExist = CheckDataBase();
        if (isDBExist){

        }else {
            this.getReadableDatabase();
            this.close();
            try {
                CopyDataBase();
            }catch (Exception e){
                Log.i("CreateDataBase Says:", e.toString());
            }
        }
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
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

