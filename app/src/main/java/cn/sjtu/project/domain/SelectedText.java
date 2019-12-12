package cn.sjtu.project.domain;

import android.widget.TextView;

public class SelectedText {
    String content;

    Range range;

    public SelectedText(TextView textView) {
        this.range = new Range(textView.getSelectionStart(), textView.getSelectionEnd());
        this.content = textView.getText().toString().substring(getStart(), getEnd());
    }

    public int getStart() {
        return this.range.getStart();
    }

    public int getEnd() {
        return this.range.getEnd();
    }


    public String getContent() {
        return this.content;
    }

    public void intelligentlyAdjust() {

    }

    public Annotation convert2Annotation() {
        Annotation res = new Annotation();
        res.setAnnotatedRange(this.range);
        res.setAnnotatedContent(this.content);
        return res;
    }
}

