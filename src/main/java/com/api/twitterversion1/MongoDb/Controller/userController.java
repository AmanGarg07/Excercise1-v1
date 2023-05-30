package com.api.twitterversion1.MongoDb.Controller;

import com.api.twitterversion1.MongoDb.User.User;
import com.api.twitterversion1.MongoDb.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class userController {

    @Autowired
    private UserService userService;

    // get all Users stored in Db
    @GetMapping("/users")
    public ResponseEntity<?> getActors(){
        return ResponseEntity.ok(this.userService.getAll());
    }
    // get user by id
    @GetMapping("/users/{id_str}")
    public ResponseEntity<User> getPersons(@PathVariable("id_str") String id_str) throws IOException, InterruptedException{

        User user =  this.userService.getById(id_str);
        if(user==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(user));
    }
    // delete user from db by id
    @DeleteMapping("/user/{id_str}")
    public ResponseEntity<?> deleteActor(@PathVariable("id_str") String id) {
        User user = this.userService.deleteUser(id);
        if(user==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(user));
    }
    // find common followers
    @GetMapping("/followers")
    public ResponseEntity<?> getCommonMovies(@RequestParam("user1") String user1, @RequestParam("user2") String user2) throws IOException, InterruptedException{
        // make a request here to imdb api
//    	System.out.println(actor1+" "+actor2);
        List<String> commonMovies =  this.userService.getCommonFollowers(user1,user2);
        if(commonMovies.isEmpty()){
            return ResponseEntity.of(Optional.of("No Common Followers"));
        }
        return ResponseEntity.of(Optional.of(commonMovies));
    }

}
