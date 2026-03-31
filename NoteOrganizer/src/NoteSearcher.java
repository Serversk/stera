import java.util.Vector;

// Separate class for search to modularize
public class NoteSearcher {
    private NoteManager manager;

    public NoteSearcher(NoteManager manager) {
        this.manager = manager;
    }

    public void performSearch(String keyword) {
        Vector<Note> results = manager.searchByKeyword(keyword);
        if (results.isEmpty()) {
            System.out.println("No notes found.");
        } else {
            System.out.println("Search Results:");
            for (Note note : results) {
                note.display();
            }
        }
    }
}
