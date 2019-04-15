package com.nazar.prototypeproofn.rest;

public class Endpoint {


    //region Auth

    public class AUTH {
        public static final String LOGIN = "auth/login";
    }

    //endregion
    //region Message

    public  class MESSAGES {
        public static final String SEND = "messages/send";
        public static final String INBOX = "messages/inbox";
        public static final String INBOX_DETAIL = "messages/inbox/"; //do follow with {id}
        public static final String INBOX_TRASH = "messages/inbox/"; //do follow with {id}/trash
    }
    //endregion

    public class USER {
        public static final String Profile = "user/profile";
        public static final String avatar = "user/avatar";
    }
    //base
    public static final String PATH = "v1/";
    public static final String DOMAIN = "https://beta.proofn.com/";

    public static String getBaseDomain(){
        return DOMAIN+PATH;
    }
}
