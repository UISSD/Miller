package com.github.uissd.miller;

import android.os.Bundle;

import com.github.uissd.miller.hook.android.RecentTasks;
import com.github.uissd.miller.hook.miui.powerkeeper.DisplayFrameSetting;
import com.github.uissd.miller.hook.miui.powerkeeper.PowerKeeperApplication;
import com.github.uissd.miller.hook.miui.powerkeeper.PowerKeeperManager;
import com.github.uissd.miller.hook.miui.powerkeeper.PowerStateMachine;
import com.github.uissd.miller.hook.miui.powerkeeper.SleepModeControllerNew;
import com.github.uissd.miller.util.Log;
import com.github.uissd.miller.util.miui.BluetoothEvent;
import com.github.uissd.miller.util.miui.FreezeBinder;
import com.github.uissd.miller.util.miui.MilletUidObserver;
import com.github.uissd.miller.util.miui.MilletUtils;
import com.github.uissd.miller.util.miui.PackageUtil;
import com.github.uissd.miller.util.miui.ProcessManager;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Hook implements IXposedHookLoadPackage {

    private static final String TAG = "Miller.Hook";
    private static final String COM_MIUI_POWERKEEPER = "com.miui.powerkeeper";
    private static final String ANDROID = "android";

    @Override
    public void handleLoadPackage(LoadPackageParam loadPackageParam) {
        new Log().run();
        Log.i(TAG, "Loading... " + loadPackageParam.packageName, 0);
        try {
            switch (loadPackageParam.packageName) {
                case COM_MIUI_POWERKEEPER:
                    Log.i(TAG, "Load PowerKeeper", 0);
                    initMIUIUtil(loadPackageParam);
                    Log.i(TAG, "initUtil", 0);

                    if (MilletUtils.getVersionTwo()) {
                        Log.i(TAG, "is Millet v2.", 0);
                    } else {
                        Log.i(TAG, "not Millet v2.", 0);
                    }

                    try {
                        PowerStateMachine powerStateMachine = new PowerStateMachine(loadPackageParam);
                        powerStateMachine.hook();
                        Log.i(TAG, "hook PowerStateMachine", 0);
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage(), 0);
                    }

                    try {
                        DisplayFrameSetting displayFrameSetting = new DisplayFrameSetting(loadPackageParam);
                        displayFrameSetting.hook();
                        Log.i(TAG, "hook DisplayFrameSetting", 0);
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage(), 0);
                    }

                    try {
                        SleepModeControllerNew sleepModeControllerNew = new SleepModeControllerNew(loadPackageParam);
                        sleepModeControllerNew.hook();
                        Log.i(TAG, "hook SleepModeControllerNew", 0);
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage(), 0);
                    }

//                BrightScenarioBase brightScenarioBase = new BrightScenarioBase(loadPackageParam);
//                brightScenarioBase.hook();
//                Log.d(TAG, "hook BrightScenarioBase"0);
//
//                EventsAggregator eventsAggregator = new EventsAggregator(loadPackageParam);
//                eventsAggregator.hook();
//                Log.d(TAG, "hook EventsAggregator"0);
                    try {
                        PowerKeeperApplication app = new PowerKeeperApplication(loadPackageParam);
                        app.hook();
                        Log.i(TAG, "hook PowerKeeperApplication", 0);
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage(), 0);
                    }
                    try {
                        PowerKeeperManager powerKeeperApplication = new PowerKeeperManager(loadPackageParam);
                        powerKeeperApplication.hook();
                        Log.i(TAG, "hook PowerKeeperManager", 0);
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage(), 0);
                    }
//                    tempHook(loadPackageParam);
                    break;

                case ANDROID:
                    Log.i(TAG, "Load Android", 1);

                    try {
                        RecentTasks recentTasks = new RecentTasks(loadPackageParam);
                        recentTasks.hook();
                        Log.i(TAG, "hook Android RecentTasks", 1);
                        Log.i(TAG, "hook Android complete.", 1);
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage(), 1);
                    }
//
//                    try {
//                        Handler handler = new Handler(loadPackageParam);
//                        handler.hook();
//                        Log.i(TAG, "hook Android Handler", 1);
//                    } catch (Exception e) {
//                        Log.i(TAG, e.getMessage(), 1);
//                    }

                    break;
            }
        } catch (Exception e) {
            Log.i(TAG, e.getMessage(), 0);
        }
    }

    private void tempHook(LoadPackageParam loadPackageParam) {
        String[] classes = {
                "com.miui.powerkeeper.statemachine.SleepModeController",
                "com.miui.powerkeeper.p006ai.AIStrategyManager",
                "com.miui.powerkeeper.p006ai.AIStrategyManager",
                "com.miui.powerkeeper.C2203b$C2215l",
                "com.miui.powerkeeper.C2203b$C2215l",
                "com.miui.powerkeeper.WakelockManagerService",
                "com.miui.powerkeeper.provider.PowerKeeperConfigureManager",
                "com.miui.powerkeeper.statemachine.ExtremePowerController",
        };
        String[] methods = {
                "dispose",
                "cancelPreloadActivity",
                "preloadApps",
                "m2019c",
                "disable",
                "m2019c",
                "onDestroy",
                "onDestory",
                "disposeInternal",
        };
        Object[] argsList = {
                new Object[]{},
                new Object[]{},
                new Object[]{Bundle.class},
                new Object[]{},
                new Object[]{},
                new Object[]{},
                new Object[]{},
                new Object[]{},
        };
        for (int i = 0; i < classes.length; i++) {
            XposedHelpers.findAndHookMethod(
                    classes[i],
                    loadPackageParam.classLoader,
                    methods[i],
                    argsList[i],
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            Log.d(TAG, "->" + param.method.getName() + "()", 0);
                            Log.d(TAG, android.util.Log.getStackTraceString(new Throwable()), 0);
                        }
                    }
            );
        }
    }

    private void initMIUIUtil(LoadPackageParam loadPackageParam) {
        PackageUtil.setLoadPackageParam(loadPackageParam);
        ProcessManager.setLoadPackageParam(loadPackageParam);
        FreezeBinder.setLoadPackageParam(loadPackageParam);
        MilletUidObserver.setLoadPackageParam(loadPackageParam);
        MilletUtils.setLoadPackageParam(loadPackageParam);
        BluetoothEvent.setLoadPackageParam(loadPackageParam);
    }
}
