package org.learning.lamiapizzeriacrud1.controller;



import java.time.LocalDate;
import java.util.Optional;

import org.learning.lamiapizzeriacrud1.model.Pizza;
import org.learning.lamiapizzeriacrud1.model.SpecialOffer;
import org.learning.lamiapizzeriacrud1.repository.PizzaRepository;
import org.learning.lamiapizzeriacrud1.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/specialoffers")
public class OffertaController {

    @Autowired
    private SpecialOfferRepository specialOfferRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    // metodi per aggiungere un'offerta
    @GetMapping("/create")
    public String create(@RequestParam("pizzaId") Integer pizzaId, Model model) {
        // dal pizzaId voglio recuperare la pizza
        Optional<Pizza> pizzaResult = pizzaRepository.findById(pizzaId);
        if (pizzaResult.isPresent()) {
            // se esiste la pizza con quell'id proseguo
           Pizza pizza = pizzaResult.get();
            // creo l'offerta da passare alla view

            SpecialOffer specialOffer=new SpecialOffer();
            // all'offerta associo la pizza presa da db
            specialOffer.setPizza(pizza);

            // precarico i campi date con valori di default
            specialOffer.setStartdate(LocalDate.now());
            specialOffer.setEnddate(LocalDate.now().plusDays(30));
            // passo l'offerta configurata alla view tramite model
            model.addAttribute("specialoffer", specialOffer);
            return "specialoffers/form";

        } else {
            // se non esiste sollevo un'eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "pizza with id " + pizzaId + " not found");
        }

    }

    @PostMapping("/create")
    public String doCreate(@ModelAttribute("specialoffer") SpecialOffer specialOfferForm) {
        // salvo l'offerta su database
        specialOfferRepository.save(specialOfferForm);

        // faccio la redirect alla show della pizza in offerta
        return "redirect:/pizze/detailpizza/" +specialOfferForm.getPizza().getId();
    }

    // metodi per modificare un'offerta
    @GetMapping("/edit/{specialofferId}")
    public String edit(@PathVariable("specialofferId") Integer id, Model model) {
        // recupero da database l'offerta da modificare prendendola per id
        Optional<SpecialOffer> specialOfferResult = specialOfferRepository.findById(id);
        if (specialOfferResult.isPresent()) {
            // passo come attributo del model l'offerta da modificare
            model.addAttribute("specialoffer", specialOfferResult.get());
            // ritorno il nome del template
            return "/specialoffers/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/edit/{specialofferId}")
    public String doEdit(@PathVariable("specialofferId") Integer specialofferId,
                         @ModelAttribute("specialoffer") SpecialOffer specialOfferForm) {
        // setto l'id dell'offerta preso dal path variable
        specialOfferForm.setId(specialofferId);
        // salvol'offerta su database
        specialOfferRepository.save(specialOfferForm);
        // faccio la redirect alla show della pizza
        return "redirect:/pizze/show/" + specialOfferForm.getPizza().getId();
    }

    // metodo per la delete
    @PostMapping("/delete/{specialofferId}")
    public String delete(@PathVariable("specialofferId") Integer id) {
        // prima di cancellare l'offerta devo recuperare l'id della pizza
        Optional<SpecialOffer> specialOfferResult = specialOfferRepository.findById(id);
        if (specialOfferResult.isPresent()) {
            Integer pizzaId = specialOfferResult.get().getPizza().getId();
           specialOfferRepository.deleteById(id);
            return "redirect:/pizze/show/" + pizzaId;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }


}


