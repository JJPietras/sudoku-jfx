package jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseManager {
    private final EntityManager entityManager;

    DatabaseManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void addData(Board board) {
        entityManager.getTransaction().begin();
        entityManager.persist(board);
        entityManager.getTransaction().commit();
    }

    public Board getByID(long id) {
        entityManager.getTransaction().begin();
        Board board = entityManager.find(Board.class, id);
        entityManager.getTransaction().commit();
        return board;
    }

    public Board getFirst() {
        entityManager.getTransaction().begin();
        Board board = (Board) entityManager.createQuery("SELECT b FROM Board b").getResultList().get(0);//find(Board.class, id);
        entityManager.getTransaction().commit();
        return board;
    }

    public Board getLast() {
        entityManager.getTransaction().begin();
        @SuppressWarnings("rawtypes") List boards;
        boards = entityManager.createQuery("SELECT b FROM Board b").getResultList();
        Board board = (Board) boards.get(boards.size() - 1);
        entityManager.getTransaction().commit();
        return board;
    }

    public void removeData(long id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Board.class, id));
        entityManager.getTransaction().commit();
    }

    public void releaseResources() {
        entityManager.close();
    }
}
