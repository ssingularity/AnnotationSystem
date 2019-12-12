package cn.sjtu.project.domain;

import android.widget.TextView;

import lombok.Data;

@Data
public class EvaluatedAnnotation extends Annotation{
    String authorName;

    Evaluation evaluation;

    @Override
    public void drawOn(TextView textView) {
        //TODO
    }
}
