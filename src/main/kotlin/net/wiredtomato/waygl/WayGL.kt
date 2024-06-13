package net.wiredtomato.waygl

import net.minecraft.util.Identifier
import net.wiredtomato.waygl.config.Config
import org.lwjgl.glfw.GLFW
import org.lwjgl.system.Configuration
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused")
object WayGL {
    const val MODID = "waygl"
    private var useVCursor = false

    @JvmField
    val LOGGER: Logger = LoggerFactory.getLogger(WayGL::class.java)

    @JvmStatic
    val platform: Int by lazy { GLFW.glfwGetPlatform() }

    @JvmStatic
    val useWayland: Boolean by lazy { GLFW.glfwPlatformSupported(GLFW.GLFW_PLATFORM_WAYLAND) }

    @JvmStatic
    val isWayland: Boolean by lazy { platform == GLFW.GLFW_PLATFORM_WAYLAND }

    fun clientInit() {
        Config.HANDLER.load()
        useVCursor = Config.useVirtualCursor

        if (Config.useNativeGlfw) {
            Configuration.GLFW_LIBRARY_NAME.set(Config.nativeGlfwPath)
        }
    }

    @JvmStatic
    fun tryUseWayland() {
        // The init hint only allows the wayland backend to be selected.
        // GLFW chooses the platform by itself.
        if (useWayland)
            GLFW.glfwInitHint(GLFW.GLFW_PLATFORM, GLFW.GLFW_PLATFORM_WAYLAND)
    }

    @JvmStatic
    fun useWayland(): Boolean {
        // If GLFW chose wayland as the platform we can safely assume that we run on wayland.
        // Note that this function may only be called *after* glfwInit has been called.
        return isWayland
    }

    @JvmStatic
    fun useVCursor() = useVCursor

    fun id(path: String) = Identifier.of(MODID, path)
}
