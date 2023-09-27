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

  /*  // metodi per modificare un borrowing
    @GetMapping("/edit/{borrowingId}")
    public String edit(@PathVariable("borrowingId") Integer id, Model model) {
        // recupero da database il Borrowing da modificare prendendolo per id
        Optional<Borrowing> borrowingResult = borrowingRepository.findById(id);
        if (borrowingResult.isPresent()) {
            // passo come attributo del model il Borrowing da modificare
            model.addAttribute("borrowing", borrowingResult.get());
            // ritorno il nome del template
            return "/borrowings/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/edit/{borrowingId}")
    public String doEdit(@PathVariable("borrowingId") Integer borrowingId,
                         @ModelAttribute("borrowing") Borrowing borrowingForm) {
        // setto l'id del borrowing preso dal path variable
        borrowingForm.setId(borrowingId);
        // salvo il borrowing su database
        borrowingRepository.save(borrowingForm);
        // faccio la redirect alla show del book
        return "redirect:/books/show/" + borrowingForm.getBook().getId();
    }

    // metodo per la delete
    @PostMapping("/delete/{borrowingId}")
    public String delete(@PathVariable("borrowingId") Integer id) {
        // prima di cancellare il borrowing devo recuperare l'id del book
        Optional<Borrowing> borrowingResult = borrowingRepository.findById(id);
        if (borrowingResult.isPresent()) {
            Integer bookId = borrowingResult.get().getBook().getId();
            borrowingRepository.deleteById(id);
            return "redirect:/books/show/" + bookId;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }*/


}


