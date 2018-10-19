package org.ships.implementation.bukkit.configuration;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.core.CorePlugin;
import org.core.configuration.ConfigurationFile;
import org.core.configuration.ConfigurationNode;
import org.core.configuration.parser.Parser;
import org.core.configuration.parser.StringMapParser;
import org.core.configuration.parser.StringParser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class YAMLConfigurationFile implements ConfigurationFile {

    protected File file;
    protected YamlConfiguration yaml;

    public YAMLConfigurationFile(File file){
        this(file, YamlConfiguration.loadConfiguration(file));
    }

    public YAMLConfigurationFile(File file, YamlConfiguration yaml){
        this.file = file;
        this.yaml = yaml;
    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public Map<ConfigurationNode, Object> getKeyValues() {
        Map<ConfigurationNode, Object> value = new HashMap<>();
        this.yaml.getKeys(true).stream().forEach(p -> {
            value.put(new ConfigurationNode(p.split(".")), this.yaml.get(p));
        });
        return value;
    }

    @Override
    public <T> Optional<T> parse(ConfigurationNode node, Parser<?, T> parser) {
        if (parser instanceof StringParser){
            return ((StringParser<T>)parser).parse(this.yaml.getString(CorePlugin.toString(".", s -> s, node.getPath())));
        }else if(parser instanceof StringMapParser){
            StringMapParser<T> mParser = (StringMapParser<T>)parser;
            ConfigurationSection section = this.yaml.createSection(CorePlugin.toString(".", s -> s, node.getPath()));
            Map<String, String> map = new HashMap<>();
            section.getKeys(false).stream().forEach(p -> map.put(p, section.getString(p)));
            return mParser.parse(map);
        }else{
            System.err.println("Unknown Parser Type: The following are supported: StringParser<> or StringMapParser<>");
            new IOException("Parser " + parser.getClass().getSimpleName() + " failed").printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public ConfigurationNode getRootNode() {
        return new ConfigurationNode();
    }

    @Override
    public void save() {
        try {
            this.yaml.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
