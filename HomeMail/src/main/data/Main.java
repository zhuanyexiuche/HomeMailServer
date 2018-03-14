package main.data;

import main.common.Question;
import main.common.Response;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Response r = new Response();
        r.setWXID("test");
        r.setWXNickName("\uD83D\uDCAF");
        r.setAvatarUrl("https://wx.qlogo.cn/mmopen/vi_32/ed8NV5iaJqHkDay91hxeWaMVE1Dm8kh0C63gwaRaNcMcPdTUMnicULAj3AFV8LgF766phwSaQz9mFxb5vibYqTLHw/0");
        r.setBriefContent("test");
        r.setDeleted(false);
        r.setQID(8);
        r.setNiMing(false);
        r.setCommentCount(0);
        r.setClapCount(0);
        r.setContent("test");
        ResponseDBHelper.getInstance().add(r);

    }
}
