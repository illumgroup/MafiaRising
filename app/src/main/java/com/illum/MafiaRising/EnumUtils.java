package com.illum.MafiaRising;


import android.widget.ImageView;

//utility functions to convert strings to their enum values
//currently converts ImageView ScaleType and Layout gravity
class EnumUtils {

    static ImageView.ScaleType getScaleType(String scaleType) {
        switch(scaleType) {
            case "center":
                return ImageView.ScaleType.CENTER;
            case "center_crop":
                return ImageView.ScaleType.CENTER_CROP;
            case "center_inside":
                return ImageView.ScaleType.CENTER_INSIDE;
            case "fit_center":
                return ImageView.ScaleType.FIT_CENTER;
            case "fit_end":
                return ImageView.ScaleType.FIT_END;
            case "fit_start":
                return ImageView.ScaleType.FIT_START;
            case "fit_xy":
                return ImageView.ScaleType.FIT_XY;
            case "matrix":
                return ImageView.ScaleType.MATRIX;
            default:
                return ImageView.ScaleType.FIT_CENTER;
        }
    }

    static int getGravity(String gravity) {
        switch(gravity) {
            case "top":
                return 0x30;
            case "bottom":
                return 0x50;
            case "left":
                return 0x03;
            case "right":
                return 0x05;
            case "center_vertical":
                return 0x10;
            case "fill_vertical":
                return 0x70;
            case "center_horizontal":
                return 0x01;
            case "fill_horizontal":
                return 0x07;
            case "center":
                return 0x11;
            case "fill":
                return 0x77;
            case "clip_vertical":
                return 0x80;
            case "clip_horizontal":
                return 0x08;
            case "start":
                return 0x00800003;
            case "end":
                return 0x00800005;
            default:
                return 0x30;
        }
    }
}
