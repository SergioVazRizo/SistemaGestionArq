package org.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bd.ConexionMySQL;
import org.model.Libro;

public class ControllerLibro {

    public List<Libro> getAllLibros() throws SQLException, ClassNotFoundException {
        List<Libro> librosList = new ArrayList<>();
        String query = "SELECT * FROM Libro";

        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conn = connMySQL.openConnection();
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();

            while (rs.next()) {
                int cve_libro = rs.getInt("cve_libro");
                String nombre_libro = rs.getString("nombre_libro");
                String autor_libro = rs.getString("autor_libro");
                String genero_libro = rs.getString("genero_libro");
                String pdf_libro = rs.getString("pdf_libro");

                Libro libro = new Libro(cve_libro, nombre_libro, autor_libro, genero_libro, pdf_libro);
                librosList.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                connMySQL.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return librosList;
    }

    public boolean agregarLibro(Libro libro) throws ClassNotFoundException {
        String query = "INSERT INTO Libro (nombre_libro, autor_libro, genero_libro, pdf_libro) VALUES (?, ?, ?, ?)";
        ConexionMySQL objConn = new ConexionMySQL();
        try {
            Connection conn = objConn.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, libro.getNombre_libro());
            pstmt.setString(2, libro.getAutor_libro());
            pstmt.setString(3, libro.getGenero_libro());
            pstmt.setString(4, libro.getPdf_libro());
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean editarLibro(Libro libro) throws ClassNotFoundException {
        String query = "UPDATE Libro SET nombre_libro=?, autor_libro=?, genero_libro=?, pdf_libro=? WHERE cve_libro=?";
        ConexionMySQL objConn = new ConexionMySQL();
        try {
            Connection conn = objConn.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, libro.getNombre_libro());
            pstmt.setString(2, libro.getAutor_libro());
            pstmt.setString(3, libro.getGenero_libro());
            pstmt.setString(4, libro.getPdf_libro());
            pstmt.setInt(5, libro.getCve_libro()); // Aquí asumo que tienes un getter para cve_libro
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean eliminarLibro(int cve_libro) throws ClassNotFoundException {
        String query = "DELETE FROM Libro WHERE cve_libro=?";
        ConexionMySQL objConn = new ConexionMySQL();
        try {
            Connection conn = objConn.openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, cve_libro);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

}
