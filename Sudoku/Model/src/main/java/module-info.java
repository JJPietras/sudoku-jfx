module Model {
    requires commons.lang3;
    opens sudoku;
    exports sudoku;
    exports sudoku.exceptions;
}