package cn.ifhu.supplier.activity.operation;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.sendtion.xrichtext.RichTextEditor;
import cn.ifhu.supplier.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 新建笔记
 */
public class NewDetailActivity extends AppCompatActivity {
    private RichTextEditor et_new_content;
    private ProgressDialog insertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_new);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        insertDialog = new ProgressDialog(this);
        insertDialog.setMessage("正在插入图片...");
        insertDialog.setCanceledOnTouchOutside(false);

        et_new_content = findViewById(R.id.et_new_content);

        openSoftKeyInput();//打开软键盘显示
        setTitle("新建描述");
        insertImagesSync();
    }

    /**
     * 打开软键盘
     */
    private void openSoftKeyInput(){
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null && !imm.isActive() && et_new_content != null){
            et_new_content.requestFocus();
            imm.showSoftInputFromInputMethod(et_new_content.getWindowToken(),
                    InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 异步方式插入图片
     */
    private void insertImagesSync(){
        insertDialog.show();

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                try{
                    et_new_content.measure(0, 0);
                    // 测试插入网络图片 http://pics.sc.chinaz.com/files/pic/pic9/201904/zzpic17414.jpg
                    emitter.onNext("http://pics.sc.chinaz.com/files/pic/pic9/201903/zzpic16838.jpg");

                    emitter.onComplete();
                }catch (Exception e){
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
            @Override
            public void onComplete() {
                if (insertDialog != null && insertDialog.isShowing()) {
                    insertDialog.dismiss();
                }
                showToast("图片插入成功");
            }

            @Override
            public void onError(Throwable e) {
                if (insertDialog != null && insertDialog.isShowing()) {
                    insertDialog.dismiss();
                }
                showToast("图片插入失败:"+e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
//                subsInsert = d;
            }

            @Override
            public void onNext(String imagePath) {
                et_new_content.insertImage(imagePath, et_new_content.getMeasuredWidth());
            }
        });
    }


    /** 显示吐司 **/
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
