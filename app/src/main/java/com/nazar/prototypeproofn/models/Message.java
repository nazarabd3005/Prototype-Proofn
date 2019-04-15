package com.nazar.prototypeproofn.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Parcelable {
    public int id;
    public String hash;
    public String contentType;
    public int messageClass;
    public String[] defaultLabels;
    public int messageType;
    public int recipientID;
    public String recipientEmail;
    public boolean asMain = false;
    public boolean asCC = false;
    public boolean asBCC = false;
    public ArrayList<User> to = new ArrayList<>();
    public Object[] recipients;
    public Object[] recipientsAsCC;
    public Object[] getRecipientsAsBCC;
    public String senderEmail;
    public User sender = new User();
    public String sentAt;
    public String threadID;
    public String inReplyTo;
    public String subjectPreview;
    public String subject;
    public String contentPreview;
    public String content;
    public int attachmentCount;
    public int availabilityStatus;
    public int deliveryStatus;

    //need
    public boolean selected = false;

    public Message() {
    }

    protected Message(Parcel in) {
        id = in.readInt();
        hash = in.readString();
        contentType = in.readString();
        messageClass = in.readInt();
        defaultLabels = in.createStringArray();
        messageType = in.readInt();
        recipientID = in.readInt();
        recipientEmail = in.readString();
        asMain = in.readByte() != 0;
        asCC = in.readByte() != 0;
        asBCC = in.readByte() != 0;
        senderEmail = in.readString();
        sentAt = in.readString();
        threadID = in.readString();
        inReplyTo = in.readString();
        subjectPreview = in.readString();
        subject = in.readString();
        contentPreview = in.readString();
        content = in.readString();
        attachmentCount = in.readInt();
        availabilityStatus = in.readInt();
        deliveryStatus = in.readInt();
        selected = in.readByte() != 0;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(hash);
        dest.writeString(contentType);
        dest.writeInt(messageClass);
        dest.writeStringArray(defaultLabels);
        dest.writeInt(messageType);
        dest.writeInt(recipientID);
        dest.writeString(recipientEmail);
        dest.writeByte((byte) (asMain ? 1 : 0));
        dest.writeByte((byte) (asCC ? 1 : 0));
        dest.writeByte((byte) (asBCC ? 1 : 0));
        dest.writeString(senderEmail);
        dest.writeString(sentAt);
        dest.writeString(threadID);
        dest.writeString(inReplyTo);
        dest.writeString(subjectPreview);
        dest.writeString(subject);
        dest.writeString(contentPreview);
        dest.writeString(content);
        dest.writeInt(attachmentCount);
        dest.writeInt(availabilityStatus);
        dest.writeInt(deliveryStatus);
        dest.writeByte((byte) (selected ? 1 : 0));
    }


    public static class RequestSend{
        public String subject;
        public String content;
        public String[] recipients;
        public String[] recipientsAsCC;
        public String[] recipientsAsBCC;
        public String contentPreview;
    }

    public static class RequestList{
        public class FORMAT_VALUE{
            public static final String MINIMAL = "minimal";
            public static final String FULL = "full";
            public static final String METADATA = "metadata";
        }
        public int limit;
        public int skip;
        public String format;
    }

    public class Response extends Message implements Parcelable{
        public String message;

        protected Response(Parcel in) {
            super(in);
        }
    }

    public class ResponseList {
        public ArrayList<Message.Response> data;
    }
}
