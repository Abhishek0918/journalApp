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
    public ResponseEntity<List<JournalEntry>> getAllEntries() {

        List<JournalEntry> entries = journalEntryService.getAllEntries();

        if (entries != null && !entries.isEmpty()) {
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> saveEntry(@RequestBody JournalEntry journalEntry) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(journalEntry);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id) {
        Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(id);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public  ResponseEntity<JournalEntry> deleteJournalEntryById(@PathVariable ObjectId myId) {
        journalEntryService.deleteEntry(myId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId id,
                                                          @RequestBody JournalEntry updatedEntry) {

        Optional<JournalEntry> oldOptional = journalEntryService.getEntryById(id);

        if (oldOptional.isPresent()) {
            JournalEntry old = oldOptional.get();

            if (updatedEntry.getTitle() != null && !updatedEntry.getTitle().isEmpty()) {
                old.setTitle(updatedEntry.getTitle());
            }
            if (updatedEntry.getContent() != null && !updatedEntry.getContent().isEmpty()) {
                old.setContent(updatedEntry.getContent());
            }

            JournalEntry saved = journalEntryService.saveEntry(old);

            return new ResponseEntity<>(saved, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
