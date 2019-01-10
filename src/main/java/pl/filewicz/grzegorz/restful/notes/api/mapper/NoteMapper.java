package pl.filewicz.grzegorz.restful.notes.api.mapper;

import pl.filewicz.grzegorz.restful.notes.api.NoteDto;
import pl.filewicz.grzegorz.restful.notes.dao.model.Note;

public class NoteMapper {
    public static NoteDto toDto(Note note) {
        if (note != null) {
            return new NoteDto(note.getId(), note.getTitle(), note.getContent());
        }

        return null;
    }

    public static Note toNote(NoteDto noteDto) {
        if (noteDto != null) {
            return new Note(noteDto.getId(), noteDto.getTitle(), noteDto.getContent());
        }

        return null;
    }
}