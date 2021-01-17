package org.ships.implementation.bukkit.text.type;

import org.core.text.colour.TextColour;
import org.core.text.style.TextStyle;
import org.core.text.type.JoinedText;
import org.core.text.type.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BukkitJoinedText implements JoinedText {

    List<Text> list = new ArrayList<>();

    public BukkitJoinedText(Text... collection){
        this(Arrays.asList(collection));
    }

    public BukkitJoinedText(Collection<Text> collection){
        this.list.addAll(collection);
    }

    @Override
    public List<Text> getChildren() {
        return this.list;
    }
}
