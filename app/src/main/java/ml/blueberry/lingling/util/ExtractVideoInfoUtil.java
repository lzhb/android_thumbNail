package ml.blueberry.lingling.util;

/**
 * Created by lzb on 2017/6/26.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
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
     * @return
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
    /**
     * 获取缩略图
     * @param imagePath:文件路径
     * @param width:缩略图宽度
     * @param height:缩略图高度
     * @return
     */
    public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
        int h = options.outHeight;//获取图片高度
        int w = options.outWidth;//获取图片宽度
        int scaleWidth = w / width; //计算宽度缩放比
        int scaleHeight = h / height; //计算高度缩放比
        int scale = 1;//初始缩放比
        if (scaleWidth < scaleHeight) {//选择合适的缩放比
            scale = scaleWidth;
        } else {
            scale = scaleHeight;
        }
        if (scale <= 0) {//判断缩放比是否符合条件
            scale = 1;
        }
        options.inSampleSize = scale;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把inJustDecodeBounds 设为 false
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
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
        return mMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
    }

    public void release() {
        if (mMetadataRetriever != null) {
            mMetadataRetriever.release();
        }
    }


}
