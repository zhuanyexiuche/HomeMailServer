package main.http;

import main.common.SecretComment;
import main.data.SecretCommentDBHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WriteSecretCommentServlet extends BaseServlet{
    @Override
    void work(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean delete = req.getParameter("delete")!=null;
        if (delete){
            int SID = Integer.parseInt(req.getParameter("SID"));
            SecretCommentDBHelper.getInstance().delete(SID);
        }else{
            int SID = Integer.parseInt(req.getParameter("SID"));
            String context = req.getParameter("context");
            String briefContext = SecretCommentDBHelper.getBrief(context);
            SecretComment comment = new SecretComment();
            comment.setSID(SID);
            comment.setContext(context);
            comment.setBriefContext(briefContext);
            SecretCommentDBHelper.getInstance().add(comment);
        }
    }
}
