package org.ships.implementation.bukkit.platform;

import org.bukkit.Bukkit;
import org.core.source.command.ConsoleSource;
import org.core.source.viewer.CommandViewer;
import org.core.text.Text;
import org.core.text.TextColours;
import org.ships.implementation.bukkit.text.BText;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class PlatformConsole implements ConsoleSource {
    @Override
    public CommandViewer sendMessage(Text message, UUID uuid) {
        try {
            Bukkit.getConsoleSender().getClass().getDeclaredMethod("sendMessage", UUID.class, String.class).invoke(Bukkit.getConsoleSender(), uuid, ((BText)message).toBukkitString());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            sendMessage(message);
        }

        return this;
    }

    @Override
    public CommandViewer sendMessage(Text message) {
        Bukkit.getConsoleSender().sendMessage(((BText)message).toBukkitString());
        return this;
    }

    @Override
    public CommandViewer sendMessagePlain(String message) {
        return sendMessage(new BText(TextColours.stripColours(message)));
    }

    @Override
    public boolean sudo(String wholeCommand) {
        return Bukkit.dispatchCommand(Bukkit.getConsoleSender(), wholeCommand);
    }
}
