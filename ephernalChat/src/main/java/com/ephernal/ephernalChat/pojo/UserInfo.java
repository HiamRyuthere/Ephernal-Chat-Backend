package com.ephernal.ephernalChat.pojo;



public class UserInfo {
    private final String username;
    private final int age;
    private final String gender;

    public UserInfo(String username, int age, String gender) {
        this.username = username;
        this.age = age;
        this.gender = gender;
    }

    public String getUsername() { return username; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
}
