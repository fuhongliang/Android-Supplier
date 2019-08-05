//package cn.ifhu.mershop.db;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.a88meeting.irdashboard.bean.LinkInvestorItem;
//
//import org.greenrobot.greendao.query.QueryBuilder;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
///**
// * Created by tommy on 17/12/23.
// */
//
//public class DBManager {
//    private String dbName;
//    private DaoMaster.DevOpenHelper openHelper;
//    private static DBManager mInstance;
//    private Context context;
//    private Set<String> followedIndustries = new HashSet<>();
//    private Map<String, String> USERINDUSTRY_KEY_MAP;
//
//    private DBManager() {
//    }
//
//    public void init(Context context, String dbName) {
//        this.context = context;
//        this.dbName = dbName;
//        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
//    }
//
//    public static DBManager getInstance() {
//        if (mInstance == null) {
//            synchronized (DBManager.class) {
//                if (mInstance == null) {
//                    mInstance = new DBManager();
//                }
//            }
//        }
//        return mInstance;
//    }
//
//    /**
//     * 获取可读数据库
//     */
//    private SQLiteDatabase getReadableDatabase() {
//        if (openHelper == null) {
//            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
//        }
//        SQLiteDatabase db = openHelper.getReadableDatabase();
//        return db;
//    }
//
//    /**
//     * 获取可写数据库
//     */
//    private SQLiteDatabase getWritableDatabase() {
//        if (openHelper == null) {
//            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
//        }
//        SQLiteDatabase db = openHelper.getWritableDatabase();
//        return db;
//    }
//
//    public void clearAllTable() {
//        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
//        DaoMaster.dropAllTables(daoMaster.getDatabase(), true);
//        DaoMaster.createAllTables(daoMaster.getDatabase(), true);
//
//    }
//
//
//    public void insertInvestorItemList(List<LinkInvestorItem> investorItemList) {
//        if (investorItemList == null) {
//            return;
//        }
//        clearLinkInvestorTable();
//        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        LinkInvestorItemDao investorItemDao = daoSession.getLinkInvestorItemDao();
//        investorItemDao.insertInTx(investorItemList);
//    }
//
//    public void insertLinkInvestor(LinkInvestorItem linkInvestorItem) {
//        if (linkInvestorItem == null) {
//            return;
//        }
//        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        LinkInvestorItemDao companyReponseModelDao = daoSession.getLinkInvestorItemDao();
//        companyReponseModelDao.insert(linkInvestorItem);
//    }
//
//    public List<LinkInvestorItem> queryInvestorLikeNameOrCompany(String name) {
//        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        LinkInvestorItemDao linkInvestoItemDao = daoSession.getLinkInvestorItemDao();
//        QueryBuilder<LinkInvestorItem> qb = linkInvestoItemDao.queryBuilder();
//        qb.whereOr(LinkInvestorItemDao.Properties.Name_en.like("%" + name + "%"),LinkInvestorItemDao.Properties.Name_zh.like("%" + name + "%"),LinkInvestorItemDao.Properties.Company_en.like("%" + name + "%"),LinkInvestorItemDao.Properties.Company_zh.like("%" + name + "%"));
//        return  qb.list();
//    }
//
//    public void clearLinkInvestorTable() {
//        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        LinkInvestorItemDao linkInvestorItemDao = daoSession.getLinkInvestorItemDao();
//        linkInvestorItemDao.deleteAll();
//    }
//}
