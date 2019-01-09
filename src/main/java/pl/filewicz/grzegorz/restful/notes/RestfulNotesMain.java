package pl.filewicz.grzegorz.restful.notes;

import pl.filewicz.grzegorz.restful.notes.dao.model.Note;
import pl.filewicz.grzegorz.restful.notes.dao.DefaultNoteCrudDao;

public class RestfulNotesMain {

    public static void main(String[] args) {
        DefaultNoteCrudDao defaultNoteCrudDao = new DefaultNoteCrudDao();

        Note note = new Note("Test note 01", "Test content 01");
//        Note note = new Note();
        note.setId(4L);

        defaultNoteCrudDao.create(note);
    }
}
