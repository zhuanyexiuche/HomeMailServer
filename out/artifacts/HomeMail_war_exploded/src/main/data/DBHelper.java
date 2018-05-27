package main.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by DELL on 2017/11/25.
 */
public abstract class DBHelper {

    private static final String DBDRIVER = "com.mysql.jdbc.Driver";

    public static final String DBURL = "jdbc:mysql://47.100.32.199:3306/HomeMail";
    private static final String USER = "calabash";
    private static final String PASS = "Wang1997";
    private static final String USELESS = "show tables";
    static{
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        PreparedStatement stat = getConn().prepareStatement(USELESS);
                        stat.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(25200000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private  String INSERT_SQL;
    private String DELETE_SQL;
    private String SELECT_SQL;
    private String UPDATE_SQL;

    protected final String TABLE_NAME;
    private final int TOTCOLUMN;
    protected final String PRIMARY_KEY;

    private static Connection conn = null;
    //Statement
    private PreparedStatement INSERT_STAT=null;
    private PreparedStatement DELETE_STAT = null;
    private PreparedStatement SELECT_STAT = null;
    private PreparedStatement UPDATE_STAT = null;
    public DBHelper(String name,String primaryKey,int num){
        this.TABLE_NAME=name;
        this.TOTCOLUMN = num;
        this.PRIMARY_KEY = primaryKey;
        connect();
        prepareSQL();
    }


    protected String Date2String(LocalDate date){
        String res = "";
        res+= date.getYear()+"-";
        if(date.getMonthValue()<10)res+="0";
        res+=date.getMonthValue()+"-";
        if (date.getDayOfMonth()<10)res+="0";
        res+=date.getDayOfMonth();
        return res;
    }


    protected String DateTime2String (LocalDateTime time){
        String res = "";
        res+= time.getYear()+"-";
        if (time.getMonthValue()<10)res+="0";
        res+= time.getMonthValue()+"-";
        if (time.getDayOfMonth()<10)res+="0";
        res+= time.getDayOfMonth()+"-";
        if (time.getHour()<10)res+="0";
        res+= time.getHour()+"-";
        if (time.getMinute()<10)res+="0";
        res+= time.getMinute()+"-";
        if (time.getSecond()<10)res+="0";
        res+= time.getSecond()+"-";
        String nano = "0000000000000000"+time.getNano();
        res+= nano.substring(nano.length()-9,nano.length());
//        System.out.println(res);
        return res;
    }


    protected LocalDate String2Date(String s){
        String[] temp = s.split("-");
        LocalDate res = LocalDate.of(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),Integer.parseInt(temp[2]));
        return res;
    }


    protected LocalDateTime String2DateTime(String s) {
        String[] temp = s.split("-");
        int[] temp2 = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            temp2[i] = Integer.parseInt(temp[i]);
        }
        LocalDateTime res = LocalDateTime.of(temp2[0], temp2[1], temp2[2], temp2[3], temp2[4], temp2[5], temp2[6]);
        return res;
    }

    private void prepareSQL(){
        INSERT_SQL="INSERT INTO "+TABLE_NAME+" VALUE (";
        for (int i=0;i<TOTCOLUMN-1;i++){
            INSERT_SQL+="?,";
        }
        INSERT_SQL+="?);";
        System.out.println(INSERT_SQL);
        DELETE_SQL = "DELETE FROM "+TABLE_NAME+" where "+PRIMARY_KEY+" =?;";
        System.out.println(DELETE_SQL);
        SELECT_SQL = "SELECT * FROM "+TABLE_NAME+" where "+PRIMARY_KEY+" =? ;";
        System.out.println(SELECT_SQL);
        UPDATE_SQL = "UPDATE "+TABLE_NAME+" set ? = ? where "+PRIMARY_KEY+"==?;";
    }
    protected static Connection getConn(){if (conn==null)connect();return conn;}
    private PreparedStatement getStat(PreparedStatement stat,String sql){
        if (conn==null){
            connect();
        }
        if (stat==null){
            try {
                stat = conn.prepareStatement(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            stat.clearParameters();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stat;
    }
    protected PreparedStatement getSelectStat(){
        PreparedStatement stat = null;
        try {
            stat = getConn().prepareStatement(SELECT_SQL);
            stat.clearParameters();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            return stat;
        }
    }
    public PreparedStatement getInsertStat(){
        return INSERT_STAT = getStat(INSERT_STAT,INSERT_SQL);
    }
    public PreparedStatement getDeleteStatement(){
        return DELETE_STAT = getStat(DELETE_STAT,DELETE_SQL);
    }
    private static void connect(){
        try{
            Class.forName(DBDRIVER);
            conn = DriverManager.getConnection(DBURL,USER,PASS);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
