package com.example.dopy.report_02.model;

import android.graphics.drawable.Drawable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Dopy on 2017-07-13.
 * ORM Lite에 따러서 생성한다.
 * http://dunkinpender.tistory.com/4
 */
@DatabaseTable(tableName = "RESTAURANT")
public class Item {
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private int _id;
    @DatabaseField
    private String name; //가게 이름
    @DatabaseField
    private String contents; //내용
    @DatabaseField
    private int imagePath; //이미지 경로
    @DatabaseField
    private int distance; //거리순
    @DatabaseField
    private int popularity; //호감순
    @DatabaseField
    private int recent; //최신순
    @DatabaseField
    private Boolean favorite;

    public Item() {
    }

    public Item(String name, String contents, int imagePath, int distance, int popularity, int recent) {
        this.name = name;
        this.contents = contents;
        this.imagePath = imagePath;
        this.distance = distance;
        this.popularity = popularity;
        this.recent = recent;
        this.favorite=false;
    }

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getContents() {
        return contents;
    }

    public int getImagePath() {
        return imagePath;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    /*TODO: naming
    click이 의미하는 바가 잘 모르겠습니다.
    click이후의 상태값을 의미하는거면 click이란 네이밍보다 다른 것으로 선언하는것이 좋을 거 같습니다.*/
    public void clickFavoriteMark(){
        favorite=!favorite;
    }
}
