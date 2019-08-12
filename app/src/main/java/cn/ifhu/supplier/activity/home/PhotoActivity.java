package cn.ifhu.supplier.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.adapter.PhotoAdapter;
import cn.ifhu.supplier.base.BaseActivity;

public class PhotoActivity extends BaseActivity {

    @BindView(R.id.vp)
    ViewPager vp;

    PhotoAdapter photoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("url_list");
        int index = bundle.getInt("index", 0);
        List<String> list = bundle.getStringArrayList("url");
        photoAdapter = new PhotoAdapter(this, list);
        vp.setAdapter(photoAdapter);
        vp.setCurrentItem(index);
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

}
