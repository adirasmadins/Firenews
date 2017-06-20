package io.github.h911002.firenews.module.index.model;

/**
 * Created by wangyong on 17-6-12.
 */
public class News extends BaseModel{
    private String mTitle;
    private String mDescription;
    private String mUrl;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
