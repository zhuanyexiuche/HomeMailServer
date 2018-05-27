package main.common;

public class Question {
    private int ID,respCount,clapCount;
    private String topic,context;
    private boolean isDeleted;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getRespCount() {
        return respCount;
    }

    public void setRespCount(int respCount) {
        this.respCount = respCount;
    }

    public int getClapCount() {
        return clapCount;
    }

    public void setClapCount(int clapCount) {
        this.clapCount = clapCount;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
