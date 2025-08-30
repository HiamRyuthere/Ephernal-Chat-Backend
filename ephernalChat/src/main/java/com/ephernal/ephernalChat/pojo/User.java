package com.ephernal.ephernalChat.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {

    private String id;
    private String username;
    private String gender;
    private int age;


}
