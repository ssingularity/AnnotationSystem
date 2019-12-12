package cn.sjtu.project.ui.activity;

import android.graphics.Rect;
import android.text.method.LinkMovementMethod;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sjtu.project.R;
import cn.sjtu.project.common.MyActivity;
import cn.sjtu.project.common.utils.JsonUtil;
import cn.sjtu.project.common.utils.SharedPreferenceUtil;
import cn.sjtu.project.common.utils.StringUtil;
import cn.sjtu.project.domain.Annotation;
import cn.sjtu.project.domain.HomeWorkResult;
import cn.sjtu.project.domain.SelectedText;
import io.noties.markwon.Markwon;

import static cn.sjtu.project.common.utils.FileUtil.readFromInputStream;

public class TextActivity extends MyActivity {
    @BindView(R.id.text)
    TextView textView;

    String homeWorkId = "1";

    HomeWorkResult homeWorkResult;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_text;
    }

    @Override
    protected void initView() {
        textView.setTextIsSelectable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void initData() {
        initTextView();
        initHomeWorkResult(homeWorkId);
        mergeAnnotationList();
        ActionMode.Callback2 textSelectionActionModeCallback = new ActionMode.Callback2() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater menuInflater = actionMode.getMenuInflater();
                menu.clear();
                menuInflater.inflate(R.menu.selection_action_menu,menu);
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                SelectedText selectedText = new SelectedText(textView);
                switch (menuItem.getItemId()) {
                    case R.id.justify:
                        selectedText.intelligentlyAdjust();
                    case R.id.annotation:
                        Annotation annotation = selectedText.convert2Annotation();
                        //TODO 写批注
                        annotation.drawOn(textView);
                        annotation.addClickedEventOn(textView, v -> {
                            //TODO 修改批注
                            toast(annotation.getAnnotatedContent());
                        });
                        //TODO 存储元数据
                        homeWorkResult.addAnnotation(annotation);
                        actionMode.finish();
                        break;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }

            @Override
            public void onGetContentRect(ActionMode mode, View view, Rect outRect) {
                //可选  用于改变弹出菜单的位置
                super.onGetContentRect(mode, view, outRect);
            }
        };
        textView.setCustomSelectionActionModeCallback(textSelectionActionModeCallback);
    }

    private void mergeAnnotationList() {
        for (Annotation annotation : homeWorkResult.getAnnotationList()) {
            annotation.drawOn(textView);
            annotation.addClickedEventOn(textView, v -> {
                toast(annotation.getAnnotatedContent());
            });
        }
    }

    private void initHomeWorkResult(String homeWorkId) {
        String json = SharedPreferenceUtil.getStringFromSharedPreference(getApplicationContext(), homeWorkId);
        if (StringUtil.isEmpty(json)) {
            homeWorkResult = HomeWorkResult.builder()
                    .homeWorkId(homeWorkId)
                    .annotationList(new ArrayList<>())
                    .build();
        }
        else {
            homeWorkResult = JsonUtil.covert2Object(json, HomeWorkResult.class);
        }
    }

    private void initTextView() {
        InputStream is = getResources().openRawResource(R.raw.sample);
        String content = readFromInputStream(is);
        final Markwon markwon = Markwon.create(getApplicationContext());
        markwon.setMarkdown(textView, content);
    }

    @OnClick({R.id.save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                String json = JsonUtil.convert2String(homeWorkResult);
                Logger.i(json);
                SharedPreferenceUtil.saveSharedPreference(getApplicationContext(), homeWorkId, json);
        }
    }
}
