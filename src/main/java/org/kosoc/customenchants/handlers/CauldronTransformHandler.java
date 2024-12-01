package org.kosoc.customenchants.handlers;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.kosoc.customenchants.Customenchants;

import java.util.List;

public class CauldronTransformHandler {

    public static void registerEvents() {
        ServerTickEvents.END_WORLD_TICK.register(CauldronTransformHandler::onWorldTick);
    }

    private static void onWorldTick(ServerWorld world) {
        for (PlayerEntity player : world.getPlayers()) {
            // Get all cauldrons near the player
            Iterable<BlockPos> nearbyBlocks = BlockPos.iterate(
                    player.getBlockPos().add(-5, -5, -5),
                    player.getBlockPos().add(5, 5, 5)
            );

            for (BlockPos pos : nearbyBlocks) {
                if (world.getBlockState(pos).isOf(Blocks.WATER_CAULDRON)) {
                    processCauldron(world, pos);
                }
            }
        }
    }

    private static void processCauldron(ServerWorld world, BlockPos pos) {
        // Get entities near the cauldron
        Box box = new Box(pos).expand(1, 1, 1);
        List<ItemEntity> items = world.getEntitiesByClass(ItemEntity.class, box, ItemEntity::isAlive);

        // Check for required items
        int diamondCount = 0;
        int netherStarCount = 0;
        int netheriteIngotCount = 0;
        int bookCount = 0;
        ItemEntity netherStarEntity;
        ItemEntity netheriteIngotEntity;
        ItemEntity bookEntity;

        for (ItemEntity item : items) {
            ItemStack stack = item.getStack();
            if (stack.getItem() == Items.DIAMOND) {
                diamondCount += stack.getCount();
            } else if (stack.getItem() == Items.NETHER_STAR) {
                netherStarCount += stack.getCount();
                netherStarEntity = item;
            } else if (stack.getItem() == Items.NETHERITE_INGOT) {
                netheriteIngotCount += stack.getCount();
                netheriteIngotEntity = item;
            } else if (stack.getItem() == Items.BOOK) {
                bookCount += stack.getCount();
                bookEntity = item;
            }
        }

        // Validate recipe
        if (diamondCount >= 6 && netherStarCount >= 1 && netheriteIngotCount >= 1 && bookCount >= 1) {
            // Remove items from the cauldron
            items.forEach(ItemEntity::discard);

            // Create the Soulbound Book
            ItemStack soulboundBook = createSoulboundBook();

            // Drop the Soulbound Book
            ItemEntity soulboundBookEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, soulboundBook);
            world.spawnEntity(soulboundBookEntity);

            // Play particle and sound effects
            world.syncWorldEvent(2005, pos, 0); // Enchantment particle effect
            world.playSound(null, pos, net.minecraft.sound.SoundEvents.ENTITY_PLAYER_LEVELUP, net.minecraft.sound.SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    private static ItemStack createSoulboundBook() {
        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        book.addEnchantment(Customenchants.SB, 1);
        return book;
    }
}

