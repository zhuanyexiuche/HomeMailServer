package main.http;

import main.common.Question;
import main.data.QuestionDBHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class WriteQuestionServlet extends BaseServlet {
    @Override
    void work(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String topic = req.getParameter("topic");
        String context = req.getParameter("context");
        Question q = new Question();
        q.setClapCount(0);
        q.setContext(context);
        q.setRespCount(0);
        q.setTopic(topic);
        q.setDeleted(false);
        QuestionDBHelper.getInstance().add(q);
        Writer writer = resp.getWriter();
        writer.write("success");
        writer.flush();
    }
}
