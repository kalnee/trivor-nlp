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
import org.kalnee.trivor.nlp.domain.Sentence;
import org.kalnee.trivor.nlp.insights.generators.Generator;
import org.kalnee.trivor.nlp.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.kalnee.trivor.nlp.domain.InsightsEnum.FUTURE_PROGRESSIVE;
import static org.kalnee.trivor.nlp.domain.TagsEnum.*;

/**
 * Future progressive verb tense insight generator.
 *
 * @see Generator
 *
 * @since 0.0.1
 */
public class FutureProgressiveGenerator implements Generator<List<String>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FutureProgressiveGenerator.class);

	private static final List<String> MUST_CONTAIN = Arrays.asList(PRP.name(), NNP.name(), NNPS.name());
	private static final List<String> MUST_CONTAIN_VERB = Collections.singletonList(VBG.name());
	private static final List<String> MUST_CONTAIN_BE = Collections.singletonList("be");
	private static final List<String> MUST_CONTAIN_WORDS = Arrays.asList(
		"Will", "will be", "Won't", "won't be", "'ll be", "gonna be"
	);
	private static final List<String> MUST_NOT_CONTAIN = Arrays.asList(VBN.name(), VBD.name());

	@Override
	public String getCode() {
		return FUTURE_PROGRESSIVE.getCode();
	}

	public List<String> generate(List<Sentence> sentences) {
		final List<String> matchedSentences = sentences
			.stream()
			.filter(s -> CollectionUtils.allMatch(s.getSentence(), MUST_CONTAIN_BE)
				&& CollectionUtils.allMatch(s.getSentenceTags(), MUST_CONTAIN_VERB)
				&& CollectionUtils.anyMatch(s.getSentenceTags(), MUST_CONTAIN)
				&& CollectionUtils.anyMatch(s.getSentence(), MUST_CONTAIN_WORDS)
				&& CollectionUtils.noneMatch(s.getSentenceTags(), MUST_NOT_CONTAIN))
			.map(Sentence::getSentence)
			.collect(toList());


		LOGGER.info(
			format("%s: %d/%d (%.2f%%)", getCode(), matchedSentences.size(), sentences.size(),
			(matchedSentences.size() * 100d / sentences.size()))
		);

		return matchedSentences;
	}
}
