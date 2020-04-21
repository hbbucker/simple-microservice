package br.com.bucker.resource;

import br.com.bucker.model.EntityBase;

import javax.ws.rs.core.Response;

/**
 * Interface Padrão para utilizar nos Resources/Controllers, com métodos básicos
 * @param <T>
 */
public interface IResource<T extends EntityBase> {
    T buscarPeloId(Long id);
    Response incluir(T entity);
    Response apagarPeloId(Long id);
    Response atualizarPeloId(Long id, T entity);
}
