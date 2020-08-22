package com.ageny.yadegar.sirokhcms.DataModelClass;

public class CitiesDataModelClass {
    String ID;
    String Title;
    String province_id;

    public CitiesDataModelClass(String ID, String title, String province_id) {
        this.ID = ID;
        Title = title;
        this.province_id = province_id;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }
}
