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

package org.kalnee.trivor.nlp.insights.generators.tenses;

import org.kalnee.trivor.nlp.domain.Sentence;
import org.kalnee.trivor.nlp.domain.VerbTenses;
import org.kalnee.trivor.nlp.insights.generators.Generator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.kalnee.trivor.nlp.domain.InsightsEnum.MIXED_TENSE;

/**
 * Generator for sentences that have more than one verb tense.
 *
 * @see Generator
 * @since 0.0.1
 */
public class MixedTensesGenerator implements Generator<Set<String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MixedTensesGenerator.class);

    private final VerbTenses verbTenses;

    public MixedTensesGenerator(VerbTenses verbTenses) {
        this.verbTenses = verbTenses;
    }

    @Override
    public String getCode() {
        return MIXED_TENSE.getCode();
    }

    @SuppressWarnings("unchecked")
    public Set<String> generate(List<Sentence> sentences) {

        final List<String> allSentences = Stream.of(
                verbTenses.getSimplePresent(),
                verbTenses.getSimplePast(),
                verbTenses.getSimpleFuture(),
                verbTenses.getPresentProgressive(),
                verbTenses.getPastProgressive(),
                verbTenses.getFutureProgressive(),
                verbTenses.getPresentPerfect(),
                verbTenses.getPastPerfect(),
                verbTenses.getFuturePerfect(),
                verbTenses.getNonSentences()
        ).flatMap(Collection::stream).collect(toList());

        final Set<String> mixed = sentences.stream()
                .map(Sentence::getSentence)
                .filter(sentence -> !allSentences.contains(sentence))
                .collect(toSet());

        LOGGER.info(
                format("%s: %d/%d (%.2f%%)", getCode(), mixed.size(), sentences.size(),
                        (mixed.size() * 100d / sentences.size()))
        );

        LOGGER.info("total: {}/{}", allSentences.size() + mixed.size(), sentences.size());
        return mixed;
    }
}
