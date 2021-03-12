package Utility;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.advancement.Advancement;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PigZapEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.server.ServiceRegisterEvent;
import org.bukkit.event.server.ServiceUnregisterEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.SpawnChangeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MyUtility {

	public static boolean isInGame(CommandSender sender) {
		if(!(sender instanceof Player)) return false;
		else return true;
	}
	
	public static void printLog(String log) {
		Bukkit.getServer().getLogger().info("[로그] "+log);
	}
	
	
	public static boolean saveInventoryToFile(String path, Inventory inventory , String fileName) {
		if (inventory == null || path == null || fileName == null) return false;
		try {
			File invFile = new File(path, fileName + ".invsave");
			if (invFile.exists()) invFile.delete();
			FileConfiguration invConfig = YamlConfiguration.loadConfiguration(invFile);

			invConfig.set("Title", inventory.getTitle());
			invConfig.set("Size", inventory.getSize());
			invConfig.set("Max stack size", inventory.getMaxStackSize());

			ItemStack[] invContents = inventory.getContents();
			for (int i = 0; i < invContents.length; i++) {
				ItemStack itemInInv = invContents[i];
				if (itemInInv != null) {
					if (itemInInv.getType() != Material.AIR) {
						invConfig.set("Slot " + i, itemInInv);
					}
				}
			}

			invConfig.save(invFile);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	public static Inventory getInventoryFromFile(File file) {
		if (file == null) {
			return null;
		}
		if (!file.exists() || file.isDirectory() || !file.getAbsolutePath().endsWith(".invsave")) {
			return null;
		}
		try {
			FileConfiguration invConfig = YamlConfiguration.loadConfiguration(file);
			Inventory inventory = null;
			String invTitle = invConfig.getString("Title", "Inventory");
			int invSize = invConfig.getInt("Size", 27);
			int invMaxStackSize = invConfig.getInt("Max stack size", 64);
			inventory = Bukkit.getServer().createInventory(null, invSize, ChatColor.translateAlternateColorCodes('§', invTitle));
			inventory.setMaxStackSize(invMaxStackSize);
			try {
				ItemStack[] invContents = new ItemStack[invSize];
				for (int i = 0; i < invSize; i++) {
					if (invConfig.contains("Slot " + i)) {
						invContents[i] = invConfig.getItemStack("Slot " + i);
					}
					else invContents[i] = new ItemStack(Material.AIR);
				}
				inventory.setContents(invContents);
			} catch (Exception ex) {
			}
			return inventory;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static ItemStack[][] copyRecipeArray(ItemStack itemArray[][]) {
		ItemStack copyArray[][] = new ItemStack[3][3];
		for(int i = 0; i < copyArray.length; i++) {
			for(int j = 0; j < copyArray[i].length; j++) {
				if(itemArray[i][j] != null)
					copyArray[i][j] = itemArray[i][j].clone();	
				else 
					copyArray[i][j] = null;
			}		
		}
		return copyArray;
	}
	
	//min ~ max 중 값 1개 반환
	public static int getRandom(int min, int max) {
		return (int)(Math.random() * (max - min + 1) + min);
	}
	
	public static void sendError(Player p, MyErrorType type) {
		if(p == null) return;
		
		switch(type) {
		case NO_MONSTER: p.sendMessage("§f[ §4에러 §f] null 값의 몬스터 선택됨"); break;
		
		default: p.sendMessage("§f[ §4에러 §f] 정의되지 않은 에러"); break;
		}
	}
	
	public static void unregisterEvents(Listener l) {
		  BlockBreakEvent.getHandlerList().unregister(l);
	        BlockBurnEvent.getHandlerList().unregister(l);
	        BlockCanBuildEvent.getHandlerList().unregister(l);
	        BlockDamageEvent.getHandlerList().unregister(l);
	        BlockDispenseEvent.getHandlerList().unregister(l);
	        BlockExpEvent.getHandlerList().unregister(l);
	        BlockFadeEvent.getHandlerList().unregister(l);
	        BlockFormEvent.getHandlerList().unregister(l);
	        BlockFromToEvent.getHandlerList().unregister(l);
	        BlockGrowEvent.getHandlerList().unregister(l);
	        BlockIgniteEvent.getHandlerList().unregister(l);
	        BlockMultiPlaceEvent.getHandlerList().unregister(l);
	        BlockPhysicsEvent.getHandlerList().unregister(l);
	        BlockPistonExtendEvent.getHandlerList().unregister(l);
	        BlockPistonRetractEvent.getHandlerList().unregister(l);
	        BlockPlaceEvent.getHandlerList().unregister(l);
	        BlockRedstoneEvent.getHandlerList().unregister(l);
	        BlockSpreadEvent.getHandlerList().unregister(l);
	        EntityBlockFormEvent.getHandlerList().unregister(l);
	        LeavesDecayEvent.getHandlerList().unregister(l);
	        NotePlayEvent.getHandlerList().unregister(l);
	        SignChangeEvent.getHandlerList().unregister(l);

	        EnchantItemEvent.getHandlerList().unregister(l);
	        PrepareItemEnchantEvent.getHandlerList().unregister(l);

	        CreatureSpawnEvent.getHandlerList().unregister(l);
	        CreeperPowerEvent.getHandlerList().unregister(l);
	        EntityBreakDoorEvent.getHandlerList().unregister(l);
	        EntityChangeBlockEvent.getHandlerList().unregister(l);
	        EntityCombustByBlockEvent.getHandlerList().unregister(l);
	        EntityCombustByEntityEvent.getHandlerList().unregister(l);
	        EntityCreatePortalEvent.getHandlerList().unregister(l);
	        EntityDamageByBlockEvent.getHandlerList().unregister(l);
	        EntityDamageByEntityEvent.getHandlerList().unregister(l);
	        EntityDeathEvent.getHandlerList().unregister(l);
	        EntityExplodeEvent.getHandlerList().unregister(l);
	        EntityInteractEvent.getHandlerList().unregister(l);
	        EntityPortalEnterEvent.getHandlerList().unregister(l);
	        EntityPortalExitEvent.getHandlerList().unregister(l);
	        EntityRegainHealthEvent.getHandlerList().unregister(l);
	        EntityShootBowEvent.getHandlerList().unregister(l);
	        EntityTameEvent.getHandlerList().unregister(l);
	        EntityTargetEvent.getHandlerList().unregister(l);
	        EntityTargetLivingEntityEvent.getHandlerList().unregister(l);
	        EntityTeleportEvent.getHandlerList().unregister(l);
	        EntityUnleashEvent.getHandlerList().unregister(l);
	        ExpBottleEvent.getHandlerList().unregister(l);
	        ExplosionPrimeEvent.getHandlerList().unregister(l);
	        FoodLevelChangeEvent.getHandlerList().unregister(l);
	        HorseJumpEvent.getHandlerList().unregister(l);
	        ItemDespawnEvent.getHandlerList().unregister(l);
	        ItemSpawnEvent.getHandlerList().unregister(l);
	        PigZapEvent.getHandlerList().unregister(l);
	        PlayerDeathEvent.getHandlerList().unregister(l);
	        PlayerLeashEntityEvent.getHandlerList().unregister(l);
	        PotionSplashEvent.getHandlerList().unregister(l);
	        ProjectileHitEvent.getHandlerList().unregister(l);
	        ProjectileLaunchEvent.getHandlerList().unregister(l);
	        SheepDyeWoolEvent.getHandlerList().unregister(l);
	        SheepRegrowWoolEvent.getHandlerList().unregister(l);
	        SlimeSplitEvent.getHandlerList().unregister(l);

	        HangingBreakByEntityEvent.getHandlerList().unregister(l);
	        HangingBreakEvent.getHandlerList().unregister(l);
	        HangingPlaceEvent.getHandlerList().unregister(l);

	        BrewEvent.getHandlerList().unregister(l);
	        CraftItemEvent.getHandlerList().unregister(l);
	        FurnaceBurnEvent.getHandlerList().unregister(l);
	        FurnaceExtractEvent.getHandlerList().unregister(l);
	        FurnaceSmeltEvent.getHandlerList().unregister(l);
	        InventoryClickEvent.getHandlerList().unregister(l);
	        InventoryCloseEvent.getHandlerList().unregister(l);
	        InventoryCreativeEvent.getHandlerList().unregister(l);
	        InventoryDragEvent.getHandlerList().unregister(l);
	        InventoryInteractEvent.getHandlerList().unregister(l);
	        InventoryMoveItemEvent.getHandlerList().unregister(l);
	        InventoryOpenEvent.getHandlerList().unregister(l);
	        InventoryPickupItemEvent.getHandlerList().unregister(l);
	        PrepareItemCraftEvent.getHandlerList().unregister(l);

	        AsyncPlayerChatEvent.getHandlerList().unregister(l);
	        AsyncPlayerPreLoginEvent.getHandlerList().unregister(l);
	        PlayerAchievementAwardedEvent.getHandlerList().unregister(l);
	        PlayerAnimationEvent.getHandlerList().unregister(l);
	        PlayerBedEnterEvent.getHandlerList().unregister(l);
	        PlayerBedLeaveEvent.getHandlerList().unregister(l);
	        PlayerBucketEmptyEvent.getHandlerList().unregister(l);
	        PlayerBucketFillEvent.getHandlerList().unregister(l);
	        PlayerChangedWorldEvent.getHandlerList().unregister(l);
	        PlayerChannelEvent.getHandlerList().unregister(l);
	        PlayerChatEvent.getHandlerList().unregister(l);
	        PlayerChatTabCompleteEvent.getHandlerList().unregister(l);
	        PlayerCommandPreprocessEvent.getHandlerList().unregister(l);
	        PlayerDropItemEvent.getHandlerList().unregister(l);
	        PlayerEditBookEvent.getHandlerList().unregister(l);
	        PlayerEggThrowEvent.getHandlerList().unregister(l);
	        PlayerExpChangeEvent.getHandlerList().unregister(l);
	        PlayerFishEvent.getHandlerList().unregister(l);
	        PlayerGameModeChangeEvent.getHandlerList().unregister(l);
	        PlayerInteractAtEntityEvent.getHandlerList().unregister(l);
	        PlayerInteractEntityEvent.getHandlerList().unregister(l);
	        PlayerInteractEvent.getHandlerList().unregister(l);
	        PlayerItemBreakEvent.getHandlerList().unregister(l);
	        PlayerItemConsumeEvent.getHandlerList().unregister(l);
	        PlayerItemHeldEvent.getHandlerList().unregister(l);
	        PlayerJoinEvent.getHandlerList().unregister(l);
	        PlayerKickEvent.getHandlerList().unregister(l);
	        PlayerLevelChangeEvent.getHandlerList().unregister(l);
	        PlayerLoginEvent.getHandlerList().unregister(l);
	        PlayerMoveEvent.getHandlerList().unregister(l);
	        PlayerPickupItemEvent.getHandlerList().unregister(l);
	        PlayerPortalEvent.getHandlerList().unregister(l);
	        PlayerPreLoginEvent.getHandlerList().unregister(l);
	        PlayerQuitEvent.getHandlerList().unregister(l);
	        PlayerRegisterChannelEvent.getHandlerList().unregister(l);
	        PlayerRespawnEvent.getHandlerList().unregister(l);
	        PlayerShearEntityEvent.getHandlerList().unregister(l);
	        PlayerStatisticIncrementEvent.getHandlerList().unregister(l);
	        PlayerTeleportEvent.getHandlerList().unregister(l);
	        PlayerToggleFlightEvent.getHandlerList().unregister(l);
	        PlayerToggleSneakEvent.getHandlerList().unregister(l);
	        PlayerToggleSprintEvent.getHandlerList().unregister(l);
	        PlayerUnleashEntityEvent.getHandlerList().unregister(l);
	        PlayerUnregisterChannelEvent.getHandlerList().unregister(l);
	        PlayerVelocityEvent.getHandlerList().unregister(l);

	        MapInitializeEvent.getHandlerList().unregister(l);
	        PluginDisableEvent.getHandlerList().unregister(l);
	        PluginEnableEvent.getHandlerList().unregister(l);
	        RemoteServerCommandEvent.getHandlerList().unregister(l);
	        ServerCommandEvent.getHandlerList().unregister(l);
	        ServerListPingEvent.getHandlerList().unregister(l);
	        ServiceRegisterEvent.getHandlerList().unregister(l);
	        ServiceUnregisterEvent.getHandlerList().unregister(l);

	        VehicleBlockCollisionEvent.getHandlerList().unregister(l);
	        VehicleCreateEvent.getHandlerList().unregister(l);
	        VehicleDamageEvent.getHandlerList().unregister(l);
	        VehicleDestroyEvent.getHandlerList().unregister(l);
	        VehicleEnterEvent.getHandlerList().unregister(l);
	        VehicleEntityCollisionEvent.getHandlerList().unregister(l);
	        VehicleExitEvent.getHandlerList().unregister(l);
	        VehicleMoveEvent.getHandlerList().unregister(l);
	        VehicleUpdateEvent.getHandlerList().unregister(l);

	        LightningStrikeEvent.getHandlerList().unregister(l);
	        ThunderChangeEvent.getHandlerList().unregister(l);
	        WeatherChangeEvent.getHandlerList().unregister(l);

	        ChunkLoadEvent.getHandlerList().unregister(l);
	        ChunkPopulateEvent.getHandlerList().unregister(l);
	        ChunkUnloadEvent.getHandlerList().unregister(l);
	        PortalCreateEvent.getHandlerList().unregister(l);
	        SpawnChangeEvent.getHandlerList().unregister(l);
	        StructureGrowEvent.getHandlerList().unregister(l);
	        WorldInitEvent.getHandlerList().unregister(l);
	        WorldLoadEvent.getHandlerList().unregister(l);
	        WorldUnloadEvent.getHandlerList().unregister(l);
	}
	
	public static boolean compareAdvancement(Advancement ad1, Advancement ad2) {
		if(ad1 == null && ad2 != null) return false;
		if(ad1 != null && ad2 == null) return false;
		
		Collection<String> col1 = ad1.getCriteria();
		Collection<String> col2 = ad2.getCriteria();
		
		int col1Size = col1.size();
		int col2Size = col2.size();
		
		if(col1Size != col2Size) return false;
		
		ArrayList<String> col1List = new ArrayList<String>(col1Size);
		ArrayList<String> col2List = new ArrayList<String>(col2Size);
		
		for(String str : col1) {
			col1List.add(str);
		}
		
		for(String str : col2) {
			col2List.add(str);
		}
		
		for(int i = 0; i < col1List.size(); i++) {
			String col1Str = col1List.get(i);
			String col2Str = col2List.get(i);
			if(!col1Str.equals(col2Str)) {
				//Bukkit.getLogger().info("return false");
				return false;
			}
		}
		//Bukkit.getLogger().info("----------");
		//Bukkit.getLogger().info(col1List.toString());
		//Bukkit.getLogger().info(col2List.toString());
		//Bukkit.getLogger().info("----------");
		
		
		//Bukkit.getLogger().info("return true");
		
		return true;
	}
	
	public static void giveItem(Player p, ItemStack giveItem) {
		for(ItemStack item : p.getInventory().getContents()) {
			if(item == null) {
				p.getInventory().addItem(giveItem);
				return;
			}
		}
		p.getWorld().dropItem(p.getLocation().clone().add(0,0.75f,0), giveItem);
	}
	
}
