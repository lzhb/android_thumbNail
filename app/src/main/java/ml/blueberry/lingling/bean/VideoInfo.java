package ml.blueberry.lingling.bean;

import android.graphics.Bitmap;

/**
 * Created by lzb on 2017/6/26.
 */
public class VideoInfo {
    private String title;
    private String mimeType;//视频格式
    private String size;
    private String duration;
    private String resolutionRation;//分辨率
    private Bitmap thumbNail;//缩率图
    private String url;//路径

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResolutionRation() {
        return resolutionRation;
    }

    public void setResolutionRation(String resolutionRation) {
        this.resolutionRation = resolutionRation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Bitmap getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(Bitmap thumbNail) {
        this.thumbNail = thumbNail;
    }

}
