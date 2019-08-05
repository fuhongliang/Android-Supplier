package cn.ifhu.supplier.model.bean;

import java.util.List;

/**
 * 提现
 * @author fuhongliang
 */
public class WithDrawBean {
    /**
     * data : []
     * balance : 0.00
     * total_amount : 0
     */

    public String balance;
    public int total_amount;
    public List<ListBean> data;



    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public List<ListBean> getData() {
        return data;
    }

    public void setData(List<ListBean> data) {
        this.data = data;
    }

    public static class ListBean{
        String amount;
        String payment_state;
        long add_time;
        String bank_no;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPayment_state() {
            return payment_state;
        }

        public void setPayment_state(String payment_state) {
            this.payment_state = payment_state;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public String getBank_no() {
            return bank_no;
        }

        public void setBank_no(String bank_no) {
            this.bank_no = bank_no;
        }
    }

}
