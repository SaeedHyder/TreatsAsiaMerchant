package com.app.merchanttreatzasia.entities;

/**
 * Created by khan_muhammad on 10/20/2017.
 */

public class TypeEnt {

    String Type;
    boolean isClick;

    public TypeEnt(String type,boolean isClick){
        setType(type);
        setClick(isClick);
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
