package br.com.bucker.resource;

import br.com.bucker.model.Cliente;
import br.com.bucker.service.ClienteService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource implements IResource<Cliente> {

    @Inject
    ClienteService clienteService;

    @GET
    @RolesAllowed("user")
    @Path("/{id}")
    public Cliente buscarPeloId(@PathParam("id") Long id) {
        return clienteService.buscarPeloId(id);
    }

    @POST
    @RolesAllowed("user")
    public Response incluir(Cliente cliente) {
        return Response.ok(clienteService.incluir(cliente)).status(Response.Status.CREATED).build();
    }

    @DELETE
    @RolesAllowed("user")
    @Path("/{id}")
    public Response apagarPeloId(@PathParam("id") Long id) {
        Response.Status response;
        if (clienteService.apagarPeloId(id) > 0) {
            response = Response.Status.NO_CONTENT;
        } else {
            response = Response.Status.NOT_FOUND;
        }

        return Response.status(response).build();
    }

    @PUT
    @RolesAllowed("user")
    @Path("/{id}")
    public Response atualizarPeloId(@PathParam("id") Long id, Cliente cliente){
        Response.Status response = Response.Status.OK;
        cliente.id = id;
        if (clienteService.atualizarPeloId(cliente) == null)
            response = Response.Status.NOT_FOUND;

        return Response.ok(cliente).status(response).build();
    }
}