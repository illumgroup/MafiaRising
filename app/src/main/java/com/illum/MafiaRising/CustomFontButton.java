package com.illum.MafiaRising;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class CustomFontButton extends Button {
    public CustomFontButton(Context context) {
        super(context);
        CustomFontUtils.applyCustomFont(this, context, null);
    }

    public CustomFontButton(Context context, AttributeSet attribs) {
        super(context, attribs);
        CustomFontUtils.applyCustomFont(this, context, attribs);
    }

    public CustomFontButton(Context context, AttributeSet attribs, int defStyle) {
        super(context, attribs, defStyle);
        CustomFontUtils.applyCustomFont(this, context, attribs);
    }
}
