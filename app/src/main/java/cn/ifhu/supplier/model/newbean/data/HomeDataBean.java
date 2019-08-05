package cn.ifhu.supplier.model.newbean.data;

import java.util.List;

/**
 * Created by KevinFu on 2019-07-16.
 * Copyright (c) 2019 KevinFu
 */
public class HomeDataBean {

    /**
     * store : {"sell_statistics_today":{"order_num":0,"order_price":"29.11"},"sell_statistics_yesterday":{"order_num":0,"order_price":"0.00"},"sell_statistics_sevendays":{"order_num":10,"order_price":"16,812.11"},"sell_statistics_thirtydays":{"order_num":23,"order_price":"16,833.55"},"sell_statistics_total":{"order_num":23,"order_price":"18,804.54"},"order_statistics":{"wait_pay_orders":8,"wait_send_orders":26,"refunding_orders":2},"order_price_chart":{"today":{"time":["00:00","04:00","08:00","12:00","16:00","20:00","24:00"],"income":["0.00","0.00","29.11","0.00","0.00","0.00","0.00"]},"yesterday":{"time":["00:00","04:00","08:00","12:00","16:00","20:00","24:00"],"income":["0.00","0.00","0.00","0.00","0.00","0.00","0.00"]},"sevenday":{"time":["07-09","07-10","07-11","07-12","07-13","07-14","07-15"],"income":["0.00","0.00","0.00","0.00","16,783.00","0.00","29.11"]},"thirtyday":{"time":["06-15","06-20","06-25","06-30","07-05","07-10","07-15"],"income":["0.00","0.00","0.00","0.00","0.00","0.00","29.11"]}}}
     */

    private StoreBean store;

    public StoreBean getStore() {
        return store;
    }

    public void setStore(StoreBean store) {
        this.store = store;
    }

    public static class StoreBean {
        /**
         * sell_statistics_today : {"order_num":0,"order_price":"29.11"}
         * sell_statistics_yesterday : {"order_num":0,"order_price":"0.00"}
         * sell_statistics_sevendays : {"order_num":10,"order_price":"16,812.11"}
         * sell_statistics_thirtydays : {"order_num":23,"order_price":"16,833.55"}
         * sell_statistics_total : {"order_num":23,"order_price":"18,804.54"}
         * order_statistics : {"wait_pay_orders":8,"wait_send_orders":26,"refunding_orders":2}
         * order_price_chart : {"today":{"time":["00:00","04:00","08:00","12:00","16:00","20:00","24:00"],"income":["0.00","0.00","29.11","0.00","0.00","0.00","0.00"]},"yesterday":{"time":["00:00","04:00","08:00","12:00","16:00","20:00","24:00"],"income":["0.00","0.00","0.00","0.00","0.00","0.00","0.00"]},"sevenday":{"time":["07-09","07-10","07-11","07-12","07-13","07-14","07-15"],"income":["0.00","0.00","0.00","0.00","16,783.00","0.00","29.11"]},"thirtyday":{"time":["06-15","06-20","06-25","06-30","07-05","07-10","07-15"],"income":["0.00","0.00","0.00","0.00","0.00","0.00","29.11"]}}
         */

        private SellStatisticsTodayBean sell_statistics_today;
        private SellStatisticsYesterdayBean sell_statistics_yesterday;
        private SellStatisticsSevendaysBean sell_statistics_sevendays;
        private SellStatisticsThirtydaysBean sell_statistics_thirtydays;
        private SellStatisticsTotalBean sell_statistics_total;
        private OrderStatisticsBean order_statistics;
        private OrderPriceChartBean order_price_chart;

        public SellStatisticsTodayBean getSell_statistics_today() {
            return sell_statistics_today;
        }

        public void setSell_statistics_today(SellStatisticsTodayBean sell_statistics_today) {
            this.sell_statistics_today = sell_statistics_today;
        }

        public SellStatisticsYesterdayBean getSell_statistics_yesterday() {
            return sell_statistics_yesterday;
        }

        public void setSell_statistics_yesterday(SellStatisticsYesterdayBean sell_statistics_yesterday) {
            this.sell_statistics_yesterday = sell_statistics_yesterday;
        }

        public SellStatisticsSevendaysBean getSell_statistics_sevendays() {
            return sell_statistics_sevendays;
        }

        public void setSell_statistics_sevendays(SellStatisticsSevendaysBean sell_statistics_sevendays) {
            this.sell_statistics_sevendays = sell_statistics_sevendays;
        }

        public SellStatisticsThirtydaysBean getSell_statistics_thirtydays() {
            return sell_statistics_thirtydays;
        }

        public void setSell_statistics_thirtydays(SellStatisticsThirtydaysBean sell_statistics_thirtydays) {
            this.sell_statistics_thirtydays = sell_statistics_thirtydays;
        }

        public SellStatisticsTotalBean getSell_statistics_total() {
            return sell_statistics_total;
        }

        public void setSell_statistics_total(SellStatisticsTotalBean sell_statistics_total) {
            this.sell_statistics_total = sell_statistics_total;
        }

        public OrderStatisticsBean getOrder_statistics() {
            return order_statistics;
        }

        public void setOrder_statistics(OrderStatisticsBean order_statistics) {
            this.order_statistics = order_statistics;
        }

        public OrderPriceChartBean getOrder_price_chart() {
            return order_price_chart;
        }

        public void setOrder_price_chart(OrderPriceChartBean order_price_chart) {
            this.order_price_chart = order_price_chart;
        }

        public static class SellStatisticsTodayBean {
            /**
             * order_num : 0
             * order_price : 29.11
             */

            private int order_num = 0;
            private String order_price = "0.0";

            public int getOrder_num() {
                return order_num;
            }

            public void setOrder_num(int order_num) {
                this.order_num = order_num;
            }

