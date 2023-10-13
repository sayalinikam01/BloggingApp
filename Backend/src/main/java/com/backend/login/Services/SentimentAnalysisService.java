package com.backend.login.Services;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
public class SentimentAnalysisService {

    private final StanfordCoreNLP pipeline;

    public SentimentAnalysisService() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        this.pipeline = new StanfordCoreNLP(props);
    }

    public String analyzeSentiment(String text) {
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        if (sentences != null && !sentences.isEmpty()) {
            CoreMap sentence = sentences.get(0);
            return sentence.get(SentimentCoreAnnotations.SentimentClass.class);
        } else {
            return "Unable to determine sentiment";
        }
    }

}

