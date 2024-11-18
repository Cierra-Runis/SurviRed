package pers.cierra_runis.survired.features.dye

import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.block.entity.ShulkerBoxBlockEntity
import net.minecraft.item.DyeItem
import net.minecraft.util.ActionResult
import org.slf4j.LoggerFactory
import pers.cierra_runis.survired.common.SurviRedRegister

object SurviRedDye : SurviRedRegister {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun register() {
        UseBlockCallback.EVENT.register(UseBlockCallback { playerEntity, world, hand, blockHitResult ->
            if (!playerEntity.isSneaking) return@UseBlockCallback ActionResult.PASS
            if (playerEntity.inventory.mainHandStack.item !is DyeItem) return@UseBlockCallback ActionResult.PASS
            val block = world.getBlockEntity(blockHitResult.blockPos)
            if (block !is ShulkerBoxBlockEntity) return@UseBlockCallback ActionResult.PASS

            logger.info(block.toString())

            return@UseBlockCallback ActionResult.SUCCESS
        })
    }
}