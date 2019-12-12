package cn.sjtu.project.domain;

import java.util.Date;

import lombok.Data;

@Data
public class HomeWork {
    String id;

    Book book;

    String description;

    String requirement;

    Date deadline;

    Date publishDate;

    String submitterName;
}
