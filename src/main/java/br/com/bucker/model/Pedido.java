package br.com.bucker.model;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Pedido extends EntityBase{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public LocalDate dataPedido;

    public Float valorTotal = null;

    @ManyToOne(fetch = FetchType.EAGER)
    public Cliente cliente;

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH } )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "pedido_id", insertable = true, updatable = true, referencedColumnName = "id")
    public List<ItemPedido> itemsPedido;

    /**
     * Adiciona um Produto aos itens do pedido, conforme a quantidade
     * @param produto
     * @param quantidade
     */
    public void addicionarItem(Produto produto, Long quantidade) {
        if (itemsPedido == null)
            itemsPedido = new ArrayList<>();

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.produto = produto;
        itemPedido.quantidade = quantidade;
        itemPedido.valorUnitario = produto.valor;
        itemPedido.valorTotal = itemPedido.quantidade * itemPedido.valorUnitario;
        itemPedido.pedido = this;

        if (this.valorTotal == null)
            this.valorTotal = 0f;

        this.valorTotal += itemPedido.valorTotal;
        this.itemsPedido.add(itemPedido);
    }

}
