package jpa;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import sudoku.SudokuBoard;

// Pisząc kwerendy istotne jest by NIE odwoływać się do nazwy tabeli, lecz nazwy encji (Board).

@Entity
@Table(name = "board")
class Board {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "board")
    private byte[] board;

    public Board() {
    }

    public Board(SudokuBoard sudokuBoard, String name) {
        this.name = name;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(Objects.requireNonNull(sudokuBoard));
            objectOutputStream.flush();
            board = byteArrayOutputStream.toByteArray();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public SudokuBoard deserialize() {
        SudokuBoard sudokuBoard = null;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(board);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            sudokuBoard = (SudokuBoard) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return sudokuBoard;
    }

    public byte[] getBoard() {
        return board;
    }
}
