package main.common;

public class Response {
    private int ID,QID,commentCount,clapCount;
    private String WXID,content,briefContent;
    private boolean NiMing,isDeleted;
    public final static int BRIEF_LENGTH = 24;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getQID() {
        return QID;
    }

    public void setQID(int QID) {
        this.QID = QID;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getClapCount() {
        return clapCount;
    }

    public void setClapCount(int clapCount) {
        this.clapCount = clapCount;
    }

    public String getWXID() {
        return WXID;
    }

    public void setWXID(String WXID) {
        this.WXID = WXID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBriefContent() {
        return briefContent;
    }

    public void setBriefContent(String content) {
        if (content.length()<=BRIEF_LENGTH)
            this.briefContent = content;
        else
            this.briefContent = content.substring(0,BRIEF_LENGTH)+"...";
    }

    public boolean isNiMing() {
        return NiMing;
    }

    public void setNiMing(boolean niMing) {
        NiMing = niMing;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
