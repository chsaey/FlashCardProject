package com.example.demo.rest;
import com.example.demo.dao.MyDAO;
import com.example.demo.entity.Flashcards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//This is to allow calls from React
@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class FlashcardsController {
    private final MyDAO myDAO;

    @Autowired
    public FlashcardsController(@Qualifier("flashcardsIMPL") MyDAO myDAO) {
        this.myDAO = myDAO;
    }

    //This is a GET request that will read a list of all the flashcards.
    //http://localhost:8080/retrieveAllFlashcards
    @GetMapping("/retrieveAllFlashcards")
    public List<Object> findAll() {
        return myDAO.fetchAll();
    }

    //This is a POST request to add a new flashcard.
    //http://localhost:8080/addFlashcard
    @PostMapping("/addFlashcard")
    public Flashcards addFlashcard(@RequestBody Flashcards theCard) {

        //also just in case they pass an id in JSON .... set id to o
        //this is to force a save of new item .... instead of update
        theCard.setId(0);

        //This will call the flashcardsImpl.save method to save a new flashcard
        //through the flashcardsDAO interface SPRING
        myDAO.save(theCard);
        return theCard;
    }

    //This is a PUT request to update an existing flashcard.
    //http://localhost:8080/updateFlashcard
    @PutMapping("/updateFlashcard")
    public Flashcards updateFlashcard(@RequestBody Flashcards updateFlashcard) {
        //this will execute an update instead of a create
        myDAO.save(updateFlashcard);
        return updateFlashcard;
    }

    //This is a DELETE request to delete an existing flashcard.
    //http://localhost:8080/deleteFlashcard/1
    @DeleteMapping("/deleteFlashcard/{flashcardId}")
    public String deleteFlashcard(@PathVariable int flashcardId) {
        //Creating a tempFlashcards to check to see if a card exists exists
        Flashcards tempFlashcards = (Flashcards) myDAO.fetchById(flashcardId);

        //This will throw an exception if the card is null
        if (tempFlashcards == null) {
            throw new RuntimeException("Flash card is not found : " + flashcardId);
        }

        //This will execute the deleteByID.
        myDAO.deleteById(flashcardId);
        return "Deleted flashcard id : " + flashcardId;
    }
}
