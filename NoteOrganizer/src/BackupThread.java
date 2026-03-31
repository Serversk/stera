// Thread for automatic backup
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class BackupThread extends Thread {
    private NoteManager manager;
    private final String backupPath = "data/notes_backup.txt";  // Use / or \\ for Windows: "data\\notes_backup.txt"
    private boolean running = true;

    public BackupThread(NoteManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        while (running) {
            try {
                // Create 'data' directory if it doesn't exist
                File dir = new File("data");
                if (!dir.exists()) {
                    dir.mkdirs();  // Creates the directory (and parents if needed)
                }

                synchronized (this) {  // Synchronization for reliability
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(backupPath))) {
                        for (Note note : manager.getNotes()) {
                            writer.write(note.getTitle() + "|" + note.getTopic() + "|" + note.getContent() + "|" + (note.getFilePath() != null ? note.getFilePath() : ""));
                            writer.newLine();
                        }
                    }
                }
                Thread.sleep(30000);  // Backup every 30 seconds
            } catch (Exception e) {
                System.out.println("Backup failed: " + e.getMessage());
            }
        }
    }

    public void stopBackup() {
        running = false;
    }
}
