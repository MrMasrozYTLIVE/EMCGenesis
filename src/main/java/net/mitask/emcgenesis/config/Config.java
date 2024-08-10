package net.mitask.emcgenesis.config;

import net.glasslauncher.mods.gcapi3.api.ConfigRoot;

public class Config {
    @ConfigRoot(
            value = "config",
            visibleName = "Main Config"
    )
    public static final ConfigObject INSTANCE = new ConfigObject();

    public static String getCheckMark() {
        return INSTANCE.checkMark;
    }

    public static String getDenyMark() {
        return INSTANCE.danyMark;
    }
}
