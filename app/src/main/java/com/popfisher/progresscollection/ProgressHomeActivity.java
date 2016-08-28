package com.popfisher.progresscollection;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.popfisher.progresscollection.progressviews.NormalArcProgressView;

public class ProgressHomeActivity extends Activity implements View.OnClickListener {

    private Button mCircleType1;
    private Button mCircleType2;
    private Button mCircleType3;
    private Button mCircleType11;

    private Dialog mDialog;
    private ViewGroup mProgressContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_home);
        initView();
    }

    private void initView() {
        mCircleType1 = findView(R.id.button_circle_progress_1);
        mCircleType2 = findView(R.id.button_circle_progress_2);
        mCircleType3 = findView(R.id.button_circle_progress_3);
        mCircleType11 = findView(R.id.button_circle_progress_11);
        mCircleType1.setOnClickListener(this);
        mCircleType2.setOnClickListener(this);
        mCircleType3.setOnClickListener(this);
        mCircleType11.setOnClickListener(this);
    }

    private void showCircleType1() {
        initDialog();
        mProgressContainer.removeAllViews();
        final int progressSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics());
        NormalArcProgressView normalArcProgressView = new NormalArcProgressView(this);
        normalArcProgressView.setRoundColor(getResources().getColor(R.color.colorRoundBg));
        normalArcProgressView.setRoundProgressColor(getResources().getColor(R.color.colorBlue));
        mProgressContainer.addView(normalArcProgressView, new ViewGroup.LayoutParams(progressSize, progressSize));
        NormalArcProgressView normalArcProgressView2 = new NormalArcProgressView(this);
        normalArcProgressView2.setRoundColor(getResources().getColor(R.color.colorRoundBg));
        normalArcProgressView2.setRoundProgressColor(getResources().getColor(R.color.colorBlue));
        normalArcProgressView2.setRotateOrientation(NormalArcProgressView.COUNTERCLOCKWISE);
        mProgressContainer.addView(normalArcProgressView2, new ViewGroup.LayoutParams(progressSize, progressSize));

        mDialog.show();
        ((NormalArcProgressView)mProgressContainer.getChildAt(0)).startProgress();
        normalArcProgressView.startProgress();
        normalArcProgressView2.startProgress();
    }

    private void showCircleType2() {

    }

    private void showCircleType3() {

    }

    private void showCircleType11() {

    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.button_circle_progress_1:
                showCircleType1();
                break;
            case R.id.button_circle_progress_2:
                showCircleType2();
                break;
            case R.id.button_circle_progress_3:
                showCircleType3();
                break;
            case R.id.button_circle_progress_11:
                showCircleType11();
                break;
        }
    }

    private void initDialog() {
        if (mDialog == null) {
            mDialog = new Dialog(this, R.style.dialog);
            mDialog.setContentView(LayoutInflater.from(this).inflate(R.layout.dialog_layout, null),
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mProgressContainer = (ViewGroup) mDialog.getWindow().getDecorView().findViewById(R.id.dialog_progress_container);
            mDialog.getWindow().getDecorView().findViewById(R.id.dialog_close_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
            // 这是对话框的宽度
            mDialog.getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;
            // 设置Activity内容从顶部弹出啊
            mDialog.getWindow().setGravity(Gravity.TOP);
            mDialog.getWindow().setWindowAnimations(R.style.dialog_anim);
        }
    }

    private <T extends View> T findView(int resId) {
        return (T) findViewById(resId);
    }
}
