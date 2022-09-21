package com.github.uissd.miller.util;

import com.github.uissd.miller.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import de.robv.android.xposed.XposedBridge;

public class Log extends TimerTask {

    private static final String TAG = "Miller.Log";
    private static final boolean ENABLE = BuildConfig.DEBUG;
    private static final String LOG_DIR = "/storage/emulated/0/MILLER/Logs";
    private static final SimpleDateFormat MSG_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
    private static final SimpleDateFormat FILE_NAME_DATE_FORMAT = new SimpleDateFormat("yyyy_MM_dd", Locale.CHINA);
    private static BufferedWriter BW;

    public static void i(String tag, String msg, int code) {
        if (ENABLE) {
            if (code == 0) {
                writeToFile(MSG_DATE_FORMAT.format(new Date()) + " [INFO] " + tag + ": " + msg + "\n");
            }
            android.util.Log.i(tag, msg);
            XposedBridge.log(tag + ": " + msg);
        }
    }

    public static void d(String tag, String msg, int code) {
        if (ENABLE) {
            if (code == 0) {
                writeToFile(MSG_DATE_FORMAT.format(new Date()) + " [DEBUG] " + tag + ": " + msg + "\n");
            }
            android.util.Log.d(tag, msg);
        }
    }

    public static void v(String tag, String msg, int code) {
        if (ENABLE) {
            if (code == 0) {
                writeToFile(MSG_DATE_FORMAT.format(new Date()) + " [VERBOSE] " + tag + ": " + msg + "\n");
            }
            android.util.Log.v(tag, msg);
        }
    }

    private synchronized static void writeToFile(String log) {
        try {
            if (BW == null && !openFile()) {
                XposedBridge.log(log);
                return;
            }
            BW.write(log);
        } catch (IOException e) {
            XposedBridge.log(e);
            XposedBridge.log(log);
        }
    }

    private static boolean openFile() {
        String filePath = LOG_DIR + "/log_" + FILE_NAME_DATE_FORMAT.format(new Date()) + ".log";
        File logDir = new File(LOG_DIR);
        if (!logDir.exists() && !logDir.mkdirs()) {
            XposedBridge.log(TAG + ": logDir doesn't exist and mkdirs failed");
            return false;
        }
        try {
            BW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true)));
        } catch (IOException e) {
            XposedBridge.log(e);
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        if (openFile()) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
            System.out.println(calendar.getTime());
            new Timer().schedule(this, calendar.getTime());
        } else {
            BW = null;
        }
    }
}
