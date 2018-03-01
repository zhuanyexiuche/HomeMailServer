package main.http;

import main.common.Response;
import main.data.ResponseDBHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class ReadResponseServlet extends BaseServlet {
    @Override
    void work(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Writer writer = resp.getWriter();
        boolean brief = "true".equals(req.getParameter("brief"));
        if (brief){
            int QID = Integer.parseInt(req.getParameter("QID"));
            ArrayList<Response> list = ResponseDBHelper.getInstance().getAllBrief(QID);
            String cont = "[";
            for (Response r : list){
                cont+="{";
                cont+="\"ID\":"+r.getID()+",";
                cont+="\"QID\":"+r.getQID()+",";
                cont+="\"WXID\":"+"\""+r.getWXID()+"\""+",";
                cont+="\"WXNickName\":"+"\""+r.getWXNickName()+"\""+",";
                cont+="\"avatarUrl\":"+"\""+r.getAvatarUrl()+"\""+",";
                cont+="\"NiMing\":"+r.isNiMing()+",";
                cont+="\"briefContent\":"+"\""+r.getBriefContent()+"\""+",";
                cont+="\"commentCount\":"+r.getCommentCount()+",";
                cont+="\"clapCount\":"+r.getClapCount();
                cont+="},";
            }
            if (list.size()!=0)cont = cont.substring(0,cont.length()-1);
            cont+="]";
            writer.write(cont);
            writer.flush();
        }else {
            int RID = Integer.parseInt(req.getParameter("RID"));
            Response r = ResponseDBHelper.getInstance().getOneFull(RID);
            String cont = "{";
            cont+="\"ID\":"+r.getID()+",";
            cont+="\"QID\":"+r.getQID()+",";
            cont+="\"WXID\":"+"\""+r.getWXID()+"\""+",";
            cont+="\"WXNickName\":"+"\""+r.getWXNickName()+"\""+",";
            cont+="\"avatarUrl\":"+"\""+r.getAvatarUrl()+"\""+",";
            cont+="\"NiMing\":"+r.isNiMing()+",";
            cont+="\"content\":"+"\""+r.getContent()+"\""+",";
            cont+="\"briefContent\":"+"\""+r.getBriefContent()+"\""+",";
            cont+="\"commentCount\":"+r.getCommentCount()+",";
            cont+="\"clapCount\":"+r.getClapCount();
            cont+="}";
            writer.write(cont);
            writer.flush();
        }
    }
}
