package e.hanglungdemo.view.home.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import e.hanglungdemo.R;
import e.hanglungdemo.view.adapter.GridImageAdapter;
import e.hanglungdemo.view.bean.PictureBean;
import e.library.BaseActivity;
import e.library.BaseDialog;
import e.library.T;

/**
 * 确认签收
 */
public class SignInActivity extends BaseActivity {

    @Bind(R.id.title_title)
    TextView trackTitle;
    @Bind(R.id.recycler)
    RecyclerView recyclerView;
    @Bind(R.id.et_lob)
    EditText etLob;
    @Bind(R.id.box_cargo_damage)
    CheckBox damageBox;
    @Bind(R.id.box_goods_lost)
    CheckBox lostBox;
    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter adapter;
    private int maxSelectNum = 9;

    private List<PictureBean> pictureBeanList = new ArrayList<>();
    private int themeId;
    private int chooseMode = PictureMimeType.ofAll();
    private BaseDialog dialog;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void initView() {
        super.initView();

        trackTitle.setTextColor(getResources().getColor(R.color.white));
        trackTitle.setText("确认签收");
        String etLobString=etLob.getText().toString().trim();

    }

    @OnClick({R.id.title_back, R.id.box_cargo_damage, R.id.box_goods_lost,
            R.id.tv_receiving,R.id.et_lob
    })
    public void click(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.box_cargo_damage:

                break;
            case R.id.box_goods_lost:

                break;
            case R.id.tv_receiving:
                T.showShortToast("你点击了收货功能");
                break;
            case R.id.et_lob:
                etLob.setCursorVisible(true);
                break;
        }
    }
    @Override
    protected void initData() {
        super.initData();
        themeId = R.style.picture_default_style;
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);

    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            showDialog(Gravity.BOTTOM, R.style.Bottom_Top_aniamtion);
//

        }

    };

    private void showDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        dialog = builder.setViewId(R.layout.photo_choose_dialog)
                .setPaddingdp(10, 0, 10, 0)
                .setGravity(grary)
                .setAnimation(animationStyle)
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.but_choose_one, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 进入相册 以下是例子：不需要的api可以不写
                        PictureSelector.create(SignInActivity.this)
                                .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                                .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                                .minSelectNum(1)// 最小选择数量
                                .imageSpanCount(4)// 每行显示个数
                                .selectionMode(true ?
                                        PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
                                .previewImage(true)// 是否可预览图片
                                .previewVideo(false)// 是否可预览视频
                                .enablePreviewAudio(false) // 是否可播放音频
                                .isCamera(true)// 是否显示拍照按钮
                                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                                .enableCrop(false)// 是否裁剪
                                .compress(true)// 是否压缩
                                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                                //.compressSavePath(getPath())//压缩图片保存地址
                                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                                .withAspectRatio(0, 0)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                                .isGif(false)// 是否显示gif图片
                                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                                .circleDimmedLayer(false)// 是否圆形裁剪
                                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                                .openClickSound(false)// 是否开启点击声音
                                .selectionMedia(selectList)// 是否传入已选图片
                                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                                .minimumCompressSize(100)// 小于100kb的图片不压缩
                                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                                //.rotateEnabled(true) // 裁剪是否可旋转图片
                                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                                //.videoQuality()// 视频录制质量 0 or 1
                                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                                //.recordVideoSecond()//录制视频秒数 默认60s
                                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                        dialog.close();
                    }
                })
                .addViewOnClickListener(R.id.but_choose_two, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //  单独拍照
                        PictureSelector.create(SignInActivity.this)
                                .openCamera(chooseMode)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                                .theme(themeId)// 主题样式设置 具体参考 values/styles
                                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                                .minSelectNum(1)// 最小选择数量
                                .selectionMode(true ?
                                        PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
                                .previewImage(true)// 是否可预览图片
                                .previewVideo(false)// 是否可预览视频
                                .enablePreviewAudio(false) // 是否可播放音频
                                .isCamera(true)// 是否显示拍照按钮
                                .enableCrop(false)// 是否裁剪
                                .compress(true)// 是否压缩
                                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                                .withAspectRatio(0, 0)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                                .isGif(false)// 是否显示gif图片
                                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                                .circleDimmedLayer(false)// 是否圆形裁剪
                                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                                .openClickSound(true)// 是否开启点击声音
                                .selectionMedia(selectList)// 是否传入已选图片
                                .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                                //.cropCompressQuality(90)// 裁剪压缩质量 默认为100
                                .minimumCompressSize(100)// 小于100kb的图片不压缩
                                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                                //.rotateEnabled() // 裁剪是否可旋转图片
                                //.scaleEnabled()// 裁剪是否可放大缩小图片
                                //.videoQuality()// 视频录制质量 0 or 1
                                //.videoSecond()////显示多少秒以内的视频or音频也可适用
                                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                        dialog.close();
                    }
                })
                .builder();
        dialog.show();

        Button button = dialog.getView(R.id.but_choose_three);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.close();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {

                        Log.i("图片-----》", media.getPath());
                    }
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

}
