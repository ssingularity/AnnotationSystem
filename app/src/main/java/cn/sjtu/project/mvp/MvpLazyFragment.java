package cn.sjtu.project.mvp;

import cn.sjtu.project.common.MyLazyFragment;
import cn.sjtu.project.mvp.proxy.IMvpPresenterProxy;
import cn.sjtu.project.mvp.proxy.MvpPresenterProxyImpl;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/11/17
 *    desc   : MVP 懒加载 Fragment 基类
 */
public abstract class MvpLazyFragment extends MyLazyFragment implements IMvpView {

    private IMvpPresenterProxy mMvpProxy;

    @Override
    protected void initFragment() {
        mMvpProxy = createPresenterProxy();
        mMvpProxy.bindPresenter();
        super.initFragment();
    }

    protected IMvpPresenterProxy createPresenterProxy() {
        return new MvpPresenterProxyImpl(this);
    }

    @Override
    public void onDestroy() {
        if (mMvpProxy != null) {
            mMvpProxy.unbindPresenter();
        }
        super.onDestroy();
    }
}