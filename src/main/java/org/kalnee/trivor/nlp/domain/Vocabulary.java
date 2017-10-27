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

import java.util.HashSet;
import java.util.Set;

public class Vocabulary {

    private Set<WordUsage> adjectives = new HashSet<>();
    private Set<WordUsage> adverbs = new HashSet<>();
    private Set<WordUsage> comparatives = new HashSet<>();
    private Set<WordUsage> modals = new HashSet<>();
    private Set<WordUsage> nouns = new HashSet<>();
    private Set<WordUsage> prepositions = new HashSet<>();
    private Set<WordUsage> superlatives = new HashSet<>();
    private Set<WordUsage> verbs = new HashSet<>();
    private Set<WordUsage> whWords = new HashSet<>();

    public Set<WordUsage> getAdjectives() {
        return adjectives;
    }

    public void setAdjectives(Set<WordUsage> adjectives) {
        this.adjectives = adjectives;
    }

    public Set<WordUsage> getAdverbs() {
        return adverbs;
    }

    public void setAdverbs(Set<WordUsage> adverbs) {
        this.adverbs = adverbs;
    }

    public Set<WordUsage> getComparatives() {
        return comparatives;
    }

    public void setComparatives(Set<WordUsage> comparatives) {
        this.comparatives = comparatives;
    }

    public Set<WordUsage> getModals() {
        return modals;
    }

    public void setModals(Set<WordUsage> modals) {
        this.modals = modals;
    }

    public Set<WordUsage> getNouns() {
        return nouns;
    }

    public void setNouns(Set<WordUsage> nouns) {
        this.nouns = nouns;
    }

    public Set<WordUsage> getPrepositions() {
        return prepositions;
    }

    public void setPrepositions(Set<WordUsage> prepositions) {
        this.prepositions = prepositions;
    }

    public Set<WordUsage> getSuperlatives() {
        return superlatives;
    }

    public void setSuperlatives(Set<WordUsage> superlatives) {
        this.superlatives = superlatives;
    }

    public Set<WordUsage> getVerbs() {
        return verbs;
    }

    public void setVerbs(Set<WordUsage> verbs) {
        this.verbs = verbs;
    }

    public Set<WordUsage> getWhWords() {
        return whWords;
    }

    public void setWhWords(Set<WordUsage> whWords) {
        this.whWords = whWords;
    }
}

