package cn.sjtu.project.mvp;

import android.content.Context;

import cn.sjtu.project.common.MyActivity;
import cn.sjtu.project.mvp.proxy.IMvpPresenterProxy;
import cn.sjtu.project.mvp.proxy.MvpPresenterProxyImpl;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/11/17
 *    desc   : MVP Activity 基类
 */
public abstract class MvpActivity extends MyActivity implements IMvpView {

    private IMvpPresenterProxy mMvpProxy;

    @Override
    public void initActivity() {
        mMvpProxy = createPresenterProxy();
        mMvpProxy.bindPresenter();
        super.initActivity();
    }

    protected IMvpPresenterProxy createPresenterProxy() {
        return new MvpPresenterProxyImpl(this);
    }

    @Override
    protected void onDestroy() {
        mMvpProxy.unbindPresenter();
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onLoading() {
        showLoading();
    }

    @Override
    public void onComplete() {
        showComplete();
    }

    @Override
    public void onEmpty() {
        showEmpty();
    }

    @Override
    public void onError() {
        showError();
    }
}