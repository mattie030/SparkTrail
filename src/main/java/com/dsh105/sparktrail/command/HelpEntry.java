package com.dsh105.sparktrail.command;

import org.bukkit.ChatColor;


public enum HelpEntry {

    SPARKTRAIL("/sparktrail", "View the plugin information."),
    TRAIL_HELP("/trail help <page>", "View the SparkTrail Help Menu"),
    TRAIL("/trail", "Open the Trail Menu to activate particle effects"),
    TRAIL_EFFECT("/trail <effect> [data]", "Activate a specific Trail effect"),
    TRAIL_TIMEOUT("/trail timeout <time>", "Sets how long until current effect auto-disables"),
    TRAIL_RANDOM("/trail random", "Activate a random Trail effect"),
    TRAIL_CLEAR("/trail clear", "Clear all Trail effect information"),
    TRAIL_START("/trail start", "Start all Trail effects previously stopped"),
    TRAIL_STOP("/trail stop", "Pause all active particle effects"),
    TRAIL_DEMO("/trail demo", "Display a Client-Side demonstration of all Trail effects."),
    TRAIL_INFO("/trail info", "View active particle effects"),
    TRAIL_SOUND("/trail sound <sound>", "Activate a Sound Trail effect"),

    TRAIL_LOC("/trail location", "Select Location Trail effects"),
    //TRAIL_LOCATION_INFO("/trail location <id> info ", "View active particle effects for a location"),
    TRAIL_LOC_LIST("/trail location list", "List all active Location Trail effects"),
    TRAIL_LOC_START("/trail start", "Start all Trail effects previously stopped"),
    TRAIL_LOC_STOP("/trail stop", "Pause all active Trail effects"),
    TRAIL_LOC_CLEAR("/trail clear", "Clear all Trail effect information"),

    TRAIL_MOB("/trail mob", "Select Entity Trail effects"),
    //TRAIL_MOB_INFO("/trail mob <uuid> info ", "View active particle effects for an Entity [by UUID]"),
    TRAIL_MOB_LIST("/trail mob list", "List all active Entity Trail effects"),

    TRAIL_PLAYER("/trail player <name>", "Open the Trail Menu to activate effects for another player"),
    TRAIL_PLAYER_INFO("/trail player <name> info", "View active Trail effects for another player"),
    TRAIL_PLAYER_LIST("/trail player list", "List all active Player Trail effects"),
    TRAIL_PLAYER_CLEAR("/trail player <name> clear", "Clear all Trail effect information for another player"),
    TRAIL_PLAYER_ACTIVATE("/trail player <name> start", "Start all Trail effects previously stopped for another player"),
    TRAIL_PLAYER_STOP("/trail player <name> stop", "Pause all active Trail effects for another player"),;

    private String line;
    private EntryType[] entryTypes;

    HelpEntry(String cmd, String desc, EntryType... entryTypes) {
        this.line = ChatColor.YELLOW + cmd + ChatColor.WHITE + "  •••  " + ChatColor.GREEN + desc;
        this.entryTypes = entryTypes;
    }

    public String getLine() {
        return this.line;
    }

    public EntryType[] getEntryTypes() {
        return this.entryTypes;
    }

    public boolean isType(EntryType entryType) {
        for (EntryType et : getEntryTypes()) {
            if (entryType.equals(et)) {
                return true;
            }
        }
        return false;
    }

    public enum EntryType {
        PLAYER_TRAIL, BLOCK_TRAIL, LOCATION_TRAIL;
    }
}