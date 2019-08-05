package cn.ifhu.supplier.model.bean;

/**
 * @author fuhongliang
 */
public class DiscountBean {

    /**
     * xianshi_name : 古典诗歌的法国
     * start_time : 0
     * end_time : 0
     * state : 1
     */
    private int xianshi_id;
    private String xianshi_name;
    private long start_time;
    private long end_time;
    private int state;
    private int lower_limit;

    public int getXianshi_id() {
        return xianshi_id;
    }

    public void setXianshi_id(int xianshi_id) {
        this.xianshi_id = xianshi_id;
    }

    public int getLower_limit() {
        return lower_limit;
    }

    public void setLower_limit(int lower_limit) {
        this.lower_limit = lower_limit;
    }

    public String getXianshi_name() {
        return xianshi_name;
    }

    public void setXianshi_name(String xianshi_name) {
        this.xianshi_name = xianshi_name;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
