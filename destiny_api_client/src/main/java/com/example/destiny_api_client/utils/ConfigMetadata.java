package com.example.destiny_api_client.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class ConfigMetadata {
    private final Context context;

    public ConfigMetadata(Context context) {
        this.context = context;
    }

    public String getData(final String attrName) throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        return applicationInfo.metaData.get(attrName).toString();
    }
}
