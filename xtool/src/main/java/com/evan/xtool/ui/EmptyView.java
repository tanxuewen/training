package com.evan.xtool.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.evan.xtool.R;

/**
 * 加载空白页实现
 * Created by evan on 2016/5/29.
 */
public class EmptyView extends RelativeLayout {

    private LinearLayout loading_layout;
    private LinearLayout failure_layout;

    private ImageView failure_iv; //失败的图片
    private TextView failure_tv; //数据为空或者加载失败时控件

    private View mBindView; //绑定view

    private ResetListener resetListener;

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            loading_layout.setVisibility(VISIBLE);
            failure_layout.setVisibility(INVISIBLE);
            if (resetListener != null) {
                resetListener.onRestListener();
            }
        }
    };

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.emptyview, null);
        loading_layout = (LinearLayout) view.findViewById(R.id.loading_layout);
        failure_layout = (LinearLayout) view.findViewById(R.id.failure_layout);

        failure_iv = (ImageView) view.findViewById(R.id.failure_iv);
        failure_tv = (TextView) view.findViewById(R.id.failure_tv);

        failure_layout.setOnClickListener(listener);

        addView(view);
    }

    public void setResetListener(ResetListener resetListener) {
        this.resetListener = resetListener;
    }

    /**
     * 绑定View
     *
     * @param view
     */
    public void bindView(View view) {
        mBindView = view;
    }

    /**
     * 成功
     */
    public void success() {
        setVisibility(GONE);
        if (mBindView != null) {
            mBindView.setVisibility(VISIBLE);
        }
    }

    public void empty() {
        failVisible(R.drawable.loading, R.string.empty_data);
    }

    public void failure(){
        failVisible(R.drawable.loading, R.string.loading_fail);
    }

    public void noNetwork(){
        failVisible(R.drawable.loading, R.string.network_ex);
    }

    /**
     * 加载失败或空白页
     * @param imgResId
     * @param textResId
     */
    private void failVisible(int imgResId, int textResId){
        if (mBindView != null) {
            mBindView.setVisibility(GONE);
        }
        setVisibility(VISIBLE);
        loading_layout.setVisibility(GONE);
        failure_layout.setVisibility(VISIBLE);
        failure_iv.setImageResource(imgResId);
        failure_tv.setText(textResId);
    }

    interface ResetListener {
        void onRestListener();
    }

}
