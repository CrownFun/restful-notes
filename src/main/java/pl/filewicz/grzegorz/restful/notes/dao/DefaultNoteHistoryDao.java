package pl.filewicz.grzegorz.restful.notes.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.filewicz.grzegorz.restful.notes.dao.model.Note;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DefaultNoteHistoryDao implements NoteHistoryDao {
    @Override
    public List<Note> readHistory(Long noteId) {
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

            query.select(root).where(
                    builder.and(
                            builder.equal(root.get("deleted"), false)
                    ),
                    builder.or(
                            builder.equal(root.get("id"), noteId),
                            builder.equal(root.get("originId"), noteId)
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
}
