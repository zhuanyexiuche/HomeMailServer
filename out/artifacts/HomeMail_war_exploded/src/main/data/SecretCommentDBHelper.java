package main.data;

import main.common.SecretComment;

import java.sql.*;
import java.util.ArrayList;

public class SecretCommentDBHelper extends DBHelper{
    /*
    1:SID int 2:SContext:String 3:SBriefContext String
    4:SSID int 5:SIsDeleted boolean 6:SName String 7:SMood String
     */
    public static final int MAX_LENGTH = 24;
    private SecretCommentDBHelper(){super("SecretComment","SID",7);}
    private static SecretCommentDBHelper instance = new SecretCommentDBHelper();
    public static SecretCommentDBHelper getInstance(){return instance;}
    public static ArrayList<String> nameList = new ArrayList();
    public static ArrayList<String> moodList = new ArrayList<>();
    static{
        nameList.add("Wang_Xiao");
        nameList.add("Hong_Xiao");
        nameList.add("Feng_Double_Chou");

        moodList.add("Happy");
        moodList.add("Happier");
        moodList.add("Happiest");
    }
    public void add(SecretComment r){
        r.setName(nameList.get((int)Math.random()*nameList.size()));
        r.setMood(moodList.get((int)(Math.random()*moodList.size())));
        PreparedStatement stat = this.getInsertStat();
        try {
            stat.setNull(1, Types.INTEGER);
            stat.setString(2,r.getContext());
            stat.setString(3,r.getBriefContext());
            stat.setInt(4,r.getSID());
            stat.setBoolean(5,false);
            stat.setString(6,r.getName());
            stat.setString(7,r.getMood());
            stat.execute();
            SecretDBHelper.getInstance().resp(r.getSID(),1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(int ID){
        try {
            int SID =-1;
            PreparedStatement stat1 = this.getConn().prepareStatement(
                    "Select SSID From "+this.TABLE_NAME+" Where SID =?"
            );
            stat1.setInt(1,ID);
            ResultSet set = stat1.executeQuery();
            while (set.next()){
                SID = set.getInt("SSID");
            }
            SecretDBHelper.getInstance().resp(SID,-1);
            PreparedStatement stat = this.getConn().prepareStatement(
                    "Update "+this.TABLE_NAME+" set SIsDeleted = true where "+this.PRIMARY_KEY+"=?"
            );
            stat.setInt(1,ID);
            stat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private SecretComment makeBrief(ResultSet set){
        SecretComment q = new SecretComment();
        try {
            q.setID(set.getInt("SID"));
           q.setSID(set.getInt("SSID"));
           q.setContext(set.getString("SContext"));
           q.setBriefContext(set.getString("SBriefContext"));
           q.setName(set.getString("SName"));
           q.setMood(set.getString("SMood"));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            return q;
        }
    }
    private SecretComment make(ResultSet set){
        return makeBrief(set);
    }
    public SecretComment getOneFull(int SID){
        SecretComment r = null;
        try {
            PreparedStatement stat = this.getConn().prepareStatement(
                    "Select * From "+this.TABLE_NAME+" Where SID = ?"
            );
            stat.setInt(1,SID);
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
    public ArrayList<SecretComment> getAllBrief(int SSID){
        ArrayList<SecretComment> res = new ArrayList<>();
        try {
            PreparedStatement stat = this.getConn().prepareStatement(
                    "Select SID,SBriefContext,SContext,SSID,SName,SMood From "+this.TABLE_NAME+" Where SSID = ?&&SIsDeleted=false"
            );
            stat.setInt(1,SSID);
            ResultSet set = stat.executeQuery();
            while (set.next()){
                res.add(makeBrief(set));
            }
//            Collections.reverse(res);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            return res;
        }
    }
    public static String getBrief(String context){
        if (context.length()<MAX_LENGTH){
            return context;
        }else{
            return context.substring(0,MAX_LENGTH)+"...";
        }
    }
}



