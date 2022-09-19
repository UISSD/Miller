package com.github.uissd.miller.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.robv.android.xposed.XposedBridge;

public class Log {

    private static final String TAG = "MILLER.Log";
    private static final boolean ENABLE = true;
    private static final String LOG_DIR = "/storage/emulated/0/MILLER/Logs";
    private static final Date DATE = new Date();
    private static final SimpleDateFormat MSG_DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.CHINA);
    private static final SimpleDateFormat FILE_NAME_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private static BufferedWriter BW;

    public static void i(String tag, String msg) {
        if (ENABLE) {
//            android.util.Log.i(tag, msg);
            XposedBridge.log(tag + ": " + msg);
            writeToFile(MSG_DATE_FORMAT.format(DATE) + " [INFO] " + tag + ": " + msg + "\n");
        }
    }

    public static void d(String tag, String msg) {
        if (ENABLE) {
//            android.util.Log.d(tag, msg);
            writeToFile(MSG_DATE_FORMAT.format(DATE) + " [DEBUG] " + tag + ": " + msg + "\n");
        }
    }

    public static void v(String tag, String msg) {
        if (ENABLE) {
//            android.util.Log.v(tag, msg);
            writeToFile(MSG_DATE_FORMAT.format(DATE) + " [VERBOSE] " + tag + ": " + msg + "\n");
        }
    }

    private synchronized static void writeToFile(String log) {
        try {
            if (BW == null) {
                String filePath = LOG_DIR + "/log_" + FILE_NAME_DATE_FORMAT.format(new Date()) + ".log";
                File logDir = new File(LOG_DIR);
                if (!logDir.exists() && !logDir.mkdirs()) {
                    XposedBridge.log(TAG + ": logDir doesn't exist and mkdirs failed");
                    XposedBridge.log(log);
                    return;
                }
                try {
                    BW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true)));
                } catch (IOException e) {
                    XposedBridge.log(e);
                    XposedBridge.log(log);
                    return;
                }
            }
            BW.write(log);
        } catch (IOException e) {
            XposedBridge.log(e);
            XposedBridge.log(log);
        }
    }
}
