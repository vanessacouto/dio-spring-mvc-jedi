package br.com.globallabs.springwebmvc.rest;

import br.com.globallabs.springwebmvc.model.Jedi;
import br.com.globallabs.springwebmvc.repository.JediRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class JediResource {

    @Autowired
    private JediRepository repository;

    // listar todos Jedi
    @GetMapping("/api/jedi")
    public List<Jedi> getAllJedi() {
        return repository.findAll();
    }

    @GetMapping("/api/jedi/{id}")
    public ResponseEntity<Jedi> getJedi(@PathVariable("id") Long id) {
        final Optional<Jedi> jedi =repository.findById(id);

        if(jedi.isPresent()) {
            return ResponseEntity.ok(jedi.get()); // status code 200 do HTTP
        } else {
            return ResponseEntity.notFound().build(); // retorna status code 404
            //throw new JediNotFoundException();
        }
    }

    @PostMapping("/api/jedi")
    @ResponseStatus(HttpStatus.CREATED) // quando essa chamada der "ok", o status code a retornar será 201, CREATED
    public Jedi createJedi(@Valid @RequestBody Jedi jedi) { // a anotação @RequestBody diz
        // ao Spring que ele vai transformar o JSON que enviarmos via POST no nosso modelo Jedi
        return repository.save(jedi);
    }

    @PutMapping("/api/jedi/{id}")
    public ResponseEntity<Jedi> updateJedi(@PathVariable("id") Long id, @Valid @RequestBody Jedi dto) {
        // além do corpo, precisamos que seja informado o "id" a ser alterado
        final Optional<Jedi> jediEntity = repository.findById(id);
        final Jedi jedi;

        if(jediEntity.isPresent()) {
            jedi = jediEntity.get();
        } else {
            return ResponseEntity.notFound().build();
        }

        jedi.setName(dto.getName());
        jedi.setLastName(dto.getLastName());

        return ResponseEntity.ok(repository.save(jedi));
    }

    @DeleteMapping("/api/jedi/{id}")
    public ResponseEntity deleteJedi(@PathVariable("id") Long id) {
        final Optional<Jedi> jedi = repository.findById(id);

        if(jedi.isPresent()) {
            repository.delete(jedi.get());
            return ResponseEntity.noContent().build(); // retorna status 204 após deletar (será o "OK" do nosso delete)
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
