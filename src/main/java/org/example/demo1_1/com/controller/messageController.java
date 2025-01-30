package org.example.demo1_1.com.controller;


import org.example.demo1_1.com.bean.User;
import org.example.demo1_1.com.service.publish;
import org.example.demo1_1.com.service.subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/message")
public class messageController {

    @Autowired
   private publish publish;

    @Autowired
    private subscribe subscribe;

    @PostMapping("/sendMessage")
    public ResponseEntity<String> sendMessage(@RequestBody User user) throws IOException, ExecutionException, InterruptedException {
        return publish.publishMessage(user);
    }

    @PostMapping("/receiveMessage")
    public ResponseEntity<Map<String,String>> receiveMessage(@RequestBody User user) throws IOException, ExecutionException, InterruptedException {
        Map<String,String> receivedMessages = subscribe.subscribeMessage(user.getUsername());
        System.out.print(receivedMessages);
        return ResponseEntity.ok(receivedMessages);
    }

}
