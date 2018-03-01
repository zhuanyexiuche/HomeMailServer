package main.test;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class OpenIdTest {
    /*
     	94ec41e5faa8ec7f2b0866e222b4f2df
     */
    public static void main(String[] args) throws IOException {
        URL realUrl = new URL("https://api.weixin.qq.com/sns/jscode2session");
        URLConnection conn = realUrl.openConnection();
        conn.setRequestProperty("accept","*/*");
        conn.setRequestProperty("connection","Keep-Alive");
        conn.setRequestProperty("Accept-Charset","utf-8");
        conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        String param = "appid=wxfd429d0571e87aa2&secret=94ec41e5faa8ec7f2b0866e222b4f2df&js_code=013sS4Vk0J4DRk1YsDUk0Q5ZUk0sS4VN&grant_type=authorization_code";
        PrintWriter out = new PrintWriter(conn.getOutputStream());
        out.print(param);
        out.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line=in.readLine())!=null){
            System.out.println(line);
        }
    }
}
