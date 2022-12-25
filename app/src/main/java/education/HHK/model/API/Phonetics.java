package education.HHK.model.API;

public class Phonetics {
    private String text;
    private String audio;
    private String sourceUrl;
    private class license {
        String name;
        String url;
    }
    public Phonetics() {
    }

    public Phonetics(String text, String audio,String sourceUrl) {
        this.text = text;
        this.audio = audio;
        this.sourceUrl = sourceUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
