package org.ships.implementation.bukkit.text.type;

import org.array.utils.ArrayUtils;
import org.core.text.colour.TextColour;
import org.core.text.style.TextStyle;
import org.core.text.type.ColouredText;
import org.core.text.type.StyledText;
import org.core.text.type.Text;

import java.util.*;

public class BukkitColouredText implements ColouredText, StyledText {

    private final String text;
    private final TextColour colour;
    private final Set<TextStyle> styles = new HashSet<>();

    public BukkitColouredText(TextColour colour, String text, TextStyle... collection){
        this(colour, text, Arrays.asList(collection));
    }

    public BukkitColouredText(TextColour colour, String text, Collection<TextStyle> collection){
        this.text = text;
        this.colour = colour;
        this.styles.addAll(collection);
    }

    @Override
    public Optional<TextColour> getColour() {
        return Optional.ofNullable(this.colour);
    }

    @Override
    public Set<TextStyle> getTextStyle() {
        return this.styles;
    }

    @Override
    public String toLegacyString() {
        List<TextStyle> list = new ArrayList<>(this.styles);
        list.sort(Comparator.comparing(ts -> ((TextStyle.Legacy)ts).getLegacySign(), Comparator.naturalOrder()));
        return ArrayUtils.toString("", t -> Text.LEGACY_CHARACTER_CODE + "" + ((TextStyle.Legacy)t).getLegacySign(), list) + Text.LEGACY_CHARACTER_CODE + ((TextColour.Legacy)this.colour).getLegacySign() + this.text;
    }

    @Override
    public String getText() {
        return this.text;
    }
}
