package com.evan.gobang.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.evan.gobang.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 五子棋盘
 * Created by evan on 2016/4/19.
 */
public class GoBangView extends View {

    private int mPanelWidth;
    private float mLineHeight;
    private int MAX_LINE = 10;
    private int MAX_COUNT_IN_LINE = 5;

    private Paint mPaint = new Paint();

    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;

    private float ratioPieceOfLineHeight = 3 / 4f;

    //白棋先手
    private boolean mIsWhite = true;
    private ArrayList<Point> mWhiteArray = new ArrayList<>();
    private ArrayList<Point> mBlackArray = new ArrayList<>();

    private boolean isGameOver;
    private boolean mIsWhiteWinner;

    private WinListener listener;

    public GoBangView(Context context) {
        this(context, null);
    }

    public GoBangView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoBangView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(0x44ff0000);
        init();
    }

    private void init() {
        mPaint.setColor(0x88000000);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_white);
        mBlackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_black);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize, heightSize);

        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }

        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPanelWidth = w;
        mLineHeight = mPanelWidth * 1.0f / MAX_LINE;

        int pieceWidth = (int) (mLineHeight * ratioPieceOfLineHeight);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, pieceWidth, pieceWidth, true);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece, pieceWidth, pieceWidth, true);
    }

    public void setListener(WinListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBoard(canvas);
        drawPiece(canvas);

        checkGameOver();
    }

    private void checkGameOver() {
        boolean whiteWin = checkFiveInLine(mWhiteArray);
        boolean blackWin = checkFiveInLine(mBlackArray);
        if (whiteWin || blackWin) {
            isGameOver = true;
            mIsWhiteWinner = whiteWin;

            if(listener != null){
                listener.onWinListener(mIsWhiteWinner);
            }

            String text = mIsWhiteWinner ? "白棋胜利" : "黑棋胜利";
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkFiveInLine(List<Point> points) {
        for (Point point : points) {
            int x = point.x;
            int y = point.y;
            boolean win = checkHorizontal(x, y, points);
            if (win) return true;
            win = checkVertical(x, y, points);
            if (win) return true;
            win = checkLeftDiagonal(x, y, points);
            if (win) return true;
            win = checkRightDiagonal(x, y, points);
            if (win) return true;
        }
        return false;
    }

    //横向检测
    private boolean checkHorizontal(int x, int y, List<Point> points) {
        int count = 1;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x - i, y))) {
                count++;
            } else {
                break;
            }
        }

        if (count == MAX_COUNT_IN_LINE) return true;

        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x + i, y))) {
                count++;
            } else {
                break;
            }
        }

        if (count == MAX_COUNT_IN_LINE) return true;

        return false;
    }

    //纵向检测
    private boolean checkVertical(int x, int y, List<Point> points) {
        int count = 1;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x, y - i))) {
                count++;
            } else {
                break;
            }
        }

        if (count == MAX_COUNT_IN_LINE) return true;

        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x, y + i))) {
                count++;
            } else {
                break;
            }
        }

        if (count == MAX_COUNT_IN_LINE) return true;

        return false;
    }

    //左斜检测
    private boolean checkLeftDiagonal(int x, int y, List<Point> points) {
        int count = 1;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x + i, y - i))) {
                count++;
            } else {
                break;
            }
        }

        if (count == MAX_COUNT_IN_LINE) return true;

        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x - i, y + i))) {
                count++;
            } else {
                break;
            }
        }

        if (count == MAX_COUNT_IN_LINE) return true;

        return false;
    }

    //右斜检测
    private boolean checkRightDiagonal(int x, int y, List<Point> points) {
        int count = 1;
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x - i, y - i))) {
                count++;
            } else {
                break;
            }
        }

        if (count == MAX_COUNT_IN_LINE) return true;

        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x + i, y + i))) {
                count++;
            } else {
                break;
            }
        }

        if (count == MAX_COUNT_IN_LINE) return true;

        return false;
    }


    //绘制棋子
    private void drawPiece(Canvas canvas) {
        for (int i = 0; i < mWhiteArray.size(); i++) {
            Point whitePoint = mWhiteArray.get(i);
            canvas.drawBitmap(mWhitePiece,
                    (whitePoint.x + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight,
                    (whitePoint.y + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight, null);
        }

        for (int i = 0; i < mBlackArray.size(); i++) {
            Point blackPoint = mBlackArray.get(i);
            canvas.drawBitmap(mBlackPiece,
                    (blackPoint.x + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight,
                    (blackPoint.y + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight, null);
        }
    }

    //绘制棋盘
    private void drawBoard(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHeight;
        for (int i = 0; i < MAX_LINE; i++) {
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);
            int y = (int) ((0.5 + i) * lineHeight);
            //绘制横线
            canvas.drawLine(startX, y, endX, y, mPaint);
            //绘制竖线
            canvas.drawLine(y, startX, y, endX, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (isGameOver) return false;

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            return true;
        }

        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            Point p = getValidPoint(x, y);

            if (mWhiteArray.contains(p) || mBlackArray.contains(p)) {
                return false;
            }

            if (mIsWhite) {
                mWhiteArray.add(p);
            } else {
                mBlackArray.add(p);
            }
            invalidate();
            mIsWhite = !mIsWhite;
            return true;
        }

        return super.onTouchEvent(event);
    }

    private Point getValidPoint(int x, int y) {
        return new Point((int) (x / mLineHeight), (int) (y / mLineHeight));
    }

    public void start(){
        mWhiteArray.clear();
        mBlackArray.clear();
        isGameOver = false;
        mIsWhiteWinner = false;
        invalidate();
    }

    private static final String INSTANCE = "instance";
    private static final String INSTANCE_GAME_OVER = "instance_game_over";
    private static final String INSTANCE_WHITE_ARRAY = "instance_white_array";
    private static final String INSTANCE_BLACK_ARRAY = "instance_black_array";

    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAME_OVER, isGameOver);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY, mWhiteArray);
        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY, mBlackArray);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            isGameOver = bundle.getBoolean(INSTANCE_GAME_OVER);
            mWhiteArray = bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
            mBlackArray = bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public interface WinListener{
         void onWinListener(boolean whiteWin);
    }
}