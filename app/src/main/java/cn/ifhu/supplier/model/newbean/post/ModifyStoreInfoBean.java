package cn.ifhu.supplier.model.newbean.post;

public class ModifyStoreInfoBean extends BasePostBean {

    private String name;
    private String logo;
    private String realname;
    private String tel;
    private String service_tel;
    private String address;
    private String header_bg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getService_tel() {
        return service_tel;
    }

    public void setService_tel(String service_tel) {
        this.service_tel = service_tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeader_bg() {
        return header_bg;
    }

    public void setHeader_bg(String header_bg) {
        this.header_bg = header_bg;
    }
}
