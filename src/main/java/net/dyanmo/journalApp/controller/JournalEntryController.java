package net.dyanmo.journalApp.controller;


import net.dyanmo.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {



    private Map<Long, JournalEntry> journalEntries = new HashMap<>();


    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());

    }
    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry journalEntry){
        journalEntries.put(journalEntry.getId(), journalEntry);
        return true;
    }

    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable Long myId) {
        return journalEntries.get(myId);
    }


    @DeleteMapping("id/{myId}")
    public JournalEntry deleteJournalEntryById(@PathVariable Long myId) {
        return journalEntries.get(myId);
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateJournalById(@PathVariable Long Id, @RequestBody JournalEntry journalEntry) {
       return journalEntries.put(Id, journalEntry);
    }
}
