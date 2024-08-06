package net.mitask.emcgenesis.util;

import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;

public class IdentifierUtil {
    public static Identifier id(String id) {
        return Identifier.of(id);
    }

    public static Identifier id(Namespace namespace, String path) {
        return Identifier.of(namespace, path);
    }

    public static String toString(Identifier identifier) {
        return identifier.toString();
    }

    public static Namespace getNamespace(Identifier identifier) {
        return identifier.getNamespace();
    }

    public static String getPath(Identifier identifier) {
        return identifier.getPath();
    }
}
