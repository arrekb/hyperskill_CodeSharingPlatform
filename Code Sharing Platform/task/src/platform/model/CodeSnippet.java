package platform.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CodeSnippet {
    private String code;
    private LocalDateTime date;
    private int time;
    private int views;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public CodeSnippet() {
        this.date = LocalDateTime.now();
        this.code = "Sample code snippet";
    }

    public CodeSnippet(String code, LocalDateTime date) {
        this.code = code;
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date.format(FORMATTER);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
