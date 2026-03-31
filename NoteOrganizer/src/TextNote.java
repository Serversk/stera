// Concrete class extending abstract Note
public class TextNote extends Note {
    public TextNote(String title, String topic, String content, String filePath) {
        super(title, topic, content, filePath);
    }

    @Override
    public void display() {
        System.out.println("Title: " + getTitle() + ", Topic: " + getTopic() + ", Content: " + getContent());
        if (getFilePath() != null && !getFilePath().isEmpty()) {
            System.out.println("Attached File: " + getFilePath());
        }
    }
}
