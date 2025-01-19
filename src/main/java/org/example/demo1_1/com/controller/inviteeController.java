package org.example.demo1_1.com.controller;


import ch.qos.logback.core.util.DelayStrategy;
import org.example.demo1_1.com.bean.User;
import org.example.demo1_1.com.bean.memberType;
import org.example.demo1_1.com.service.subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.example.demo1_1.com.bean.memberType.INVITEE;

@RestController
@RequestMapping("/invitee")
public class inviteeController {
    @Autowired
    subscribe subscribe;

    @PostMapping("/joinRoom")
    public ResponseEntity<String> joinRoom(@RequestBody User user) throws IOException {
        if(user.getMemberType().equals(INVITEE))
        {
            String subscriptionId = user.getUsername()+"-1234";
            String subscriptionName = subscribe.createSubscription(subscriptionId,user.getRoomName());
            return ResponseEntity.ok(user.getRoomName()+"Chat Room joined successfully");
        }
        else
        {
            return ResponseEntity.ok("Member Type is not Invitee");
        }
    }
}
