package pers.cierra_runis.survired

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import pers.cierra_runis.survired.features.history.SurviRedHistory
import pers.cierra_runis.survired.features.pickup.SurviRedPickup
import pers.cierra_runis.survired.features.rename.SurviRedRename

object SurviRed : ModInitializer {
  private val logger = LoggerFactory.getLogger(this.javaClass)
  override fun onInitialize() {
    SurviRedPickup.surviRedRegister()
    SurviRedRename.surviRedRegister()
    SurviRedHistory.surviRedRegister()
  }
}