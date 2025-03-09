package cn.onea.sunkplugin;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class CoinManager {
    private final SunkPlugins plugin;
    private YamlConfiguration data;
    private File dataFile;

    public CoinManager(SunkPlugins plugin) {
        this.plugin = plugin;
        loadData();
    }

    private void loadData() {
        dataFile = new File(plugin.getDataFolder(), "sunkcoins.yml");
        if (!dataFile.exists()) {
            plugin.saveResource("sunkcoins.yml", false);
        }
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void saveData() {
        try {
            data.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("无法保存金币数据！");
        }
    }

    public int getCoins(UUID uuid) {
        return data.getInt(uuid.toString(), 0);
    }

    public void setCoins(UUID uuid, int amount) {
        data.set(uuid.toString(), amount);
        saveData();
    }

    public void addCoins(UUID uuid, int amount) {
        setCoins(uuid, getCoins(uuid) + amount);
    }

    public void removeCoins(UUID uuid, int amount) {
        setCoins(uuid, Math.max(0, getCoins(uuid) - amount));
    }
}