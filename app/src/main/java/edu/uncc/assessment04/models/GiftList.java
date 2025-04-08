package edu.uncc.assessment04.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "giftLists")
public class GiftList implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long giftId;
    @ColumnInfo
    String name;
    @ColumnInfo
    public long userID;

    public long getGiftId() {
        return giftId;
    }

    public void setGiftId(long giftId) {
        this.giftId = giftId;
    }

    public GiftList() {
    }

    public GiftList(String name, long userID) {
        this.name = name;
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
