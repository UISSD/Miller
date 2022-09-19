package com.github.uissd.miller;

import com.github.uissd.miller.hook.android.RecentTasks;
import com.github.uissd.miller.hook.miui.powerkeeper.DisplayFrameSetting;
import com.github.uissd.miller.hook.miui.powerkeeper.PowerKeeperApplication;
import com.github.uissd.miller.hook.miui.powerkeeper.PowerStateMachine;
import com.github.uissd.miller.hook.miui.powerkeeper.SleepModeControllerNew;
import com.github.uissd.miller.util.Log;
import com.github.uissd.miller.util.miui.FreezeBinder;
import com.github.uissd.miller.util.miui.MilletUidObserver;
import com.github.uissd.miller.util.miui.MilletUtils;
import com.github.uissd.miller.util.miui.PackageUtil;
import com.github.uissd.miller.util.miui.ProcessManager;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Hook implements IXposedHookLoadPackage {

    private static final String TAG = "MILLER.Hook";
    private static final String COM_MIUI_POWERKEEPER = "com.miui.powerkeeper";
    private static final String ANDROID = "android";

    @Override
    public void handleLoadPackage(LoadPackageParam loadPackageParam) {
        Log.i(TAG, "Loading... " + loadPackageParam.packageName);
        try {
            switch (loadPackageParam.packageName) {
                case COM_MIUI_POWERKEEPER:
                    Log.i(TAG, "Load PowerKeeper");
                    initMIUIUtil(loadPackageParam);
                    Log.i(TAG, "initUtil");

                    if (MilletUtils.getVersionTwo()) {
                        Log.i(TAG, "is Millet v2.");
                    } else {
                        Log.i(TAG, "not Millet v2.");
                    }

                    try {
                        PowerStateMachine powerStateMachine = new PowerStateMachine(loadPackageParam);
                        powerStateMachine.hook();
                        Log.i(TAG, "hook PowerStateMachine");
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage());
                    }

                    try {
                        DisplayFrameSetting displayFrameSetting = new DisplayFrameSetting(loadPackageParam);
                        displayFrameSetting.hook();
                        Log.i(TAG, "hook DisplayFrameSetting");
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage());
                    }

                    try {
                        SleepModeControllerNew sleepModeControllerNew = new SleepModeControllerNew(loadPackageParam);
                        sleepModeControllerNew.hook();
                        Log.i(TAG, "hook SleepModeControllerNew");
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage());
                    }

//                BrightScenarioBase brightScenarioBase = new BrightScenarioBase(loadPackageParam);
//                brightScenarioBase.hook();
//                Log.d(TAG, "hook BrightScenarioBase");
//
//                EventsAggregator eventsAggregator = new EventsAggregator(loadPackageParam);
//                eventsAggregator.hook();
//                Log.d(TAG, "hook EventsAggregator");
                    try {
                        PowerKeeperApplication app = new PowerKeeperApplication(loadPackageParam);
                        app.hook();
                        Log.i(TAG, "hook PowerKeeperApplication");
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage());
                    }
                    break;
                case ANDROID:
                    Log.i(TAG, "Load Android");

                    try {
                        RecentTasks recentTasks = new RecentTasks(loadPackageParam);
                        recentTasks.hook();
                        Log.i(TAG, "hook Android RecentTasks");
                        Log.i(TAG, "hook Android complete.");
                    } catch (Exception e) {
                        Log.i(TAG, e.getMessage());
                    }
                    break;
            }
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
    }

    private void initMIUIUtil(LoadPackageParam loadPackageParam) {
        PackageUtil.setLoadPackageParam(loadPackageParam);
        ProcessManager.setLoadPackageParam(loadPackageParam);
        FreezeBinder.setLoadPackageParam(loadPackageParam);
        MilletUidObserver.setLoadPackageParam(loadPackageParam);
        MilletUtils.setLoadPackageParam(loadPackageParam);
    }
}
