package org.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import org.controller.ControllerLibro;
import org.model.Libro;

@Path("/libros")
public class RestLibro {

    private final ControllerLibro controllerLibro;

    public RestLibro() {
        this.controllerLibro = new ControllerLibro();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addLibro(String libroJson) {
        Gson gson = new Gson();
        Libro libro = gson.fromJson(libroJson, Libro.class);

        try {
            boolean success = controllerLibro.agregarLibro(libro);
            if (success) {
                return Response.status(Response.Status.CREATED).entity("Libro agregado exitosamente").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al agregar el libro").build();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al agregar el libro: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLibro(@PathParam("id") int id) {
        try {
            Libro libro = controllerLibro.buscarLibroPorId(id);
            if (libro != null) {
                return Response.ok(libro).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Libro no encontrado").build();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener el libro: " + e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLibros() {
        try {
            List<Libro> libros = controllerLibro.getAllLibros();
            return Response.ok(libros).build();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener los libros: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLibro(@PathParam("id") int id, String libroJson) {
        Gson gson = new Gson();
        Libro libro = gson.fromJson(libroJson, Libro.class);
        libro.setCve_libro(id); // Asegura que el ID en el libro esté correcto

        try {
            boolean success = controllerLibro.editarLibro(libro);
            if (success) {
                return Response.ok("Libro actualizado exitosamente").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Libro no encontrado para actualizar").build();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al actualizar el libro: " + e.getMessage()).build();
        }
    }
}
