package cn.sjtu.project.domain;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class SelectedText {
    String content;

    TextView textView;

    int start;

    int end;

    public SelectedText(TextView textView) {
        this.textView = textView;
        this.start = textView.getSelectionStart();
        this.end = textView.getSelectionEnd();
        this.content = textView.getText().toString().substring(getStart(), getEnd());
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public String getContent() {
        return this.content;
    }

    public void paint(int color) {
        BackgroundColorSpan blueSpan = new BackgroundColorSpan(color);
        SpannableStringBuilder builder = new SpannableStringBuilder(textView.getText());
        builder.setSpan(blueSpan, getStart(), getEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }

    public void addClickedEvent(View.OnClickListener onClickListener) {
        SpannableStringBuilder builder = new SpannableStringBuilder(textView.getText());
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                onClickListener.onClick(widget);
            }
        }, getStart(), getEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }

    public void intelligentlyAdujust() {

    }
}

