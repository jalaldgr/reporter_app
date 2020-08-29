package com.ageny.yadegar.sirokhcms.DataModelClass;

import android.util.Log;

import java.text.ParseException;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class GetPreNewsDataModelClass {

    String referral_folder_id;
    String StartInterview;
    String EndInterview;
    String TopTitle;
    String Title;
    String BottomTitle;
    String ContentSummary;
    String MainContent;
    String news_id;
    String Image;
    String news_category_id;
    String Seen;
    String pre_news_state_id;
    String news_type_id;
    String province_id;
    String city_id;
    String reporter_id;
    String created_at;
    String updated_at;
    String NewsTypeTitle;
    String PreNewsStateTitle;
    String ProvinceTitle;
    String CityTitle;
    String first_name;
    String last_name;



    public GetPreNewsDataModelClass(String referral_folder_id, String startInterview, String endInterview,  String topTitle, String title, String bottomTitle, String contentSummary, String mainContent, String news_id, String image, String news_category_id, String seen, String pre_news_state_id, String news_type_id,String province_id, String city_id, String reporter_id, String created_at, String updated_at, String newsTypeTitle, String preNewsStateTitle, String provinceTitle, String cityTitle, String first_name, String last_name) {
        referral_folder_id = (referral_folder_id);
        StartInterview = (!startInterview.isEmpty())? startInterview : "ثبت نشده";
        EndInterview = (!endInterview.isEmpty() )? endInterview : "ثبت نشده";
        TopTitle = (!(topTitle.equals(null)||topTitle=="null"))?topTitle:" ";
        Log.d("hhh", "GetPreNewsDataModelClass: "+ TopTitle);
        Title = (!title.equals(null)?title:"ثبت نشده");
        BottomTitle = (!(bottomTitle.equals(null)||bottomTitle=="null"))?bottomTitle:" ";
        ContentSummary = contentSummary;
        MainContent = (!(mainContent.equals(null)||mainContent=="null"))?mainContent:" ";
        news_id = (!news_id.equals(null))?news_id:"0";
        Image = (!image.equals(null))?image:"/assets/no-image.png";
        this.news_category_id = (!news_category_id.equals(null))?news_category_id:"0";
        Seen = (!seen.equals(null))?seen:"0";
        this.pre_news_state_id = (!pre_news_state_id.equals(null))?pre_news_state_id:"0";
        this.news_type_id = (!news_type_id.equals(null))?news_type_id:"0";
        this.province_id = (!province_id.equals(null))?province_id:"0";
        this.city_id = (!(city_id.equals(null)))?city_id:"0";
        this.reporter_id = reporter_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        NewsTypeTitle = (!newsTypeTitle.isEmpty())?newsTypeTitle:"ثبت نشده";
        PreNewsStateTitle = (!preNewsStateTitle.isEmpty())?preNewsStateTitle:"ثبت نشده";
        ProvinceTitle = (!provinceTitle.isEmpty()) ? provinceTitle : "ثبت نشده";
        CityTitle = (!(cityTitle.equals(null)||cityTitle=="null")) ? cityTitle : "ثبت نشده";
        this.first_name = (!first_name.equals(null)) ? first_name :"ثبت نشده";
        this.last_name = ( !(last_name.equals(null)||last_name=="null")) ? last_name : "ثبت نشده";
    }

    public String getReferral_folder_id() {
        return referral_folder_id;
    }

    public void setReferral_folder_id(String referral_folder_id) {
        this.referral_folder_id = referral_folder_id;
    }

    public String getStartInterview() throws ParseException {
        PersianDateFormat persianDateFormat=new PersianDateFormat("yyyy-MM-dd HH:mm:ss");
        PersianDate pdatestart;
        if(!StartInterview.equals(null)) {
            pdatestart = persianDateFormat.parseGrg(StartInterview, "yyyy-MM-dd HH:mm:ss");
            StartInterview = pdatestart.toString() ;
        }
        return StartInterview;
    }

    public void setStartInterview(String startInterview) {

        StartInterview = startInterview;
    }

    public String getEndInterview() throws ParseException {
        PersianDateFormat persianDateFormat=new PersianDateFormat("yyyy-MM-dd HH:mm:ss");
        PersianDate pdateend;
        if(!EndInterview.equals(null)) {
            pdateend = persianDateFormat.parseGrg(EndInterview, "yyyy-MM-dd HH:mm:ss");
            EndInterview = pdateend.toString();
        }
        return EndInterview;
    }

    public void setEndInterview(String endInterview) {
        EndInterview = endInterview;
    }

    public String getTopTitle() {
        return TopTitle;
    }

    public void setTopTitle(String topTitle) {
        TopTitle = topTitle;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBottomTitle() {
        return BottomTitle;
    }

    public void setBottomTitle(String bottomTitle) {
        BottomTitle = bottomTitle;
    }

    public String getContentSummary() {
        return ContentSummary;
    }

    public void setContentSummary(String contentSummary) {
        ContentSummary = contentSummary;
    }

    public String getMainContent() {
        return MainContent;
    }

    public void setMainContent(String mainContent) {
        MainContent = mainContent;
    }

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getNews_category_id() {
        return news_category_id;
    }

    public void setNews_category_id(String news_category_id) {
        this.news_category_id = news_category_id;
    }

    public String getSeen() {
        return Seen;
    }

    public void setSeen(String seen) {
        Seen = seen;
    }

    public String getPre_news_state_id() {
        return pre_news_state_id;
    }

    public void setPre_news_state_id(String pre_news_state_id) {
        this.pre_news_state_id = pre_news_state_id;
    }

    public String getNews_type_id() {
        return news_type_id;
    }

    public void setNews_type_id(String news_type_id) {
        this.news_type_id = news_type_id;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getReporter_id() {
        return reporter_id;
    }

    public void setReporter_id(String reporter_id) {
        this.reporter_id = reporter_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getNewsTypeTitle() {
        return NewsTypeTitle;
    }

    public void setNewsTypeTitle(String newsTypeTitle) {
        NewsTypeTitle = newsTypeTitle;
    }

    public String getPreNewsStateTitle() {
        return PreNewsStateTitle;
    }

    public void setPreNewsStateTitle(String preNewsStateTitle) {
        PreNewsStateTitle = preNewsStateTitle;
    }

    public String getProvinceTitle() {
        return ProvinceTitle;
    }

    public void setProvinceTitle(String provinceTitle) {
        ProvinceTitle = provinceTitle;
    }

    public String getCityTitle() {
        return CityTitle;
    }

    public void setCityTitle(String cityTitle) {
        CityTitle = cityTitle;
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

    @Override
    public String toString() {
        return "GetPreNewsDataModelClass{" +
                "referral_folder_id='" + referral_folder_id + '\'' +
                ", StartInterview='" + StartInterview + '\'' +
                ", EndInterview='" + EndInterview + '\'' +
                ", TopTitle='" + TopTitle + '\'' +
                ", Title='" + Title + '\'' +
                ", BottomTitle='" + BottomTitle + '\'' +
                ", ContentSummary='" + ContentSummary + '\'' +
                ", MainContent='" + MainContent + '\'' +
                ", news_id='" + news_id + '\'' +
                ", Image='" + Image + '\'' +
                ", news_category_id='" + news_category_id + '\'' +
                ", Seen='" + Seen + '\'' +
                ", pre_news_state_id='" + pre_news_state_id + '\'' +
                ", news_type_id='" + news_type_id + '\'' +
                ", province_id='" + province_id + '\'' +
                ", city_id='" + city_id + '\'' +
                ", reporter_id='" + reporter_id + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", NewsTypeTitle='" + NewsTypeTitle + '\'' +
                ", PreNewsStateTitle='" + PreNewsStateTitle + '\'' +
                ", ProvinceTitle='" + ProvinceTitle + '\'' +
                ", CityTitle='" + CityTitle + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }
}
