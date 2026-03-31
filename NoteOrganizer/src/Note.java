// Abstract class for notes
public abstract class Note {
    private String title;
    private String topic;
    private String content;
    private String filePath;

    public Note(String title, String topic, String content, String filePath) {
        this.title = title;
        this.topic = topic;
        this.content = content;
        this.filePath = filePath;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public abstract void display();
}
