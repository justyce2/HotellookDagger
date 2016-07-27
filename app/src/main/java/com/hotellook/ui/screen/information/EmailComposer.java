package com.hotellook.ui.screen.information;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.dependencies.HotellookComponent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Iterator;
import java.util.zip.GZIPOutputStream;
import me.zhanghai.android.materialprogressbar.BuildConfig;

public class EmailComposer {
    public static final String FILE_NAME = "technical report.gzip";
    public static final String HOTELLOOK_EMAIL = "android@hotellook.ru";

    public void sendFeedbackEmail(Context context, int rating, String message) {
        try {
            StringBuilder sb = new StringBuilder().append(context.getString(C1178R.string.about_mail_info)).append("\n").append("Device: " + Build.MODEL).append("\n").append("Android version: " + VERSION.RELEASE).append("\n").append("Application version: " + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName).append("\n").append("Token: " + getComponent().getHotellookClient().getToken()).append("\n").append("IP: " + getIPAddresses()).append("\n\n");
            String subject = context.getString(C1178R.string.about_mail_subject);
            if (rating != 0) {
                subject = subject + ": " + context.getResources().getQuantityString(C1178R.plurals.email_stars, rating, new Object[]{Integer.valueOf(rating)});
            }
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("plain/text");
            intent.putExtra("android.intent.extra.EMAIL", new String[]{HOTELLOOK_EMAIL});
            intent.putExtra("android.intent.extra.SUBJECT", subject);
            if (message != null) {
                intent.putExtra("android.intent.extra.TEXT", message);
            }
            intent.putExtra("android.intent.extra.STREAM", getFileWithTrackedSearches(context, sb.toString()));
            context.startActivity(Intent.createChooser(intent, context.getString(C1178R.string.about_mail_title)));
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getIPAddresses() {
        try {
            Iterator it = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
            if (it.hasNext()) {
                return Collections.list(((NetworkInterface) it.next()).getInetAddresses()).toString();
            }
        } catch (Exception e) {
        }
        return BuildConfig.FLAVOR;
    }

    private HotellookComponent getComponent() {
        return HotellookApplication.getApp().getComponent();
    }

    private Uri getFileWithTrackedSearches(Context context, String additionalInfo) {
        try {
            createFile(context, FILE_NAME, additionalInfo + getComponent().getSearchTracker().getSearchesJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.parse("content://com.hotellook.provider" + File.separator + FILE_NAME);
    }

    private void createFile(Context context, String fileName, String content) throws IOException {
        File cacheFile = new File(context.getCacheDir() + File.separator + fileName);
        cacheFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(cacheFile);
        OutputStreamWriter osw = new OutputStreamWriter(new GZIPOutputStream(fos), "UTF8");
        osw.write(content);
        osw.flush();
        osw.close();
        fos.close();
    }
}
