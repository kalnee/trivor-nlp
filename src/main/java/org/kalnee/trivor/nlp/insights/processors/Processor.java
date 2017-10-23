package org.kalnee.trivor.nlp.insights.processors;

import org.kalnee.trivor.nlp.domain.Sentence;
import org.kalnee.trivor.nlp.domain.SentimentEnum;
import org.kalnee.trivor.nlp.domain.Token;
import org.kalnee.trivor.nlp.handlers.FileHandler;
import org.kalnee.trivor.nlp.handlers.FileHandlerFactory;
import org.kalnee.trivor.nlp.nlp.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.System.lineSeparator;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.SPACE;

public abstract class Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);
    private static final String SPACES_REGEX = "\\s+";

    private URI uri;
    private String content;

    private final SentenceDetector sentenceDetector = new SentenceDetector();
    private final SimpleTokenizer tokenizer = new SimpleTokenizer();
    private final POSTagger tagger = new POSTagger();
    private final Lemmatizer lemmatizer = new Lemmatizer();
    private final Chunker chunker = new Chunker();
    private final SentimentAnalyser sentimentAnalyser = new SentimentAnalyser();

    private Map<SentimentEnum, BigDecimal> sentiment;
    private List<Sentence> sentences = new ArrayList<>();

    Processor(URI uri) {
        this.uri = uri;
    }

    Processor(String content) {
        this.content = content;
    }

    List<Predicate<String>> getFilters() {
        return emptyList();
    }

    List<Function<String, String>> getMappers() {
        return emptyList();
    }

    private void preProcess() {
        String lines = content;
        if (!Objects.isNull(uri)) {
            final FileHandler fileHandler = FileHandlerFactory.create(uri).getHandler();
            lines = fileHandler.lines().collect(joining(lineSeparator()));
        }

        content = Stream.of(lines.split(lineSeparator()))
                .filter(line -> getFilters().stream().reduce(Predicate::and).map(f -> f.test(line)).orElse(true))
                .map(line -> getMappers().stream().reduce(Function::andThen).map(f -> f.apply(line)).orElse(line))
                .map(String::trim)
                .collect(joining(" "));
    }

    void process() {
        preProcess();

        final List<String> detectedSentences = sentenceDetector.detect(content)
                .stream()
                .map(s -> s.replaceAll(SPACES_REGEX, SPACE))
                .collect(toList());

        sentences = detectedSentences.stream().map(s -> {
            final List<String> rawTokens = tokenizer.tokenize(s);
            final List<String> tags = tagger.tag(rawTokens);
            final List<Double> probs = tagger.probs();
            final List<String> lemmas = lemmatizer.lemmatize(rawTokens, tags);
            final List<Chunk> chunks = chunker.chunk(rawTokens, tags, probs);

            final List<Token> tokens = new ArrayList<>();

            for (int i = 0; i < rawTokens.size(); i++) {
                tokens.add(new Token(rawTokens.get(i), tags.get(i), lemmas.get(i), probs.get(i)));
            }

            return new Sentence(s, tokens, chunks);
        }).collect(toList());

        sentiment = sentimentAnalyser.categorize(
                sentences.stream().map(Sentence::getSentence).collect(toList())
        );

        LOGGER.info("File processed successfully");
    }

    public Map<SentimentEnum, BigDecimal> getSentiment() {
        return sentiment;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public String getContent() {
        return content;
    }
}
