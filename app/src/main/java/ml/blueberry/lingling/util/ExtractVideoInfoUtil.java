package ml.blueberry.lingling.util;

/**
 * Created by lzb on 2017/6/26.
 */

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExtractVideoInfoUtil {
    private MediaMetadataRetriever mMetadataRetriever;

    public ExtractVideoInfoUtil() {
        mMetadataRetriever = new MediaMetadataRetriever();
    }
    public void setDataSource(String path){
        mMetadataRetriever.setDataSource(path);
    }
    public int getVideoWidth() {
        String w = mMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        int width = -1;
        if (!TextUtils.isEmpty(w)) {
            width = Integer.valueOf(w);
        }
        return width;
    }

    public int getVideoHeight() {
        String h = mMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        int height = -1;
        if (!TextUtils.isEmpty(h)) {
            height = Integer.valueOf(h);
        }
        return height;
    }

    /**
     * 获取视频的关键一帧的缩略图
     * @return 图片的路径
     */
    public Bitmap extractFrames() {
        return mMetadataRetriever.getFrameAtTime();
    }

    /**
     * 暂时保留
     *
     * @param bitmap
     * @param name
     * @param outPutFileDirPath
     * @return path
     */
    private String saveImageToSD(Bitmap bitmap, String name, String outPutFileDirPath) {
        if (bitmap == null) {
            return "";
        }
        File appDir = new File(outPutFileDirPath);
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
        return file.getAbsolutePath();
    }

    /***
     * 获取视频的长度时间
     *
     * @return String 毫秒
     */
    public String getVideoLength() {
        return mMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
    }
    /**
     * 获取视频旋转角度
     *
     * @return
     */
    public int getVideoDegree() {
        int degree = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            String degreeStr = mMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
            if (!TextUtils.isEmpty(degreeStr)) {
                degree = Integer.valueOf(degreeStr);
            }
        }
        return degree;
    }

    public String getMimetype() {
        String mimeType= mMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
        //TODO 使用split
        //        String[] ss=mimeType.toLowerCase().split("/");
        //        return ss[ss.length-1];
        if(mimeType.endsWith("mpeg")){
            return "mpeg";
        }else if(mimeType.endsWith("vivo")){
            return "vivo";
        }
        return "mp4";
    }

    public void release() {
        if (mMetadataRetriever != null) {
            mMetadataRetriever.release();
        }
    }


}
