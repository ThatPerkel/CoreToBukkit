package org.ships.implementation.bukkit.text;

import org.bukkit.ChatColor;
import org.core.CorePlugin;
import org.core.text.Text;

public class BText implements Text {

    protected String text;

    public BText(String text){
        this.text = text;
    }

    public String toBukkitString(){
        return this.text;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof BText)){
            return false;
        }
        BText text = (BText)obj;
        return text.toPlain().equals(toPlain());
    }

    @Override
    public boolean equalsExact(String chars) {
        return this.text.equals(chars);
    }

    @Override
    public String toPlain() {
        return ChatColor.stripColor(this.text);
    }

    @Override
    public Text append(Text... text) {
        return new BText(this.text + CorePlugin.toString("", t -> ((BText)t).text, text));
    }
}
