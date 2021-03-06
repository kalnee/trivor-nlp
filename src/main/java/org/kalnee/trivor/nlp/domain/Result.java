/* Copyright (c) 2016 Kalnee
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.kalnee.trivor.nlp.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Result {

    private Integer numberOfSentences;
    private Map<SentimentEnum, BigDecimal> sentimentAnalysis;
    private RateOfSpeech rateOfSpeech;
    private Set<SentenceFrequency> frequentSentences;
    private Set<ChunkFrequency> frequentChunks;
    private List<FrequencyRate> frequencyRate;
    private Set<PhrasalVerbUsage> phrasalVerbs;

    private Vocabulary vocabulary;

    public Integer getNumberOfSentences() {
        return numberOfSentences;
    }

    public void setNumberOfSentences(Integer numberOfSentences) {
        this.numberOfSentences = numberOfSentences;
    }

    public Map<SentimentEnum, BigDecimal> getSentimentAnalysis() {
        return sentimentAnalysis;
    }

    public void setSentimentAnalysis(Map<SentimentEnum, BigDecimal> sentimentAnalysis) {
        this.sentimentAnalysis = sentimentAnalysis;
    }

    public RateOfSpeech getRateOfSpeech() {
        return rateOfSpeech;
    }

    public void setRateOfSpeech(RateOfSpeech rateOfSpeech) {
        this.rateOfSpeech = rateOfSpeech;
    }

    public Set<SentenceFrequency> getFrequentSentences() {
        return frequentSentences;
    }

    public void setFrequentSentences(Set<SentenceFrequency> frequentSentences) {
        this.frequentSentences = frequentSentences;
    }

    public Set<ChunkFrequency> getFrequentChunks() {
        return frequentChunks;
    }

    public void setFrequentChunks(Set<ChunkFrequency> frequentChunks) {
        this.frequentChunks = frequentChunks;
    }

    public List<FrequencyRate> getFrequencyRate() {
        return frequencyRate;
    }

    public void setFrequencyRate(List<FrequencyRate> frequencyRate) {
        this.frequencyRate = frequencyRate;
    }

    public Set<PhrasalVerbUsage> getPhrasalVerbs() {
        return phrasalVerbs;
    }

    public void setPhrasalVerbs(Set<PhrasalVerbUsage> phrasalVerbs) {
        this.phrasalVerbs = phrasalVerbs;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }
}
