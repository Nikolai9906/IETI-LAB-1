package edu.eci.ieti.microservices.controller;

import edu.eci.ieti.microservices.data.User;
import edu.eci.ieti.microservices.dto.UserDto;
import edu.eci.ieti.microservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 *
 * @author Nikolai9906
 */
@RestController
@RequestMapping( "/v1/user" )
public class UserController {
    private final UserService userService;

    public UserController(@Autowired UserService userService )
    {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> all()
    {
        try{
            return ResponseEntity.ok().body(userService.all());
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping( "/{id}" )
    public ResponseEntity<User> findById( @PathVariable String id )
    {

        try{
            return ResponseEntity.ok().body(userService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<User> create( @RequestBody UserDto userDto )
    {
        try{
            Random rand = new Random(); //instance of random class
            int upperbound = 100;
            //generate random values from 0-100
            int int_random = rand.nextInt(upperbound);
            String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                    +"lmnopqrstuvwxyz!@#$%&";
            Random rnd = new Random();
            StringBuilder sb = new StringBuilder(int_random);
            for (int i = 0; i < 5; i++)
                sb.append(chars.charAt(rnd.nextInt(chars.length())));

            User user = new User(sb.toString(),userDto.getName(),userDto.getEmail(),
                    userDto.getLastName(),userDto.getCreatedAt());
            return ResponseEntity.ok().body(userService.create(user));
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping( "/{id}" )
    public ResponseEntity<User> update( @RequestBody UserDto userDto, @PathVariable String id )
    {
        try{
            User user = userService.findById(id);
            if( user != null){
                user.setEmail(userDto.getEmail());
                user.setCreatedAt(userDto.getCreatedAt());
                user.setLastName(userDto.getLastName());
                user.setName(userDto.getName());
                return ResponseEntity.ok().body(userService.update(user, id));
            }
            return ResponseEntity.status(404).build();
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping( "/{id}" )
    public ResponseEntity<Boolean> delete( @PathVariable String id )
    {
        try{
            userService.deleteById(id);
            boolean delete = userService.findById(id) != null ? false : true;
            return ResponseEntity.ok().body(delete);
        }catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
}
