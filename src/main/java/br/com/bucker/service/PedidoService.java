package br.com.bucker.service;

import br.com.bucker.model.ItemPedido;
import br.com.bucker.model.Pedido;
import br.com.bucker.model.Produto;
import org.hibernate.collection.internal.PersistentIdentifierBag;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Map;

@ApplicationScoped
public class PedidoService extends GenericService<Pedido> implements IService<Pedido> {

    @Transactional
    public Pedido buscarPeloId(Long id) {
        return Pedido.findById(id);
    }

    @Transactional
    public Pedido incluir(Pedido entity) {

        if (entity.isPersistent())
            entity.persistAndFlush();
        else
            Pedido.persist(entity);

        return  buscarPeloId(entity.id);

    }

    @Transactional
    public Long apagarPeloId(Long id) {
        return Pedido.delete("id = ?1", id);
    }

    @Transactional
    public Pedido atualizarPeloId(Pedido entity) {

        if (entity.itemsPedido != null) {
            for (ItemPedido item : entity.itemsPedido) {
                if (item.isPersistent())
                    item.persist();
                else
                    ItemPedido.persist(item);
            }
        }

        String queryUpdate = stringUpdate(entity);
        Map<String, Object> map = objetoParaMap(entity);
        Pedido.update(queryUpdate, map);
        Pedido pedido = buscarPeloId(entity.id);
        pedido.valorTotal = totalizarValoresDosItens(pedido);
        return pedido;
    }

    public Float totalizarValoresDosItens(Pedido pedido) {

        Float total = 0f;

        if (pedido.itemsPedido != null) {
            for (ItemPedido item : pedido.itemsPedido)
                total += item.valorTotal;
        }
        return total;

    }

    public Pedido buscarPeloIdCliente(Long id) {
        return Pedido.find("cliente.id = ?1", id).firstResult();
    }
}
