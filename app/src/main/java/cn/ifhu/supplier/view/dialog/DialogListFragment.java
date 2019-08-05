package cn.ifhu.supplier.view.dialog;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ifhu.supplier.R;
import cn.ifhu.supplier.utils.DeviceUtil;

/**
 * @author fuhongliang
 */
public class DialogListFragment  extends BaseDialogFragment{

    private OperateDialogConfirmListner confirmListner;
    private LinearLayout llContent;
    List<String> stringList = new ArrayList<String>();
    public static void showOperateDialog(FragmentManager fragmentManager, Bundle bundle, OperateDialogConfirmListner confirmListner){
        DialogListFragment dialogFragment = new DialogListFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.setDialogConfirmListner(confirmListner);
        dialogFragment.show(fragmentManager,"AlertDialogFragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Bottom_top_dialog_style);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels- DeviceUtil.dip2px(0), ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.link_dialog_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        TextView title = view.findViewById(R.id.tv_title);
        Bundle bundle = getArguments();
        title.setText(bundle.getString("title"));
        stringList = bundle.getStringArrayList("stringList");
        llContent = view.findViewById(R.id.ll_content);
        addOptionalView();
    }

    public void addOptionalView() {
        llContent.removeAllViews();
        if (stringList != null && stringList.size()>0){
            for (int i = 0; i < stringList.size(); i++) {
                View view = getLayoutInflater().inflate(R.layout.item_content, null);
                ImageView sepLine = view.findViewById(R.id.sep_line);
                final TextView tvContent = view.findViewById(R.id.tv_content);
                if (i == stringList.size()-1) {
                    sepLine.setVisibility(View.GONE);
                } else {
                    sepLine.setVisibility(View.VISIBLE);
                }
                tvContent.setText(stringList.get(i));
                view.setOnClickListener(view1 -> {
                    if(confirmListner!=null){
                        confirmListner.onClickTextView(tvContent.getText().toString());
                        dismiss();
                    }
                });
                llContent.addView(view);
            }
        }
    }

    public void setDialogConfirmListner(OperateDialogConfirmListner alertDialogConfirmListner) {
        this.confirmListner = alertDialogConfirmListner;
    }

    public interface OperateDialogConfirmListner {
        void onClickTextView(String string);
    }
}