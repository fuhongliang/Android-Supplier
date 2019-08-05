package cn.ifhu.supplier.model.newbean.post;

import static cn.ifhu.supplier.utils.MchInfoLogic.getMchAccessToken;
import static cn.ifhu.supplier.utils.MchInfoLogic.getMchId;

/**
 * 请求的基类bean
 */
public class BasePostBean {

    private int is_debug = 1;

    private String access_token = getMchAccessToken();

    private int mch_id = getMchId();

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getMch_id() {
        return mch_id;
    }

    public void setMch_id(int mch_id) {
        this.mch_id = mch_id;
    }
}
