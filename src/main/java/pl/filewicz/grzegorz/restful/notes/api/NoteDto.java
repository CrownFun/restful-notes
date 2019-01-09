package pl.filewicz.grzegorz.restful.notes.api;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NoteDto {
    private long id;
    @NotNull(message = "Note field Title cannot be NULL")
    private String title;
    @NotNull(message = "Note field Content cannot be NULL")
    private String content;

    public NoteDto() {}

    public NoteDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public NoteDto(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NoteDto{");
        sb.append("id='").append(id).append('\'');
        sb.append("title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
