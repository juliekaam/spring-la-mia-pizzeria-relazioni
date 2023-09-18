package org.learning.lamiapizzeriacrud1.controller;

import org.learning.lamiapizzeriacrud1.model.Pizza;
import org.learning.lamiapizzeriacrud1.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
            model.addAttribute("pizza",pizzaList); // passo la lista di libri al model
            return "pizze/listpizze";
        }
        //metodo show che mostra il detaglio di una pizza preso per id
@GetMapping("/show/{pizzaId}")
    public String show(@PathVariable("pizzaId")Integer id,Model model){
    Optional<Pizza> pizzaOptional=pizzaRepository.findById(id);
    if(pizzaOptional.isPresent()){
        Pizza pizzaFound=pizzaOptional.get();
        model.addAttribute("pizza",pizzaFound);
        return "pizze/detailpizza";
    }else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}

}
