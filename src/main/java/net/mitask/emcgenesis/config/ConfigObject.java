package net.mitask.emcgenesis.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class ConfigObject {
    @ConfigEntry(name = "Check Mark")
    public String checkMark = "+";
    @ConfigEntry(name = "Deny mark")
    public String danyMark = "-";
    @ConfigEntry(name = "Learning status", description = "Should learning status be visible?")
    public Boolean isLearningVisible = true;
}
