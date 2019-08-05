package cn.ifhu.supplier.net;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author fuhongliang
 */
public class SchedulerUtils {
    public static ObservableTransformer ioMainScheduler() {
        return (Observable upstream) -> {
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        };
    }
}
