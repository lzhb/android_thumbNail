package ml.blueberry.lingling.bean;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by Administrator on 2017/6/28.
 */
public class PictureInfo {
    private String title;
    private Uri uri;//路径
    private Bitmap thumbNail;//缩略图
    private String mimeType;//格式
    private String size;

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Bitmap getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(Bitmap thumbNail) {
        this.thumbNail = thumbNail;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}