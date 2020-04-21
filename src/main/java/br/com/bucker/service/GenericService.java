package br.com.bucker.service;

import br.com.bucker.model.EntityBase;
import br.com.bucker.util.ObjectMapperUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GenericService<T extends EntityBase> {

    protected static ObjectMapper mapper = ObjectMapperUtils.getNewMapper("dd/MM/yyyy");
    static {
        mapper.disable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
    }


    /**
     * Cria um a String HQL para atualizar o objeto no banco, desconsiderandos os campos nulos
     * na atualização do registro, mantendo os valores originais do banco
     * @param entity
     * @return
     */
    protected String stringUpdate(T entity) {
        Map<String, Object> entityMap = objetoParaMap(entity);
        Map<String, Object> entityUpdate = new HashMap<>();
        entityUpdate.put("id", entity.id);

        String queryUpdate = "";
        for (Map.Entry<String, Object> entry : entityMap.entrySet()) {
            if (entry.getValue() != null && !"id".equals(entry.getKey())) {
                entityUpdate.put(entry.getKey(), entry.getValue());
                if (entry.getKey().toLowerCase().contains("data"))
                    queryUpdate += ", " + entry.getKey() + " = TO_DATE(:" + entry.getKey() + ", 'DD/MM/YYYY')";
                else
                    queryUpdate += ", " + entry.getKey() + " = :" + entry.getKey();
            }
        }

        return queryUpdate.substring(1) + " Where id = :id";
    }

    protected Map<String, Object> objetoParaMap(T entity) {
        Map<String, Object> novoMapa = new HashMap<>();
        Map<String, Object> map = mapper.convertValue(entity, Map.class);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null &&
                    !(entry.getValue() instanceof ArrayList) &&
                    (
                            entry.getValue() instanceof Long ||
                            entry.getValue() instanceof Integer ||
                            entry.getValue() instanceof String ||
                            entry.getValue() instanceof Float ||
                            entry.getValue() instanceof Double ||
                            entry.getValue() instanceof Boolean
                    )) {
                novoMapa.put(entry.getKey(), entry.getValue());
            }
        }
        return novoMapa;
    }
}
