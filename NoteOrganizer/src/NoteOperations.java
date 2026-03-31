// Interface for operations
import java.util.Vector;

public interface NoteOperations {
    void addNote(Note note);
    void editNote(String title, String newContent);
    void deleteNote(String title);
    Vector<Note> searchByKeyword(String keyword);
    void saveToFile() throws Exception;
    void loadFromFile() throws Exception;
    void attachFileToNote(String title, String sourceFilePath) throws Exception;
}
