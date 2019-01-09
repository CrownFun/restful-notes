package pl.filewicz.grzegorz.restful.notes.service;

import pl.filewicz.grzegorz.restful.notes.api.NoteDto;
import pl.filewicz.grzegorz.restful.notes.api.mapper.NoteMapper;
import pl.filewicz.grzegorz.restful.notes.dao.DefaultNoteHistoryDao;
import pl.filewicz.grzegorz.restful.notes.dao.NoteHistoryDao;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultNoteHistoryService implements NoteHistoryService {
    private NoteHistoryDao noteHistoryDao;

    public DefaultNoteHistoryService() {
        this.noteHistoryDao = new DefaultNoteHistoryDao();
    }

    public DefaultNoteHistoryService(NoteHistoryDao noteHistoryDao) {
        this.noteHistoryDao = noteHistoryDao;
    }

    @Override
    public List<NoteDto> readHistory(Long noteId) {
        List<NoteDto> noteHistory = noteHistoryDao.readHistory(noteId)
                .stream()
                .map(NoteMapper::toDto)
                .collect(Collectors.toList());

        return noteHistory;
    }
}
