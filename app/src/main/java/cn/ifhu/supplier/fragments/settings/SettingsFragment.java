package cn.ifhu.supplier.fragments.settings;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ifhu.supplier.R;
import cn.ifhu.supplier.activity.me.AboutUsActivity;
import cn.ifhu.supplier.activity.me.AccountAndSafeActivity;
import cn.ifhu.supplier.activity.me.StoreSetUpActivity;
import cn.ifhu.supplier.base.BaseFragment;
import cn.ifhu.supplier.utils.DialogUtils;
import cn.ifhu.supplier.utils.MchInfoLogic;
import cn.ifhu.supplier.view.GlideImageView.GlideImageView;
import cn.ifhu.supplier.view.dialog.nicedialog.ConfirmDialog;

/**
 * @author tony
 */
public class SettingsFragment extends BaseFragment {

    @BindView(R.id.iv_avatar)
    GlideImageView mIvAvatar;
    Unbinder unbinder;
    @BindView(R.id.tv_store_name)
    TextView mTvStoreName;
    @BindView(R.id.tv_address)
    TextView mTvAddress;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void initData() {
        mTvStoreName.setText(MchInfoLogic.getMchInfoBean().getMch_info().getMch_name());
        mTvAddress.setText(MchInfoLogic.getMchInfoBean().getMch_info().getAddress());
        mIvAvatar.loadCircle(MchInfoLogic.getMchInfoBean().getMch_info().getLogo());
    }


    @OnClick(R.id.rl_store_setting)
    public void onMRlStoreSettingClicked() {
        startActivity(new Intent(getContext(), StoreSetUpActivity.class));
    }

    @OnClick(R.id.rl_account_security)
    public void onMRlAccountSecurityClicked() {
        startActivity(new Intent(getContext(), AccountAndSafeActivity.class));
    }

    @OnClick(R.id.rl_contact_platform)
    public void onMRlContactPlatformClicked() {
        DialogUtils.showConfirmDialog("温馨提示", "是否拨打平台客服电话", "取消", "确定", getActivity().getSupportFragmentManager(), new ConfirmDialog.ButtonOnclick() {
            @Override
            public void cancel() {
            }

            @Override
            public void ok() {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + MchInfoLogic.getMchInfoBean().getMch_info().getService_tel());
                intent.setData(data);
                getActivity().startActivity(intent);
            }
        });
    }

    @OnClick(R.id.rl_about_platform)
    public void onMRlAboutPlatformClicked() {
        startActivity(new Intent(getContext(), AboutUsActivity.class));
    }
}
