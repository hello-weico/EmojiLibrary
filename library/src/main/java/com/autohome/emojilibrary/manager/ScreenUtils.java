package com.autohome.emojilibrary.manager;

import android.content.Context;


/**
 * <p>屏幕分辨率转换工具</p>
 * <p>移入其它库使用时如有和此类相同类，尽量合并使用。</p>
 * @author Zouxh
 *
 */
public class ScreenUtils {
	

    public static int dpToPx(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return  (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (spValue * fontScale + 0.5f);  
    }
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dipInt(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }
    
}
