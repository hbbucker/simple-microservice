package br.com.bucker.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class ItemPedido extends EntityBase{

    public Long quantidade = 1l;
    public Float valorUnitario = null;
    public Float valorTotal = null;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    public Pedido pedido;

    @OneToOne
    public Produto produto;

}
