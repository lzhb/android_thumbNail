package ml.blueberry.lingling.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ml.blueberry.lingling.bean.PictureInfo;


/**
 * Created by lzb on 2017/6/28.
 */

public class CollectAllPictureUtil {
    private int mDefaultWidth=300;
    private int mDefaultHeight=300;

    public CollectAllPictureUtil() {
    }

    public void setDefaultWidth(int defaultWidth) {
        mDefaultWidth = defaultWidth;
    }

    public void setDefaultHeight(int defaultHeight) {
        mDefaultHeight = defaultHeight;
    }

    public List<PictureInfo> getPictureInfoListFromAll(Context context){
        List<PictureInfo> videoInfos = new ArrayList<>();
        String projection[] = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.MIME_TYPE,MediaStore.Images.Media.SIZE
        };
        Uri videoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor  = context.getContentResolver().query(videoUri, projection, null, null, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        Log.d("ling", "getPictureInfoListFromAll count : "+count);
        for (int i = 0; i < 20; i++) {
            PictureInfo picInfo = new PictureInfo();
            picInfo.setAbsolutePath(cursor.getString(0));
            picInfo.setTitle(cursor.getString(1));
            picInfo.setMimeType(formatMimeType(cursor.getString(2)));
            picInfo.setSize(formatSizeOfFile(cursor.getString(3)));
            videoInfos.add(picInfo);
            cursor.moveToNext();
        }

        return videoInfos;
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

    private String formatMimeType(String mimeType){
        String type="jpg";
        if(mimeType.endsWith("jpg")){
            return type;
        }else if(mimeType.endsWith("jpeg")){
            return "jpeg";
        }else if(mimeType.endsWith("png")){
            return "png";
        }
        return type;
    }
    /**
     * 根据指定的图像路径和大小来获取缩略图
     * @param width 指定输出图像的宽度
     * @param height 指定输出图像的高度
     * @return 生成的缩略图
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
}
