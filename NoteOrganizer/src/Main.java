import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        NoteManager manager = new NoteManager();
        NoteSearcher searcher = new NoteSearcher(manager);
        BackupThread backupThread = new BackupThread(manager);
        backupThread.start();  // Start background backup

        try {
            manager.loadFromFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {  // Loop for flow control
            System.out.println("\nStudy Forge Menu:");
            System.out.println("1. Add Note");
            System.out.println("2. Edit Note");
            System.out.println("3. Delete Note");
            System.out.println("4. Search Notes");
            System.out.println("5. Attach File to Note");
            System.out.println("6. View All Notes");
            System.out.println("7. Save & Exit");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {  // Switch for flow control
                case 1:
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Topic: ");
                    String topic = scanner.nextLine();
                    System.out.print("Content: ");
                    String content = scanner.nextLine();
                    manager.addNote(new TextNote(title, topic, content, ""));
                    break;
                case 2:
                    System.out.print("Title to edit: ");
                    String editTitle = scanner.nextLine();
                    System.out.print("New Content: ");
                    String newContent = scanner.nextLine();
                    manager.editNote(editTitle, newContent);
                    break;
                case 3:
                    System.out.print("Title to delete: ");
                    String delTitle = scanner.nextLine();
                    manager.deleteNote(delTitle);
                    break;
                case 4:
                    System.out.print("Keyword: ");
                    String keyword = scanner.nextLine();
                    searcher.performSearch(keyword);
                    break;
                case 5:
                    System.out.print("Title to attach file: ");
                    String attachTitle = scanner.nextLine();
                    System.out.print("Source File Path (e.g., C:/path/to/file.pdf): ");
                    String sourcePath = scanner.nextLine();
                    try {
                        manager.attachFileToNote(attachTitle, sourcePath);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    manager.viewAllNotes();
                    break;
                case 7:
                    try {
                        manager.saveToFile();
                        backupThread.stopBackup();
                        exit = true;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }
}
