package pl.filewicz.grzegorz.restful.notes.dao;

import pl.filewicz.grzegorz.restful.notes.dao.model.Note;

import java.util.List;

public interface NoteCrudDao {
    Note create(Note note);
    Note read(Long noteId);
    List<Note> readAll();
    Note update(Note note);
    Note delete(Long noteId);
}
