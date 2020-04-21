package br.com.bucker.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

/**
 * SuperClasse padrão para a geração da tabela no banco de forma automática
 */
@MappedSuperclass
public abstract class EntityBase extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", insertable=false, updatable=false)
    public Long id = null;

    public EntityBase(){}

    public String toString() {
        return this.getClass().getSimpleName() + "<" + this.id + ">";
    }
}
