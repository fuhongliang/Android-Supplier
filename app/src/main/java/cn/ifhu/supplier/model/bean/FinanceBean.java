package cn.ifhu.supplier.model.bean;

import java.util.List;

public class FinanceBean {


    /**
     * y_jiesuan : 0
     * d_jiesuan : 0
     * list : [{"amount":-240,"state":"1","ob_no":2019042,"os_month":201904},{"amount":0,"state":"1","ob_no":2019032,"os_month":201903},{"amount":0,"state":"1","ob_no":2019022,"os_month":201902}]
     * account : {"bank_type":"工商银行储蓄卡","account_number":"28"}
     */

    private double y_jiesuan;
    private double d_jiesuan;
    private AccountBean account;
    private List<ListBean> list;
    /**
     * addtime : 2019-05-06 11:52:37
     * msg : 可为免费空位欺负你委屈而烦恼为妇女
     */

    private String addtime;
    private String msg;

    public double getY_jiesuan() {
        return y_jiesuan;
    }

    public void setY_jiesuan(int y_jiesuan) {
        this.y_jiesuan = y_jiesuan;
    }

    public double getD_jiesuan() {
        return d_jiesuan;
    }

    public void setD_jiesuan(int d_jiesuan) {
        this.d_jiesuan = d_jiesuan;
    }

    public AccountBean getAccount() {
        return account;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class AccountBean {
        /**
         * bank_type : 工商银行储蓄卡
         * account_number : 28
         */

        private String bank_type;
        private String account_number;

        public String getBank_type() {
            return bank_type;
        }

        public void setBank_type(String bank_type) {
            this.bank_type = bank_type;
        }

        public String getAccount_number() {
            return account_number;
        }

        public void setAccount_number(String account_number) {
            this.account_number = account_number;
        }
    }

    public static class ListBean {
        /**
         * amount : -240
         * state : 1
         * ob_no : 2019042
         * os_month : 201904
         */

        private double amount;
        private String state;
        private int ob_no;
        private int os_month;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getOb_no() {
            return ob_no;
        }

        public void setOb_no(int ob_no) {
            this.ob_no = ob_no;
        }

        public int getOs_month() {
            return os_month;
        }

        public void setOs_month(int os_month) {
            this.os_month = os_month;
        }
    }


}
