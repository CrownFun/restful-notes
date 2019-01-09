package pl.filewicz.grzegorz.restful.notes.service;

import pl.filewicz.grzegorz.restful.notes.api.NoteDto;
import pl.filewicz.grzegorz.restful.notes.api.mapper.NoteMapper;
import pl.filewicz.grzegorz.restful.notes.dao.DefaultNoteCrudDao;
import pl.filewicz.grzegorz.restful.notes.dao.NoteCrudDao;
import pl.filewicz.grzegorz.restful.notes.dao.model.Note;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultNoteCrudService implements NoteCrudService {
    private NoteCrudDao noteCrudDao;

    public DefaultNoteCrudService() {
        this.noteCrudDao = new DefaultNoteCrudDao();
    }

    public DefaultNoteCrudService(NoteCrudDao noteCrudDao) {
        this.noteCrudDao = noteCrudDao;
    }

    @Override
    public Long create(NoteDto noteDto) {
        Note note = NoteMapper.toNote(noteDto);

        note.setCreated(LocalDate.now());
        note.setModified(LocalDate.now());

        return noteCrudDao.create(note);
    }

    public NoteDto read(Long noteId) {
        return NoteMapper.toDto(noteCrudDao.read(noteId));
    }

    @Override
    public List<NoteDto> readAll() {
        List<Note> notes = noteCrudDao.readAll();

        List<NoteDto> noteDtos = notes.stream()
                .map(NoteMapper::toDto)
                .collect(Collectors.toList());

        return noteDtos;
    }

    @Override
    public NoteDto update(NoteDto noteDto) {
        Note updatedNote = noteCrudDao.update(NoteMapper.toNote(noteDto));

        return NoteMapper.toDto(updatedNote);
    }

    @Override
    public NoteDto delete(Long noteId) {
        return NoteMapper.toDto(noteCrudDao.delete(noteId));
    }
}
