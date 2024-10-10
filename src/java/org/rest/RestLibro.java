package org.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import org.controller.ControllerLibro;
import org.model.Libro;

@Path("libro")
public class RestLibro {

    @Path("getAllLibros")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getAllLibros() {
        String out = null;
        List<Libro> libros = null;
        ControllerLibro cl = new ControllerLibro();
        try {
            libros = cl.getAllLibros();
            out = new Gson().toJson(libros);
        } catch (ClassNotFoundException | SQLException e) {
            out = "{\"error\":\"Ocurrió un error. Intente más tarde.\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity(out).build();
        }
        return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(out).build();
    }

    @Path("agregarLibro")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarLibro(@FormParam("nombre_libro") String nombre_libro,
            @FormParam("autor_libro") String autor_libro,
            @FormParam("genero_libro") String genero_libro,
            @FormParam("pdf_libro") String pdf_libro) {
        String out;
        ControllerLibro cl = new ControllerLibro();
        try {
            Libro nuevoLibro = new Libro(0, nombre_libro, autor_libro, genero_libro, pdf_libro);
            boolean resultado = cl.agregarLibro(nuevoLibro);
            if (resultado) {
                out = "{\"success\":\"Libro agregado correctamente\"}";
            } else {
                out = "{\"error\":\"No se pudo agregar el libro\"}";
            }
            return Response.ok(out).build();
        } catch (ClassNotFoundException e) {
            out = "{\"error\":\"Ocurrió un error. Intente más tarde.\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(out).build();
        }
    }

    @Path("editarLibro/{cve_libro}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response editarLibro(@PathParam("cve_libro") int cve_libro,
            @FormParam("nombre_libro") String nombre_libro,
            @FormParam("autor_libro") String autor_libro,
            @FormParam("genero_libro") String genero_libro,
            @FormParam("pdf_libro") String pdf_libro) {
        String out;
        ControllerLibro cl = new ControllerLibro();
        try {
            Libro libro = new Libro(cve_libro, nombre_libro, autor_libro, genero_libro, pdf_libro);
            boolean resultado = cl.editarLibro(libro);
            if (resultado) {
                out = "{\"success\":\"Libro editado correctamente\"}";
            } else {
                out = "{\"error\":\"No se pudo editar el libro\"}";
            }
            return Response.ok(out).build();
        } catch (ClassNotFoundException e) {
            out = "{\"error\":\"Ocurrió un error. Intente más tarde.\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(out).build();
        }
    }

    @Path("eliminarLibro/{cve_libro}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarLibro(@PathParam("cve_libro") int cve_libro) {
        String out;
        ControllerLibro cl = new ControllerLibro();
        try {
            boolean resultado = cl.eliminarLibro(cve_libro);
            if (resultado) {
                out = "{\"success\":\"Libro eliminado correctamente\"}";
            } else {
                out = "{\"error\":\"No se pudo eliminar el libro\"}";
            }
            return Response.ok(out).build();
        } catch (ClassNotFoundException e) {
            out = "{\"error\":\"Ocurrió un error. Intente más tarde.\"}";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(out).build();
        }
    }
}
