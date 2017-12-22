package common;

import com.google.common.collect.Lists;

import java.util.List;

public class Character
{
    private String name;
    private List<String> dialogue;

    public Character(String name)
    {
        this.name = name;
        this.dialogue = Lists.newArrayList();
    }

    public String getName() {
        return name;
    }

    public List<String> getDialogue() {
        return dialogue;
    }

    public void addDialogue(String newDialogue)
    {
        dialogue.add(newDialogue);
    }
}
