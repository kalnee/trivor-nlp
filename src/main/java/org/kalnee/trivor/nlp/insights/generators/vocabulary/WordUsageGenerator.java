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

package org.kalnee.trivor.nlp.insights.generators.vocabulary;

import org.kalnee.trivor.nlp.domain.Sentence;
import org.kalnee.trivor.nlp.domain.Token;
import org.kalnee.trivor.nlp.domain.WordUsage;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.Character.isUpperCase;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toSet;

abstract class WordUsageGenerator {

    private static final String WORD_REGEX = "([a-zA-Z]{2,})";
    private static final double ACCEPTED_PROB = 0.90;

    abstract List<String> getTags();

    Set<WordUsage> getSentences(List<Sentence> sentences) {
        return getSentences(sentences, Token::getLemma, null);
    }

    Set<WordUsage> getSentences(List<Sentence> sentences, Predicate<String> customFilter) {
        return getSentences(sentences, Token::getLemma, customFilter);
    }

    Set<WordUsage> getSentences(List<Sentence> sentences, Function<Token, String> customMapper,
                                 Predicate<String> customFilter) {
        final Map<String, Set<String>> words = new HashMap<>();
        for (Sentence sentence: sentences) {
            Stream<String> wordsStream = sentence.getTokens().stream()
                    .filter(t -> t.getToken().matches(WORD_REGEX))
                    .filter(t -> getTags().contains(t.getTag()))
                    .filter(t -> !isUpperCase(t.getToken().charAt(0)))
                    .filter(t -> t.getProb() >= ACCEPTED_PROB)
                    .map(customMapper);

            if (customFilter != null) {
                wordsStream = wordsStream.filter(customFilter);
            }

            wordsStream.forEach(word -> {
                final Set<String> wordSentences = words.getOrDefault(word, new HashSet<>());
                wordSentences.add(sentence.getSentence());
                words.put(word, wordSentences);
            });
        }

        return words.entrySet().stream()
                .sorted(reverseOrder(comparingInt(e -> e.getValue().size())))
                .map(e -> new WordUsage(e.getKey(), e.getValue())).collect(toSet());
    }

    Set<WordUsage> getExamples(Set<WordUsage> sentences) {
        return sentences.stream().limit(2).collect(toSet());
    }
}
