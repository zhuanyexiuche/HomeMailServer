package main.http;

import main.common.Secret;
import main.data.SecretDBHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class ReadSecretServlet extends BaseServlet {
    @Override
    void work(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Writer writer = resp.getWriter();
        boolean brief = req.getParameter("brief").equals("true");
        boolean search = req.getParameter("search")!=null;
        if (brief){
            ArrayList<Secret> list=null;
            if (search){
                /*
                String word = req.getParameter("keyWord");
                list = SecretDBHelper.getInstance().getAllBrief(word);
                */
            }else{
                list = SecretDBHelper.getInstance().getAllBrief();
            }
            String cont = "[";
            for (Secret q:list){
                cont+="{";
                cont+="\"ID\":"+q.getID()+",";
                cont+="\"briefContext\":"+"\""+q.getBriefContext()+"\""+",";
                cont+="\"respCount\":"+q.getRespCount();
                cont+="},";
            }
            if (list.size()!=0)
                cont = cont.substring(0,cont.length()-1);
            cont+="]";
            writer.write(cont);
            writer.flush();

        }else{
            int ID = Integer.parseInt(req.getParameter("QID"));
            Secret q = SecretDBHelper.getInstance().getOneFull(ID);
            String cont="{";
            cont+="\"ID\":"+q.getID()+",";
            cont+="\"context\":"+"\""+q.getContext()+"\""+",";
            cont+="\"briefContext\":"+"\""+q.getBriefContext()+"\""+",";
            cont+="\"respCount\":"+q.getRespCount();
            cont+="}";
            writer.write(cont);
            writer.flush();
        }

    }
}
