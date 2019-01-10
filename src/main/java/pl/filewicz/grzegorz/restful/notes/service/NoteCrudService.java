package pl.filewicz.grzegorz.restful.notes.service;

import pl.filewicz.grzegorz.restful.notes.api.NoteDto;
import pl.filewicz.grzegorz.restful.notes.dao.model.Note;

import java.util.List;

public interface NoteCrudService {
    Note create(NoteDto noteDto);
    NoteDto read(Long noteId);
    List<NoteDto> readAll();
    NoteDto update(NoteDto noteDto);
    NoteDto delete(Long noteId);
}
