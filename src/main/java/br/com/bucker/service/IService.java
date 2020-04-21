package br.com.bucker.service;

import br.com.bucker.model.EntityBase;

public interface IService<T extends EntityBase> {
    T buscarPeloId(Long id);
    T incluir(T entity);
    Long apagarPeloId(Long id);
    T atualizarPeloId(T entity);
}
