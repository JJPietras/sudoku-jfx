package sudoku;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import sudoku.exceptions.ContainerOutOfBoundsException;
import sudoku.exceptions.FieldOutOfBoundsException;
import sudoku.exceptions.InvalidFieldValueException;
import sudoku.exceptions.SudokuBoardException;
import sudoku.exceptions.SudokuContainerException;

public class SudokuBoard implements Serializable, Cloneable {

    private final SudokuSolver sudokuSolver;
    private final List<List<SudokuField>> board;

    public SudokuBoard(SudokuSolver solver) {
        @SuppressWarnings("unchecked")
        List<SudokuField>[] array = new List[Consts.SIZE];
        SudokuField[] fields;
        for (int i = 0; i < Consts.SIZE; i++) {
            fields = new SudokuField[Consts.SIZE];
            for (int j = 0; j < Consts.SIZE; j++) {
                fields[j] = new SudokuField();
            }
            array[i] = Arrays.asList(fields);
        }
        board = Arrays.asList(array);
        sudokuSolver = Objects.requireNonNull(solver);
    }

    public void solveGame() throws FieldOutOfBoundsException, InvalidFieldValueException {
        sudokuSolver.solve(this);
    }

    public int getField(int x, int y) throws FieldOutOfBoundsException {
        if (x < 0 || y < 0 || x >= Consts.SIZE || y >= Consts.SIZE) {
            throw new FieldOutOfBoundsException(
                    Consts.OUT_OF_BOUNDS,
                    new ArrayIndexOutOfBoundsException()
            );
        }
        return board.get(y).get(x).getFieldValue();
    }

    public void setField(int x, int y, int value) throws
            FieldOutOfBoundsException, InvalidFieldValueException {
        if (x < 0 || y < 0 || x >= Consts.SIZE || y >= Consts.SIZE) {
            throw new FieldOutOfBoundsException(
                    Consts.OUT_OF_BOUNDS,
                    new ArrayIndexOutOfBoundsException()
            );
        }
        board.get(y).get(x).setFieldValue(value);
    }

    @SuppressWarnings("unused")
    private boolean checkBoard() throws SudokuBoardException, SudokuContainerException {
        for (int i = 0; i < Consts.SIZE; i++) {
            if (!(getRow(i).verify() || getColumn(i).verify())) {
                return false;
            }
        }
        for (int i = 0; i < Consts.SIZE; i += Consts.BOX_SIZE) {
            for (int j = 0; j < Consts.SIZE; j += Consts.BOX_SIZE) {
                if (!getBox(i, j).verify()) {
                    return false;
                }
            }
        }
        return true;
    }

    SudokuRow getRow(int y) throws ContainerOutOfBoundsException, SudokuContainerException {
        if (y < 0 || y > 8) {
            throw new ContainerOutOfBoundsException(
                    Consts.ROW_OUT_OF_BOUNDS + " " + y,
                    new IllegalArgumentException()
            );
        }
        return new SudokuRow(board.get(y));
    }

    SudokuColumn getColumn(int x) throws ContainerOutOfBoundsException, SudokuContainerException {
        if (x < 0 || x > 8) {
            throw new ContainerOutOfBoundsException(
                    Consts.COL_OUT_OF_BOUNDS + " " + x,
                    new IllegalArgumentException()
            );
        }
        List<SudokuField> column = Arrays.asList(new SudokuField[Consts.SIZE]);
        for (int i = 0; i < Consts.SIZE; i++) {
            column.set(i, board.get(i).get(x));
        }
        return new SudokuColumn(column);
    }

    SudokuBox getBox(int x, int y) throws SudokuBoardException, SudokuContainerException {
        if (x < 0 || x >= Consts.SIZE) {
            throw new ContainerOutOfBoundsException(
                    Consts.COL_OUT_OF_BOUNDS + " " + x,
                    new IllegalArgumentException()
            );
        }
        if (y < 0 || y >= Consts.SIZE) {
            throw new ContainerOutOfBoundsException(
                    Consts.ROW_OUT_OF_BOUNDS + " " + y,
                    new IllegalArgumentException()
            );
        }

        List<SudokuField> box = Arrays.asList(new SudokuField[Consts.SIZE]);
        int k = 0;
        //Get first indexes of box
        int boxY = (y % Consts.BOX_SIZE) * Consts.BOX_SIZE;
        int boxX = (x % Consts.BOX_SIZE) * Consts.BOX_SIZE;
        for (int i = boxY; i < boxY + Consts.BOX_SIZE; i++) {
            for (int j = boxX; j < boxX + Consts.BOX_SIZE; j++) {
                box.set(k++, board.get(i).get(j));
            }
        }
        return new SudokuBox(box);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuBoard that = (SudokuBoard) o;

        return new EqualsBuilder()
                .append(board, that.board)
                .isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(board)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("sudokuSolver", sudokuSolver)
                .append("board", board)
                .toString();
    }

    @Override
    @SuppressWarnings("all")
    protected SudokuBoard clone() {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);

            ByteArrayInputStream byteArrayInputStream =
                    new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            try {
                return (SudokuBoard) objectInputStream.readObject();
            } catch (ClassNotFoundException exception) {
                return null;
            }
        } catch (IOException exception) {
            return null;
        }

    }
}