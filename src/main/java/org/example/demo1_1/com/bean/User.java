package org.example.demo1_1.com.bean;

public class User {
    private String username;
    private memberType memberType;
    private String roomName;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public memberType getMemberType() {
        return memberType;
    }

    public void setMemberType(memberType memberType) {
        this.memberType = memberType;
    }
}
