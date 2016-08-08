package com.udacity.newsapp;

/**
 * Created by nloc on 8/8/2016.
 */
public class News {
//http://content.guardianapis.com/search?q=OLYMPICS&api-key=test&show-fields=trailText,thumbnail
    private String title;
    private String sectionName;
    private String webUrl;
    private String publicationDate;

    public News(String title, String sectionName, String webUrl, String publicationDate) {
        this.title = title;
        this.sectionName = sectionName;
        this.webUrl = webUrl;
        this.publicationDate = publicationDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }


    public String getTitle() {
        return title;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

}
