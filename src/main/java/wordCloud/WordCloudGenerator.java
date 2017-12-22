package wordCloud;

import com.google.common.collect.Lists;
import com.kennycason.kumo.*;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;

import java.awt.*;
import java.util.List;

import common.Character;

public class WordCloudGenerator
{
    private static final List<String> stopWords = Lists.newArrayList("a", "about", "above", "after", "again","against", "all", "am", "an",
            "and", "any", "are", "aren't", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by",
            "can't", "cannot", "could", "couldn't", "did", "didn't", "do", "does", "doesn't", "doing", "don't", "down", "during", "each", "few", "for",
            "from", "further", "had", "hadn't", "has", "hasn't", "have", "haven't", "having", "he", "he'd", "he'll", "he's", "her", "here", "here's",
            "hers", "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "isn't", "it",
            "it's", "its", "itself", "let's", "me", "more", "most", "mustn't", "my", "myself", "no", "nor", "not", "of", "off", "on", "once", "only", "or",
            "other", "ought", "our", "ours", "ourselves", "out", "over", "own", "same", "shan't", "she", "she'd", "she'll", "she's", "should", "shouldn't",
            "so", "some", "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves", "then", "there", "there's", "these", "they",
            "they'd", "they'll", "they're", "they've", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasn't","we", "we'd",
            "we'll", "we're", "we've", "were", "weren't", "what", "what's", "when", "when's", "where", "where's", "which", "while", "who", "who's", "whom",
            "why", "why's", "with", "won't", "would", "wouldn't", "you", "you'd", "you'll", "you're", "you've","your", "yours", "yourself", "yourselves");

    public void createWordCloud(Character character)
    {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(200);
        frequencyAnalyzer.setMinWordLength(5);
        frequencyAnalyzer.setStopWords(stopWords);

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(character.getDialogue());
        final Dimension dimension = new Dimension(600, 600);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 50));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile(character.getName() + ".png");
    }

    public void createPolarityWordCloud(Character character1, Character character2)
    {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(750);
        frequencyAnalyzer.setMinWordLength(4);
        frequencyAnalyzer.setStopWords(stopWords);

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(character1.getDialogue());
        final List<WordFrequency> wordFrequencies2 = frequencyAnalyzer.load(character2.getDialogue());
        final Dimension dimension = new Dimension(600, 600);
        final PolarWordCloud wordCloud = new PolarWordCloud(dimension, CollisionMode.PIXEL_PERFECT, PolarBlendMode.BLUR);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
        wordCloud.build(wordFrequencies, wordFrequencies2);
        wordCloud.writeToFile(character1.getName() + " - " + character2.getName() + ".png");
    }
}
