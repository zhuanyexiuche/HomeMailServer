package main.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class ReadOpenIDServlet extends BaseServlet {
    private final static String appid = "wxfd429d0571e87aa2";
    private final static String secret="94ec41e5faa8ec7f2b0866e222b4f2df";
    private final static String requestUrl="https://api.weixin.qq.com/sns/jscode2session";
    @Override
    void work(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        URL realUrl = new URL(requestUrl);
        URLConnection conn = realUrl.openConnection();
        conn.setRequestProperty("accept","*/*");
        conn.setRequestProperty("connection","Keep-Alive");
        conn.setRequestProperty("Accept-Charset","utf-8");
        conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        String code = req.getParameter("code");
        String param = "appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
        PrintWriter out = new PrintWriter(conn.getOutputStream());
        out.print(param);
        out.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        String ans ="";
        while ((line=in.readLine())!=null){
            ans+=line;
        }
        Writer writer = resp.getWriter();
        writer.write(ans);
        writer.flush();
    }
}
