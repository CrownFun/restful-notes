package pl.filewicz.grzegorz.restful.notes.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "NOTES")
public class Note implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;

    @Column(name = "ORIGIN_ID")
    private Long originId;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "CREATED")
    private LocalDate created;

    @Column(name = "MODIFIED")
    private LocalDate modified;

    @Column(name = "VERSION")
    private int version;

    @Column(name = "DELETED")
    boolean deleted;

    public Note() {}

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Note(long id, String title, String content) {
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

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
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

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getModified() {
        return modified;
    }

    public void setModified(LocalDate modified) {
        this.modified = modified;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id &&
                originId == note.originId &&
                version == note.version &&
                deleted == note.deleted &&
                Objects.equals(title, note.title) &&
                Objects.equals(content, note.content) &&
                Objects.equals(created, note.created) &&
                Objects.equals(modified, note.modified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originId, title, content, created, modified, version, deleted);
    }


}
