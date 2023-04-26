package com.exercise.demo;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public interface ToDoRepository extends CommonRepository<ToDo> {
    Map<String, ToDo> toDos = new HashMap<>();

    @Override
    public default ToDo save(ToDo domain) {
        ToDo result = toDos.get(domain.getId());
        if (result != null) {
            result.setModifier(LocalDateTime.now());
            result.setDescription(domain.getDescription());
            result.setCompleted(domain.isCompleted());
            domain = result;
        }
        toDos.put(domain.getId(), domain);
        return toDos.get(domain.getId());
    }
    @Override
    public default Iterable<ToDo> save(Collection<ToDo> domains) {
        domains.forEach(this::save);
        return findAll();
    }
    @Override
    public default void  delete(ToDo domain) {
        toDos.remove(domain.getId());
    }
    @Override
    public default ToDo findById(String id){
        return toDos.get(id);
    }
    @Override
    public default Iterable<ToDo> findAll(){
        return toDos.entrySet().stream().sorted(entryComparator).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    Comparator<Map.Entry<String,ToDo>> entryComparator = Comparator.comparing((Map.Entry<String, ToDo> o) -> o.getValue().getCreated());
}
