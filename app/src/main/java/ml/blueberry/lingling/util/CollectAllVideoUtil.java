package ml.blueberry.lingling.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
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


    public CollectAllVideoUtil() {
        mExtractVideoInfoUtil=new ExtractVideoInfoUtil();
        retriever = new MediaMetadataRetriever();
    }

    public List<VideoInfo> getVideoInfoList(String dirPath ) {
        List<VideoInfo> videoInfos = new ArrayList<>();
        File dir = new File(dirPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (fileName.endsWith("mp4") ) {
                    String strFileName = files[i].getAbsolutePath();
                    mExtractVideoInfoUtil.setDataSource(strFileName);
                    VideoInfo vInfo = new VideoInfo();
                    vInfo.setTitle(fileName);
                    int width = mExtractVideoInfoUtil.getVideoWidth();
                    int height = mExtractVideoInfoUtil.getVideoHeight();
                    String ratio = Integer.toString(width) + "x" + Integer.toString(height);
                    vInfo.setResolutionRation(ratio);
                    vInfo.setMimeType(mExtractVideoInfoUtil.getMimetype());
                    long fileS = files[i].length();
                    vInfo.setSize(formatSizeOfFile(fileS));
                    vInfo.setDuration(mExtractVideoInfoUtil.getVideoLength());
                    vInfo.setThumbNail(mExtractVideoInfoUtil.extractFrames());
                    vInfo.setUrl(files[i].getAbsolutePath());
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
                , MediaStore.Video.Media.RESOLUTION,MediaStore.Video.Media.SIZE
        };
        Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor  = context.getContentResolver().query(videoUri, projection, null, null, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        for (int i = 0; i < count; i++) {

            VideoInfo vInfo = new VideoInfo();
            vInfo.setUrl(cursor.getString(0));
            vInfo.setTitle(cursor.getString(1));
            vInfo.setDuration(cursor.getString(2));
            vInfo.setMimeType(cursor.getString(3));
            vInfo.setResolutionRation(cursor.getString(4));
            vInfo.setSize(formatSizeOfFile(cursor.getString(5)));
            vInfo.setThumbNail(getThumbnail(cursor.getString(0)));
            videoInfos.add(vInfo);
            cursor.moveToNext();
        }

        return videoInfos;
    }

    private Bitmap getThumbnail(String path){
        retriever.setDataSource(path);
        Bitmap bitmap = retriever.getFrameAtTime();
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
