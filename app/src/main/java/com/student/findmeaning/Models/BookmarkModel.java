package com.student.findmeaning.Models;

public class BookmarkModel {
        private int id;
        private String word;

    public BookmarkModel(String word) {
        this(-1, word);
    }
        public BookmarkModel(int id, String word) {
            this.id = id;
            this.word = word;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

}
