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

package org.kalnee.trivor.nlp;

import org.kalnee.trivor.nlp.domain.Config;
import org.kalnee.trivor.nlp.insights.processors.SubtitleProcessor;
import org.kalnee.trivor.nlp.insights.processors.TranscriptProcessor;

import java.net.URISyntaxException;

import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class Main {

    private static final String TRANSCRIPT_CHARACTER_REGEX = "^([0-9]+\\+*[0-9]*):\\s*";

    public static void main(String[] args) throws URISyntaxException {

        SubtitleProcessor sp = new SubtitleProcessor
                .Builder(Main.class.getResource("/language/s1e1.srt").toURI())
                .withDuration(43)
                .withConfig(new Config.Builder().chunkProb(.98).sentimentAnalysis(false).vocabularyProb(.98).build())
                .build();

        TranscriptProcessor tp = new TranscriptProcessor
                .Builder(Main.class.getResource("/transcripts/football").toURI())
                .withFilters(singletonList(line -> !line.contains("De Bruyne")))
                .withMappers(singletonList(line -> line.replaceAll(TRANSCRIPT_CHARACTER_REGEX, EMPTY)))
                .build();

        tp.getResult().getFrequentChunks().stream().limit(10)
                .forEach(chunk -> System.out.println(chunk.getChunk() + ": " + chunk.getFrequency()));

        System.out.println("\n");
        tp.getResult().getPhrasalVerbs().stream().limit(10).forEach(pv -> {
            System.out.println("\n");
            System.out.println(pv.getPhrasalVerb());
            System.out.println(pv.getSentences());
        });

        System.out.println("\n\n");
        tp.getResult().getVocabulary().getVerbs().stream().limit(15)
                .map(verb -> verb.getWord() + ": " + verb.getSentences().size())
                .forEach(System.out::println);

        System.out.println("\n\n");
        tp.getResult().getVocabulary().getNouns().stream().limit(15)
                .map(noun -> noun.getWord() + ": " + noun.getSentences().size())
                .forEach(System.out::println);

        System.out.println("\n\n");
        tp.getResult().getVocabulary().getAdjectives().stream().limit(15)
                .map(adjective -> adjective.getWord() + ": " + adjective.getSentences().size())
                .forEach(System.out::println);

        System.out.println("\n\n");
        tp.getResult().getFrequentSentences().stream().limit(10)
                .map(s -> s.getSentence() + ": " + s.getFrequency())
                .forEach(System.out::println);

        System.out.println("\n\n");
        tp.getResult().getFrequencyRate().forEach(
                s -> System.out.println(s.getFrequency() + ": " + singletonList(s.getWords()))
        );

    }

}
