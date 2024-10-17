package org.controller;

import java.sql.SQLException;
import java.util.List;
import org.dao.LibroDAO;
import org.model.Libro;

public class ControllerLibro {

    private final LibroDAO libroDAO;

    public ControllerLibro() {
        this.libroDAO = new LibroDAO(); 
    }

    public List<Libro> getAllLibros() throws SQLException, ClassNotFoundException {
        return libroDAO.findAll(); 
    }

    public boolean agregarLibro(Libro libro) throws ClassNotFoundException, SQLException {
        try {
            libroDAO.insert(libro);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error al agregar el libro: " + e.getMessage());
            return false;
        }
    }

    public boolean editarLibro(Libro libro) throws ClassNotFoundException, SQLException {
        try {
            libroDAO.update(libro);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error al editar el libro: " + e.getMessage());
            return false;
        }
    }

    public Libro buscarLibroPorId(int id) throws ClassNotFoundException, SQLException {
        return libroDAO.findById(id);
    }
}
