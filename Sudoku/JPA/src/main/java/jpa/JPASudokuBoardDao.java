package jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import sudoku.Dao;
import sudoku.SudokuBoard;
import sudoku.exceptions.ReadBoardException;
import sudoku.exceptions.WriteBoardException;

public class JPASudokuBoardDao implements Dao<SudokuBoard> {
    private final EntityManager entityManager;
    private String name;
    private Long id;

    private JPASudokuBoardDao() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("unit");
        entityManager = factory.createEntityManager();
    }

    public JPASudokuBoardDao(Long id) {
        this();
        this.id = id;
    }

    public JPASudokuBoardDao(String name) {
        this();
        this.name = name;
    }

    @Override
    public SudokuBoard read() throws ReadBoardException {
        if (id != null) {
            return getById();
        } else if (name != null) {
            return getByName();
        } else {
            throw new ReadBoardException("PLACEHOLDER", new IllegalArgumentException());
        }
    }

    @Override
    public void write(SudokuBoard obj) throws WriteBoardException {
        if (name == null) {
            throw new WriteBoardException();
        }
        entityManager.getTransaction().begin();
        entityManager.persist(wrapSudokuBoard(obj));
        entityManager.getTransaction().commit();
    }

    private Board wrapSudokuBoard(SudokuBoard sudokuBoard) {
        return new Board(sudokuBoard, name);
    }

    private SudokuBoard getById() throws ReadBoardException {
        try {
            entityManager.getTransaction().begin();
            Board board = entityManager.find(Board.class, id);
            entityManager.getTransaction().commit();
            return board.deserialize();

        } catch (Exception exception) {
            throw new ReadBoardException("PLACEHOLDER", exception);
        }
    }

    private SudokuBoard getByName() throws ReadBoardException {
        try {
            entityManager.getTransaction().begin();
            @SuppressWarnings("rawtypes")
            List list = entityManager.createQuery(
                    "SELECT board FROM Board board WHERE board.name = :n"
            ).setParameter("n", name).getResultList();

            entityManager.getTransaction().commit();
            Board board = (Board) list.get(0);
            return board.deserialize();

        } catch (Exception exception) {
            throw new ReadBoardException("PLACEHOLDER", exception);
        }
    }
}
