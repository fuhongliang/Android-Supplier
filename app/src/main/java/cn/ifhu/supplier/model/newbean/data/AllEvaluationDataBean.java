package cn.ifhu.supplier.model.newbean.data;

import java.util.List;

public class AllEvaluationDataBean {

    private List<CommentBean> comment;

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public static class CommentBean {
        /**
         * id : 7
         * order_detail_id : 147
         * score : 2.0
         * content : 还阔以
         * pic_list : ["",""]
         * is_hide : 1
         * addtime : 1563003298
         * reply_content : 你好
         * name : α
         * avatar : https://wx.qlogo.cn/mmopen/vi_32/6d2FEzo0bpqN8LoK3bn7Mj6Md0BfrLw2Jlf2AqFMWwISgujAby3xPiaciawFos3Hiav8CLiae70IR2O1VPPGnOxtqw/132
         */

        private int id;
        private int order_detail_id;
        private String score;
        private String content;
        private List<String> pic_list;
        private int is_hide;
        private int addtime;
        private String reply_content;
        private String name;
        private String avatar;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrder_detail_id() {
            return order_detail_id;
        }

        public void setOrder_detail_id(int order_detail_id) {
            this.order_detail_id = order_detail_id;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<String> getPic_list() {
            return pic_list;
        }

        public void setPic_list(List<String> pic_list) {
            this.pic_list = pic_list;
        }

        public int getIs_hide() {
            return is_hide;
        }

        public void setIs_hide(int is_hide) {
            this.is_hide = is_hide;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public String getReply_content() {
            return reply_content;
        }

        public void setReply_content(String reply_content) {
            this.reply_content = reply_content;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
