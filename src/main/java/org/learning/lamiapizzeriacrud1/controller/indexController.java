package org.learning.lamiapizzeriacrud1.controller;

import org.learning.lamiapizzeriacrud1.model.Pizza;
import org.learning.lamiapizzeriacrud1.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pizze")

public class indexController {



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

}
