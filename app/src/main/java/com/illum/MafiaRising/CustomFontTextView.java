package com.illum.MafiaRising;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomFontTextView extends TextView {
    public CustomFontTextView(Context context) {
        super(context);
        CustomFontUtils.applyCustomFont(this, context, null);
    }

    public CustomFontTextView(Context context, AttributeSet attribs)
    {
        super(context, attribs);
        CustomFontUtils.applyCustomFont(this, context, attribs);
    }

    public CustomFontTextView(Context context, AttributeSet attribs, int defStyle)
    {
        super(context, attribs, defStyle);
        CustomFontUtils.applyCustomFont(this, context, attribs);
    }
}
