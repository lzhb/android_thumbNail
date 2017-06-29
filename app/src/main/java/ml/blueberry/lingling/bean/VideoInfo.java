package ml.blueberry.lingling.bean;

/**
 * Created by lzb on 2017/6/26.
 */
public class VideoInfo {
    private String title;
    private String mimeType;//视频格式
    private String size;
    private long duration; //毫秒
    private String resolutionRation;//分辨率
    private String filePath;
    private String thumbPath;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }
}
