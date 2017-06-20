package io.github.h911002.firenews.module.me;

/**
 * Created by athrun on 17/6/3.
 */

public class MenuItem {
    public String title;
    public int icon;
    public String url;
    public boolean isExpanded;
    public int type;//1 menu 2 task

    public MenuItem(String title, int icon, String url, int type) {
        this.title = title;
        this.icon = icon;
        this.url = url;
        this.type = type;
    }
}
