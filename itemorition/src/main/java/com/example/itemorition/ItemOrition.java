package com.example.itemorition;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by Zhangchen on 2017/8/14.
 */

public class ItemOrition extends RecyclerView.ItemDecoration {

    /**
     * 水平方向
     */
    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;

    /**
     * 垂直方向
     */
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;

    private int mHeight = 1;//分割线高度
    private int mColor;//分割线颜色
    private int orientation;//分割线方向
    private int padleft = 0;//paddingleft或者paddingtop
    private int padright = 0;//paddingright或者paddingbottom

    private float scale; //将dp转换为px单位
    private Context context;
    private Paint mPaint;

    //文字标签属性值
    private boolean istvlabel = false;//是否出现标签
    private String labeltext; //标签内容
    private int labelcolor;//标签字体颜色
    private int labelpadright;//标签距离右边距
    private int textsize;//标签字体大小

    //图片标签属性值
    private boolean isimglabel = false;//是否出现文字标签
    private int imgResource;//图片资源

    private boolean sectionBoolean = false;//打开标签属性
    private DecorationCallback callback;
    private TextPaint paintText;
    private int topGap;
    private Paint.FontMetrics fontMetrics;


    public ItemOrition(Context context) {
        this(context, VERTICAL);
    }

    public ItemOrition(Context context, int orientation) {
        this.context = context;
        this.orientation = orientation;
        mPaint = new Paint();
        scale = context.getResources().getDisplayMetrics().density; //将dp转换为px
    }

    public ItemOrition(Context context, int orientation, int backgroundcolor,int titlecolor,DecorationCallback callback) {
        this.context = context;
        this.orientation = orientation;
        mPaint = new Paint();
        scale = context.getResources().getDisplayMetrics().density; //将dp转换为px
        this.callback = callback;
        this.sectionBoolean = true;

        mPaint.setColor(backgroundcolor);
        paintText = new TextPaint();
        paintText.setTypeface(Typeface.DEFAULT_BOLD);
        paintText.setAntiAlias(true);
        paintText.setTextSize(80);
        paintText.setColor(titlecolor);
        paintText.getFontMetrics(fontMetrics);
        paintText.setTextAlign(Paint.Align.LEFT);
        fontMetrics = new Paint.FontMetrics();
        topGap = context.getResources().getDimensionPixelOffset(R.dimen.dp_32);
    }

