package com.lubuntum.guesswhoapp.cards;

import com.google.gson.annotations.SerializedName;

public class Card {
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
