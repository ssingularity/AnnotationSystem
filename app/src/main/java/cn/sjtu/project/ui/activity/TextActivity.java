package cn.sjtu.project.ui.activity;

import android.graphics.Color;
import android.graphics.Rect;
import android.text.method.LinkMovementMethod;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.sjtu.project.R;
import cn.sjtu.project.common.MyActivity;
import cn.sjtu.project.domain.SelectedText;
import io.noties.markwon.Markwon;

public class TextActivity extends MyActivity {
    @BindView(R.id.text)
    TextView textView;

    Map<String, String> store;

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
        store = new HashMap<>();
        initTextView();

        ActionMode.Callback2 textSelectionActionModeCallback = new ActionMode.Callback2() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return true;//返回false则不会显示弹窗
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
                switch (menuItem.getItemId()) {
                    case R.id.annotation:
                        SelectedText selectedText = new SelectedText(textView);
                        //TODO 开始写批注
                        selectedText.paint(Color.YELLOW);
                        selectedText.addClickedEvent(v -> {
                            toast(selectedText.getContent());
                        });
                        //TODO 存储元数据
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

    private void initTextView() {
        InputStream is = getResources().openRawResource(R.raw.sample);
        String content = readFromInputStream(is);
        final Markwon markwon = Markwon.create(getApplicationContext());
        markwon.setMarkdown(textView, content);
    }

    private String readFromInputStream(InputStream is) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while (null != (line = bf.readLine())) {
                sb.append(line);
                sb.append("\n");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
