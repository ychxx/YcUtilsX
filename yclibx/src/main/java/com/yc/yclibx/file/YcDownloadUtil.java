package com.yc.yclibx.file;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.yc.yclibx.R;
import com.yc.yclibx.comment.YcEmpty;
import com.yc.yclibx.comment.YcLog;
import com.yc.yclibx.comment.YcResources;

import org.xutils.common.Callback;
import org.xutils.x;

import java.io.File;

/**
 * 下载
 */
public class YcDownloadUtil {

    public static void downloadApk(Context context, final String url, String apkPath, DownloadCall downloadCall) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressDrawable(YcResources.getDrawable(R.drawable.yc_progessbar));
        progressDialog.setMessage("正在下载中...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressNumberFormat("");
        downloadApk(url, apkPath, progressDialog, downloadCall);
    }

    public static void downloadApk(final String url, String savePath, ProgressDialog progressDialog, DownloadCall downloadCall) {
        String urlEncode = Uri.encode(url, ":._-$,;~()/+-=*");//转码防止中文下载地址导致无法下载
        org.xutils.http.RequestParams requestParams = new org.xutils.http.RequestParams(urlEncode);  // 下载地址
        requestParams.setSaveFilePath(savePath); // 为RequestParams设置文件下载后的保存路径
//        requestParams.setAutoRename(false); // 下载完成后自动为文件命名

        x.http().get(requestParams, new Callback.ProgressCallback<File>() {

            @Override
            public void onSuccess(File result) {
                YcLog.e("成功");
                downloadCall.onSuccess(result);
                if (progressDialog != null)
                    progressDialog.dismiss();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                YcLog.e("下载失败");
                downloadCall.onFail("下载失败");
                if (progressDialog != null)
                    progressDialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                YcLog.e("取消下载");
                downloadCall.onFail("下载被取消了");
                if (progressDialog != null)
                    progressDialog.dismiss();
            }

            @Override
            public void onFinished() {
                YcLog.e("结束下载");
                if (progressDialog != null)
                    progressDialog.dismiss();
            }

            @Override
            public void onWaiting() {
                // 网络请求开始的时候调用
                YcLog.e("等待下载");
            }

            @Override
            public void onStarted() {
                // 下载的时候不断回调的方法
                YcLog.e("开始下载");
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                // 当前的下载进度和文件总大小
                YcLog.e("正在下载中......total：" + total + " current：" + current);
                if (progressDialog != null) {
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setMessage("正在下载中......");
                    progressDialog.setMax((int) total);
                    progressDialog.setProgress((int) current);
                    if (!progressDialog.isShowing()) {
                        progressDialog.show();
                    }
                }
            }
        });
    }

    public static void downloadImg(final String url, DownloadCall downloadCall) {
        if (YcEmpty.isEmpty(url)) {
            YcLog.e("下载的图片地址为空");
            return;
        }
        String[] img = url.split("/");
        String imgName;
        if (img == null || img.length <= 0) {
            imgName = url;
        } else {
            imgName = img[img.length - 1];
        }
        org.xutils.http.RequestParams requestParams = new org.xutils.http.RequestParams(url);  // 下载地址

        final String imgPath = Environment.getExternalStorageDirectory() + "/YcUtils/" + File.separator + imgName;
        File imgFile = new File(imgPath);
        if (imgFile.exists()) {
            downloadCall.onSuccess(imgFile);
            return;
        } else {
            YcFileUtils.createFile(imgPath);
        }
        requestParams.setSaveFilePath(imgPath); // 为RequestParams设置文件下载后的保存路径
//        requestParams.setAutoRename(false); // 下载完成后自动为文件命名
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {

            @Override
            public void onSuccess(File result) {
                YcLog.e("成功");
                downloadCall.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                YcLog.e("下载失败");
                YcFileUtils.delFiel(imgPath);
                downloadCall.onFail("下载失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                YcLog.e("取消下载");
                YcFileUtils.delFiel(imgPath);
                downloadCall.onFail("下载被取消了");
            }

            @Override
            public void onFinished() {
                YcLog.e("结束下载");
            }

            @Override
            public void onWaiting() {
                // 网络请求开始的时候调用
                YcLog.e("等待下载");
            }

            @Override
            public void onStarted() {
                // 下载的时候不断回调的方法
                YcLog.e("开始下载");
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                // 当前的下载进度和文件总大小
                YcLog.e("正在下载中......total：" + total + " current：" + current);
            }
        });
    }

    public interface DownloadCall {
        void onSuccess(File result);

        void onFail(String msg);
    }
}