            public String getOrder_price() {
                return order_price;
            }

            public void setOrder_price(String order_price) {
                this.order_price = order_price;
            }
        }

        public static class SellStatisticsYesterdayBean {
            /**
             * order_num : 0
             * order_price : 0.00
             */

            private int order_num = 0;
            private String order_price = "0.0";

            public int getOrder_num() {
                return order_num;
            }

            public void setOrder_num(int order_num) {
                this.order_num = order_num;
            }

            public String getOrder_price() {
                return order_price;
            }

            public void setOrder_price(String order_price) {
                this.order_price = order_price;
            }
        }

        public static class SellStatisticsSevendaysBean {
            /**
             * order_num : 10
             * order_price : 16,812.11
             */

            private int order_num = 0;
            private String order_price = "0.0";

            public int getOrder_num() {
                return order_num;
            }

            public void setOrder_num(int order_num) {
                this.order_num = order_num;
            }

            public String getOrder_price() {
                return order_price;
            }

            public void setOrder_price(String order_price) {
                this.order_price = order_price;
            }
        }

        public static class SellStatisticsThirtydaysBean {
            /**
             * order_num : 23
             * order_price : 16,833.55
             */

            private int order_num = 0;
            private String order_price = "0.0";

            public int getOrder_num() {
                return order_num;
            }

            public void setOrder_num(int order_num) {
                this.order_num = order_num;
            }

            public String getOrder_price() {
                return order_price;
            }

            public void setOrder_price(String order_price) {
                this.order_price = order_price;
            }
        }

        public static class SellStatisticsTotalBean {
            /**
             * order_num : 23
             * order_price : 18,804.54
             */

            private int order_num;
            private String order_price;

            public int getOrder_num() {
                return order_num;
            }

            public void setOrder_num(int order_num) {
                this.order_num = order_num;
            }

            public String getOrder_price() {
                return order_price;
            }

            public void setOrder_price(String order_price) {
                this.order_price = order_price;
            }
        }

        public static class OrderStatisticsBean {
            /**
             * wait_pay_orders : 8
             * wait_send_orders : 26
             * refunding_orders : 2
             */

            private int wait_pay_orders;
            private int wait_send_orders;
            private int refunding_orders;

            public int getWait_pay_orders() {
                return wait_pay_orders;
            }

            public void setWait_pay_orders(int wait_pay_orders) {
                this.wait_pay_orders = wait_pay_orders;
            }

            public int getWait_send_orders() {
                return wait_send_orders;
            }

            public void setWait_send_orders(int wait_send_orders) {
                this.wait_send_orders = wait_send_orders;
            }

            public int getRefunding_orders() {
                return refunding_orders;
            }

            public void setRefunding_orders(int refunding_orders) {
                this.refunding_orders = refunding_orders;
            }
        }

        public static class OrderPriceChartBean {
            /**
             * today : {"time":["00:00","04:00","08:00","12:00","16:00","20:00","24:00"],"income":["0.00","0.00","29.11","0.00","0.00","0.00","0.00"]}
             * yesterday : {"time":["00:00","04:00","08:00","12:00","16:00","20:00","24:00"],"income":["0.00","0.00","0.00","0.00","0.00","0.00","0.00"]}
             * sevenday : {"time":["07-09","07-10","07-11","07-12","07-13","07-14","07-15"],"income":["0.00","0.00","0.00","0.00","16,783.00","0.00","29.11"]}
             * thirtyday : {"time":["06-15","06-20","06-25","06-30","07-05","07-10","07-15"],"income":["0.00","0.00","0.00","0.00","0.00","0.00","29.11"]}
             */

            private TodayBean today;
            private YesterdayBean yesterday;
            private SevendayBean sevenday;
            private ThirtydayBean thirtyday;

            public TodayBean getToday() {
                return today;
            }

            public void setToday(TodayBean today) {
                this.today = today;
            }

            public YesterdayBean getYesterday() {
                return yesterday;
            }

            public void setYesterday(YesterdayBean yesterday) {
                this.yesterday = yesterday;
            }

            public SevendayBean getSevenday() {
                return sevenday;
            }

            public void setSevenday(SevendayBean sevenday) {
                this.sevenday = sevenday;
            }

            public ThirtydayBean getThirtyday() {
                return thirtyday;
            }

            public void setThirtyday(ThirtydayBean thirtyday) {
                this.thirtyday = thirtyday;
            }

            public static class TodayBean {
                private List<String> time;
                private List<String> income;

                public List<String> getTime() {
                    return time;
                }

                public void setTime(List<String> time) {
                    this.time = time;
                }

                public List<String> getIncome() {
                    return income;
                }

                public void setIncome(List<String> income) {
                    this.income = income;
                }
            }

            public static class YesterdayBean {
                private List<String> time;
                private List<String> income;

                public List<String> getTime() {
                    return time;
                }

                public void setTime(List<String> time) {
                    this.time = time;
                }

                public List<String> getIncome() {
                    return income;
                }

                public void setIncome(List<String> income) {
                    this.income = income;
                }
            }

            public static class SevendayBean {
                private List<String> time;
                private List<String> income;

                public List<String> getTime() {
                    return time;
                }

                public void setTime(List<String> time) {
                    this.time = time;
                }

                public List<String> getIncome() {
                    return income;
                }

                public void setIncome(List<String> income) {
                    this.income = income;
                }
            }

            public static class ThirtydayBean {
                private List<String> time;
                private List<String> income;

                public List<String> getTime() {
                    return time;
                }

                public void setTime(List<String> time) {
                    this.time = time;
                }

                public List<String> getIncome() {
                    return income;
                }

                public void setIncome(List<String> income) {
                    this.income = income;
                }
            }
        }
    }
}
