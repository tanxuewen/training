package com.evan.xtool.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.evan.xtool.R;

/**
 * 加载空白页实现
 * Created by evan on 2016/5/29.
 */
public class EmptyLayout extends RelativeLayout {

    private String mLoadingText;//加载中显示的文字
    private String mFailText; //数据为空或者加载失败时显示的文字

    private LinearLayout loading_layout;
    private LinearLayout failure_layout;
    private TextView mLoadingTextView;//加载中控件
    private TextView mFailTextView;//数据为空或者加载失败时控件
    private Button mButton;//重新加载按钮
    private View mBindView; //绑定view

    private ResetListener resetListener;

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            loading_layout.setVisibility(VISIBLE);
            if (resetListener != null) {
                resetListener.onRestListener();
            }
        }
    };

    public EmptyLayout(Context context) {
        this(context, null);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.emptyview, null);
        loading_layout = (LinearLayout) view.findViewById(R.id.loading_layout);
        mLoadingTextView = (TextView) view.findViewById(R.id.loading_view);
        failure_layout = (LinearLayout) view.findViewById(R.id.failure_layout);
        mFailTextView = (TextView) view.findViewById(R.id.failure_view);
        mButton = (Button) view.findViewById(R.id.reload_btn);

        mButton.setOnClickListener(listener);

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
        if (mBindView != null) {
            mBindView.setVisibility(GONE);
        }
        setVisibility(VISIBLE);
        loading_layout.setVisibility(GONE);
        failure_layout.setVisibility(VISIBLE);
        mFailTextView.setVisibility(VISIBLE);
        mButton.setVisibility(VISIBLE);
    }

    interface ResetListener {
        void onRestListener();
    }

}
