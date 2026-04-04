package net.dyanmo.journalApp.service;

import net.dyanmo.journalApp.entity.JournalEntry;
import net.dyanmo.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    // Save or update an entry
    public JournalEntry saveEntry(JournalEntry journalEntry){
        return journalEntryRepository.save(journalEntry);
    }

    // Get all entries
    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    // Get entry by ID
    public Optional<JournalEntry> getEntryById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    // Delete entry by ID
    public void deleteEntry(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }
}