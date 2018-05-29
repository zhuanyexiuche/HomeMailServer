package main.common;

public class Secret {
    private int ID,respCount;
    private String context,briefContext;
    private boolean isDeleted;
    private String name,mood;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

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

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getBriefContext() {
        return briefContext;
    }

    public void setBriefContext(String briefContext) {
        this.briefContext = briefContext;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
