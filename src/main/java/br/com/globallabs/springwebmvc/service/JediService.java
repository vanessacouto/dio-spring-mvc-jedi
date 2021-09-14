package br.com.globallabs.springwebmvc.service;

import br.com.globallabs.springwebmvc.excepiton.JediNotFoundException;
import br.com.globallabs.springwebmvc.model.Jedi;
import br.com.globallabs.springwebmvc.repository.JediRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JediService {

    // injeta JediRepository
    // o Spring vai criar uma instancia desse repository para podermos usar
    @Autowired
    private JediRepository repository;

    public List<Jedi> findAll() {

        return repository.findAll();
    }

    public Jedi findById(final Long id) {
        final Optional<Jedi> jedi = repository.findById(id);

        if(jedi.isPresent()) {
            return jedi.get();
        } else {
            throw new JediNotFoundException();
        }
    }

    public Jedi save(final Jedi jedi) {
        return repository.save(jedi);
    }

    public Jedi update(final Long id, final Jedi dto) {
        final Optional<Jedi> jediEntity = repository.findById(id);
        final Jedi jedi;

        if (jediEntity.isPresent()) {
            jedi = jediEntity.get();
        } else {
            throw new JediNotFoundException();
        }

        jedi.setName(dto.getName());
        jedi.setLastName(dto.getLastName());

        return repository.save(jedi);
    }

    public void delete(final Long id) {
        final Jedi jedi = findById(id); // já lança exception se não existir o "id"

        repository.delete(jedi);
    }

    public List<Jedi> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}