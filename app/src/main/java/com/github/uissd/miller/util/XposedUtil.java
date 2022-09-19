package com.github.uissd.miller.util;

import de.robv.android.xposed.XposedHelpers;

public class XposedUtil {

    public static Object field(Object thisObject, String field) {
        return XposedHelpers.getObjectField(thisObject, field);
    }

    public static Object call(Object thisObject, String method, Object... args) {
        return XposedHelpers.callMethod(thisObject, method, args);
    }
}
