package com.nazar.prototypeproofn.models;

public class Auth {
    public static class Request {
        public String identifier;
        public String password;
    }
    public class Response {
        public String token;
        public User user;
    }
}
