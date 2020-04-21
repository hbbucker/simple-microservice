package br.com.bucker.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Cliente extends EntityBase {

    public String nome;
    public String endereco;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public LocalDate dataDeNascimento;

//    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    public List<Pedido> pedidos;
}
