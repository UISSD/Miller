package com.github.uissd.miller.hook;

import com.github.uissd.miller.util.Log;

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

    abstract protected String getTAG();

    @Override
    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
        String method = param.method.getName();
        Log.d(getTAG(), "<<" + method + ">>");
        beforeMethod(param);
        Object result = replaceMethod(param);
        param.setResult(result);
        afterMethod(param);
        return result;
    }
}
