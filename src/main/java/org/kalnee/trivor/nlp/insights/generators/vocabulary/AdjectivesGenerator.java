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

import org.kalnee.trivor.nlp.domain.Config;
import org.kalnee.trivor.nlp.domain.Sentence;
import org.kalnee.trivor.nlp.domain.WordUsage;
import org.kalnee.trivor.nlp.insights.generators.Generator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.kalnee.trivor.nlp.domain.InsightsEnum.ADJECTIVES_USAGE;
import static org.kalnee.trivor.nlp.domain.TagsEnum.JJ;
import static org.kalnee.trivor.nlp.utils.LanguageUtils.NOT_ADJECTIVES;

/**
 * Sentence generator for adjectives.
 *
 * @see VocabularyGenerator
 * @see Generator
 *
 * @since 0.0.1
 */
public class AdjectivesGenerator extends VocabularyGenerator implements Generator<Set<WordUsage>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdjectivesGenerator.class);

	public AdjectivesGenerator(Config config) {
		super(config);
	}

	@Override
	List<String> getTags() {
		return Collections.singletonList(JJ.name());
	}

	@Override
	public String getCode() {
		return ADJECTIVES_USAGE.getCode();
	}

	@Override
	public Set<WordUsage> generate(List<Sentence> sentences) {
		final Set<WordUsage> wordUsages = getSentences(sentences, w -> !NOT_ADJECTIVES.contains(w));
		LOGGER.info("{} - {}", getCode(), getExamples(wordUsages));

    	return wordUsages;
	}
}
