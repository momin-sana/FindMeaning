package com.student.findmeaning.Models;

public class HistoryModel {
        private int id;
        private String word;

        public HistoryModel(String word) {
            this(-1, word);
        }

        public HistoryModel(int id, String word) {
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
