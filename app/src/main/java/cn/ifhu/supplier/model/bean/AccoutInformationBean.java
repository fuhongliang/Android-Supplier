package cn.ifhu.supplier.model.bean;

public class AccoutInformationBean {

    /**
     * account_name : 三爷
     * account_number : 13121454125
     * bank_type : 中国邮政
     * bank_name : 开户支行
     * bank_address : 开户地区
     */

    private String account_name;
    private String account_number;
    private String bank_type;
    private String bank_name;
    private String bank_address;

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_address() {
        return bank_address;
    }

    public void setBank_address(String bank_address) {
        this.bank_address = bank_address;
    }
}
