package org.learning.lamiapizzeriacrud1.controller;

import jakarta.validation.Valid;
import org.learning.lamiapizzeriacrud1.model.Pizza;
import org.learning.lamiapizzeriacrud1.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizze")

public class PizzaController {



        @Autowired
        // dependency injection: quando si crea un oggetto di tipo pizzaController ha bisogno di un PizzaRepository
        private PizzaRepository pizzaRepository;

        // metodo index che mostra la lista di tutte le pizze
        @GetMapping
        public String index(Model model) {
            List<Pizza> pizzaList = pizzaRepository.findAll();// questa è la lista di libri presa da database
            model.addAttribute("pizze",pizzaList); // passo la lista di libri al model
            return "pizze/listpizze";
        }
        //metodo show che mostra il detaglio di una pizza preso per id
@GetMapping("/show/{pizzaId}")
    public String show(@PathVariable("pizzaId")Integer id,Model model){
    Optional<Pizza> pizzaOptional=pizzaRepository.findById(id);
    if(pizzaOptional.isPresent()){
        Pizza pizzaFound=pizzaOptional.get();
        model.addAttribute("pizze",pizzaFound);
        return "pizze/detailpizza";
    }else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}

    // controller che mostra la pagina di creazione di un Book
    @GetMapping("/create") // url
    public String create(Model model) {
        // aggiungiamo al model un attributo di tipo Book
        model.addAttribute("pizza", new Pizza());

        return "pizze/form"; // template
    }

    // metodo che gestisce la POST di creazione di un Book
    /*
     * l'annnotation @Valid davanti al parametro formBook fa scattare la validazione degli attributi
     * di Book che hanno delle annotation di validazione (es. @Notblank)
     * Gli errori di validazione vengono raccolti nella mappa BindingResult bindingResult
     * */
    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza,
                           BindingResult bindingResult) {
        // formBook è un oggetto Book costruito con i dati che arrivano dalla request, quindi dal form

        // prima di salvare il book verifico che non ci siano errori di validazione
        if (bindingResult.hasErrors()) {
            return "pizze/form"; // template
        }

        // posso manipolare l'oggetto formBook prima di salvarlo
        formPizza.setName(formPizza.getName().toUpperCase());

        // per salvare il book su database chiama in aiuto il bookRepository
        pizzaRepository.save(formPizza);
        // sela pizza è stato salvata con successo faccio una redirect alla pagina della lista
        return "redirect:/pizze";
    }

}


