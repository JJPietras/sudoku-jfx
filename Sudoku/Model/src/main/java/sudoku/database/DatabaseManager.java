package sudoku.database;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class DatabaseManager {
    private final EntityManager entityManager;

    public DatabaseManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public Board getByID(long id) {
        entityManager.getTransaction().begin();
        Board board = entityManager.find(Board.class, id);
        entityManager.getTransaction().commit();
        return board;
    }

    public List<Board> getAllEntries() {
        entityManager.getTransaction().begin();
        @SuppressWarnings("unchecked")
        List<Board> boards = entityManager.createQuery(
                "SELECT board FROM Board board"
        ).getResultList();
        entityManager.getTransaction().commit();
        return boards;
    }

    public void removeData(String name) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Board WHERE name = :n")
                .setParameter("n", name).executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void removeAll() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Board").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Deprecated
    public void addObject(Board board) {
        entityManager.getTransaction().begin();
        entityManager.persist(board);
        entityManager.getTransaction().commit();
    }

    public void releaseResources() {
        entityManager.close();
    }
}
