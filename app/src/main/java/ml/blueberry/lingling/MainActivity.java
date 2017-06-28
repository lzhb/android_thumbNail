package ml.blueberry.lingling;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ml.blueberry.lingling.bean.PictureInfo;
import ml.blueberry.lingling.bean.VideoInfo;
import ml.blueberry.lingling.util.CollectAllPictureUtil;
import ml.blueberry.lingling.util.CollectAllVideoUtil;

public class MainActivity extends AppCompatActivity {
    private CollectAllVideoUtil mColletAllVideoutil;
    private CollectAllPictureUtil mColletAllPictureutil;
    private TextView mTextViewInfo;
    private ImageView mImageView;
//    private final String path = Environment.getExternalStorageDirectory() + "/2.mp4";
private final String dirPath = "/storage/emulated/0/1Videoshow/tmp";

    private ListView listView;
    private List<VideoInfo> mListInfo;
    private List<PictureInfo> mPicListInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }
    private void initData() {
        mColletAllVideoutil = new CollectAllVideoUtil();
        mColletAllPictureutil=new CollectAllPictureUtil();

//        mListInfo=mColletAllVideoutil.getVideoInfoList(dirPath);
//        mListInfo=mColletAllVideoutil.getVideoInfoListFromAll(this);
        mPicListInfo=mColletAllPictureutil.getPictureInfoListFromAll(this);
        listView.setAdapter(new PicListViewAdapter(mPicListInfo));

    }

    private void initView() {
        listView=(ListView)this.findViewById(R.id.MyListView);    //将listView与布局对象关联
    }


    public class VideoListViewAdapter extends BaseAdapter {
        View[] itemViews;
        public VideoListViewAdapter(List<VideoInfo> mlistInfo) {
            // TODO Auto-generated constructor stub
            itemViews = new View[mlistInfo.size()];
            for(int i=0;i<mlistInfo.size();i++){
                VideoInfo getInfo=(VideoInfo)mlistInfo.get(i);    //获取第i个对象
                //调用makeItemView，实例化一个Item
                itemViews[i]=makeItemView(getInfo);
            }
        }
        public int getCount() {
            return itemViews.length;
        }
        public View getItem(int position) {
            return itemViews[position];
        }

        public long getItemId(int position) {
            return position;
        }

        //绘制Item的函数
        private View makeItemView(VideoInfo vInfo) {
            LayoutInflater inflater = (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            View itemView = inflater.inflate(R.layout.my_listview_item, null);

            TextView title = (TextView) itemView.findViewById(R.id.item_title);
            title.setText(vInfo.getTitle());
            TextView info = (TextView) itemView.findViewById(R.id.item_info);
            String duration = vInfo.getDuration();
            String ratio = vInfo.getResolutionRation();
            String mimeType = vInfo.getMimeType();
            String size=vInfo.getSize();
            String uri=vInfo.getUri().toString();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("duration:").append(duration).append(" ");
            stringBuilder.append("ratio:").append(ratio).append("  ");
            stringBuilder.append("mimeType:").append(mimeType).append(" ");
            stringBuilder.append("size:").append(size).append(" ");
            stringBuilder.append("uri:").append(uri);

            info.setText(stringBuilder.toString());

            ImageView thumbNail = (ImageView) itemView.findViewById(R.id.item_img);
            thumbNail.setImageBitmap(vInfo.getThumbNail());
            return itemView;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                return itemViews[position];
            return convertView;
        }
    }

    public class PicListViewAdapter extends BaseAdapter {
        View[] itemViews;
        public PicListViewAdapter(List<PictureInfo> mlistInfo) {
            // TODO Auto-generated constructor stub
            itemViews = new View[mlistInfo.size()];
            for(int i=0;i<mlistInfo.size();i++){
                PictureInfo getInfo=(PictureInfo)mlistInfo.get(i);    //获取第i个对象
                //调用makeItemView，实例化一个Item
                itemViews[i]=makeItemView(getInfo);
            }
        }
        public int getCount() {
            return itemViews.length;
        }
        public View getItem(int position) {
            return itemViews[position];
        }

        public long getItemId(int position) {
            return position;
        }

        //绘制Item的函数
        private View makeItemView(PictureInfo picInfo) {
            LayoutInflater inflater = (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            View itemView = inflater.inflate(R.layout.my_listview_item, null);

            TextView title = (TextView) itemView.findViewById(R.id.item_title);
            title.setText(picInfo.getTitle());
            TextView info = (TextView) itemView.findViewById(R.id.item_info);
            String mimeType = picInfo.getMimeType();
            String size=picInfo.getSize();
            String uri=picInfo.getUri().toString();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("mimeType:").append(mimeType).append(" ");
            stringBuilder.append("size:").append(size).append(" ");
            stringBuilder.append("uri:").append(uri);
            info.setText(stringBuilder.toString());
            ImageView thumbNail = (ImageView) itemView.findViewById(R.id.item_img);
            thumbNail.setImageBitmap(picInfo.getThumbNail());
            return itemView;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                return itemViews[position];
            return convertView;
        }
    }

}
