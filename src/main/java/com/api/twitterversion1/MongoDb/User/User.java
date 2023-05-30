package com.api.twitterversion1.MongoDb.User;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("users")
public class User {
    @Id
    private String id_str;
    private String name;
    private String description;
    private int followers_count;
    private int friends_count;
    private List<String> followersList;

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public void setFriends_count(int friends_count) {
        this.friends_count = friends_count;
    }

    public List<String> getFollowersList() {
        return followersList;
    }

    public void setFollowersList(List<String> followersList) {
        this.followersList = followersList;
    }

    public User(String id_str, String name, String description, int followers_count, int friends_count, List<String> followersList) {
        this.id_str = id_str;
        this.name = name;
        this.description = description;
        this.followers_count = followers_count;
        this.friends_count = friends_count;
        this.followersList = followersList;
    }
}
