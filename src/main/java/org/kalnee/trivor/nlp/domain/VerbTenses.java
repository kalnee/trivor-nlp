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

public class VerbTenses {

    private Set<String> simplePresent = new HashSet<>();
    private Set<String> simplePast = new HashSet<>();
    private Set<String> simpleFuture = new HashSet<>();
    private Set<String> presentProgressive = new HashSet<>();
    private Set<String> pastProgressive = new HashSet<>();
    private Set<String> futureProgressive = new HashSet<>();
    private Set<String> presentPerfect = new HashSet<>();
    private Set<String> pastPerfect = new HashSet<>();
    private Set<String> futurePerfect = new HashSet<>();

    private Set<String> mixedTenses = new HashSet<>();
    private Set<String> nonSentences = new HashSet<>();

    public Set<String> getSimplePresent() {
        return simplePresent;
    }

    public void setSimplePresent(Set<String> simplePresent) {
        this.simplePresent = simplePresent;
    }

    public Set<String> getSimplePast() {
        return simplePast;
    }

    public void setSimplePast(Set<String> simplePast) {
        this.simplePast = simplePast;
    }

    public Set<String> getSimpleFuture() {
        return simpleFuture;
    }

    public void setSimpleFuture(Set<String> simpleFuture) {
        this.simpleFuture = simpleFuture;
    }

    public Set<String> getPresentProgressive() {
        return presentProgressive;
    }

    public void setPresentProgressive(Set<String> presentProgressive) {
        this.presentProgressive = presentProgressive;
    }

    public Set<String> getPastProgressive() {
        return pastProgressive;
    }

    public void setPastProgressive(Set<String> pastProgressive) {
        this.pastProgressive = pastProgressive;
    }

    public Set<String> getFutureProgressive() {
        return futureProgressive;
    }

    public void setFutureProgressive(Set<String> futureProgressive) {
        this.futureProgressive = futureProgressive;
    }

    public Set<String> getPresentPerfect() {
        return presentPerfect;
    }

    public void setPresentPerfect(Set<String> presentPerfect) {
        this.presentPerfect = presentPerfect;
    }

    public Set<String> getPastPerfect() {
        return pastPerfect;
    }

    public void setPastPerfect(Set<String> pastPerfect) {
        this.pastPerfect = pastPerfect;
    }

    public Set<String> getFuturePerfect() {
        return futurePerfect;
    }

    public void setFuturePerfect(Set<String> futurePerfect) {
        this.futurePerfect = futurePerfect;
    }

    public Set<String> getMixedTenses() {
        return mixedTenses;
    }

    public void setMixedTenses(Set<String> mixedTenses) {
        this.mixedTenses = mixedTenses;
    }

    public Set<String> getNonSentences() {
        return nonSentences;
    }

    public void setNonSentences(Set<String> nonSentences) {
        this.nonSentences = nonSentences;
    }
}

