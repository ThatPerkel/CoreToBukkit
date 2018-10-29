package org.ships.implementation.bukkit.configuration;

import org.bukkit.configuration.file.YamlConfiguration;
import org.core.CorePlugin;
import org.core.configuration.ConfigurationFile;
import org.core.configuration.ConfigurationNode;
import org.core.configuration.parser.Parser;
import org.core.configuration.parser.StringMapParser;
import org.core.configuration.parser.StringParser;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
            Map<String, String> map = new HashMap<>();
            getChildren(node).forEach(n ->{
                String path = CorePlugin.toString(".", t -> t, n.getPath());
                String value = this.yaml.getString(path);
                map.put(path, value);
            });
            if(map.isEmpty()){
                return Optional.empty();
            }
            return mParser.parse(map);
        }else{
            System.err.println("Unknown Parser Type: The following are supported: StringParser<> or StringMapParser<>");
            new IOException("Parser " + parser.getClass().getSimpleName() + " failed").printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> parseString(ConfigurationNode node) {
        String value = this.yaml.getString(CorePlugin.toString(".", s -> s, node.getPath()));
        return Optional.ofNullable(value);
    }

    @Override
    public Optional<Integer> parseInt(ConfigurationNode node) {
        int value = this.yaml.getInt(CorePlugin.toString(".", s -> s, node.getPath()));
        return Optional.ofNullable(value);
    }

    @Override
    public Optional<Double> parseDouble(ConfigurationNode node) {
        double value = this.yaml.getDouble(CorePlugin.toString(".", s -> s, node.getPath()));
        return Optional.ofNullable(value);
    }

    @Override
    public Optional<Boolean> parseBoolean(ConfigurationNode node) {
        boolean value = this.yaml.getBoolean(CorePlugin.toString(".", s -> s, node.getPath()));
        return Optional.ofNullable(value);
    }

    @Override
    public <T> Optional<List<T>> parseList(ConfigurationNode node, StringParser<T> parser) {
        List<String> stringList = this.yaml.getStringList(CorePlugin.toString(".", t -> t, node.getPath()));
        List<T> valueList = new ArrayList<>();
        stringList.stream().forEach(s -> parser.parse(s).ifPresent(v -> valueList.add(v)));
        if(valueList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(valueList);
    }

    @Override
    public <T> void set(ConfigurationNode node, Parser<?, T> parser, T value) {
        this.yaml.set(CorePlugin.toString(".", s -> s, node.getPath()), parser.unparse(value));
    }

    @Override
    public void set(ConfigurationNode node, Object value) {
        this.yaml.set(CorePlugin.toString(".", s -> s, node.getPath()), value);
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

    private Set<ConfigurationNode> getChildren(ConfigurationNode node){
        Set<String> keys = this.yaml.getValues(true).keySet();
        Set<ConfigurationNode> ret = new HashSet();
        keys.stream().filter(k -> k.startsWith(CorePlugin.toString(".", s -> s, node.getPath()))).forEach(r -> ret.add(new ConfigurationNode(r)));
        return ret;
    }
}
