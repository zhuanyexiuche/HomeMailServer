package main.data;

import main.common.Question;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class QuestionDBHelper extends DBHelper {
    /*
    1:QID int (auto) 2:QTopic String 3:QContext String 4:QRespCount int 5:QClapCount int
    6:QIsDeleted boolean
     */
    private static QuestionDBHelper instance = new QuestionDBHelper();
    public static QuestionDBHelper getInstance(){return instance;}
    private QuestionDBHelper(){
        super("Question","QID",6);
    }
    public void add(Question q){
        PreparedStatement stat = this.getInsertStat();
        try {
            stat.setNull(1, Types.INTEGER);
            stat.setString(2,q.getTopic());
            stat.setString(3,q.getContext());
            stat.setInt(4,q.getRespCount());
            stat.setInt(5,q.getClapCount());
            stat.setBoolean(6,q.isDeleted());
            stat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(int ID){
        try {
            PreparedStatement stat = this.getConn().prepareStatement(
                    "Update "+this.TABLE_NAME+" set QIsDeleted = true where "+this.PRIMARY_KEY+"="+ID
            );
            stat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Question makeBrief(ResultSet set){
        Question q = new Question();
        try {
            q.setID(set.getInt("QID"));
            q.setTopic(set.getString("QTopic"));
            q.setRespCount(set.getInt("QRespCount"));
            q.setClapCount(set.getInt("QClapCount"));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            return q;
        }
    }
    private Question make(ResultSet set){
        Question q = makeBrief(set);
        try {
            q.setContext(set.getString("QContext"));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            return q;
        }
    }
    public Question getOneFull(int ID){
        Question q=null;
        try {
            PreparedStatement stat = this.getConn().prepareStatement(
                    "Select * From "+this.TABLE_NAME+" Where QID = ?"
            );
            stat.setInt(1,ID);
            ResultSet set = stat.executeQuery();
            while (set.next()){
                q = make(set);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            return q;
        }
    }
    public ArrayList<Question> getAllBrief(){
        ArrayList<Question> res = new ArrayList<>();
        try {
            PreparedStatement stat = this.getConn().prepareStatement(
                    "Select QID,QTopic,QRespCount,QClapCount From "+this.TABLE_NAME+" where QIsDeleted = false"
            );
            ResultSet set = stat.executeQuery();
            while (set.next()){
                res.add(makeBrief(set));
            }
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    public void addResp(int QID,int delta){
        int nowCount=-1;
        try {
            PreparedStatement stat1 = this.getConn().prepareStatement(
                    "Select QRespCount From "+this.TABLE_NAME+" Where QID =?"
            );
            stat1.setInt(1,QID);
            ResultSet set = stat1.executeQuery();
            while (set.next()){
                nowCount=set.getInt("QRespCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(nowCount==-1)return;
        try {
            PreparedStatement stat2 = this.getConn().prepareStatement(
                    "Update "+this.TABLE_NAME+" set QRespCount =? Where QID =?"
            );
            stat2.setInt(1,nowCount+delta);
            stat2.setInt(2,QID);
            stat2.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void clap(int QID,int delta){
        int nowCount=-1;
        try {
            PreparedStatement stat1 = this.getConn().prepareStatement(
                    "Select QClapCount From "+this.TABLE_NAME+" Where QID =?"
            );
            stat1.setInt(1,QID);
            ResultSet set = stat1.executeQuery();
            while (set.next()){
                nowCount=set.getInt("QClapCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(nowCount==-1)return;
        try {
            PreparedStatement stat2 = this.getConn().prepareStatement(
                    "Update "+this.TABLE_NAME+" set QClapCount =? Where QID =?"
            );
            stat2.setInt(1,nowCount+delta);
            stat2.setInt(2,QID);
            stat2.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void edit(int QID,String context){
        try {
            PreparedStatement stat = this.getConn().prepareStatement(
                    "Update "+this.TABLE_NAME+" Set QContext =? Where QID = ?"
            );
            stat.setString(1,context);
            stat.setInt(2,QID);
            stat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
