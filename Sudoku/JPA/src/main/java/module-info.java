module JPA {
    requires java.persistence;
    requires Model;

    opens jpa;
    exports jpa;
}