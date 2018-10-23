package org.ships.implementation.bukkit.platform;

import org.bukkit.Bukkit;
import org.core.source.command.ConsoleSource;
import org.core.source.viewer.CommandViewer;
import org.core.text.TextColours;

public class PlatformConsole implements ConsoleSource {
    @Override
    public CommandViewer sendMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
        return this;
    }

    @Override
    public CommandViewer sendMessagePlain(String message) {
        return sendMessage(TextColours.stripColours(message));
    }
}
