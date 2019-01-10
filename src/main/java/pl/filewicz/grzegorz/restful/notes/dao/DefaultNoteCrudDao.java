package pl.filewicz.grzegorz.restful.notes.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.filewicz.grzegorz.restful.notes.dao.model.Note;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

public class DefaultNoteCrudDao implements NoteCrudDao {

    private NoteHibernateUtil noteHibernateUtil;

    public DefaultNoteCrudDao() {
    }

    public DefaultNoteCrudDao(NoteHibernateUtil noteHibernateUtil) {
        this.noteHibernateUtil = noteHibernateUtil;
    }

    public Note create(Note note) {
        // Create a session
        Session session = DefaultNoteHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // Begin a transaction
            transaction = session.beginTransaction();
            // Save the Note
            session.save(note);
            // Commit the transaction
            transaction.commit();
        } catch (HibernateException e) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            e.printStackTrace();
        } finally {
            // Close the session
            session.close();
        }

        return note;
    }

    public Note read(Long noteId) {
        Note note = null;

        // Create a session
        Session session = DefaultNoteHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // Begin a transaction
            transaction = session.beginTransaction();

            // Read the Note
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Note> query = builder.createQuery(Note.class);
            Root<Note> root = query.from(Note.class);

            builder.max(root.get("version"));
            query.select(root).where(
                    builder.and(
                            builder.equal(root.get("id"), noteId),
                            builder.equal(root.get("deleted"), false)
                    ),
                    builder.or(
                            builder.equal(root.get("id"), root.get("originId")),
                            builder.isNull(root.get("originId"))
                    )
            );

            List<Note> resultList = session.createQuery(query).getResultList();
            if (!resultList.isEmpty()) {
                note = resultList.get(0);
            }

            // Commit the transaction
            transaction.commit();
        } catch (HibernateException e) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            e.printStackTrace();
        } finally {
            // Close the session
            session.close();
        }

        return note;
    }

    public List<Note> readAll() {
        List<Note> notes = null;

        // Create a session
        Session session = DefaultNoteHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // Begin a transaction
            transaction = session.beginTransaction();

            // Read All the Notes
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Note> query = builder.createQuery(Note.class);
            Root<Note> root = query.from(Note.class);

            builder.max(root.get("version"));
            query.select(root).where(
                    builder.and(
                            builder.equal(root.get("deleted"), false)
                    ),
                    builder.or(
                            builder.equal(root.get("id"), root.get("originId")),
                            builder.isNull(root.get("originId"))
                    )
            );
            notes = session.createQuery(query).getResultList();

            // Commit the transaction
            transaction.commit();
        } catch (HibernateException e) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            e.printStackTrace();
        } finally {
            // Close the session
            session.close();
        }

        return notes;
    }

    public Note update(Note note) {
        Note foundNote = null;

        // Create a session
        Session session = DefaultNoteHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // Begin a transaction
            transaction = session.beginTransaction();

            // Update the Note
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Note> query = builder.createQuery(Note.class);
            Root<Note> root = query.from(Note.class);

            builder.max(root.get("version"));
            query.select(root).where(
                    builder.and(
                            builder.equal(root.get("id"), note.getId()),
                            builder.equal(root.get("deleted"), false)
                    ),
                    builder.or(
                            builder.equal(root.get("id"), root.get("originId")),
                            builder.isNull(root.get("originId"))
                    )
            );

            List<Note> resultList = session.createQuery(query).getResultList();
            if (!resultList.isEmpty()) {
                foundNote = resultList.get(0);

                // create new version
                note.setOriginId(note.getId());
                note.setCreated(foundNote.getCreated());
                note.setModified(LocalDate.now());
                note.setVersion(foundNote.getVersion());

                session.save(note);

                // update existing one
                foundNote.setOriginId(foundNote.getId());
                foundNote.setTitle(note.getTitle());
                foundNote.setContent(note.getContent());
                foundNote.setVersion(foundNote.getVersion() + 1);

                session.saveOrUpdate(foundNote);
            }

            // Commit the transaction
            transaction.commit();
        } catch (HibernateException e) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            e.printStackTrace();
        } finally {
            // Close the session
            session.close();
        }

        return foundNote;
    }

    public Note delete(Long noteId) {
        Note foundNote = null;

        // Create a session
        Session session = DefaultNoteHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // Begin a transaction
            transaction = session.beginTransaction();

            // Delete the Note
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Note> query = builder.createQuery(Note.class);
            Root<Note> root = query.from(Note.class);

            query.select(root).where(
                    builder.and(
                            builder.equal(root.get("id"), noteId),
                            builder.equal(root.get("deleted"), false)
                    ),
                    builder.or(
                            builder.equal(root.get("id"), root.get("originId")),
                            builder.isNull(root.get("originId"))
                    )
            );

            List<Note> resultCurrentNoteList = session.createQuery(query).getResultList();
            if (!resultCurrentNoteList.isEmpty()) {
                foundNote = resultCurrentNoteList.get(0);

                // remove current version
                foundNote.setDeleted(true);
                session.saveOrUpdate(foundNote);

                // remove history
                query.select(root).where(builder.equal(root.get("originId"), foundNote.getId()));

                List<Note> resultHistoryNoteList = session.createQuery(query).getResultList();
                if (!resultHistoryNoteList.isEmpty()) {
                    for (Note note : resultHistoryNoteList) {
                        note.setDeleted(true);
                        session.saveOrUpdate(note);
                    }
                }
            }

            // Commit the transaction
            transaction.commit();
        } catch (HibernateException e) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            e.printStackTrace();
        } finally {
            // Close the session
            session.close();
        }

        return foundNote;
    }
}
