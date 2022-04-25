package com.abin.RedisDemo.controller;

import com.abin.RedisDemo.model.User;
import com.abin.RedisDemo.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class UserController {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Cacheable(value = "users",key = "#userId", unless = "#result.followers < 12000")
    @RequestMapping(value = "/{userId}",method = RequestMethod.GET)
    public User getUser(@PathVariable String userId){
        LOG.info("Getting user with id {}.");
        return userRepository.findOne(Long.valueOf(userId));


    }

    @CachePut(value = "users",key = "#user.id")
    @PutMapping("/update")
    public User updatePersonById(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    @CacheEvict(value = "users", allEntries = true)
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id){
        LOG.info("deleting person with id");
        userRepository.delete(id);
    }
}
