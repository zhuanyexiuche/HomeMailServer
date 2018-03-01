package main.data;

import main.common.Question;
import main.common.Response;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class ResponseDBHelper extends DBHelper{
    /*
    1:RID int(auto)  2:RQID int 3:RWXID String 4:RWXNickName String 5:RAvatarUrl String
    6:RNiMing boolean 7:RContent String 8:RBriefContent String 9:RCommentCount int
    10:RClapCount int 11:RIsDeleted boolean
     */
    private ResponseDBHelper(){super("Response","RID",11);}
    private static ResponseDBHelper instance = new ResponseDBHelper();
    public static ResponseDBHelper getInstance(){return instance;}
    public void add(Response r){
        PreparedStatement stat = this.getInsertStat();
        try {
            stat.setNull(1, Types.INTEGER);
            stat.setInt(2,r.getQID());
            stat.setString(3,r.getWXID());
            stat.setString(4,r.getWXNickName());
            stat.setString(5,r.getAvatarUrl());
            stat.setBoolean(6,r.isNiMing());
            stat.setString(7,r.getContent());
            stat.setString(8,r.getBriefContent());
            stat.setInt(9,r.getCommentCount());
            stat.setInt(10,r.getClapCount());
            stat.setBoolean(11,r.isDeleted());
            stat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(int ID){
        try {
            int QID =-1;
            int clapCount =-1;
            PreparedStatement stat1 = this.getConn().prepareStatement(
                    "Select RQID,RClapCount From "+this.TABLE_NAME+" Where RID =?"
            );
            stat1.setInt(1,ID);
            ResultSet set = stat1.executeQuery();
            while (set.next()){
                QID = set.getInt("RQID");
                clapCount = set.getInt("RClapCount");
            }
            QuestionDBHelper.getInstance().addResp(QID,-1);
            QuestionDBHelper.getInstance().clap(QID,-clapCount);
            PreparedStatement stat = this.getConn().prepareStatement(
                    "Update "+this.TABLE_NAME+" set RIsDeleted = true where "+this.PRIMARY_KEY+"=?"
            );
            stat.setInt(1,ID);
            stat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Response makeBrief(ResultSet set){
        Response q = new Response();
        try {
            q.setID(set.getInt("RID"));
            q.setQID(set.getInt("RQID"));
            q.setWXID(set.getString("RWXID"));
            q.setNiMing(set.getBoolean("RNiMing"));
            q.setBriefContent(set.getString("RBriefContent"));
            q.setClapCount(set.getInt("RClapCount"));
            q.setCommentCount(set.getInt("RCommentCount"));
            q.setAvatarUrl(set.getString("RAvatarUrl"));
            q.setWXNickName(set.getString("RWXNickName"));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            return q;
        }
    }
    private Response make(ResultSet set){
        Response q = makeBrief(set);
        try {
            q.setContent(set.getString("RContent"));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            return q;
        }
    }
    public Response getOneFull(int RID){
        Response r = null;
        try {
            PreparedStatement stat = this.getConn().prepareStatement(
                    "Select * From "+this.TABLE_NAME+" Where RID = ?"
            );
            stat.setInt(1,RID);
            ResultSet set = stat.executeQuery();
            while (set.next()){
                r = make(set);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            return r;
        }
    }
    public ArrayList<Response> getAllBrief(int QID){
        ArrayList<Response> res = new ArrayList<>();
        try {
            PreparedStatement stat = this.getConn().prepareStatement(
                    "Select RID,RQID,RWXID,RNiMing,RBriefContent,RCommentCount,RClapCount,RAvatarUrl,RWXNickName From "+this.TABLE_NAME+" Where RQID = ?&&RIsDeleted=false"
            );
            stat.setInt(1,QID);
            ResultSet set = stat.executeQuery();
            while (set.next()){
                res.add(makeBrief(set));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            return res;
        }
    }
    public void clap(int RID,int delta){
        int nowCount =-1;
        int QID = -1;
        try {
            PreparedStatement stat1 = this.getConn().prepareStatement(
                    "Select RClapCount,RQID From "+this.TABLE_NAME+" Where RID =?"
            );
            stat1.setInt(1,RID);
            ResultSet set = stat1.executeQuery();
            while (set.next()){
                nowCount = set.getInt("RClapCount");
                QID = set.getInt("RQID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (nowCount==-1)return;
        try {
            PreparedStatement stat2 = this.getConn().prepareStatement(
                    "Update "+this.TABLE_NAME+" Set RClapCount = ? Where RID = ?"
            );
            stat2.setInt(1,nowCount+delta);
            stat2.setInt(2,RID);
            stat2.execute();
            QuestionDBHelper.getInstance().clap(QID,delta);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void edit(int RID,String content){
        String briefCont;
        if (content.length()<=Response.BRIEF_LENGTH)
            briefCont = content;
        else
            briefCont = content.substring(0,Response.BRIEF_LENGTH)+"...";
        try {
            PreparedStatement stat = this.getConn().prepareStatement(
                    "Update "+this.TABLE_NAME+" Set RContent = ?,RBriefContent = ? Where RID = ?"
            );
            stat.setString(1,content);
            stat.setString(2,briefCont);
            stat.setInt(3,RID);
            stat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
