package br.com.bucker.resource;

import br.com.bucker.model.Produto;
import br.com.bucker.service.ProdutoService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/produto")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutosResource implements IResource<Produto> {

    @Inject
    ProdutoService produtoService;

    @GET
    @RolesAllowed("user")
    @Path("/{id}")
    public Produto buscarPeloId(@PathParam("id") Long id) {
        return produtoService.buscarPeloId(id);
    }

    @POST
    @RolesAllowed("admin")
    public Response incluir(Produto produto) {
        return Response.ok(produtoService.incluir(produto)).status(Response.Status.CREATED).build();
    }

    @DELETE
    @RolesAllowed("admin")
    @Path("/{id}")
    public Response apagarPeloId(@PathParam("id") Long id) {
        Response.Status response;
        if (produtoService.apagarPeloId(id) > 0) {
            response = Response.Status.NO_CONTENT;
        } else {
            response = Response.Status.NOT_FOUND;
        }

        return Response.status(response).build();
    }

    @PUT
    @RolesAllowed("admin")
    @Path("/{id}")
    public Response atualizarPeloId(@PathParam("id") Long id, Produto produto){
        Response.Status response = Response.Status.OK;
        produto.id = id;
        if (produtoService.atualizarPeloId(produto) == null)
            response = Response.Status.NOT_FOUND;

        return Response.ok(produto).status(response).build();
    }

}