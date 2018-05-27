package main.http;

import main.common.Clap;
import main.data.ClapDBHelper;
import main.data.ResponseDBHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WriteClapServlet extends BaseServlet {

    void work(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int RID = Integer.parseInt(req.getParameter("RID"));
        String WXID = req.getParameter("WXID");
        Clap c = new Clap();
        c.setRID(RID);
        c.setWXID(WXID);
        int delta =1;
        if (ClapDBHelper.getInstance().claped(c)){
            delta =-1;
        }
        ResponseDBHelper.getInstance().clap(RID,delta);
        ClapDBHelper.getInstance().clap(c);

    }
}
