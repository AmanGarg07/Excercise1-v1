package com.api.twitterversion1.MongoDb.userService;

import com.api.twitterversion1.MongoDb.User.User;
import com.api.twitterversion1.MongoDb.UserRepo.UserRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;



    public List<User> getAll(){
        return userRepo.findAll();
    }
    private  ObjectMapper mapper = new ObjectMapper();
    private  File from = new File("/Users/aman.garg1/Desktop/Spring boot apps/twitter-version-1/src/main/resources/TwitterData.json");
    private  JsonNode masterNode;

    {
        try {
            masterNode = mapper.readTree(from);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User getById(String id_str) throws IOException, InterruptedException{

        JsonNode ansNode = null;
//        System.out.println(id_str);
        for (JsonNode node : masterNode) {
            String targetValueNode = node.get("id").asText();
//            System.out.println(targetValueNode);
            if (targetValueNode.equals(id_str)) {
                ansNode = node;
                break;
            }
        }
        if(ansNode == null)
            return null;

        String name = ansNode.get("screenName").asText();
        String description = ansNode.get("avatar").asText();
        int followers_count = ansNode.get("followersCount").asInt();
        int friends_count = ansNode.get("friendsCount").asInt();
        List<String> followersList = new ArrayList<>();

        for(JsonNode followerId: ansNode.get("followers")){
            followersList.add(followerId.asText());
        }

        User user = new User(id_str,name,description,followers_count,friends_count,followersList);
//        System.out.println(user);
        userRepo.save(user);

        return user;
    }

    private  String convertString(String id){
        return "\"" + id + "\"";
    }
    public List<String> getCommonFollowers(String id1, String id2){

        String editedId1=convertString(id1);
        String editedId2=convertString(id2);

        Optional<User> user1 = userRepo.findById(id1);
        Optional<User> user2 = userRepo.findById(id2);

        
        List<String> commonFollowers = new ArrayList<>();

        for(String follower : user1.get().getFollowersList()) {
            if(user2.get().getFollowersList().contains(follower)) {
                commonFollowers.add(follower);
            }
        }
        return commonFollowers;
    }
    public User deleteUser(String id_str) {

        if(id_str==null){
            return null;
        }
        User user = userRepo.findById(id_str).get();
        userRepo.deleteById(id_str);
        return user;
    }
}
