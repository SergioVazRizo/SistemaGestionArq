package org.controller;

import java.sql.SQLException;
import java.util.List;
import org.dao.LibroDAO;
import org.model.Libro;

public class ControllerLibro {

    private LibroDAO libroDAO;

    public ControllerLibro() {
        this.libroDAO = new LibroDAO();
    }

    public List<Libro> getAllLibros() throws SQLException, ClassNotFoundException {
        return libroDAO.getAllLibros();
    }

    public boolean agregarLibro(Libro libro) throws ClassNotFoundException, SQLException {
        return libroDAO.agregarLibro(libro);
    }

    public boolean editarLibro(Libro libro) throws ClassNotFoundException, SQLException {
        return libroDAO.editarLibro(libro);
    }

    public boolean eliminarLibro(int cve_libro) throws ClassNotFoundException, SQLException {
        return libroDAO.eliminarLibro(cve_libro);
    }
}