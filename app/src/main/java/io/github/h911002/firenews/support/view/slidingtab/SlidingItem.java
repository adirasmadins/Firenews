package io.github.h911002.firenews.support.view.slidingtab;

/**
 * @author liao_h
 * @version 1.0
 * @description
 * @E_mail liao_h@ctrip.com
 * @data 2017/4/19 15:11
 * @copy 携程信息技术有限公司
 */
public class SlidingItem {
    public String name;
    public String tag;
    public String time;

    public SlidingItem(String name) {
        this.name = name;
    }
    public SlidingItem(String name, String tag, String time) {
        this.name = name;
        this.tag = tag;
        this.time = time;
    }
}
