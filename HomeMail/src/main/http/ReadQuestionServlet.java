package main.http;

import main.common.Question;
import main.data.QuestionDBHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class ReadQuestionServlet extends BaseServlet{
    @Override
    void work(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Writer writer = resp.getWriter();
        boolean brief = req.getParameter("brief").equals("true");
        if (brief){
            ArrayList<Question> list = QuestionDBHelper.getInstance().getAllBrief();
            String cont = "[";
            for (Question q:list){
                cont+="{";
                cont+="\"ID\":"+q.getID()+",";
                cont+="\"topic\":"+"\""+q.getTopic()+"\""+",";
//                cont+="\"context\":"+"\""+q.getContext()+"\""+",";
                cont+="\"respCount\":"+q.getRespCount()+",";
                cont+="\"clapCount\":"+q.getClapCount();
                cont+="},";
            }
            if (list.size()!=0)
                cont = cont.substring(0,cont.length()-1);
            cont+="]";
            writer.write(cont);
            writer.flush();
        }else{
            int ID = Integer.parseInt(req.getParameter("QID"));
            Question q = QuestionDBHelper.getInstance().getOneFull(ID);
            String cont="{";
            cont+="\"ID\":"+q.getID()+",";
            cont+="\"topic\":"+"\""+q.getTopic()+"\""+",";
            cont+="\"context\":"+"\""+q.getContext()+"\""+",";
            cont+="\"respCount\":"+q.getRespCount()+",";
            cont+="\"clapCount\":"+q.getClapCount();
            cont+="}";
            writer.write(cont);
            writer.flush();
        }

    }
}
