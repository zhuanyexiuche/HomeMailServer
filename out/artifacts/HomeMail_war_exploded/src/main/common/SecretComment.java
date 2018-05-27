package main.common;

public class SecretComment {
    private int ID,SID;
    private String context,briefContext;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
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
}
