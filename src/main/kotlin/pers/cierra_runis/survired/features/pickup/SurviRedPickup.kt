package pers.cierra_runis.survired.features.pickup

import net.fabricmc.fabric.api.event.player.UseEntityCallback
import net.minecraft.entity.Entity
import net.minecraft.item.ItemStack
import net.minecraft.item.SpawnEggItem
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.ActionResult
import pers.cierra_runis.survired.common.SurviRedRegister


object SurviRedPickup : SurviRedRegister {

  override fun surviRedRegister() {
    UseEntityCallback.EVENT.register { playerEntity, world, hand, entity, entityHitResult ->
      if (!playerEntity.isSneaking) return@register ActionResult.PASS

      val spawnEgg = SpawnEggItem.forEntity(entity.type) ?: return@register ActionResult.FAIL

      val entityNbt = entity.writeNbt(NbtCompound())
      for (key in arrayOf("Pos", "Motion", "Rotation", "UUID")) entityNbt.remove(key)

      val stack = ItemStack(spawnEgg).apply {
        nbt = NbtCompound().apply {
          put("EntityTag", entityNbt)
        }
      }

      if (playerEntity.inventory.emptySlot != -1) playerEntity.giveItemStack(stack)
      else playerEntity.dropItem(stack, true)

      entity.remove(Entity.RemovalReason.DISCARDED)
      return@register ActionResult.SUCCESS
    }
  }
}