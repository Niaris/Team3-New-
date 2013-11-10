/**
 * 
 */
package com.team3.utils;

import android.os.Build;

/**
 * @author Admin
 *
 */
public  class Utils {

	/**
	 * 
	 * @return android API(SDK) version
	 */
	public static int getApi(){
		return Build.VERSION.SDK_INT;
	}
	
}
