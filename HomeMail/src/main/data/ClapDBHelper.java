package main.data;

import main.common.Clap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class ClapDBHelper extends DBHelper {
    /*
    1:CID int (auto) 2:CRID int 3:CWXID String 4:CIsDeleted
     */
    private static ClapDBHelper instance = new ClapDBHelper();
    public static ClapDBHelper getInstance(){return instance;}
    private ClapDBHelper() {
        super("Clap", "CID", 4);
    }
    public void add(Clap clap){
        PreparedStatement stat = this.getInsertStat();
        try {
            stat.setNull(1, Types.INTEGER);
            stat.setInt(2, clap.getRID());
            stat.setString(3, clap.getWXID());
            stat.setBoolean(4, false);
            stat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
    点赞以及取消
     */
    public void clap(Clap clap){
        int ID = findClap(clap);
        if (ID==-1) {
            add(clap);
        }else{
            try {
                PreparedStatement stat = this.getConn().prepareStatement(
                        "Select CisDeleted From "+this.TABLE_NAME+" Where CID =?"
                );
                stat.setInt(1,ID);
                boolean isDeleted=false;
                ResultSet set = stat.executeQuery();
                while (set.next()){
                    isDeleted = set.getBoolean("CIsDeleted");
                }
                PreparedStatement stat2 = this.getConn().prepareStatement(
                        "Update "+this.TABLE_NAME+" Set CIsDeleted =? Where CID =?"
                );
                stat2.setBoolean(1,!isDeleted);
                stat2.setInt(2,ID);
                stat2.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public int findClap(Clap c){
        try {
            PreparedStatement stat = this.getConn().prepareStatement(
                    "Select CID From "+this.TABLE_NAME+" Where CRID=?&&CWXID=?"
            );
            stat.setInt(1,c.getRID());
            stat.setString(2,c.getWXID());
            ResultSet set = stat.executeQuery();
            while (set.next()){
                return set.getInt("CID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public boolean claped(Clap clap){
        try {
            PreparedStatement stat = this.getConn().prepareStatement(
                    "Select CID From "+this.TABLE_NAME+" Where CRID=?&&CWXID=?&&CIsDeleted=false"
            );
            stat.setInt(1,clap.getRID());
            stat.setString(2,clap.getWXID());
            ResultSet set = stat.executeQuery();
            while (set.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
