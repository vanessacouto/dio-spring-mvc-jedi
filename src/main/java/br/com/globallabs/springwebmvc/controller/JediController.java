package br.com.globallabs.springwebmvc.controller;

import br.com.globallabs.springwebmvc.model.Jedi;
import br.com.globallabs.springwebmvc.service.JediService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

// a partir dessa anotação (@Controller) o Spring sabe que vai ter que controlar as instancias desse Controller
// o Spring será responsável por todo o Ciclo de vida desse objeto
@Controller
public class JediController {

    @Autowired
    private JediService service;

    @GetMapping("/jedi")
    public ModelAndView jedi() {
        final ModelAndView modelAndView = new ModelAndView();
        // seta o nome da pagina HTML
        modelAndView.setViewName("jedi");

        //adiciona um objeto lista chamado "allJedi"
        modelAndView.addObject("allJedi", service.findAll());

        return modelAndView;
    }

    @GetMapping("/new-jedi")
    public ModelAndView newJedi() {
        final ModelAndView modelAndView = new ModelAndView();
        // seta o nome da pagina HTML
        modelAndView.setViewName("new-jedi");

        //adiciona um objeto chamado "jedi"
        modelAndView.addObject("jedi", new Jedi());

        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(value = "name") final String name) {

        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jedi");

        modelAndView.addObject("allJedi", service.findByNameContainingIgnoreCase(name));

        return modelAndView;
    }

    // ao clicar no botão submit vai fazer uma ação de POST e passar o objeto Jedi
    @PostMapping("/jedi")
    public String createJedi(@Valid @ModelAttribute Jedi jedi, BindingResult result, RedirectAttributes redirectAttributes) { // usando a anotação @ModelAttribute o
        // Spring consegue converter o "name" e "lastName" que passamos no "form" para o objeto Jedi

        if(result.hasErrors()) { // se não passar na validação, permanece na página atual
            return "new-jedi";
        }

        service.save(jedi);
        // após inserir, vai carregar uma mensagem de sucesso na página "jedi" (a página do "redirect")
        redirectAttributes.addFlashAttribute("message", "Jedi cadastrado com sucesso!");

        // após adicionar, redireciona para a tela que lista os Jedi
        return "redirect:jedi";
    }

    @GetMapping("/jedi/{id}/delete")
    public String deleteJedi(@PathVariable("id") final Long id, RedirectAttributes redirectAttributes) {

        final Jedi jedi = service.findById(id);

        service.delete(jedi.getId());

        redirectAttributes.addFlashAttribute("message", "Jedi removido com sucesso.");

        return "redirect:/jedi" ;
    }

    @GetMapping("/jedi/{id}/update")
    public String updateJedi(@PathVariable("id") final Long id, Model model) {

        final Jedi jedi = service.findById(id);
        model.addAttribute("jedi", jedi);

        return "edit-jedi";
    }
}
