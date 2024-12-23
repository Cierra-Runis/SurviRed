package pers.cierra_runis.survired.features.rename

import com.mojang.brigadier.arguments.StringArgumentType
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.item.ItemStack
import net.minecraft.server.command.CommandManager
import net.minecraft.text.Text
import pers.cierra_runis.survired.common.SurviRedRegister

object SurviRedRename : SurviRedRegister {
  private val COST_EXP = 2000

  override fun surviRedRegister() =
    CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
      dispatcher.register(
        CommandManager.literal("rename")
          .requires { it.isExecutedByPlayer }
          .then(createRemoveCommand())
          .then(createSetCommand())
      )
    }


  private fun createRemoveCommand() = CommandManager.literal("remove").executes { context ->
    val player = context.source.player ?: return@executes 0
    if (player.totalExperience < COST_EXP) {
      context.source.sendFeedback({ Text.literal("Need More than $COST_EXP to Remove") }, false)
      return@executes 0
    }
    val stack = player.mainHandStack
    stack.removeSubNbt("display")
    context.source.sendFeedback({ Text.literal("Removed Name") }, false)
    player.addExperience(-COST_EXP)
    return@executes 1
  }

  private fun createSetCommand() = CommandManager.literal("set").then(
    CommandManager.argument("name", StringArgumentType.string()).executes { context ->
      val player = context.source.player ?: return@executes 0
      if (player.totalExperience < COST_EXP) {
        context.source.sendFeedback({ Text.literal("Need More than $COST_EXP to Rename") }, false)
        return@executes 0
      }
      val name = StringArgumentType.getString(context, "name")
      val stack = player.mainHandStack
      setCustomName(stack, name)
      context.source.sendFeedback({ Text.literal("Set Name") }, false)
      player.addExperience(-COST_EXP)
      return@executes 1
    }
  )

  private fun setCustomName(stack: ItemStack, name: String) =
    stack.getOrCreateSubNbt("display").apply {
      putString("Name", "{\"text\":\"$name\", \"italic\":false}")
    }
}
