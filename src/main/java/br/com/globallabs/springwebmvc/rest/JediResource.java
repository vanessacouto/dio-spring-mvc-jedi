package br.com.globallabs.springwebmvc.rest;

import br.com.globallabs.springwebmvc.model.Jedi;
import br.com.globallabs.springwebmvc.repository.JediRepository;
import br.com.globallabs.springwebmvc.service.JediService;
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
    private JediService service;

    // listar todos Jedi
    @GetMapping("/api/jedi")
    public List<Jedi> getAllJedi() {
        return service.findAll();
    }

    @GetMapping("/api/jedi/{id}")
    public ResponseEntity<Jedi> getJedi(@PathVariable("id") Long id) {
        final Jedi jedi = service.findById(id);

        return ResponseEntity.ok(jedi); // status code 200 do HTTP

        // para retornar 404 faremos de outra maneira: via a classe "ResourceAdvice"
        //return ResponseEntity.notFound().build(); // retorna status code 404
    }

    @PostMapping("/api/jedi")
    @ResponseStatus(HttpStatus.CREATED) // quando essa chamada der "ok", o status code a retornar será 201, CREATED
    public Jedi createJedi(@Valid @RequestBody Jedi jedi) { // a anotação @RequestBody diz
        // ao Spring que ele vai transformar o JSON que enviarmos via POST no nosso modelo Jedi
        return service.save(jedi);
    }

    @PutMapping("/api/jedi/{id}")
    public ResponseEntity<Jedi> updateJedi(@PathVariable("id") Long id, @Valid @RequestBody Jedi dto) {
        // além do corpo, precisamos que seja informado o "id" a ser alterado

        final Jedi jedi = service.update(id, dto);
        return ResponseEntity.ok(jedi);
    }

    @DeleteMapping("/api/jedi/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJedi(@PathVariable("id") Long id) {
       service.delete(id);
    }
}
