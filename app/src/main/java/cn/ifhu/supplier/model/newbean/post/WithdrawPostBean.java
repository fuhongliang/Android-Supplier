package cn.ifhu.supplier.model.newbean.post;

public class WithdrawPostBean extends BasePostBean {
    private double money;
    private int type;
    private TypeData type_data;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public TypeData getType_data() {
        return type_data;
    }

    public void setType_data(TypeData type_data) {
        this.type_data = type_data;
    }

    public static class TypeData{
        private String account;
        private String bank_name;
        private String nickname;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
