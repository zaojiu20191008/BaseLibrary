package com.niubility.library.widget.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

import com.niubility.library.tools.DownloadTask;
import com.niubility.library.tools.DownloadTask.DownloadTaskCallback;
import com.niubility.library.utils.ToastUtils;

public class DownloadProgressDialog extends ProgressDialog {

    private String downloadUrl;

    private DownloadTaskCallback downloadTaskCallback;

    public void setDownloadTaskCallback(DownloadTaskCallback downloadTaskCallback) {
        this.downloadTaskCallback = downloadTaskCallback;
    }

    public DownloadProgressDialog(Context context) {
        super(context);
    }

    public DownloadProgressDialog(Context context, int theme) {
        super(context, theme);
    }


    public DownloadProgressDialog(Context context, String downloadUrl) {
        super(context);

        this.downloadUrl = downloadUrl;
    }

    /**
     * 必须设置有效的下载URL
     *
     * @param downloadUrl
     */
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public void show() {

        if (TextUtils.isEmpty(downloadUrl)) {
            ToastUtils.showToast(getContext(), "无效的DownloadURL");

            return;
        }

        super.show();
        DownloadTask downloadTask = new DownloadTask(this);
        downloadTask.setDownloadTaskCallback(downloadTaskCallback);
        downloadTask.execute(downloadUrl);

    }
}
