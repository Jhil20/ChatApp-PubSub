package org.example.demo1_1.com.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/publisher")
public class publish {
    @Value("${gcp.project-id}")
    private String projectId;

    final
    subscribe subscribe;

    public publish(subscribe subscribe) {
        this.subscribe = subscribe;
    }

    public ResponseEntity<String> publishMessage(String message,String topicId) throws IOException, ExecutionException, InterruptedException {
        TopicName topicName = TopicName.of(projectId, topicId);
        Publisher publisher = null;
        try {
            publisher =Publisher.newBuilder(topicName).build();
            ByteString data = ByteString.copyFromUtf8(message);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
            ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
            String messageId = messageIdFuture.get();
            System.out.println("Published message ID: " + messageId);
            return ResponseEntity.ok(message+" "+messageId);
        }
        finally {
            if(publisher != null)
            {
                publisher.shutdown();
                publisher.awaitTermination(5, TimeUnit.SECONDS);
            }
        }
    }

    public String createTopic (String topicId,String subscriptionId) throws IOException{
        try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
            TopicName topicName = TopicName.of(projectId, topicId);
            Topic topic = topicAdminClient.createTopic(topicName);
            String subscriptionName = subscribe.createSubscription(subscriptionId,topicId);
            System.out.println("Created topic: " + topic.getName());
            System.out.println("Created sub: " + subscriptionName);
            return topic.getName();
        }
    }
}
