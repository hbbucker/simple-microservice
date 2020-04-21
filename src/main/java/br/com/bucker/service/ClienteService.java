package br.com.bucker.service;

import br.com.bucker.model.Cliente;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Map;

@ApplicationScoped
public class ClienteService extends GenericService<Cliente> implements IService<Cliente> {

    @Transactional
    public Cliente buscarPeloId(Long id) {
        return Cliente.findById(id);
    }

    @Transactional
    public Cliente incluir(Cliente entity) {

        if (entity.isPersistent())
            entity.persist();
        else
            Cliente.persist(entity);

        return entity;
    }

    @Transactional
    public Long apagarPeloId(Long id) {
        return Cliente.delete("id = ?1", id);
    }

    @Transactional
    public Cliente atualizarPeloId(Cliente entity) {
        String queryUpdate = stringUpdate(entity);
        Map<String, Object> map = objetoParaMap(entity);
        Cliente.update(queryUpdate, map);
        return buscarPeloId(entity.id);
    }
}
