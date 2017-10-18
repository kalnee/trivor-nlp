# trivor-nlp [![CircleCI](https://circleci.com/gh/kalnee/trivor-nlp/tree/master.svg?style=svg)](https://circleci.com/gh/kalnee/trivor-nlp/tree/master)

<h5 align="center">Library to detect and generate insights over spoken language found in Movies and TV Shows.</h5>

trivor-nlp leverages the use of NPL (Natural Language Processing) to detect sentences, tokens as well as the meaning of each token in the given sentence. 
After processing all sentences, several generators will produce valuable information that can be easily consumed.

## Prerequisites

- Java 8+

## Usage

### Add dependency
```xml
<dependency>
  <groupId>org.kalnee</groupId>
  <artifactId>trivor-nlp</artifactId>
  <version>0.0.1-alpha.0</version>
</dependency>
```

### Build a SubtitleProcessor

```java
final SubtitleProcessor sp = new SubtitleProcessor.Builder(uri).withDuration(43).build();
```

```java
sp.getSentences(); // return each identified sentence and its properties such as tokens.
sp.getResult(); // return the result from several insight generators such as vocabulary.
```

## License

MIT (c) Kalnee. See [LICENSE](https://github.com/kalnee/trivor-nlp/blob/master/LICENSE.md) for details.
