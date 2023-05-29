package com.v1.api.services;

import com.v1.api.ActorRepo.ActorRepo;
import com.v1.api.model.Actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

@Service
public class ActorService {
    
	@Autowired
	private ActorRepo actorRepo;

	private  HashMap<String, String> actorMap = new HashMap<>();
	
	// get all actors
    public List<Actor> getAll(){
        return actorRepo.findAll();
    }
    
    // find a actor by Id
    public Actor getById(String id) throws IOException, InterruptedException{
    	
    	HttpRequest request = HttpRequest.newBuilder()
    			.uri(URI.create("https://actor-movie-api1.p.rapidapi.com/getid/" + id + "?apiKey=62ffac58c57333a136053150eaa1b587"))
    			.header("X-RapidAPI-Key", "7dc1609a47msh6c82753d3dfb565p18388ejsne9f9aa39b617")
    			.header("X-RapidAPI-Host", "actor-movie-api1.p.rapidapi.com")
    			.method("GET", HttpRequest.BodyPublishers.noBody())
    			.build();
    	HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//    	System.out.println(response.body());
    
    	ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readValue(response.body(), JsonNode.class);
    	String actorId = jsonNode.get(0).get("actorId").asText();
    	List<String> movies = new ArrayList<>();
    	
    	for (JsonNode node : jsonNode) {
    		JsonNode targetValueNode = node.get("original_title");

            // Check if the key exists in the current entry
            if (targetValueNode != null) {
                // Retrieve the value as a string
                String targetValue = targetValueNode.asText();
                movies.add(targetValue);
            } else {
                System.out.println("Key not found in the entry");
            }
    	}
    	Actor actor = new Actor(actorId,id,movies);
    	actorRepo.save(actor);
    	actorMap.put(id, actorId);

    	return actor;
    }

	// find list of common movies of two actors
    public List<String> getCommonMovies(String id1, String id2){

    	String actorId1 = actorMap.get(id1);
    	String actorId2 = actorMap.get(id2);

    	Actor actor1 = actorRepo.findById(actorId1).get();
    	Actor actor2 = actorRepo.findById(actorId2).get();
    	List<String> commonMovies = new ArrayList<>();

    	for(String movie : actor1.getMovies()) {
    		if(actor2.getMovies().contains(movie)) {
    			commonMovies.add(movie);
    		}
    	}
    	return commonMovies;
    }
 
    
    // delete a person by ID
    public Actor deleteActor(String id) {

    	String actorId= actorMap.get(id);
		if(actorId==null){
			return null;
		}
		Actor actor = actorRepo.findById(actorId).get();
    	actorRepo.deleteById(actorId);
    	actorMap.remove(id);
    	return actor;
    }
   
    
}
