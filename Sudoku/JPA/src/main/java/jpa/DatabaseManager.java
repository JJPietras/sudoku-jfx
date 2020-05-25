package jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
        List<Board> boards = entityManager.createQuery("SELECT FROM Board").getResultList();
        entityManager.getTransaction().commit();
        return boards;
    }

    public void removeData(long id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Board.class, id));
        entityManager.getTransaction().commit();
    }

    public void removeAll() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Board").executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void releaseResources() {
        entityManager.close();
    }
}
