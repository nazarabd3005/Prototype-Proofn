package com.nazar.prototypeproofn.models;

public class User {
    public int id;
    public String hash;
    public String firstName;
    public String lastName;
    public String fullName;
    public String avatarPathSmall;
    public String avatarPathMedium;
    public String avatarPathLarge;
    public String activeEmail;
    public boolean isViP = false;
    public String phoneNumber;
    public boolean hasUnablePassword = false;


    public class Response extends User {
        private String message;
    }
}
