package io.github.h911002.firenews.support.utils;

import android.content.Context;

public final class DisplayUtils {

	/**
	 * @param context
	 * @return 屏幕宽度
	 */
	public static int getDisplayWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}
	
	/**
	 * 
	 * @param context
	 * @param radio  比例
	 * @return 屏幕宽度 * radio
	 */
	public static int getDisplayWidthRadio(Context context, float radio) {
		return Math.round(getDisplayWidth(context) * radio);
	}
	
	/**
	 * @param context
	 * @return 屏幕高度
	 */
	public static int getDisplayHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}
	
	/**
	 * @param context
	 * @param radio 比例
	 * @return 屏幕高度 * radio
	 */
	public static int getDisplayHeightRadio(Context context, float radio) {
		return Math.round(getDisplayHeight(context) * radio);
	}
	
	/**
	 * 
	 * @param context
	 * @return 屏幕密度
	 */
	public static int getDisplayDensity(Context context) {
		return (int) context.getResources().getDisplayMetrics().density;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
}
