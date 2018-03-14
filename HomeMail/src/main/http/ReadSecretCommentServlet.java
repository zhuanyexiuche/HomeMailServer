package main.http;

import main.common.SecretComment;
import main.data.SecretCommentDBHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class ReadSecretCommentServlet extends BaseServlet {

    void work(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean brief = "true".equals(req.getParameter("brief"));
        Writer writer = resp.getWriter();
        if (brief){
            int SID = Integer.parseInt(req.getParameter("SID"));
            ArrayList<SecretComment> list = SecretCommentDBHelper.getInstance().getAllBrief(SID);
            String cont = "[";
            for (SecretComment comment:list){
                cont+="{";
                cont+="\"ID\":"+comment.getID()+",";
                cont+="\"SID\":"+comment.getSID()+",";
                cont+="\"briefContext\":"+"\""+comment.getBriefContext()+"\""+",";
                cont+="\"context\":"+"\""+comment.getContext()+"\"";
                cont+="},";
            }
            if (list.size()!=0){
                cont = cont.substring(0,cont.length()-1)+"]";
            }else{
                cont+="]";
            }
            writer.write(cont);
            writer.flush();
        }else{
            int SID = Integer.parseInt(req.getParameter("SID"));
            SecretComment comment = SecretCommentDBHelper.getInstance().getOneFull(SID);
            String cont = "{";
            cont+="\"ID\":"+comment.getID()+",";
            cont+="\"SID\":"+comment.getSID()+",";
            cont+="\"briefContext\":"+"\""+comment.getBriefContext()+"\""+",";
            cont+="\"context\":"+"\""+comment.getContext()+"\"";
            cont+="}";
            writer.write(cont);
            writer.flush();
        }
    }
}
