package main.http;

import main.common.Response;
import main.data.QuestionDBHelper;
import main.data.ResponseDBHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class WriteResponseServlet extends BaseServlet {
    private static final String AIM_ADD = "add";
    private static final String AIM_CLAP = "clap";
    @Override
    void work(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String aim = req.getParameter("aim");
        Writer writer = resp.getWriter();
        if (AIM_CLAP.equals(aim)){
            int RID = Integer.parseInt(req.getParameter("RID"));
            ResponseDBHelper.getInstance().clap(RID);
            writer.write("success");
            writer.flush();
        }else if (AIM_ADD.equals(aim)){
            String text = req.getParameter("text");
            String WXID = req.getParameter("WXID");
            int QID = Integer.parseInt(req.getParameter("QID"));
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
            QuestionDBHelper.getInstance().addResp(QID);
            writer.write("success");
            writer.flush();
        }
    }
}
