package main.http;

import main.common.Response;
import main.data.QuestionDBHelper;
import main.data.ResponseDBHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class WriteResponseServlet extends BaseServlet {
    @Override
    void work(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Writer writer = resp.getWriter();
        String deleteFlag =req.getParameter("delete");
        if (deleteFlag!=null){
            int RID = Integer.parseInt(req.getParameter("RID"));
            ResponseDBHelper.getInstance().delete(RID);
            System.out.println("delete");
        }else{
            int QID = Integer.parseInt(req.getParameter("QID"));
            System.out.println("add");
            String WXID = req.getParameter("WXID");
            String text = req.getParameter("text");
            Response r = new Response();
            r.setContent(text);
            r.setClapCount(0);
            r.setCommentCount(0);
            r.setNiMing(false);
            r.setQID(QID);
            r.setWXID(WXID);
            r.setDeleted(false);
            r.setBriefContent(text);
            ResponseDBHelper.getInstance().add(r);
            QuestionDBHelper.getInstance().addResp(QID,1);
            writer.write("success");
            writer.flush();
        }
    }
}
