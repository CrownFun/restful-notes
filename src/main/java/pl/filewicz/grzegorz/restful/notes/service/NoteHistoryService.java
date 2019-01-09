package pl.filewicz.grzegorz.restful.notes.service;

import pl.filewicz.grzegorz.restful.notes.api.NoteDto;

import java.util.List;

public interface NoteHistoryService {
    List<NoteDto> readHistory(Long noteId);
}
