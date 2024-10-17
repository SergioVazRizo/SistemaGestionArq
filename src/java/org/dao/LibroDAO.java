package org.dao;

import org.model.Libro;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import org.bd.ConexionMySQL;

public class LibroDAO {

    private ConexionMySQL conexionMySQL;

    public LibroDAO() {
        this.conexionMySQL = new ConexionMySQL();
    }

    public void insert(Libro libro) throws SQLException, ClassNotFoundException {
        Connection connection = conexionMySQL.openConnection();
        String query = "INSERT INTO Libro (nombre_libro, autor_libro, genero_libro, pdf_libro, estatus) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, libro.getNombre_libro());
            statement.setString(2, libro.getAutor_libro());
            statement.setString(3, libro.getGenero_libro());
            statement.setString(4, libro.getPdf_libro());
            statement.setBoolean(5, libro.getEstatus());
            statement.executeUpdate();
        } finally {
            conexionMySQL.closeConnection();
        }
    }

    public Libro findById(int id) throws SQLException, ClassNotFoundException {
        Connection connection = conexionMySQL.openConnection();
        String query = "SELECT * FROM Libro WHERE cve_libro = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Libro(resultSet.getInt("cve_libro"),
                                     resultSet.getString("nombre_libro"),
                                     resultSet.getString("autor_libro"),
                                     resultSet.getString("genero_libro"),
                                     resultSet.getString("pdf_libro"),
                                     resultSet.getBoolean("estatus"));
                }
            }
        } finally {
            conexionMySQL.closeConnection();
        }
        return null;
    }

    public List<Libro> findAll() throws SQLException, ClassNotFoundException {
        List<Libro> libros = new ArrayList<>();
        Connection connection = conexionMySQL.openConnection();
        String query = "SELECT * FROM Libro";
        
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                libros.add(new Libro(resultSet.getInt("cve_libro"),
                                     resultSet.getString("nombre_libro"),
                                     resultSet.getString("autor_libro"),
                                     resultSet.getString("genero_libro"),
                                     resultSet.getString("pdf_libro"),
                                     resultSet.getBoolean("estatus")));
            }
        } finally {
            conexionMySQL.closeConnection();
        }
        return libros;
    }

    public void update(Libro libro) throws SQLException, ClassNotFoundException {
        Connection connection = conexionMySQL.openConnection();
        String query = "UPDATE Libro SET nombre_libro = ?, autor_libro = ?, genero_libro = ?, pdf_libro = ?, estatus = ? WHERE cve_libro = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, libro.getNombre_libro());
            statement.setString(2, libro.getAutor_libro());
            statement.setString(3, libro.getGenero_libro());
            statement.setString(4, libro.getPdf_libro());
            statement.setBoolean(5, libro.getEstatus());
            statement.setInt(6, libro.getCve_libro());
            statement.executeUpdate();
        } finally {
            conexionMySQL.closeConnection();
        }
    }

}


