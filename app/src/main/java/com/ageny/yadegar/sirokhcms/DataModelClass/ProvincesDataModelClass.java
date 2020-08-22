package com.ageny.yadegar.sirokhcms.DataModelClass;

public class ProvincesDataModelClass {

    String Id;
    String Title;
    String TelegramChannelName;
    String Disabled;

    public ProvincesDataModelClass(String id, String title, String telegramChannelName, String disabled) {
        Id = id;
        Title = title;
        TelegramChannelName = telegramChannelName;
        Disabled = disabled;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTelegramChannelName() {
        return TelegramChannelName;
    }

    public void setTelegramChannelName(String telegramChannelName) {
        TelegramChannelName = telegramChannelName;
    }

    public String getDisabled() {
        return Disabled;
    }

    public void setDisabled(String disabled) {
        Disabled = disabled;
    }
}
