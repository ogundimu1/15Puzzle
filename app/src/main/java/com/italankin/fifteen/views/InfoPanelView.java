package com.italankin.fifteen.views;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.italankin.fifteen.Colors;
import com.italankin.fifteen.Dimensions;
import com.italankin.fifteen.Game;
import com.italankin.fifteen.R;
import com.italankin.fifteen.Settings;
import com.italankin.fifteen.Tools;

public class InfoPanelView extends BaseView {

    /**
     * фон игрового поля
     */
    private Paint mPaintBg;
    /**
     * отображение текстов инфо
     */
    private Paint mPaintTextValue;
    /**
     * отображение заголовков инфо
     */
    private Paint mPaintTextCaption;

    /**
     * режим игры
     */
    private String mTextMode[];

    /**
     * текущее количество ходов
     */
    private String mTextMoves;
    /**
     * текущее время
     */
    private String mTextTime;

    /**
     * панель инфо
     */
    private RectF mRectInfo;

    private int mValueTextOffset;
    private int mCaptionTextOffset;

    public InfoPanelView(Resources res) {
        mPaintBg = new Paint();
        mPaintBg.setAntiAlias(Settings.antiAlias);
        mPaintBg.setColor(Colors.backgroundField);

        mPaintTextValue = new Paint();
        mPaintTextValue.setAntiAlias(Settings.antiAlias);
        mPaintTextValue.setTypeface(Settings.typeface);
        mPaintTextValue.setTextAlign(Paint.Align.CENTER);
        mPaintTextValue.setTextSize(Dimensions.interfaceFontSize * 1.8f);
        mPaintTextValue.setColor(Colors.getInfoTextColor());

        mPaintTextCaption = new Paint(mPaintTextValue);
        mPaintTextCaption.setTextSize(Dimensions.interfaceFontSize * 1.4f);
        mPaintTextCaption.setTextAlign(Paint.Align.LEFT);

        mRectInfo = new RectF(0.0f, Dimensions.topBarHeight + Dimensions.infoBarHeight / 2,
                Dimensions.surfaceWidth, Dimensions.fieldMarginTop - Dimensions.infoBarHeight / 2);

        Rect r = new Rect();
        mPaintTextValue.getTextBounds("A", 0, 1, r);
        mValueTextOffset = r.centerY();
        mPaintTextCaption.getTextBounds("A", 0, 1, r);
        mCaptionTextOffset = r.centerY();

        mTextMode = res.getStringArray(R.array.game_modes);
        mTextMoves = res.getString(R.string.info_moves);
        mTextTime = res.getString(R.string.info_time);

        mShow = true;
    }

    @Override
    public void draw(Canvas canvas, long elapsedTime) {
        if (!mShow) {
            return;
        }

        canvas.drawRoundRect(mRectInfo, Dimensions.tileCornerRadius, Dimensions.tileCornerRadius,
                mPaintBg);

        // режим игры
        canvas.drawText(
                mTextMode[Settings.gameMode].toUpperCase() + (Settings.hardmode ? "*" : ""),
                Dimensions.surfaceWidth * 0.25f, mRectInfo.centerY() - mValueTextOffset,
                mPaintTextValue);

        float row1 = mRectInfo.top + mRectInfo.height() * 0.3f - mCaptionTextOffset;
        float row2 = mRectInfo.top + mRectInfo.height() * 0.7f - mCaptionTextOffset;
        // надписи
        mPaintTextCaption.setColor(Colors.getTileTextColor());
        mPaintTextCaption.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(mTextMoves, Dimensions.surfaceWidth / 2.0f, row1, mPaintTextCaption);
        canvas.drawText(mTextTime, Dimensions.surfaceWidth / 2.0f, row2, mPaintTextCaption);
        // значения
        mPaintTextCaption.setColor(Colors.getInfoTextColor());
        mPaintTextCaption.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(Integer.toString(Game.getMoves()),
                Dimensions.surfaceWidth - Dimensions.spacing * 2.0f, row1, mPaintTextCaption);
        canvas.drawText(Tools.timeToString(Game.getTime()),
                Dimensions.surfaceWidth - Dimensions.spacing * 2.0f, row2, mPaintTextCaption);
    }

    public boolean onClick(float x, float y) {
        return false;
    }

    @Override
    public void update() {
        mPaintTextValue.setColor(Colors.getInfoTextColor());
        mPaintBg.setColor(Colors.backgroundField);
    }
}
