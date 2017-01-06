package com.illum.MafiaRising;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

//allows apply font
//requires FontCache and attribs.xml with font and textStyle attributes
class CustomFontUtils {
    private static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    //gets font and textStyle and calls selectTypeface to get the font style, then sets it
    //not sure if completely correct, how does CustomFontButton work if this is TextView?
    static void applyCustomFont(TextView customFontTextView, Context context, AttributeSet attribs)
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

    //gets hardcoded font style in assets based on fontName and textStyle
    static Typeface selectTypeface(Context context, String fontName, int textStyle)
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
        else if(fontName != null && fontName.contentEquals(context.getString(R.string.font_name_manteka))) {
            return FontCache.getTypeface("Manteka.ttf",context);
        }
        else
        {
            return Typeface.DEFAULT;
        }
    }
}
