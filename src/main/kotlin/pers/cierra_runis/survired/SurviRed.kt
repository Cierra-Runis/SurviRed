package pers.cierra_runis.survired

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import pers.cierra_runis.survired.pickup.SurviRedPickupEventListener

object SurviRed : ModInitializer {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    override fun onInitialize() {
        logger.info("Loading...")
        SurviRedPickupEventListener.registerRightClickEvent()
        logger.info("Loaded!")
    }
}