package main.data;

import main.common.Clap;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class ClapDBHelper extends DBHelper {
    /*
    1:CID int (auto) 2:CRID int 3:CWXID String 4:CisDeleted
     */
    private static ClapDBHelper instance = new ClapDBHelper();
    public static ClapDBHelper getInstance(){return instance;}
    private ClapDBHelper() {
        super("Clap", "CID", 3);
    }
    public void add(Clap clap){
        PreparedStatement stat = this.getInsertStat();
        try {
            stat.setNull(1, Types.INTEGER);
            stat.setInt(2,clap.getRID());
            stat.setString(3,clap.getWXID());
            stat.setBoolean(4,false);
            stat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
