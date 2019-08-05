package cn.ifhu.supplier.model.newbean.data;

import java.util.List;

public class WithdrawalsRecordDataBean {

    private List<CashBean> cash;

    public List<CashBean> getCash() {
        return cash;
    }

    public void setCash(List<CashBean> cash) {
        this.cash = cash;
    }

    public static class CashBean {
        /**
         * id : 1
         * money : 10.00
         * status : 0
         * addtime : 1562553344
         */

        private int id;
        private String money;
        private int status;
        private int addtime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }
    }
}
