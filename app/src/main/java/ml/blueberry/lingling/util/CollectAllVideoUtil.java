package ml.blueberry.lingling.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ml.blueberry.lingling.bean.VideoInfo;

/**
 * Created by lzb on 2017/6/26.
 */
public class CollectAllVideoUtil {
    private ExtractVideoInfoUtil mExtractVideoInfoUtil;
    private MediaMetadataRetriever retriever;
private String tempPath =Environment.getExternalStorageDirectory() + "/videoThumb";
    public CollectAllVideoUtil() {
        mExtractVideoInfoUtil=new ExtractVideoInfoUtil();
        retriever = new MediaMetadataRetriever();
    }

    public List<VideoInfo> getVideoInfoListFromVideoPath(String dirPath ) {
        List<VideoInfo> videoInfos = new ArrayList<>();
        File dir = new File(dirPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (fileName.endsWith("mp4") ) {
                    String absolutePath = files[i].getAbsolutePath();
                    mExtractVideoInfoUtil.setDataSource(absolutePath);
                    VideoInfo vInfo = new VideoInfo();
                    vInfo.setTitle(fileName);
                    int width = mExtractVideoInfoUtil.getVideoWidth();
                    int height = mExtractVideoInfoUtil.getVideoHeight();
                    String ratio = Integer.toString(width) + "x" + Integer.toString(height);
                    vInfo.setResolutionRation(ratio);
                    vInfo.setMimeType(mExtractVideoInfoUtil.getMimetype());
                    long fileS = files[i].length();
                    vInfo.setSize(formatSizeOfFile(fileS));
                    vInfo.setDuration(Long.getLong(mExtractVideoInfoUtil.getVideoLength()));
                    vInfo.setFilePath(absolutePath);
                    //暂时不提供指定目录下视频的thumbNail
                    videoInfos.add(vInfo);
                }
            }
        }
        return videoInfos;
    }

    public List<VideoInfo>  getVideoInfoListFromAll(Context context){

        List<VideoInfo> videoInfos = new ArrayList<>();
        String projection[] = {MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DURATION, MediaStore.Video.Media.MIME_TYPE
                , MediaStore.Video.Media.RESOLUTION,MediaStore.Video.Media.SIZE,MediaStore.Video.Media._ID,
        };
        Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor  = context.getContentResolver().query(videoUri, projection, null, null, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        for (int i = 0; i < count; i++) {
            VideoInfo vInfo = new VideoInfo();
            vInfo.setFilePath(cursor.getString(0));
            String fileName = cursor.getString(1);
            vInfo.setTitle(fileName);
            vInfo.setDuration(Long.valueOf(cursor.getString(2)));
            vInfo.setMimeType(cursor.getString(3));
            vInfo.setResolutionRation(cursor.getString(4));
            vInfo.setSize(formatSizeOfFile(cursor.getString(5)));
            if(isExistOfThumbNail(fileName)){
                vInfo.setThumbPath(tempPath+"/"+fileName);
                Log.d("ling", "getVideoInfoListFromAll: 已存在thumbnail"+fileName);
            }else {
                Log.d("ling", "getVideoInfoListFromAll: 不存在thumbnail"+fileName);
                vInfo.setThumbPath(saveImageToSD(getThumbnail(cursor.getString(0)), fileName));
            }
            videoInfos.add(vInfo);
            cursor.moveToNext();
        }
        cursor.close();
        return videoInfos;
    }
    private Boolean isExistOfThumbNail(String name){
        List<VideoInfo> videoInfos = new ArrayList<>();
        File dir = new File(tempPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        for(File f :files){
            if(f.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private String saveImageToSD (Bitmap bitmap, String name) {
        if (bitmap == null) {
            return "";
        }
        File appDir = new File(tempPath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file = new File(appDir, name);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("ling", "saveImageToSD: "+file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    private Bitmap getThumbnail(String path){
        retriever.setDataSource(path);
        Bitmap bitmap = retriever.getFrameAtTime(2000);
        return bitmap;
    }
    private String formatSizeOfFile(String fileSize){
        String s;
        DecimalFormat df = new DecimalFormat("#.00");
        long size=Long.valueOf(fileSize);
        if (size < 1024) {
            s = df.format((double) size) + "BT";
        } else if (size < 1048576) {
            s = df.format((double) size / 1024) + "KB";
        } else if (size < 1073741824) {
            s = df.format((double) size / 1048576) + "MB";
        } else {
            s = df.format((double) size / 1073741824) + "GB";
        }
        return s;
    }
    private String formatSizeOfFile(long size){
        String s;
        DecimalFormat df = new DecimalFormat("#.00");
        if (size < 1024) {
            s = df.format((double) size) + "BT";
        } else if (size < 1048576) {
            s = df.format((double) size / 1024) + "KB";
        } else if (size < 1073741824) {
            s = df.format((double) size / 1048576) + "MB";
        } else {
            s = df.format((double) size / 1073741824) + "GB";
        }
        return s;
    }


}
