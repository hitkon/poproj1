package agh.ics.oop;

public class Message {
    String text;
    Object attachment;
    public Message(String text){
        this.text = text;
    }
    public Message(String text, Object obj){
        this.text = text;
        attachment = obj;
    }
    public String getText(){
        return text;
    }
    public Object getAttachment(){
        return attachment;
    }
}
