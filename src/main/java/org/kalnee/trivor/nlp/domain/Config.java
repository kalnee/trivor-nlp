package org.kalnee.trivor.nlp.domain;

import java.util.Optional;

import static java.lang.Boolean.TRUE;

public class Config {

    private Double vocabularyProb = 0.9;
    private Double chunkProb = 0.5;
    private Boolean sentimentAnalysis = TRUE;

    public Config() {
    }

    private Config(Double vocabularyProb, Double chunkProb, Boolean sentimentAnalysis) {
        Optional.ofNullable(vocabularyProb).ifPresent(v -> this.vocabularyProb = v);
        Optional.ofNullable(chunkProb).ifPresent(c -> this.chunkProb = c);
        Optional.ofNullable(sentimentAnalysis).ifPresent(s -> this.sentimentAnalysis = s);
    }

    public Double getVocabularyProb() {
        return vocabularyProb;
    }

    public Double getChunkProb() {
        return chunkProb;
    }

    public Boolean getSentimentAnalysis() {
        return sentimentAnalysis;
    }

    public static class Builder {
        private Double vocabularyProb;
        private Double chunkProb;
        private Boolean sentimentAnalysis;

        public Builder(){
        }

        public Builder vocabularyProb(Double vocabularyProb) {
            this.vocabularyProb = vocabularyProb;
            return this;
        }

        public Builder chunkProb(Double chunkProb) {
            this.chunkProb = chunkProb;
            return this;
        }

        public Builder sentimentAnalysis(Boolean sentimentAnalysis) {
            this.sentimentAnalysis = sentimentAnalysis;
            return this;
        }

        public Config build() {
            return new Config(vocabularyProb, chunkProb, sentimentAnalysis);
        }
    }
}
