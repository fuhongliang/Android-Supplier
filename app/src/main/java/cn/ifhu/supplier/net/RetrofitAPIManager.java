package cn.ifhu.supplier.net;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import cn.ifhu.supplier.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author fuhongliang
 */
public class RetrofitAPIManager {
    private static Retrofit uploadRetrofit;

    private static final long TIMEOUT = 1000;
    private static Retrofit retrofit;

    //正式环境
    private static String RELEASE_URL = "https://shequapi.ifhu.cn/mch/";

    public static String DEV_URL = "https://testshequapi.ifhu.cn/mch/";

    private static String TEST_URL = "https://testshequapi.ifhu.cn/mch/";

    private static final String STRING_API_ENV = BuildConfig.STRING_API_ENV;

    private static String BASE_URL = STRING_API_ENV.equals("0")
            ? RELEASE_URL : DEV_URL;


    private RetrofitAPIManager() {
    }

    public static Retrofit getClientApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(genericClient())
                    .build();
        }

        return retrofit;
    }

    /**
     * 上传专用
     *
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T createUpload(final Class<T> service) {
        return getUploadClientApi().create(service);
    }


    /**
     * 上传图片专用的Retrofit
     */
    public static Retrofit getUploadClientApi() {
        if (uploadRetrofit == null) {
            uploadRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(genericUploadClient())
                    .build();
        }

        return uploadRetrofit;
    }

    private static OkHttpClient genericUploadClient() {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor(message -> Log.d("RetrofitAPIManager", message)).setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return httpClient;
    }

    public static <T> T create(final Class<T> service) {
        return getClientApi().create(service);
    }

    private static OkHttpClient genericClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor(message -> Logger.d(message)).setLevel(HttpLoggingInterceptor.Level.BODY));

        OkHttpClient httpClient = builder.build();
        return httpClient;
    }
}
