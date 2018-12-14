package e.hanglungdemo.view.home.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;
import e.hanglungdemo.R;
import e.hanglungdemo.view.adapter.TraceListAdapter;
import e.hanglungdemo.view.bean.TraceBean;
import e.library.BaseActivity;

/**
 * 订单跟踪
 */
public class TrackingActivity extends BaseActivity {
    @Bind(R.id.title_title)
    TextView trackTitle;
    @Bind(R.id.lvTrace)
    ListView lvTrace;
    private List<TraceBean> traceBeanList = new ArrayList<>(10);
    private TraceListAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_tracking;
    }
    @Override
    protected void initView() {
        super.initView();
        trackTitle.setTextColor(getResources().getColor(R.color.white));
        trackTitle.setText(getIntent().getStringExtra("title"));
    }
    @Override
    protected void initData() {
        super.initData();
        // 模拟一些假的数据
        traceBeanList.add(new TraceBean("2018年9月2日 上午12:04:01", "在湖北武汉洪山区光谷公司长江社区便民服务站进行签收扫描，快件已被 已签收 签收"));
        traceBeanList.add(new TraceBean("2018年9月2日 上午9:57:25", "在湖北武汉洪山区光谷公司长江社区便民服务站进行派件扫描；派送业务员：老王；联系电话：17846921506"));
        traceBeanList.add(new TraceBean("2018年9月1日 下午4:43:29", "在湖北武汉洪山区光谷公司进行快件扫描，将发往：湖北武汉洪山区光谷公司长江社区便民服务站"));
        traceBeanList.add(new TraceBean("2018年9月1日 上午9:9:21", "从湖北武汉分拨中心发出，本次转运目的地：湖北武汉洪山区光谷公司"));
        traceBeanList.add(new TraceBean("2018年9月1日 上午1:53:14", "在湖南长沙分拨中心进行装车扫描，即将发往：湖北武汉分拨中心"));
        traceBeanList.add(new TraceBean("2018年9月1日 上午1:50:18", "在分拨中心湖南长沙分拨中心进行称重扫描"));
        traceBeanList.add(new TraceBean("2018年9月2日 上午9:27:58", "在湖南隆回县公司进行到件扫描"));
        traceBeanList.add(new TraceBean("2018年9月1日 上午9:9:21", "从湖北武汉分拨中心发出，本次转运目的地：湖北武汉洪山区光谷公司"));
        traceBeanList.add(new TraceBean("2018年9月1日 上午1:53:14", "在湖南长沙分拨中心进行装车扫描，即将发往：湖北武汉分拨中心"));
        traceBeanList.add(new TraceBean("2018年9月1日 上午1:50:18", "在分拨中心湖南长沙分拨中心进行称重扫描"));
        traceBeanList.add(new TraceBean("2018年9月2日 上午9:27:58", "在湖南隆回县公司进行到件扫描"));
        adapter = new TraceListAdapter(this, traceBeanList);
        lvTrace.setAdapter(adapter);

    }
    @OnClick({R.id.title_back,R.id.re_track_orders})
    public void click(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.re_track_orders:
                startActivity(new Intent(this,SignInActivity.class));
                break;

        }
    }
}
