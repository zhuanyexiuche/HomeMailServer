package main.http;

import main.common.Secret;
import main.data.SecretDBHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class WriteSecretServlet extends BaseServlet {

    void work(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String context = req.getParameter("context").replace("\n","\\n");
        Secret q = new Secret();
        q.setContext(context);
        q.setRespCount(0);
        q.setDeleted(false);
        q.setBriefContext(SecretDBHelper.getBrief(context));
        SecretDBHelper.getInstance().add(q);
        Writer writer = resp.getWriter();
        writer.write("success");
        writer.flush();
    }
}
