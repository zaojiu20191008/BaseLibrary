package com.niubility.demo.mvp.base;

import android.content.Context;
import androidx.annotation.NonNull;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public class LocalAppGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);

//        int memoryCacheSizeBytes = 1024 * 1024 * 20; // 20mb
//        int diskCacheSizeBytes = 1024 * 1024 * 100;  //100 MB
//        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes))
//                .setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));

    }

    @Override
    public boolean isManifestParsingEnabled() {
        return super.isManifestParsingEnabled();

//        return false;
    }
}
