package org.example.demo1_1.com.controller;

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.pubsub.v1.stub.SubscriberStub;
import com.google.cloud.pubsub.v1.stub.SubscriberStubSettings;
import com.google.pubsub.v1.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/subscriber")
public class subscribe {

    @Value("${gcp.project-id}")
    private String projectId;

    @Value("${gcp.pubsub.subscription-name}")
    private String subscriptionId;

    @GetMapping("/subscribeMessage")
    public String subscribeMessage() {
        StringBuilder receiveMessageBuilder = new StringBuilder();

        // Define the project and subscription
        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);

        // Initialize the SubscriberStub for low-level control
        try (SubscriberStub subscriberStub = SubscriberStubSettings.newBuilder().build().createStub()) {

            // Create a PullRequest for fetching messages
            PullRequest pullRequest = PullRequest.newBuilder()
                    .setSubscription(subscriptionName.toString())
                    .setMaxMessages(10) // Number of messages to pull
                    .setReturnImmediately(false) // Wait for messages if none are available
                    .build();

            // Pull messages
            PullResponse pullResponse = subscriberStub.pullCallable().call(pullRequest);

            // Process each received message
            for (ReceivedMessage message : pullResponse.getReceivedMessagesList()) {
                String data = message.getMessage().getData().toStringUtf8();
                System.out.println("Id: " + message.getMessage().getMessageId());
                System.out.println("Data: " + data);

                // Append message data to StringBuilder
                receiveMessageBuilder.append(data).append("\n");

                // Acknowledge the message to Pub/Sub
                subscriberStub.acknowledgeCallable().call(
                        AcknowledgeRequest.newBuilder()
                                .setSubscription(subscriptionName.toString())
                                .addAckIds(message.getAckId())
                                .build()
                );
            }

        } catch (IOException | ApiException e) {
            System.err.println("Error during Pub/Sub pull: " + e.getMessage());
        }

        // Return the accumulated messages
        System.out.println("All received messages: " + receiveMessageBuilder);
        return receiveMessageBuilder.toString();
    }
}
