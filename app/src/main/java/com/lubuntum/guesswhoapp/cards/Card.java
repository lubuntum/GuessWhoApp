package com.lubuntum.guesswhoapp.cards;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Card implements Serializable {
    @SerializedName("characterName")
    public String characterName;
    @SerializedName("present")
    public String present;
    @SerializedName("imageId")
    public String imageId;

    public Card(String characterName, String present, String imageId) {
        this.characterName = characterName;
        this.present = present;
        this.imageId = imageId;
    }
    public Card(){}

    public Card copy(){
        return new Card(this.characterName, this.present, this.imageId);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s : %s", characterName, present);
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
