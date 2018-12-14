package e.library;


import android.os.Bundle;
import android.view.WindowManager;

import butterknife.ButterKnife;


public abstract class BaseActivity extends SwipeActivity{
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));
     //StatusBarUtil.getStatusBarLightMode(this.getWindow());

        //Android中软键盘弹出时底部布局上移问题(解决方式之一)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initData();
        initView();
        doFirstRequest();
    }


    @Override
    public void onStart() {
        super.onStart();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    protected void initView() {
    }
    protected void initData() {
    }


    protected void doFirstRequest() {

    }

    protected abstract int getLayoutId();


}
