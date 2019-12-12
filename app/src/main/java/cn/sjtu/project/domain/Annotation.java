package cn.sjtu.project.domain;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import lombok.Data;

@Data
public class Annotation {
    Range annotatedRange;

    String annotatedContent;

    String annotationText;

    int color = Color.YELLOW;

    public void drawOn(TextView textView) {
        BackgroundColorSpan blueSpan = new BackgroundColorSpan(color);
        SpannableStringBuilder builder = new SpannableStringBuilder(textView.getText());
        builder.setSpan(blueSpan, getStart(), getEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }

    public void addClickedEventOn(TextView textView,
                                  View.OnClickListener onClickListener) {
        SpannableStringBuilder builder = new SpannableStringBuilder(textView.getText());
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                onClickListener.onClick(widget);
            }
        }, getStart(), getEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }

    private int getEnd() {
        return this.annotatedRange.getEnd();
    }

    private int getStart() {
        return this.annotatedRange.getStart();
    }
}
