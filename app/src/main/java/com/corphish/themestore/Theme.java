package com.corphish.themestore;

/**
 * Created by Avinaba on 10/12/2016.
 */

/*
* Theme API
* */
public class Theme {

    // Name - Name of theme
    String name;

    // Package name - Package name of the theme apk (Example - com.foo.myawesometheme)
    String packageName;

    // Description - A brief description about the theme
    String description;

    // DownloadURL - Direct download link for this theme
    String downloadUrl;

    // ThumbnailURL - Direct image link of Theme thumbnail
    String thumbnailUrl;

    // BannerURL - Direct image link of Theme banner
    String bannerUrl;

    // ImageURLs - Array of direct image links for theme screenshots
    String[] imageURls;


    /*
    * set Functions: Use this functions to set theme details while initializing
    */
    public void setName(String name) {
        this.name = name;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public void setImageURls(String[] imageURls) {
        this.imageURls = imageURls;
    }


    /*
    * get Functions: Use this to get Theme Informtion
    */
    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getDescription() {
        return description;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public String[] getImageURls() {
        return imageURls;
    }
}
