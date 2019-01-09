package pl.filewicz.grzegorz.restful.notes.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.filewicz.grzegorz.restful.notes.dao.model.Note;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DefaultNoteCrudDao implements NoteCrudDao {

    private NoteHibernateUtil noteHibernateUtil;

    public DefaultNoteCrudDao() {
    }

    public DefaultNoteCrudDao(NoteHibernateUtil noteHibernateUtil) {
        this.noteHibernateUtil = noteHibernateUtil;
    }

    public Long create(Note note) {
        Long createdNoteId = -1L;

        // Create a session
        Session session = DefaultNoteHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // Begin a transaction
            transaction = session.beginTransaction();
            // Save the Note
            createdNoteId = (Long) session.save(note);
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

        return createdNoteId;
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
            CriteriaQuery<Note> criteria = builder.createQuery(Note.class);
            Root<Note> root = criteria.from(Note.class);
            criteria.where(builder.equal(root.get("id"), noteId));
            criteria.where(builder.equal(root.get("deleted"), false));
            note = session.createQuery(criteria).getSingleResult();

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
            CriteriaQuery<Note> criteria = builder.createQuery(Note.class);
            Root<Note> root = criteria.from(Note.class);
            criteria.where(builder.equal(root.get("deleted"), false));
            notes = session.createQuery(criteria).getResultList();

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
        // Create a session
        Session session = DefaultNoteHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // Begin a transaction
            transaction = session.beginTransaction();
            // Update the Note
            Note foundNote = session.find(Note.class, note.getId());
            foundNote.setTitle(note.getTitle());
            foundNote.setContent(note.getContent());

            session.saveOrUpdate(foundNote);
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

    public Note delete(Long noteId) {
        Note foundNote = null;

        // Create a session
        Session session = DefaultNoteHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // Begin a transaction
            transaction = session.beginTransaction();
            // Update the Note
            foundNote = session.find(Note.class, noteId);
            foundNote.setDeleted(true);

            session.saveOrUpdate(foundNote);
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
