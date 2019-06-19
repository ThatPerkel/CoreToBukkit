package org.ships.implementation.bukkit.platform;

import org.bukkit.Bukkit;
import org.core.source.command.ConsoleSource;
import org.core.source.viewer.CommandViewer;
import org.core.text.Text;
import org.core.text.TextColours;
import org.ships.implementation.bukkit.text.BText;

public class PlatformConsole implements ConsoleSource {
    @Override
    public CommandViewer sendMessage(Text message) {
        Bukkit.getConsoleSender().sendMessage(((BText)message).toBukkitString());
        return this;
    }

    @Override
    public CommandViewer sendMessagePlain(String message) {
        return sendMessage(new BText(TextColours.stripColours(message)));
    }
}
