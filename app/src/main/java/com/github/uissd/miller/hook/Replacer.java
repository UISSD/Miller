package com.github.uissd.miller.hook;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;

public abstract class Replacer extends XC_MethodReplacement implements Hooker {

    protected void afterMethod(MethodHookParam param) throws Throwable {
    }

    protected void beforeMethod(MethodHookParam param) throws Throwable {
    }

    protected Object replaceMethod(MethodHookParam param) throws Throwable {
        return XposedBridge.invokeOriginalMethod(param.method, param.thisObject, param.args);
    }

    abstract protected void logMethodName(MethodHookParam param);

    @Override
    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
        logMethodName(param);
        beforeMethod(param);
        Object result = replaceMethod(param);
        param.setResult(result);
        afterMethod(param);
        return result;
    }
}
