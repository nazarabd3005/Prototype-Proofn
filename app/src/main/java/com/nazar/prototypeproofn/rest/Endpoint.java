package com.nazar.prototypeproofn.rest;

public class Endpoint {


    //region Auth

    public class AUTH {
        public static final String LOGIN = "Auth/login";
    }

    //endregion
    //region Messages

    public  class MESSAGES {
        public static final String SEND = "Messages/send";
        public static final String INBOX = "Messages/inbox";
        public static final String INBOX_DETAIL = "Messages/inbox/"; //do follow with {id}
        public static final String INBOX_TRASH = "Messages/inbox/"; //do follow with {id}/trash
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
