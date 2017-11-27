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

import org.junit.Test;
import org.kalnee.trivor.nlp.domain.Config;
import org.mockito.internal.util.reflection.Whitebox;

import java.net.URI;
import java.net.URISyntaxException;

import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertTrue;

public class TranscriptProcessorTest {

    private static final String TIME_REGEX = "^([0-9]+\\+*[0-9]*):\\s*";

    private URI getTranscriptURI(String subtitle) throws URISyntaxException {
        return getClass().getClassLoader().getResource(subtitle).toURI();
    }

    @Test
    public void testExistentContentFromUri() throws URISyntaxException {
        TranscriptProcessor transcriptProcessor = new TranscriptProcessor.Builder(
                getTranscriptURI("transcripts/football")
        ).withFilters(singletonList(line -> !line.contains("De Bruyne")))
        .withMappers(singletonList(line -> line.replaceAll(TIME_REGEX, EMPTY)))
        .build();

        String content = (String) Whitebox.getInternalState(transcriptProcessor, "content");

        assertTrue(content.startsWith("NO GOAL!!!"));
    }

    @Test
    public void testExistentContentFromString() throws URISyntaxException {
        TranscriptProcessor transcriptProcessor = new TranscriptProcessor.Builder(
                "29: NO GOAL!!! Another close one for City."
        ).withMappers(singletonList(line -> line.replaceAll("^([0-9]+\\+*[0-9]*):\\s*", EMPTY)))
         .build();

        String content = (String) Whitebox.getInternalState(transcriptProcessor, "content");

        assertTrue(content.startsWith("NO GOAL!!!"));
    }

    @Test
    public void testConfig() throws URISyntaxException {
        TranscriptProcessor transcriptProcessor = new TranscriptProcessor.Builder(
                "This is a very good transcript with many sentences."
        ).withConfig(new Config.Builder().chunkProb(1.).vocabularyProb(1.).sentimentAnalysis(false).build())
         .build();

        assertTrue(transcriptProcessor.getSentences().get(0).getChunks().isEmpty());
        assertTrue(transcriptProcessor.getResult().getVocabulary().getVerbs().isEmpty());
        assertTrue(transcriptProcessor.getResult().getSentimentAnalysis().isEmpty());
    }
}
