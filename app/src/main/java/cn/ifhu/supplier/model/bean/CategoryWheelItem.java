package cn.ifhu.supplier.model.bean;


import jsc.kit.wheel.base.WheelItem;

public class CategoryWheelItem extends WheelItem {
    int id;

    public CategoryWheelItem(String label) {
        super(label);
    }

    public CategoryWheelItem(String label ,int id) {
        super(label);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
