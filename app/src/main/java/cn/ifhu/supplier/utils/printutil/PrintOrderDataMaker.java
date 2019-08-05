package cn.ifhu.supplier.utils.printutil;

import android.content.Context;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.ifhu.supplier.model.bean.OrderBean;
import cn.ifhu.supplier.utils.OrderLogic;
import cn.ifhu.supplier.utils.UserLogic;


/**
 * 测试数据生成器
 *
 * @author liuguirong
 * @date 8/1/17
 */

public class PrintOrderDataMaker implements PrintDataMaker {


    private String qr;
    private int width;
    private int height;
    Context btService;
    private String remark = "微点筷客推出了餐厅管理系统，可用手机快速接单（来自客户的预订订单），进行订单管理、后厨管理等管理餐厅。";


    public PrintOrderDataMaker(Context btService, String qr, int width, int height) {
        this.qr = qr;
        this.width = width;
        this.height = height;
        this.btService = btService;
    }


    @Override
    public List<byte[]> getPrintData(int type) {
        ArrayList<byte[]> data = new ArrayList<>();

        try {
            OrderBean orderBean = OrderLogic.getPrintingOrder();
            //打印测试
            if (orderBean == null){
                PrinterWriter printer;
                printer = type == PrinterWriter58mm.TYPE_58 ? new PrinterWriter58mm(height, width) : new PrinterWriter80mm(height, width);
                printer.setAlignCenter();
                data.add(printer.getDataAndReset());
                printer.printLineFeed();
                printer.print(new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
                        .format(new Date(System.currentTimeMillis())));
                printer.printLineFeed();
                printer.printLine();

                printer.printLineFeed();
                printer.setAlignCenter();
                printer.setEmphasizedOn();
                printer.setFontSize(1);
                printer.print("今日单号#" + "38");

                printer.printLineFeed();
                printer.setFontSize(0);
                printer.setEmphasizedOff();
                printer.setAlignCenter();
                printer.print(UserLogic.getUser().getStore_name());
                printer.printLineFeed();

                printer.setAlignCenter();
                printer.setFontSize(1);
                printer.print("在线支付(已支付)");
                printer.printLineFeed();

                printer.setFontSize(0);
                printer.printLine();
                printer.printLineFeed();
                printer.setAlignLeft();
                printer.print("骑手电话: " + "188****0997");
                printer.printLineFeed();
                printer.print("期望送达时间: " + "立即送达");
                printer.printLineFeed();
                printer.print("下单时间：" + "2019-10-1 17：00");
                printer.printLineFeed();
                printer.print("订单号：20190504048484");
                printer.printLineFeed();
//菜单
                printer.printLine();
                printer.printLineFeed();

                printer.setAlignCenter();
                printer.print("菜品信息");
                printer.printLineFeed();
                printer.setAlignCenter();
                printer.printInOneLine("菜名", "数量", "单价", 0);
                printer.printLineFeed();
                for (int i = 0; i < 3; i++) {
                    printer.printInOneLine("干锅包菜", "X" + 3, "￥" + 30, 0);
                    printer.printLineFeed();
                }

                printer.printLineFeed();
                printer.printLine();

                printer.setAlignCenter();
                printer.print("其他");
                printer.printLineFeed();
                printer.setAlignLeft();
                printer.print("配送费: " + "5.00");
                printer.printLineFeed();
                printer.print("包装费: " + "2.00");
                printer.printLineFeed();
                printer.printLine();
//合计
                printer.printLineFeed();
                printer.setAlignRight();
                printer.print("合计：￥100");
                printer.printLineFeed();
                printer.printLine();
//地址
                printer.printLineFeed();
                printer.setAlignLeft();
                printer.setEmphasizedOn();
                printer.setFontSize(1);
                printer.print("新安三路28号海关大厦1610");
                printer.printLineFeed();
                printer.print("188266660997");
                printer.printLineFeed();
                printer.print("张(先生)");
                printer.printLineFeed();
                printer.setFontSize(0);
                printer.printLine();

//底部
                printer.printLineFeed();
                printer.setAlignCenter();
                printer.print("LINLINFA");
                printer.printLineFeed();
                printer.printLineFeed();
                printer.printLineFeed();
                printer.feedPaperCutPartial();
                data.add(printer.getDataAndClose());

            }else {
                PrinterWriter printer;
                printer = type == PrinterWriter58mm.TYPE_58 ? new PrinterWriter58mm(height, width) : new PrinterWriter80mm(height, width);
                printer.setAlignCenter();
                data.add(printer.getDataAndReset());
                printer.printLineFeed();
                printer.print(orderBean.getAdd_time());
                printer.printLineFeed();
                printer.printLine();

                printer.printLineFeed();
                printer.setAlignCenter();
                printer.setEmphasizedOn();
                printer.setFontSize(1);
                printer.print("今日单号#" + orderBean.getOrder_id());

                printer.printLineFeed();
                printer.setFontSize(0);
                printer.setEmphasizedOff();
                printer.setAlignCenter();
                printer.print(UserLogic.getUser().getStore_name());
                printer.printLineFeed();

                printer.setAlignCenter();
                printer.setFontSize(1);
                printer.print("在线支付(已支付)");
                printer.printLineFeed();

                printer.setFontSize(0);
                printer.printLine();
                printer.printLineFeed();
                printer.setAlignLeft();
                printer.print("骑手电话: " + "188****0997");
                printer.printLineFeed();
                printer.print("期望送达时间: " + "立即送达");
                printer.printLineFeed();
                printer.print("下单时间：" + orderBean.getAdd_time());
                printer.printLineFeed();
                printer.print("订单号："+orderBean.getOrder_sn());
                printer.printLineFeed();
//菜单
                printer.printLine();
                printer.printLineFeed();

                printer.setAlignCenter();
                printer.print("商品信息");
                printer.printLineFeed();
                printer.setAlignCenter();
                printer.printInOneLine("商品", "数量", "单价", 0);
                printer.printLineFeed();
                for (OrderBean.ExtendOrderGoodsBean orderGoodsBean: orderBean.getExtend_order_goods()) {
                    printer.printInOneLine(orderGoodsBean.getGoods_name(), "X" + orderGoodsBean.getGoods_num(), "￥" + orderGoodsBean.getGoods_price(), 0);
                    printer.printLineFeed();
                }

                printer.printLineFeed();
                printer.printLine();

                printer.setAlignCenter();
                printer.print("其他");
                printer.printLineFeed();
                printer.setAlignLeft();
                printer.print("配送费: " + "5.00");
                printer.printLineFeed();
                printer.print("包装费: " + "2.00");
                printer.printLineFeed();
                printer.printLine();
//合计
                printer.printLineFeed();
                printer.setAlignRight();
                printer.print("合计："+orderBean.getTotal_price());
                printer.printLineFeed();
                printer.printLine();
//地址
                printer.printLineFeed();
                printer.setAlignLeft();
                printer.setEmphasizedOn();
                printer.setFontSize(1);
                printer.print(orderBean.getExtend_order_common().getAddress());
                printer.printLineFeed();
                printer.print(orderBean.getExtend_order_common().getPhone());
                printer.printLineFeed();
                printer.print(orderBean.getExtend_order_common().getReciver_name());
                printer.printLineFeed();
                printer.setFontSize(0);
                printer.printLine();

//底部
                printer.printLineFeed();
                printer.setAlignCenter();
                printer.print("LINLINFA");
                printer.printLineFeed();
                printer.printLineFeed();
                printer.printLineFeed();
                printer.feedPaperCutPartial();
                data.add(printer.getDataAndClose());
            }

            return data;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


}
