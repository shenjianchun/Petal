package com.jc.petal.data.model;

/**
 * 周刊
 * Created by 14110105 on 2016-08-04.
 */
public class Weekly {


    /**
     * weekly_id : 247
     * is_final : 1
     * release_date : 2016-06-30T16:00:00.000Z
     * release_no : 2016-07-1
     * title : 每条大鱼，都会相遇
     * description : 
     * 一部《大鱼海棠》，让等待了十二年的梦，终于即将醒来。“我们的梦想是做一部深深打动人心的动画电影，带给少年爱与信仰的力量。”——《大鱼·海棠》原作者Tidus。 
     * 是在，也许每件东西的诞生都会经历太多的坎坷和迷茫，但只要拼命尽力过了，结果就不会差。
     * cover : /img/weekly/247/cover_430x230
     * ad_content : null
     * ad_content_small : null
     * ad_link : null
     */

    public int weekly_id;
    public int is_final;
    public String release_date;
    public String release_no;
    public String title;
    public String description;
    public String cover;
    public Object ad_content;
    public Object ad_content_small;
    public Object ad_link;


    @Override
    public String toString() {
        return "Weekly{" +
                "weekly_id=" + weekly_id +
                ", is_final=" + is_final +
                ", release_date='" + release_date + '\'' +
                ", release_no='" + release_no + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", cover='" + cover + '\'' +
                ", ad_content=" + ad_content +
                ", ad_content_small=" + ad_content_small +
                ", ad_link=" + ad_link +
                '}';
    }
}
