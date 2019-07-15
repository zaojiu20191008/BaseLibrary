package com.niubility.library.tools;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 *
 */
public class DownloadTask extends AsyncTask<String, Integer, String> {


    ProgressDialog progressDialog;
    File file;

    DownloadTaskCallback downloadTaskCallback;

    public void setDownloadTaskCallback(DownloadTaskCallback downloadTaskCallback) {
        this.downloadTaskCallback = downloadTaskCallback;
    }

    public DownloadTask(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    @Override
    protected String doInBackground(String... params) {

        URL url;
        HttpURLConnection conn;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;

        try {
            url = new URL(params[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);

            int fileLength = conn.getContentLength();
            bis = new BufferedInputStream(conn.getInputStream());
            String fileName = Environment.getExternalStorageDirectory().getPath() + "/zaojiu/DownTask/update.apk";
            file = new File(fileName);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            byte data[] = new byte[4 * 1024];
            long total = 0;
            int count;
            while ((count = bis.read(data)) != -1) {
                total += count;
                publishProgress((int) (total * 100 / fileLength));
                fos.write(data, 0, count);
                fos.flush();
            }
            fos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }


    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);

        progressDialog.setProgress(progress[0]);
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (downloadTaskCallback != null) {
            downloadTaskCallback.onFinishDownload(file);
        }

//        openFile(file);//打开安装apk文件操作
        progressDialog.dismiss();//关闭对话框
    }


    private void openFile(File file) {
//        if (file!=null){
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//            TaskListActivity.this.startActivity(intent);
//        }
    }



    /*private void showDownloadProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在下载...");
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        String downloadUrl = "http://ac-edNxPKqQ.clouddn.com/80xxxxxxxebcefda.apk";
        new DownloadAPK(progressDialog).execute(downloadUrl);
    }*/


    public interface DownloadTaskCallback{
        void onFinishDownload(File file);
    }

}
