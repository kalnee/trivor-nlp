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

package org.kalnee.trivor.nlp.insights.processors;

import org.apache.commons.lang3.time.StopWatch;
import org.kalnee.trivor.nlp.domain.*;
import org.kalnee.trivor.nlp.insights.generators.*;
import org.kalnee.trivor.nlp.insights.generators.tenses.*;
import org.kalnee.trivor.nlp.insights.generators.vocabulary.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class InsightsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsightsProcessor.class);
    private final Config config;

    InsightsProcessor() {
        this(new Config());
    }

    InsightsProcessor(Config config) {
        this.config = config != null ? config : new Config();
    }

    Result process(Transcript transcript) {
        final StopWatch sw = new StopWatch();
        sw.start();
        LOGGER.info("Running Transcript Generators");

        final Result result = process(transcript.getSentences());
        result.setSentimentAnalysis(new SentimentGenerator(transcript.getSentiment()).generate(transcript.getSentences()));

        sw.stop();
        LOGGER.info("Insights generated in {}ms", new Object[]{sw.getTime()});
        return result;
    }

    Result process(Subtitle subtitle) {
        final StopWatch sw = new StopWatch();
        sw.start();
        LOGGER.info("Running Transcript Generators");

        final Result result = process(subtitle.getSentences());
        result.setSentimentAnalysis(new SentimentGenerator(subtitle.getSentiment()).generate(subtitle.getSentences()));
        result.setRateOfSpeech(new RateOfSpeechGenerator(subtitle.getDuration()).generate(subtitle.getSentences()));

        sw.stop();
        LOGGER.info("Insights generated in {}ms", new Object[]{sw.getTime()});
        return result;
    }

    private Result process(List<Sentence> sentences) {
        final Result result = new Result();

        result.setNumberOfSentences(new NumberOfSentencesGenerator().generate(sentences));
        result.setFrequentSentences(new FrequentSentencesGenerator().generate(sentences));
        result.setFrequentChunks(new FrequentChunksGenerator().generate(sentences));
        result.setFrequencyRate(new FrequencyRateGenerator().generate(sentences));

        final Vocabulary vocabulary = new Vocabulary();
        vocabulary.setAdjectives(new AdjectivesGenerator(config).generate(sentences));
        vocabulary.setAdverbs(new AdverbsGenerator(config).generate(sentences));
        vocabulary.setComparatives(new ComparativesGenerator(config).generate(sentences));
        vocabulary.setModals(new ModalsGenerator(config).generate(sentences));
        vocabulary.setNouns(new NounsGenerator(config).generate(sentences));
        vocabulary.setPrepositions(new PrepositionGenerator(config).generate(sentences));
        vocabulary.setSuperlatives(new SuperlativesGenerator(config).generate(sentences));
        vocabulary.setVerbs(new VerbsGenerator(config).generate(sentences));
        vocabulary.setWhWords(new WhWordsGenerator(config).generate(sentences));
        result.setVocabulary(vocabulary);
        result.setPhrasalVerbs(new PhrasalVerbsGenerator(vocabulary.getVerbs()).generate(sentences));

        final VerbTenses verbTenses = new VerbTenses();
        verbTenses.setSimplePresent(new SimplePresentGenerator().generate(sentences));
        verbTenses.setSimplePast(new SimplePastGenerator().generate(sentences));
        verbTenses.setSimpleFuture(new SimpleFutureGenerator().generate(sentences));
        verbTenses.setPresentProgressive(new PresentProgressiveGenerator().generate(sentences));
        verbTenses.setPastProgressive(new PastProgressiveGenerator().generate(sentences));
        verbTenses.setFutureProgressive(new FutureProgressiveGenerator().generate(sentences));
        verbTenses.setPresentPerfect(new PresentPerfectGenerator().generate(sentences));
        verbTenses.setPastPerfect(new PastPerfectGenerator().generate(sentences));
        verbTenses.setFuturePerfect(new FuturePerfectGenerator().generate(sentences));
        verbTenses.setNonSentences(new NonSentencesGenerator().generate(sentences));
        verbTenses.setMixedTenses(new MixedTensesGenerator(verbTenses).generate(sentences));
        result.setVerbTenses(verbTenses);

        return result;
    }
}
