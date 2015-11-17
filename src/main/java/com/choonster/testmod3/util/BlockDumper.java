package com.choonster.testmod3.util;

import com.choonster.testmod3.Logger;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameData;

import java.io.PrintWriter;

/**
 * Dumps the unlocalised names and the output of the {@link net.minecraft.item.ItemBlock}'s {@link Object#toString()} method for all of this mod's blocks.
 * <p>
 * I wrote this because I was getting an {@link AbstractMethodError} from a lambda implementing {@link net.minecraft.client.renderer.ItemMeshDefinition} and the only the toString output was included in the crash report.
 */
public class BlockDumper {
	public static void dump() {
		try (PrintWriter writer = new PrintWriter("TestMod3_BlockDump_" + (FMLCommonHandler.instance().getEffectiveSide().isClient() ? "Client" : "Server") + ".txt", "UTF-8")) {
			writer.println("Name - toString");

			for (Object key : GameData.getBlockRegistry().getKeys()) {
				if (key.toString().startsWith(Constants.RESOURCE_PREFIX)) {
					Item item = GameData.getItemRegistry().getObject(key);
					if (item != null) {
						writer.printf("%s - %s\n", item.getUnlocalizedName(), item.toString());
					}
				}
			}
		} catch (Exception e) {
			Logger.fatal(e, "Exception dumping blocks");

		}
	}
}