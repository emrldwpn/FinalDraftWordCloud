import finalDraft.ScriptParser;

import java.util.Map;

import common.Character;
import wordCloud.WordCloudGenerator;

public class Main
{
    public static void main(String[] args)
    {
        final ScriptParser scriptParser = new ScriptParser();
        final WordCloudGenerator wordCloudGenerator = new WordCloudGenerator();

        final Map<String, Character> characterMap = scriptParser.parseScript("The In-between - 3rd draft.fdx");
        wordCloudGenerator.createWordCloud(characterMap.get("ruth"));
    }
}
