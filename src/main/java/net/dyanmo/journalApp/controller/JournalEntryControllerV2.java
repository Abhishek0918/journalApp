package net.dyanmo.journalApp.controller;


import net.dyanmo.journalApp.entity.JournalEntry;
import net.dyanmo.journalApp.repository.JournalEntryRepository;
import net.dyanmo.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.bson.types.ObjectId;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/journal")
public  class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping
    public List<JournalEntry> getAllEntries() {
        return journalEntryService.getAllEntries();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
         journalEntryService.saveEntry(journalEntry);
        return journalEntry;
    }



    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id){
        Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(id);
        if (journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public boolean  deleteJournalEntryById(@PathVariable ObjectId myId) {
        journalEntryService.deleteEntry(myId);
        return true;
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry updatedEntry) {
        Optional<JournalEntry> oldOptional = journalEntryService.getEntryById(id);

        if(oldOptional.isPresent()) {
            JournalEntry old = oldOptional.get();

            if(updatedEntry.getTitle() != null && !updatedEntry.getTitle().isEmpty()) {
                old.setTitle(updatedEntry.getTitle());
            }
            if(updatedEntry.getContent() != null && !updatedEntry.getContent().isEmpty()) {
                old.setContent(updatedEntry.getContent());
            }

            // Save updated entry
            return journalEntryService.saveEntry(old);
        } else {
            // Entry not found, return null or throw exception
            return null;
        }
    }
}
