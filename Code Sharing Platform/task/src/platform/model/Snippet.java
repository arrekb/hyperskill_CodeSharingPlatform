package platform.model;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity(name = "snippets")
public class Snippet {

    @Id
    @Column
    //    @GeneratedValue(strategy = GenerationType.AUTO)
    private String snippetId;

    @Column
    private String snippetCode;

    @Column
    private LocalDateTime snippetDate;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Column
    private long time;

    @Column
    private int views;

    @Column
    private boolean timeRestriction;

    @Column
    private boolean viewsRestriction;

    public Snippet() {
        this.snippetId = UUID.randomUUID().toString();
        this.snippetDate = LocalDateTime.now();
        this.time = 0;
        this.views = 0;
        this.timeRestriction = false;
        this.viewsRestriction = false;
    }

    public Snippet(String snippetCode, int time, int views) {
        this.snippetId = UUID.randomUUID().toString();
        this.snippetDate = LocalDateTime.now();
        this.snippetCode = snippetCode;
        this.time = time;
        this.views = views;
        if (time > 0) {
            this.timeRestriction = true;
        } else {
            this.timeRestriction = false;
        }
        if (views > 0) {
            this.viewsRestriction = true;
        } else {
            this.viewsRestriction = false;
        }
    }

    public String getSnippetId() {
        return snippetId;
    }

    public void setSnippetId(String snippetId) {
        this.snippetId = snippetId;
    }

    public String getSnippetCode() {
        return snippetCode;
    }

    public void setSnippetCode(String snippetCode) {
        this.snippetCode = snippetCode;
    }

    public LocalDateTime getSnippetDate() {
        return snippetDate;
    }

    public String getSnippetDateFormatted() {
        return snippetDate.format(FORMATTER);
    }

    public void setSnippetDate(LocalDateTime date) {
        this.snippetDate = date;
    }

    public long getTime() {
        return time;
    }

    public long getTimeLeft() {
        // time - (now - created)
        if (time < 0) {
            return time;
        }
        long timeLeft = this.time - Duration.between(this.snippetDate, LocalDateTime.now()).toSeconds();
        return timeLeft > 0 ? timeLeft : 0;
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

    public boolean isTimeRestriction() {
        return timeRestriction;
    }

    public void setTimeRestriction(boolean timeRestriction) {
        this.timeRestriction = timeRestriction;
    }

    public boolean isViewsRestriction() {
        return viewsRestriction;
    }

    public void setViewsRestriction(boolean viewsRestriction) {
        this.viewsRestriction = viewsRestriction;
    }
}
