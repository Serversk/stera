import java.io.*;
import java.util.Vector;

// Manages notes
public class NoteManager implements NoteOperations {
    private Vector<Note> notes = new Vector<>();
    private final String filePath = "data/notes.txt";
    private final String attachmentsDir = "data/attachments/";

    @Override
    public void addNote(Note note) {
        notes.add(note);
        System.out.println("Note added.");
    }

    @Override
    public void editNote(String title, String newContent) {
        for (Note note : notes) {
            if (note.getTitle().equalsIgnoreCase(title)) {
                note.setContent(newContent);
                System.out.println("Note edited.");
                return;
            }
        }
        System.out.println("Note not found.");
    }

    @Override
    public void deleteNote(String title) {
        for (int i = 0; i < notes.size(); i++) {  // Loop for control flow
            if (notes.get(i).getTitle().equalsIgnoreCase(title)) {
                notes.remove(i);
                System.out.println("Note deleted.");
                return;
            }
        }
        System.out.println("Note not found.");
    }

    @Override
    public Vector<Note> searchByKeyword(String keyword) {
        Vector<Note> results = new Vector<>();
        for (Note note : notes) {  // String operations and loops
            if (note.getContent().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(note);
            }
        }
        return results;
    }
	
    public void viewAllNotes() {
    if (notes.isEmpty()) {
        System.out.println("No notes available.");
    } else {
        System.out.println("All Notes:");
        for (Note note : notes) {
            note.display();
            System.out.println("---");
            }
        }
    }

    @Override
    public void attachFileToNote(String title, String sourceFilePath) throws Exception {
        for (Note note : notes) {
            if (note.getTitle().equalsIgnoreCase(title)) {
                // Create attachments dir if needed
                File dir = new File(attachmentsDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // Copy file using binary I/O streams
                String destFileName = attachmentsDir + new File(sourceFilePath).getName();
                try (FileInputStream fis = new FileInputStream(sourceFilePath);
                     FileOutputStream fos = new FileOutputStream(destFileName)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                } catch (IOException e) {
                    throw new IOException("File attachment failed: " + e.getMessage());
                }

                note.setFilePath(destFileName);
                System.out.println("File attached to note.");
                return;
            }
        }
        System.out.println("Note not found.");
    }

    @Override
    public synchronized void saveToFile() throws Exception {  // Synchronized for reliability
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Note note : notes) {
                writer.write(note.getTitle() + "|" + note.getTopic() + "|" + note.getContent() + "|" + (note.getFilePath() != null ? note.getFilePath() : ""));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new FileNotFoundException("File save failed: " + e.getMessage());
        }
    }

    @Override
    public synchronized void loadFromFile() throws Exception {
        File file = new File(filePath);
        if (!file.exists()) return;  // No error if file not found initially

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    String filePath = parts.length > 3 ? parts[3] : null;
                    notes.add(new TextNote(parts[0], parts[1], parts[2], filePath));
                }
            }
        } catch (IOException e) {
            throw new FileNotFoundException("File load failed: " + e.getMessage());
        }
    }

    public Vector<Note> getNotes() {
        return notes;
    }
}
