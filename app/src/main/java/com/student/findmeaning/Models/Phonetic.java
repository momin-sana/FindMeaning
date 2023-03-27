package com.student.findmeaning.Models;

public class Phonetic {

    private String text;
    private String audio;

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

    public boolean hasAudio() {
        return audio != null && !audio.isEmpty();
    }

    public boolean hasText() {
        return text != null && !text.isEmpty();
    }
}
