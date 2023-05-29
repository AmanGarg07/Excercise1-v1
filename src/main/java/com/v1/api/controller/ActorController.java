package com.v1.api.controller;


import com.v1.api.model.Actor;
import com.v1.api.services.ActorService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ActorController {
//    @RequestMapping(value="/hi",method= RequestMethod.GET)

	@Autowired
    private ActorService actorService;
    
    
    @GetMapping("/actors")
    public ResponseEntity<?> getActors(){
        return ResponseEntity.ok(this.actorService.getAll());
    }
    
    @GetMapping("/actor/{id}")
    public ResponseEntity<Actor> getPersons(@PathVariable("id") String id) throws IOException, InterruptedException{
    	// make a request here to imdb api
        Actor person =  this.actorService.getById(id);
        if(person==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(person));
    }
    @GetMapping("/movies")
    public ResponseEntity<?> getCommonMovies(@RequestParam("actor1") String actor1, @RequestParam("actor2") String actor2) throws IOException, InterruptedException{
    	// make a request here to imdb api
//    	System.out.println(actor1+" "+actor2);
        List<String> commonMovies =  this.actorService.getCommonMovies(actor1,actor2);
        if(commonMovies==null){
            return ResponseEntity.of(Optional.of("No common movies"));
        }
        return ResponseEntity.of(Optional.of(commonMovies));
    }
     
    @DeleteMapping("/actor/{id}")
    public ResponseEntity<?> deleteActor(@PathVariable("id") String id) {
    	Actor actor = this.actorService.deleteActor(id);
        if(actor==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    	return ResponseEntity.of(Optional.of(actor));
    }


}
