package cn.sjtu.project.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeWorkResult {
    String id;

    String homeWorkId;

    String authorName;

    List<Annotation> annotationList;

    public void addAnnotation(Annotation annotation) {
        annotationList.add(annotation);
    }
}
