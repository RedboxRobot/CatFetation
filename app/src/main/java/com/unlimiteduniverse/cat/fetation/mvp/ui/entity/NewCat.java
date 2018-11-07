package com.unlimiteduniverse.cat.fetation.mvp.ui.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Irvin
 * @time 2018/11/7 0007
 */
@Entity
public class NewCat {
    @Id(autoincrement = true)
    Long id;
    @NotNull
    String catName;
    String catAvatar;
    String catSex;
    String catWeight;
    int isNeutering;
    String birthday;
    String comingDay;
    String describe;
    @Generated(hash = 1238199174)
    public NewCat(Long id, @NotNull String catName, String catAvatar, String catSex,
            String catWeight, int isNeutering, String birthday, String comingDay,
            String describe) {
        this.id = id;
        this.catName = catName;
        this.catAvatar = catAvatar;
        this.catSex = catSex;
        this.catWeight = catWeight;
        this.isNeutering = isNeutering;
        this.birthday = birthday;
        this.comingDay = comingDay;
        this.describe = describe;
    }
    @Generated(hash = 1638089981)
    public NewCat() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCatName() {
        return this.catName;
    }
    public void setCatName(String catName) {
        this.catName = catName;
    }
    public String getCatAvatar() {
        return this.catAvatar;
    }
    public void setCatAvatar(String catAvatar) {
        this.catAvatar = catAvatar;
    }
    public String getCatSex() {
        return this.catSex;
    }
    public void setCatSex(String catSex) {
        this.catSex = catSex;
    }
    public String getCatWeight() {
        return this.catWeight;
    }
    public void setCatWeight(String catWeight) {
        this.catWeight = catWeight;
    }
    public int getIsNeutering() {
        return this.isNeutering;
    }
    public void setIsNeutering(int isNeutering) {
        this.isNeutering = isNeutering;
    }
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getComingDay() {
        return this.comingDay;
    }
    public void setComingDay(String comingDay) {
        this.comingDay = comingDay;
    }
    public String getDescribe() {
        return this.describe;
    }
    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
