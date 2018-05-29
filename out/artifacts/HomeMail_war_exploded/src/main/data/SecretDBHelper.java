package main.data;

import main.common.Secret;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

/*
1:SID int 2:SContext String 3:SBriefContext String 4:SRespCount int 5:SIsDeleted boolean
6：SName String 7:SMood String
 */
public class SecretDBHelper extends DBHelper {
    public static final int MAX_LENGTH = 24;
    public static SecretDBHelper getInstance(){return instance;}
    private static SecretDBHelper instance = new SecretDBHelper();
    private SecretDBHelper(){
        super("Secret","SID",7);
    }
    public static ArrayList<String> nameList = SecretCommentDBHelper.nameList;
    public static ArrayList<String> moodList = SecretCommentDBHelper.moodList;
    public void add(Secret s){
        s.setName(nameList.get((int)Math.random()*nameList.size()));
        s.setMood(moodList.get((int)Math.random()*moodList.size()));
        PreparedStatement stat = this.getInsertStat();
        try {
            stat.setNull(1, Types.INTEGER);
            stat.setString(2,s.getContext());
            stat.setString(3,s.getBriefContext());
            stat.setInt(4,0);
            stat.setBoolean(5,false);
            stat.setString(6,s.getName());
            stat.setString(7,s.getMood());
            stat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Secret makeBrief(ResultSet set){
        Secret s = new Secret();
        try {
            s.setID(set.getInt("SID"));
            s.setBriefContext(set.getString("SBriefContext"));
            s.setRespCount(set.getInt("SRespCount"));
            s.setName(set.getString("SName"));
            s.setMood(set.getString("SMood"));
//            s.setDeleted(set.getBoolean("SIsDeleted"));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            return s;
        }
    }
    public Secret make(ResultSet set){
        Secret s = makeBrief(set);
        try {
            s.setContext(set.getString("SContext"));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            return s;
        }
    }
    public ArrayList<Secret> getAllBrief(){
        ArrayList<Secret> res = new ArrayList<>();
        try {
            PreparedStatement stat = this.getConn().prepareStatement(
                    "Select * From "+this.TABLE_NAME+" Where SIsDeleted =false"
            );
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
    public Secret getOneFull(int ID){
        Secret s =null;
        try {
            PreparedStatement stat = this.getConn().prepareStatement(
                    "Select * From "+this.TABLE_NAME+" Where SID =?"
            );
            stat.setInt(1,ID);
            ResultSet set = stat.executeQuery();
            while (set.next()){
                s = make(set);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            return s;
        }
    }
    public void resp(int ID,int delta){
        Secret s = getOneFull(ID);
        try {
            PreparedStatement stat = this.getConn().prepareStatement(
                    "Update "+this.TABLE_NAME+" Set SRespCount =? Where SID =?"
            );
            stat.setInt(1,s.getRespCount()+delta);
            stat.setInt(2,ID);
            stat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(int ID){
        try {
            PreparedStatement stat = this.getConn().prepareStatement(
                    "Update "+this.TABLE_NAME+" Set SIsDeleted = true Where SID =?"
            );
            stat.setInt(1,ID);
            stat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String getBrief(String context){
        if (context.length()<24){
            return context;
        }else{
            return context.substring(0,MAX_LENGTH)+"...";
        }
    }
}
