package cn.ifhu.supplier.model.bean;

import java.util.List;

public class NoticeBean {


    private List<MsgBean> msg;

    public List<MsgBean> getMsg() {
        return msg;
    }

    public void setMsg(List<MsgBean> msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        private String msg_content;
        private long addtime;
        private String msg_type;

        public String getMsg_type() {
            return msg_type;
        }

        public void setMsg_type(String msg_type) {
            this.msg_type = msg_type;
        }

        public String getMsg_content() {
            return msg_content;
        }

        public void setMsg_content(String msg_content) {
            this.msg_content = msg_content;
        }

        public long getAddtime() {
            return addtime;
        }

        public void setAddtime(long addtime) {
            this.addtime = addtime;
        }
    }
}
