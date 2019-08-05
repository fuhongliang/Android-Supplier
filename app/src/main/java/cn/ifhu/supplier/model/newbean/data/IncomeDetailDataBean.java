package cn.ifhu.supplier.model.newbean.data;

import java.util.List;

public class IncomeDetailDataBean {

    /**
     * accounts : [{"id":42,"price":"898.10","type":1,"desc":"订单（20190713153820449050）结算","addtime":1564390891},{"id":40,"price":"10948.04","type":1,"desc":"订单（20190719162414401589）结算","addtime":1564390537}]
     * balance : 51085.05
     */

    private String balance;
    private List<AccountsBean> accounts;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<AccountsBean> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountsBean> accounts) {
        this.accounts = accounts;
    }

    public static class AccountsBean {
        /**
         * id : 42
         * price : 898.10
         * type : 1
         * desc : 订单（20190713153820449050）结算
         * addtime : 1564390891
         */

        private int id;
        private String price;
        private int type;
        private String desc;
        private int addtime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }
    }
}
