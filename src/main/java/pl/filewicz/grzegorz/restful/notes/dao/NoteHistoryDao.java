package pl.filewicz.grzegorz.restful.notes.dao;

import pl.filewicz.grzegorz.restful.notes.dao.model.Note;

import java.util.List;

public interface NoteHistoryDao {
    List<Note> readHistory(Long noteId);
}
