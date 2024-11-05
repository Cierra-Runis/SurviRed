package pers.cierra_runis.survired.pickup

import net.fabricmc.fabric.api.event.player.UseEntityCallback
import net.minecraft.item.SpawnEggItem
import net.minecraft.util.ActionResult

class PickupRightClickEventListener {
    fun registerRightClickEvent() {
        UseEntityCallback.EVENT.register(UseEntityCallback { playerEntity, world, hand, entity, entityHitResult ->
            if (playerEntity.isSneaking) {
                entity.toString()
                val spawnEgg = SpawnEggItem.forEntity(entity.type)
                spawnEgg
                playerEntity.giveItemStack(spawnEgg)
                return@isSneaking ActionResult.SUCCESS
            }
            return@UseEntityCallback ActionResult.PASS
        })
    }
}