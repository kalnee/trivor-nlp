# trivor-nlp [![CircleCI](https://circleci.com/gh/kalnee/trivor-nlp/tree/master.svg?style=svg)](https://circleci.com/gh/kalnee/trivor-nlp/tree/master)

trivor-nlp leverages the use of NPL (Natural Language Processing) to detect sentences, tokens as well as the meaning of each token in the given sentence. 
After processing all sentences, several generators will produce valuable information that can be easily consumed.

## Prerequisites

- Java 8+

## Usage

### 1. Add dependency

```xml
<dependency>
  <groupId>org.kalnee</groupId>
  <artifactId>trivor-nlp</artifactId>
  <version>0.0.1-alpha.1</version>
</dependency>
```

### 2. Create a Processor

`trivor-nlp` provides two processors:

- `TranscriptProcessor`: general-purpose processor, the content must be accessed either via URI or String.
- `SubtitleProcessor`: subtitle-only processor, the content must be accessed via URI.

Accepted URI schemas:

- file://
- jar://
- s3:// _Make sure to have the AWS Credentials in place._

#### Create a TranscriptProcessor from URI or String

```java
// from URI
TranscriptProcessor tp = new TranscriptProcessor.Builder(uri).build();
```

```java
// from String
TranscriptProcessor tp = new TranscriptProcessor.Builder("This is a sentence.").build();
```

#### Custom filters and mappers

For each line in the provided content, custom filters and mappers can be used to clean up the text before running the NLP 
models. Both fields are optional.

```java
TranscriptProcessor tp = new TranscriptProcessor.Builder(uri)
        .withFilters(singletonList(line -> !line.contains("Name")))
        .withMappers(singletonList(line -> line.replaceAll(TRANSCRIPT_REGEX, EMPTY)))
        .build();
```

#### Create a SubtitleProcessor from URI

```java
final SubtitleProcessor sp = new SubtitleProcessor.Builder(uri).withDuration(43).build();
```

All the necessary filters and mappers have already been provided for a `Subtitle`.

### 3. Result

After successfully building a processor, the NLP results can be accessed as follows:

#### processor.getSentences()

This method return the list of sentences. Each sentence is composed by the identified tokens, tags and chunks:

```json
{
            "sentence" : "My name's Forrest.",
            "tokens" : [ 
                {
                    "token" : "My",
                    "tag" : "PRP$",
                    "lemma" : "my",
                    "prob" : 0.976362822572366
                }, 
                {
                    "token" : "name",
                    "tag" : "NN",
                    "lemma" : "name",
                    "prob" : 0.98267246788283
                }, 
                {
                    "token" : "'s",
                    "tag" : "POS",
                    "lemma" : "'s",
                    "prob" : 0.933313435543914
                }, 
                {
                    "token" : "Forrest",
                    "tag" : "NNP",
                    "lemma" : "forrest",
                    "prob" : 0.908174572293974
                }, 
                {
                    "token" : ".",
                    "tag" : ".",
                    "lemma" : ".",
                    "prob" : 0.982098322024085
                }
            ],
            "chunks" : [ 
                {
                    "tokens" : [ 
                        "My", 
                        "name"
                    ],
                    "tags" : [ 
                        "PRP$", 
                        "NN"
                    ]
                }, 
                {
                    "tokens" : [ 
                        "'s", 
                        "Forrest"
                    ],
                    "tags" : [ 
                        "POS", 
                        "NNP"
                    ]
                }
            ]
        }
``` 

#### processor.getResult()

This method return a `Result` object with many different insights such as:

- Rate of Speech (only for `Subtitles`)
- Frequency Rate
- Frequent Sentences
- Frequent Chunks
- Vocabulary
- Verb Tenses
- Phrasal Verbs
- Sentiment Analysis

The full documentation can be accessed [here](https://github.com/kalnee/trivor-nlp/blob/master).

## License

MIT (c) Kalnee. See [LICENSE](https://github.com/kalnee/trivor-nlp/blob/master/LICENSE.md) for details.
