package edu.uncc.assessment04.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "giftListItems")
public class GiftListItem {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo
    String name;
    @ColumnInfo
    Integer quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGiftId() {
        return giftId;
    }

    public void setGiftId(long giftId) {
        this.giftId = giftId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @ColumnInfo
    private long giftId;
    @ColumnInfo
    private long userId;

    public GiftListItem() {
    }

    public GiftListItem(String name, Integer quantity, long giftId, long userId) {
        this.name = name;
        this.quantity = quantity;
        this.giftId = giftId;
        this.userId = userId;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
