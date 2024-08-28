package com.example.e_commercekotlin.data.model;

public class Followings {
    private int followingImage;
    private String followingName;
    private String followingType;

    public Followings(int followingImage, String followingName, String followingType) {
        this.followingImage = followingImage;
        this.followingName = followingName;
        this.followingType = followingType;
    }

    public int getFollowingImage() {
        return followingImage;
    }

    public String getFollowingType() {
        return followingType;
    }

    public String getFollowingName() {
        return followingName;
    }
}
