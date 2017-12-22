package finalDraft;

import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import common.Character;

public class ScriptParser
{
    private static final Logger LOG = Logger.getLogger(ScriptParser.class);

    public Map<String, Character> parseScript(String fileLocation)
    {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e)
        {
            LOG.error(e.getMessage(), e);
            return Maps.newHashMap();
        }

        final Document document;
        try
        {
            document = builder.parse(new File(fileLocation));
        }
        catch (SAXException e)
        {
            LOG.error(e.getMessage(), e);
            return Maps.newHashMap();
        }
        catch (IOException e)
        {
            LOG.error(e.getMessage(), e);
            return Maps.newHashMap();
        }

        document.getDocumentElement().normalize();

        final NodeList nodeList = document.getElementsByTagName("Paragraph");
        final Map<String, Character> characterMap = Maps.newHashMap();

        String characterName = null;
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            final Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                final Element element = (Element) node;
                if ("Character".equals(element.getAttribute("Type")))
                {
                    characterName = getCharacterName(element);
                    if (!characterMap.containsKey(characterName.toLowerCase()))
                    {
                        characterMap.put(characterName.toLowerCase(), new Character(characterName));
                    }
                }
                else if ("Dialogue".equals(element.getAttribute("Type")))
                {
                    final String dialogue = getDialogue(element);
                    if (characterName != null)
                    {
                        final Character character = characterMap.get(characterName.toLowerCase());
                        if (character != null)
                        {
                            character.addDialogue(dialogue.replace("’", "'"));
                        }
                    }
                }
            }
        }

        return characterMap;
    }

    private String getCharacterName(Element characterNode)
    {
        String characterName = characterNode.getChildNodes().item(1).getTextContent();
        if (characterName.contains("("))
        {
            characterName = characterName.substring(0, characterName.indexOf("("));
        }
        characterName = characterName.trim().toLowerCase();
        characterName = characterName.substring(0, 1).toUpperCase() + characterName.substring(1);
        for (int i = 1; i < characterName.length(); i++)
        {
            if (characterName.charAt(i) == ' ')
            {
                characterName = characterName.substring(0, i + 1) + characterName.substring(i + 1, i + 2).toUpperCase() + characterName.substring(i + 2);
            }
        }

        return characterName;
    }

    private String getDialogue(Element dialogueNode)
    {
        final StringBuilder dialogue = new StringBuilder();
        for (int i = 1; i < dialogueNode.getChildNodes().getLength() - 1; i += 2)
        {
            dialogue.append(dialogueNode.getChildNodes().item(i).getTextContent());
        }

        return dialogue.toString();
    }
}
