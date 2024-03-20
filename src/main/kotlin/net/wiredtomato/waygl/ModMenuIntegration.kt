package net.wiredtomato.waygl

import com.terraformersmc.modmenu.ModMenuModMenuCompat
import com.terraformersmc.modmenu.api.ConfigScreenFactory
import eu.midnightdust.lib.config.MidnightConfig

object ModMenuIntegration: ModMenuModMenuCompat() {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { parent -> MidnightConfig.getScreen(parent, WayGL.MODID) }
    }
}