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

import org.kalnee.trivor.nlp.domain.Result;
import org.kalnee.trivor.nlp.domain.Subtitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;

public class SubtitleProcessor extends Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubtitleProcessor.class);

    private static final String SUBTITLE_INDEX_REGEX = "^(\\d+)$";
    private static final String SUBTITLE_TIME_REGEX = "^((\\d+.*)\\s-->\\s(\\d+.*))$";
    private static final String SUBTITLE_DIALOG_REGEX = "^\\s*-|_\\s*";
    private static final String SUBTITLE_HTML_REGEX = "<[^>]*>";
    private static final String SUBTITLE_CC_REGEX = "\\[.*\\]\\s*";
    private static final String SUBTITLE_CC2_REGEX = "\\(.*\\)\\s*";
    private static final String SUBTITLE_CC_INITIAL_REGEX = "\\(.*";
    private static final String SUBTITLE_CC_FINAL_REGEX = ".*\\)";
    private static final String SUBTITLE_CHARACTER_REGEX = "^([A-Za-z]+\\s*[A-Za-z]+):\\s*";
    private static final String SUBTITLE_URL_REGEX = ".*www\\.[a-zA-Z]+.*";
    private static final String SUBTITLE_PREVIOUS_REGEX = "^Previously on.*$";
    private static final String SUBTITLE_SONG_REGEX = ".*♪.*";
    private static final String SUBTITLE_INITIAL_QUOTE_REGEX = "^'|\\s'";
    private static final String SUBTITLE_FINAL_QUOTE_REGEX = "'\\s";
    private static final String SUBTITLE_ADS_REGEX =
            ".*(Subtitle|subtitle|sync by|Sync by|Downloaded|VIP|Synchronized by|Created by).*";

    private final InsightsProcessor insightsProcessor;

    private Subtitle subtitle;

    private Result result;
    private Integer duration;

    private SubtitleProcessor(URI uri, Integer duration) {
        super(uri);
        this.duration = duration;
        this.insightsProcessor = new InsightsProcessor();
    }

    @Override
    List<Predicate<String>> getFilters() {
        return Arrays.asList(
                line -> !line.matches(SUBTITLE_INDEX_REGEX),
                line -> !line.matches(SUBTITLE_TIME_REGEX),
                line -> !line.matches(SUBTITLE_SONG_REGEX),
                line -> !line.matches(SUBTITLE_URL_REGEX),
                line -> !line.matches(SUBTITLE_PREVIOUS_REGEX),
                line -> !line.matches(SUBTITLE_ADS_REGEX)
        );
    }

    @Override
    List<Function<String, String>> getMappers() {
        return Arrays.asList(
                line -> line.replaceAll(SUBTITLE_DIALOG_REGEX, EMPTY),
                line -> line.replaceAll(SUBTITLE_HTML_REGEX, EMPTY),
                line -> line.replaceAll(SUBTITLE_CC_REGEX, EMPTY),
                line -> line.replaceAll(SUBTITLE_CC2_REGEX, EMPTY),
                line -> line.replaceAll(SUBTITLE_CC_INITIAL_REGEX, EMPTY),
                line -> line.replaceAll(SUBTITLE_CC_FINAL_REGEX, EMPTY),
                line -> line.replaceAll(SUBTITLE_CHARACTER_REGEX, EMPTY),
                line -> line.replaceAll(SUBTITLE_INITIAL_QUOTE_REGEX, SPACE),
                line -> line.replaceAll(SUBTITLE_FINAL_QUOTE_REGEX, SPACE)
        );
    }

    @Override
    void process() {
        super.process();

        subtitle = new Subtitle(getSentences(), duration, getSentiment());
        result = insightsProcessor.process(subtitle);
    }

    public Subtitle getSubtitle() {
        return subtitle;
    }

    public Result getResult() {
        return result;
    }

    public static class Builder {
        private final URI uri;
        private Integer duration;

        public Builder(URI uri) {
            this.uri = uri;
        }

        public Builder withDuration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public SubtitleProcessor build() {
            final SubtitleProcessor subtitleProcessor = new SubtitleProcessor(uri, duration);
            subtitleProcessor.process();
            return subtitleProcessor;
        }
    }
}
