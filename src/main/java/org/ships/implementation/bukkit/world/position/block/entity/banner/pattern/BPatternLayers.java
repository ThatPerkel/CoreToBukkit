package org.ships.implementation.bukkit.world.position.block.entity.banner.pattern;

import org.bukkit.block.banner.Pattern;
import org.core.world.position.block.entity.banner.pattern.PatternLayer;
import org.core.world.position.block.entity.banner.pattern.PatternLayers;
import org.core.world.position.block.entity.banner.pattern.PatternLayersSnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BPatternLayers implements PatternLayers {

    protected org.bukkit.block.Banner banner;

    public BPatternLayers(org.bukkit.block.Banner banner){
        this.banner = banner;
    }

    @Override
    public List<PatternLayer> getLayers() {
        List<PatternLayer> set = new ArrayList<>();
        this.banner.getPatterns().stream().forEach(p -> set.add(new BPatternLayer(p)));
        return set;
    }

    @Override
    public PatternLayers addLayers(Collection<PatternLayer> layers) {
        layers.forEach(l -> this.banner.addPattern(((BPatternLayer)l).pattern));
        return this;
    }

    @Override
    public PatternLayers addLayer(int A, PatternLayer layer) {
        List<Pattern> list = this.banner.getPatterns();
        list.add(A, ((BPatternLayer)layer).pattern);
        this.banner.setPatterns(list);
        return this;
    }

    @Override
    public PatternLayers removeLayer(int layer) {
        this.banner.removePattern(layer);
        return this;
    }

    @Override
    public PatternLayers removeLayers() {
        for(int A = this.banner.numberOfPatterns(); A > 0; A--){
            this.banner.removePattern(A);
        }
        return this;
    }

    @Override
    public PatternLayersSnapshot createSnapshot() {
        return new BPatternLayersSnapshot(this.getLayers());
    }
}
