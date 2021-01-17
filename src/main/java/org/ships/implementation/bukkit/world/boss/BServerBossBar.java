package org.ships.implementation.bukkit.world.boss;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.core.entity.living.human.player.LivePlayer;
import org.core.text.Text;
import org.core.world.boss.ServerBossBar;
import org.core.world.boss.colour.BossColour;
import org.ships.implementation.bukkit.entity.living.human.player.live.BLivePlayer;
import org.ships.implementation.bukkit.text.BText;
import org.ships.implementation.bukkit.world.boss.colour.BBossColour;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BServerBossBar implements ServerBossBar {

    org.bukkit.boss.BossBar bossBar;

    public BServerBossBar(){
        this(Bukkit.createBossBar("<Unset Message>", BarColor.PURPLE, BarStyle.SOLID));
    }

    public BServerBossBar(org.bukkit.boss.BossBar boss){
        this.bossBar = boss;
    }

    @Override
    @Deprecated
    public Text getMessage() {
        return new BText(this.bossBar.getTitle());
    }

    @Override
    @Deprecated
    public ServerBossBar setMessage(Text text) {
        this.bossBar.setTitle(((BText)text).toBukkitString());
        return this;
    }

    @Override
    public BossColour getColour() {
        return new BBossColour(this.bossBar.getColor());
    }

    @Override
    public ServerBossBar setColour(BossColour colour) {
        this.bossBar.setColor(((BBossColour)colour).getBukkitColor());
        return this;
    }

    @Override
    public int getValue() {
        return (int)(this.bossBar.getProgress() * 100);
    }

    @Override
    public ServerBossBar setValue(int value) {
        if(value > 100){
            throw new IllegalArgumentException("ServerBossBar.SetValue must be between 0 and 100 (" + value + ")");
        }
        double percent = (value/100.0);
        this.bossBar.setProgress(percent);
        return this;
    }

    @Override
    public Set<LivePlayer> getPlayers() {
        List<org.bukkit.entity.Player> players = this.bossBar.getPlayers();
        Set<LivePlayer> set = new HashSet<>();
        players.forEach(p -> set.add(new BLivePlayer(p)));
        return set;
    }

    @Override
    public ServerBossBar register(LivePlayer... players) {
        for(LivePlayer player : players){
            this.bossBar.addPlayer(((BLivePlayer)player).getBukkitEntity());
        }
        return this;
    }

    @Override
    public ServerBossBar deregister(LivePlayer... players) {
        for(LivePlayer player : players){
            this.bossBar.removePlayer(((BLivePlayer)player).getBukkitEntity());
        }
        return this;
    }
}