    //通过Rect为每个Item设置偏移，用于绘制Decoration
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (position != 0) {
            outRect.top = mHeight;
        }
        if (sectionBoolean) {
            long groupId = callback.getGroupId(position);
            if (groupId < 0) return;
            if (position == 0 || isFirstGroup(position)) { //同组第一个才能添加
                outRect.top = topGap;
            }
        }
    }


    //通过该方法，在Canvas上绘制内容，在绘制Item之前调用。（如果没有通过getItemOffsets设置偏移的话，Item的内容会将其覆盖）
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childView = parent.getChildAt(i);
            if (orientation == HORIZONTAL) {
                final int top = parent.getTop() + padleft;
                final int left = childView.getLeft() - mHeight;
                final int right = childView.getLeft();
                final int bottom = parent.getBottom() - padright;
                c.drawRect(left, top, right, bottom, mPaint);
            } else if (orientation == VERTICAL) {
                final int left = parent.getLeft() + padleft;
                final int right = parent.getRight() - padright;
                final int bottom = childView.getTop();
                final int top = bottom - mHeight;
                c.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    //通过该方法，在Canvas上绘制内容,在Item之后调用。(画的内容会覆盖在item的上层)
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        Paint mpaint = new Paint();
        int itemCounrt = state.getItemCount();
        int childcount = parent.getChildCount();
        float lineHeight = paintText.getTextSize() + fontMetrics.descent;
        long pregroup, groupId = -1;
        for (int i = 0; i < childcount; i++) {
            View child = parent.getChildAt(i);
            float left = child.getLeft();
            float right = child.getRight();
            float top = child.getTop();
            float bottom = child.getBottom();
            if (istvlabel) {
                mpaint.setColor(0XFF704825);
                if (labelcolor > 0) {
                    mpaint.setColor(labelcolor);
                }
                if (textsize > 0) {
                    mpaint.setTextSize(textsize);
                } else {
                    mpaint.setTextSize((int) (13 * scale + 0.5f));
                }
                Rect rect = new Rect();
                mpaint.getTextBounds(labeltext, 0, labeltext.length(), rect);
                mpaint.setStrokeWidth(5);
                c.drawText(labeltext, right - labelpadright - (rect.right - rect.left), top + (bottom - top) / 2 + (rect.bottom - rect.top) / 2, mpaint);
            } else if (isimglabel) {
                Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), imgResource);
                int hweigh = (int) (bottom - top - (int) (30 * scale + 0.5f)) / 2;
                Rect bitrect = new Rect(0, 0, (int) (30 * scale + 0.5f), (int) (30 * scale + 0.5f));
                Rect parentrect = new Rect((int) (right - (int) (30 * scale + 0.5f)), (int) (top + hweigh), (int) right, (int) (bottom - hweigh));
                c.drawBitmap(bmp, bitrect, parentrect, mpaint);
            }
            if (sectionBoolean) {
                int position = parent.getChildAdapterPosition(child);
                pregroup = groupId;
                groupId = callback.getGroupId(position);
                if (groupId < 0 || pregroup == groupId) continue;
                String textLine = callback.getGroupFirstLine(position).toUpperCase();
                if (TextUtils.isEmpty(textLine)) continue;

                float textY = Math.max(topGap, child.getTop());
                if (position + 1 < itemCounrt) {
                    //下一个和前一个不一样移动当前
                    long nextGroupId = callback.getGroupId(position + 1);
                    if (nextGroupId != groupId && child.getBottom() < textY) {
                        textY = child.getBottom();
                    }
                }
                c.drawRect(left, textY - topGap, right, textY, mPaint);
                c.drawText(textLine, left, textY, paintText);
            }
        }


    }

    /**
     * 设置分隔线颜色
     *
     * @param color
     */
    public void setColor(int color) {
        this.mColor = color;
        mPaint.setColor(color);
    }

    /**
     * 设置分隔线高度 dp
     *
     * @param height
     */
    public void setHeight(int height) {
        this.mHeight = (int) (height * scale + 0.5f);
    }

    /**
     * 设置分隔线padding
     */
    public void setPadding(int one, int two) {
        padleft = (int) (one * scale + 0.5f);
        padright = (int) (two * scale + 0.5f);
    }

    public void setPadding(int i) {
        padleft = padright = (int) (i * scale + 0.5f);
    }

    /**
     * 是否添加标签
     */
    public void setLabel(boolean islabel, String text, int padright, int color, int textsize) {
        this.istvlabel = islabel;
        this.labeltext = text;
        this.labelcolor = color;
        this.labelpadright = (int) (padright * scale + 0.5f);
        this.textsize = (int) (textsize * scale + 0.5f);
    }

    public void setLabel(boolean islabel, String text, int padright) {
        this.istvlabel = islabel;
        this.labeltext = text;
        this.labelpadright = (int) (padright * scale + 0.5f);
    }

    /**
     * 是否添加图片型标签
     */
    public void setImageLabel(boolean isimglabel, int imgResource) {
        this.isimglabel = isimglabel;
        this.imgResource = imgResource;
    }

    /**
     * 判断是否是该组数据第一个
     *
     * @param position
     * @return
     */
    private boolean isFirstGroup(int position) {
        if (position == 0) {
            return true;
        } else {
            long prevGroupId = callback.getGroupId(position - 1);
            long groupId = callback.getGroupId(position);
            return prevGroupId != groupId;
        }
    }

    public interface DecorationCallback {
        long getGroupId(int position);

        String getGroupFirstLine(int position);
    }

}
