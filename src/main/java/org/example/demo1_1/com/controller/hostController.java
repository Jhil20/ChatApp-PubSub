package org.example.demo1_1.com.controller;

import org.example.demo1_1.com.bean.User;
import org.example.demo1_1.com.service.publish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.example.demo1_1.com.bean.memberType.HOST;

@RestController
@RequestMapping("/host")
public class hostController {

    @Autowired
    private publish publish;

    @PostMapping("/createRoom")
    public ResponseEntity<String> createRoom(@RequestBody User user) throws IOException {
        if(user.getMemberType().equals(HOST))
        {
            String topicId = user.getRoomName()+"-1234";
            String subscriptionId = user.getUsername()+"-1234";
            String topicName = publish.createTopic(topicId,subscriptionId); //2 parameter is subscription id
            return ResponseEntity.ok(topicName+"Chat Room created successfully");
        }
        else{
            return ResponseEntity.ok("Member Type is not Host");
        }
    }
}
