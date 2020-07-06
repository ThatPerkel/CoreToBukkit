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
import java.util.regex.Pattern;

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

    public YamlConfiguration getYaml(){
        return this.yaml;
    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public ConfigurationFile reload() {
        this.yaml = YamlConfiguration.loadConfiguration(this.file);
        return this;
    }

    @Override
    public Map<ConfigurationNode, Object> getKeyValues() {
        Map<ConfigurationNode, Object> value = new HashMap<>();
        this.yaml.getKeys(true).forEach(p -> {
            value.put(new ConfigurationNode(p.split(Pattern.quote("."))), this.yaml.get(p));
        });
        return value;
    }

    @Override
    public <T> Optional<T> parse(ConfigurationNode node, Parser<?, T> parser) {
        if (parser instanceof StringParser){
            if(parser instanceof StringParser.SpecialParser){
                return ((StringParser.SpecialParser<T>) parser).get(this, node);
            }
            return ((StringParser<T>)parser).parse(this.yaml.getString(CorePlugin.toString(".", s -> s, node.getPath())));
        }else if(parser instanceof StringMapParser){
            StringMapParser<T> mParser = (StringMapParser<T>)parser;
            Map<String, String> map = new HashMap<>();
            String path = CorePlugin.toString(".", t -> t, node.getPath());
            mParser.getKeys().forEach(k -> {
                String value2 = path + "." + k;
                String value3 = this.yaml.getString(value2);
                map.put(k, value3);
            });
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
        if(!this.yaml.contains(CorePlugin.toString(".", node.getPath()))){
            return Optional.empty();
        }
        int value = this.yaml.getInt(CorePlugin.toString(".", s -> s, node.getPath()));
        return Optional.of(value);
    }

    @Override
    public Optional<Double> parseDouble(ConfigurationNode node) {
        if(!this.yaml.contains(CorePlugin.toString(".", node.getPath()))){
            return Optional.empty();
        }
        double value = this.yaml.getDouble(CorePlugin.toString(".", s -> s, node.getPath()));
        return Optional.of(value);
    }

    @Override
    public Optional<Boolean> parseBoolean(ConfigurationNode node) {
        if(!this.yaml.contains(CorePlugin.toString(".", node.getPath()))){
            return Optional.empty();
        }
        boolean value = this.yaml.getBoolean(CorePlugin.toString(".", s -> s, node.getPath()));
        return Optional.of(value);
    }

    @Override
    public <T> Optional<List<T>> parseList(ConfigurationNode node, StringParser<T> parser) {
        List<String> stringList = this.yaml.getStringList(CorePlugin.toString(".", t -> t, node.getPath()));
        List<T> valueList = new ArrayList<>();
        stringList.forEach(s -> parser.parse(s).ifPresent(v -> valueList.add(v)));
        if(valueList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(valueList);
    }

    @Override
    public String parseString(ConfigurationNode node, String defaut) {
        return null;
    }

    @Override
    public int parseInt(ConfigurationNode node, int defaut) {
        return 0;
    }

    @Override
    public double parseDouble(ConfigurationNode node, double defaut) {
        return 0;
    }

    @Override
    public boolean parseBoolean(ConfigurationNode node, boolean defaut) {
        return false;
    }

    @Override
    public <T> List<T> parseList(ConfigurationNode node, StringParser<T> parser, List<T> defaut) {
        return null;
    }

    @Override
    public <T> void set(ConfigurationNode node, Parser<?, T> parser, T value) {
        if(parser instanceof StringParser.SpecialParser){
            ((StringParser.SpecialParser<T>) parser).set(value, this, node);
            return;
        }
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
}
