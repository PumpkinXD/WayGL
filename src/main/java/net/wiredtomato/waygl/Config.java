package net.wiredtomato.waygl;

import eu.midnightdust.lib.config.MidnightConfig;

public class Config extends MidnightConfig {
    @Comment(category = "natives", centered = true) public static Comment nativesHint;
    @Comment(category = "natives") public static Comment nativesSpacer;
    @Entry(category = "natives") public static boolean useNativeGlfw = false;
    @Comment(category = "natives", centered = true) public static Comment nativeGlfwPathHint;
    @Entry(category = "natives") public static String nativeGlfwPath = "/usr/lib/libglfw.so";
    @Comment(category = "cursor", centered = true) public static Comment cursorHintL1;
    @Comment(category = "cursor", centered = true) public static Comment cursorHintL2;
    @Entry(category = "cursor") public static boolean useVirtualCursor = true;
}
