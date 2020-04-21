package br.com.bucker.resource;

import br.com.bucker.model.Pedido;
import br.com.bucker.model.Produto;
import br.com.bucker.service.PedidoService;
import br.com.bucker.service.ProdutoService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pedido")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource implements IResource<Pedido> {

    @Inject
    public PedidoService pedidoService;

    @Inject
    public ProdutoService produtoService;

    @GET
    @RolesAllowed("user")
    @Path("/{id}")
    public Pedido buscarPeloId(@PathParam("id") Long id) {
        return pedidoService.buscarPeloId(id);
    }

    @POST
    @RolesAllowed("user")
    public Response incluir(Pedido pedido) {
        pedido = pedidoService.incluir(pedido);
        return Response.ok(pedido).status(Response.Status.CREATED).build();
    }

    @DELETE
    @RolesAllowed("user")
    @Path("/{id}")
    public Response apagarPeloId(@PathParam("id") Long id) {
        Response.Status response;
        if (pedidoService.apagarPeloId(id) > 0) {
            response = Response.Status.NO_CONTENT;
        } else {
            response = Response.Status.NOT_FOUND;
        }

        return Response.status(response).build();
    }

    @PUT
    @RolesAllowed("user")
    @Path("/{id}")
    public Response atualizarPeloId(@PathParam("id") Long id, Pedido pedido){
        Response.Status response = Response.Status.OK;
        pedido.id = id;
        if (pedidoService.atualizarPeloId(pedido) == null)
            response = Response.Status.NOT_FOUND;

        return Response.ok(pedido).status(response).build();
    }


    @GET
    @RolesAllowed("user")
    @Path("/{id}/{produtoId}/{produtoQtd}")
    @Transactional
    public Response incluirItemPedido(@PathParam("id") Long id,
                                      @PathParam("produtoId") Long produtoId,
                                      @PathParam("produtoQtd") Long produtoQtd) {

        Response.Status response = Response.Status.OK;

        Produto produto = produtoService.buscarPeloId(produtoId);
        Pedido pedido = pedidoService.buscarPeloId(id);

        pedido.addicionarItem(produto, produtoQtd);

        if (pedidoService.atualizarPeloId(pedido) == null)
            response = Response.Status.NOT_FOUND;

        return Response.ok(pedido).status(response).build();
    }
}