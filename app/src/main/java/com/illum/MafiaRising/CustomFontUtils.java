package com.illum.MafiaRising;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomFontUtils {
    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public static void applyCustomFont(TextView customFontTextView, Context context, AttributeSet attribs)
    {
        TypedArray attribArray = context.obtainStyledAttributes(
                attribs,
                R.styleable.CustomFontTextView);

        String fontName = attribArray.getString(R.styleable.CustomFontTextView_font);

        int textStyle = attribArray.getInt(R.styleable.CustomFontTextView_textStyle, 0);

        if(textStyle == 0)
        {
            textStyle = attribs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);
        }

        Typeface customFont = selectTypeface(context, fontName, textStyle);
        customFontTextView.setTypeface(customFont);

        attribArray.recycle();
    }

    public static Typeface selectTypeface(Context context, String fontName, int textStyle)
    {
        if(fontName != null && fontName.contentEquals(context.getString(R.string.font_name_kefa)))
        {
            switch(textStyle)
            {
                case Typeface.BOLD:
                    return FontCache.getTypeface("Kefa-Bold.ttf",context);
                case Typeface.ITALIC:
                    return FontCache.getTypeface("Kefa-Regular.ttf",context);
                case Typeface.BOLD_ITALIC:
                    return FontCache.getTypeface("Kefa-Bold.ttf",context);
                case Typeface.NORMAL:
                default:
                    return FontCache.getTypeface("Kefa-Regular.ttf",context);
            }
        }
        else
        {
            return Typeface.DEFAULT;
        }
    }
}
