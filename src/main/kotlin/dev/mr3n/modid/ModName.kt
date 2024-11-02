package dev.mr3n.modid

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object ModName : ModInitializer {

    private val logger = LoggerFactory.getLogger("modid")

    override fun onInitialize() {
        logger.info("Hello World!")
    }
}