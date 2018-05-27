package main.http;

import main.common.Clap;
import main.data.ClapDBHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ReadClapServlet extends BaseServlet {
    void work(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int RID =Integer.parseInt(req.getParameter("RID"));
        String WXID = req.getParameter("WXID");
        Clap c = new Clap();
        c.setRID(RID);
        c.setWXID(WXID);
        boolean result = ClapDBHelper.getInstance().claped(c);
        Writer writer = resp.getWriter();
        writer.write(result?"true":"false");
        writer.flush();
    }
}
