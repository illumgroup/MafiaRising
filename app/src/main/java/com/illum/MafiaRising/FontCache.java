package com.illum.MafiaRising;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

//gets the font style if it exists or returns Default
//uses HashMap to quickly grab it on later calls
class FontCache {

    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    static Typeface getTypeface(String fontname, Context context)
    {
        Typeface typeface = fontCache.get(fontname);
        if(typeface == null)
        {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(),"fonts/"+fontname);
            } catch (Exception e)
            {
                return Typeface.DEFAULT;
            }

            fontCache.put(fontname, typeface);
        }

        return typeface;
    }
}
