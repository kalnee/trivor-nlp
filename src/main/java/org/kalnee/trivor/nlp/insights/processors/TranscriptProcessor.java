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

import org.kalnee.trivor.nlp.domain.Config;
import org.kalnee.trivor.nlp.domain.Result;
import org.kalnee.trivor.nlp.domain.Transcript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class TranscriptProcessor extends Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranscriptProcessor.class);

    private final InsightsProcessor insightsProcessor;

    private List<Function<String, String>> mappers;
    private List<Predicate<String>> filters;
    private Transcript transcript;
    private Result result;

    private TranscriptProcessor(URI uri, Config config) {
        super(uri, config);
        this.insightsProcessor = new InsightsProcessor(config);
    }

    private TranscriptProcessor(String content, Config config) {
        super(content, config);
        this.insightsProcessor = new InsightsProcessor(config);
    }

    TranscriptProcessor(URI uri, List<Function<String, String>> mappers, List<Predicate<String>> filters, Config config) {
        this(uri, config);
        this.mappers = mappers;
        this.filters = filters;
    }

    TranscriptProcessor(String content, List<Function<String, String>> mappers, List<Predicate<String>> filters, Config config) {
        this(content, config);
        this.mappers = mappers;
        this.filters = filters;
    }

    @Override
    List<Function<String, String>> getMappers() {
        return mappers;
    }

    @Override
    public List<Predicate<String>> getFilters() {
        return filters;
    }

    @Override
    void process() {
        super.process();

        transcript = new Transcript(getSentences());
        result = insightsProcessor.process(transcript);
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public Result getResult() {
        return result;
    }

    public static class Builder {
        private URI uri;
        private String content;
        private List<Function<String, String>> mappers = Collections.emptyList();
        private List<Predicate<String>> filters = Collections.emptyList();
        private Config config;

        public Builder(URI uri) {
            this.uri = uri;
        }

        public Builder(String content) {
            this.content = content;
        }

        public Builder withMappers(List<Function<String, String>> mappers) {
            this.mappers = mappers;
            return this;
        }

        public Builder withFilters(List<Predicate<String>> filters) {
            this.filters = filters;
            return this;
        }

        public Builder withConfig(Config config) {
            this.config = config;
            return this;
        }

        public TranscriptProcessor build() {
            if (Objects.isNull(uri) && Objects.isNull(content)) {
                throw new IllegalStateException("uri or content must be provided");
            }

            TranscriptProcessor transcriptProcessor;

            if (!Objects.isNull(uri)) {
                transcriptProcessor = new TranscriptProcessor(uri, mappers, filters, config);
            } else {
                transcriptProcessor = new TranscriptProcessor(content, mappers, filters, config);
            }
            transcriptProcessor.process();
            return transcriptProcessor;
        }
    }
}
