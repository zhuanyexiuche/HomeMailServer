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
        /*
        Question q = new Question();
        q.setClapCount(0);
        q.setContext("如题");
        q.setDeleted(false);
        q.setRespCount(0);
        q.setTopic("为什么大家都上学了，我还在家里玩耍");
        QuestionDBHelper.getInstance().add(q);
        */
        /*
        ArrayList<Question>list = QuestionDBHelper.getInstance().getAllBrief();
        Response r = new Response();
        r.setClapCount(10);
        r.setCommentCount(20);
        r.setContent("兹慈");
        r.setDeleted(false);
        r.setNiMing(false);
        r.setWXID("calabash_boy");
        r.setBriefContent("兹慈");
        for (Question que:list){
            r.setQID(que.getID());
            ResponseDBHelper.getInstance().add(r);
        }
        */
    }
}
