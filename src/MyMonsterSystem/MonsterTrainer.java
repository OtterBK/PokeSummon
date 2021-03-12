package MyMonsterSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.advancement.Advancement;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import MonsterSkyblock.MonsterSkyblock;
import MyMonsterSystem.Monster.MonsterType;
import Utility.MyErrorType;
import Utility.MyUtility;

public class MonsterTrainer {

	public static HashMap<String, MonsterTrainer> trainerMap = new HashMap<String, MonsterTrainer>();
	
	private final String MS = "§f[ §eBS §f] ";
	private final String dirPath;
	private final int MAX_MONSTER = 6; //몬스터 최대 소지수
	
	private int controlAmout = 1; //한번에 조종 가능한 몬스터 수 //저장
	private int usingAmout = 0; //현재 소환중인 몬스터 수 
	
	private String uuid; //저장
	private Player trainerPlayer;
	private ArrayList<MyMonster> monsterList = new ArrayList<MyMonster>(6);
	private int archieve = 0; //저장
	private int selectMonsterIndex;
	private Inventory monsterUI;
	
	private ItemStack deco;
	private ItemStack deco2;
	private ItemStack monsterBall_normal;
	private ItemStack evolveStone;
	
	private Advancement recentAdvancement;
	
	private boolean isSummoning = false;
	
	private MonsterTrainerEvent monsterTrainerEvent = new MonsterTrainerEvent();
	
	public static int loadAllData() {
		int loadCnt = 0;
		
		File dir = new File(MonsterSkyblock.plugin.getDataFolder()+"/TrainerPlayers/");
		if(dir == null || !dir.isDirectory()) {
			return 0;
		}
		
		for(File file : dir.listFiles()) {
			try {
				String fileName = file.getName();
				String fileUUID = fileName.replace(".data", "");
				
				MonsterTrainer trainer = new MonsterTrainer(fileUUID);
				trainer.load();
				loadCnt++;
			}catch (Exception e) {
				MyUtility.printLog(file.getName()+"의 데이터 로드중 오류 발생");
				e.printStackTrace();
			}
		}
		return loadCnt;
	}
	
	public MonsterTrainer(String uuid) {
		this.uuid = uuid;
		dirPath = MonsterSkyblock.plugin.getDataFolder()+"/TrainerPlayers/";
		
		Bukkit.getServer().getPluginManager().registerEvents(monsterTrainerEvent, MonsterSkyblock.plugin);
		monsterUI = Bukkit.createInventory(null, 27, "§0§l동료 포켓몬 목록");
			
		deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)12);
		ItemMeta meta = deco.getItemMeta();
		meta.setDisplayName("　");
		deco.setItemMeta(meta);
		
		deco2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)15);
		meta = deco2.getItemMeta();
		meta.setDisplayName("§f[ §a선택된 포켓몬이 없습니다. §f]");
		deco2.setItemMeta(meta);
		
		monsterBall_normal = new ItemStack(Material.RECORD_10, 1);
		meta = monsterBall_normal.getItemMeta();
		meta.setDisplayName("§f[ §b몬스터 볼 §f]");
		ArrayList<String> loreList = new ArrayList<String>();
		loreList.add("");
		loreList.add("§b- §d좌클릭 §7-> §a몬스터 선택창 열기");
		loreList.add("§b- §d우클릭 §7-> §a몬스터 소환");
		loreList.add("");
		meta.setLore(loreList);
		monsterBall_normal.setItemMeta(meta);
		
		evolveStone = new ItemStack(Material.RECORD_9, 1);
		meta = monsterBall_normal.getItemMeta();
		meta.setDisplayName("§f[ §b진화의 원석 §f]");
		loreList.clear();
		loreList.add("");
		loreList.add("§b- §d좌클릭 §7-> §a몬스터 선택창 열기");
		loreList.add("§b- §d우클릭 §7-> §a몬스터 소환");
		loreList.add("");
		meta.setLore(loreList);
		evolveStone.setItemMeta(meta);
		
		if(trainerMap.containsKey(uuid)) {
			MonsterTrainer prevTrainer = trainerMap.get(uuid);
			prevTrainer.delete();
		}
		trainerMap.put(uuid, this);	
		
		Player p = Bukkit.getPlayer(UUID.fromString(uuid));
		if(p != null) {
			trainerPlayer = p;
			sendMessage("당신은 이제 트레이너입니다.");
		}
		
		giveMonsterBall(MonsterBallType.normal);
		updateUI();
		
		save();
	}
	
	public void delete() {
		trainerMap.remove(uuid);
		this.uuid = null;
		this.trainerPlayer = null;
		MyUtility.unregisterEvents(monsterTrainerEvent);
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public void sendMessage(String str) {
		if(trainerPlayer != null) {
			trainerPlayer.sendMessage(MS+str);
			sendSound(Sound.BLOCK_STONE_BREAK);
		}
	}
	
	public void sendTitle(String mainTitle, String subTitle, int time) {
		if(trainerPlayer != null) {
			trainerPlayer.sendTitle(mainTitle, subTitle, 0, time, 0);
			sendSound(Sound.BLOCK_STONE_BREAK);
		}
	}
	
	public boolean addMonster(MyMonster monster) {
		if(monsterList.size() >= MAX_MONSTER) {
			sendMessage("이미 몬스터를 최대("+MAX_MONSTER+"마리) 보유 중입니다.");
			return false;
		} else {
			monsterList.add(monster);
			monster.setTrainer(this);
			sendMessage("이제 "+monster.monsterName+" 몬스터와 동료입니다.");
			updateUI();
			return true;
		}
	}
	
	public String getName() {
		OfflinePlayer offP =  Bukkit.getOfflinePlayer(UUID.fromString(uuid));
		if(offP != null) {
			return offP.getName();
		} else return null;
	}
	
	public boolean removeMonster(int removeIndex) {
		MyMonster monster = monsterList.get(removeIndex);
		monster.freeMonster();
		monsterList.remove(removeIndex);
		updateUI();
		return true;
	}
	
	public boolean removeMonster(String uuid) {
		MyMonster target = null;
		for(MyMonster monster : monsterList) {
			if(monster.getUUID().equals(uuid)) {
				target = monster;
				break;
			}
		}
		
		if(target != null) {
			monsterList.remove(target);
			target.freeMonster();
			sendMessage("더 이상 §a"+target.monsterName+" §f몬스터를 소유하지 않습니다.");
			updateUI();
			return true;
		} else return false;
	}
	
	public void giveMonsterBall(MonsterBallType type) {
		ItemStack ball;
		switch(type) {
		case normal: ball = monsterBall_normal; break;
		default: ball = monsterBall_normal;
		}
		
		
		if(trainerPlayer != null) {
			for(ItemStack item : trainerPlayer.getInventory().getContents()) {
				if(item == null) {
					trainerPlayer.getInventory().addItem(ball);
					return;
				}
			}
			trainerPlayer.getWorld().dropItem(trainerPlayer.getLocation(), ball);
		}
	}
	
	public void updateUI() {
		
		monsterUI.clear();
		
		for(int i = 0 ; i < 19; i++)
			monsterUI.setItem(i, deco);
		
		MyMonster selectMonster = null;
		if(0 <= selectMonsterIndex && selectMonsterIndex < monsterList.size()) {
			selectMonster = monsterList.get(selectMonsterIndex);
			if(selectMonster != null) {
				String monsterName = selectMonster.getMonsterName();
				
				ItemStack selectMonsterIcon = new ItemStack(Material.BOOK_AND_QUILL, 1);
				ItemMeta meta = selectMonsterIcon.getItemMeta();
				meta.setDisplayName(monsterName);
				ArrayList<String> loreList = selectMonster.getStatusLore();
				loreList.add("");
				loreList.add("§b좌클릭 §7->§a 해당 몬스터 선택");
				loreList.add("§b우클릭 §7->§a 진화의 원석 사용");
				loreList.add("");
				meta.setLore(loreList);
				selectMonsterIcon.setItemMeta(meta);
				selectMonsterIcon.addUnsafeEnchantment(Enchantment.LUCK, 1);
				
				monsterUI.setItem(4, selectMonsterIcon);
			}	
		} else monsterUI.setItem(4, deco2);

		int slotIndex = 19;
		for(MyMonster tmpMonster : monsterList) {
			if(tmpMonster != null) {
				String monsterName = tmpMonster.getMonsterName();
				
				ItemStack tmpMonsterIcon = new ItemStack(Material.BOOK, 1);
				ItemMeta meta = tmpMonsterIcon.getItemMeta();
				meta.setDisplayName(monsterName);
				ArrayList<String> loreList = tmpMonster.getStatusLore();
				loreList.add("");
				loreList.add("§b좌클릭 §7->§a 해당 몬스터 선택");
				loreList.add("§b우클릭 §7->§a 진화의 원석 사용");
				loreList.add("");
				meta.setLore(loreList);
				tmpMonsterIcon.setItemMeta(meta);
				if(selectMonster != null && selectMonster.equals(tmpMonster)) {
					tmpMonsterIcon.addUnsafeEnchantment(Enchantment.LUCK, 1);
				}
				
				monsterUI.setItem(slotIndex, tmpMonsterIcon);
				if(++slotIndex == 22) slotIndex++; //22번쨰 슬롯은 건너뛰기
			}
		}
		monsterUI.setItem(22, deco);
		monsterUI.setItem(26, deco);
	}
	
	public int selectMonster(int index) {
		if(index <= 0) {
			selectMonsterIndex = 0;
		}else if(index >= monsterList.size()) {
			selectMonsterIndex = monsterList.size()-1;
		} else {
			selectMonsterIndex = index;
		}
		sendMessage(getSelectedMonster().getMonsterName()+" 선택!");
		updateUI();
		return selectMonsterIndex;
	}
	
	public void sendSound(Sound sound) {
		if(trainerPlayer != null) {
			trainerPlayer.playSound(trainerPlayer.getLocation(), sound, 2.0f, 1.0f);
		}
	}
	
	public MyMonster getSelectedMonster() {
		if(monsterList.size() != 0 && 0 <= selectMonsterIndex && selectMonsterIndex < monsterList.size()) {
			return monsterList.get(selectMonsterIndex);	
		}else return null;
	}
	
	public MyMonster getSelectedMonster(int index) {
		if(monsterList.size() != 0 && 0 <= selectMonsterIndex && selectMonsterIndex < monsterList.size()) {
			return monsterList.get(index);	
		}else return null;
	}
	
	public boolean summonMonster() {
		if(trainerPlayer == null) return false; 
		
		MyMonster monster = getSelectedMonster();
		if(monster == null) return false;
		
		if(monster.monsterPlayer == null) return false;
		if(!monster.monsterPlayer.isOnline()) return false;
		
		if(!monster.isSleep()) {
			sendTitle("", "§e§l이미 소환돼 있는 몬스터입니다.", 60);
			return false;
		}
		
		if(monster.isDead()) {
			sendTitle("", "§e§l회복 중인 몬스터입니다.", 60);
			return false;
		}
		
		if(usingAmout >= controlAmout) {
			sendMessage("§e§l더 이상 동시 소환은 불가능합니다. (가능 횟수 : §e"+controlAmout+"회§f)");
			return false;
		}
		
		if(isSummoning) {
			sendTitle("", "§e§l현재 몬스터를 소환하는 중입니다.", 60);
			return false;
		} else {
			isSummoning = true;
		}
		
		sendTitle("", "§e§l너로 정했다!", 60);
		trainerPlayer.getWorld().playSound(trainerPlayer.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1.5F, 0.1F);

		ItemStack throwStack = new ItemStack(Material.CLAY_BALL, 1);
		throwStack.setAmount(1);
		Location pLoc = trainerPlayer.getEyeLocation();

		Item thrownItem = trainerPlayer.getWorld().dropItem(pLoc, throwStack);
		thrownItem.setVelocity(pLoc.getDirection().multiply(1.3f));
		thrownItem.setPickupDelay(200);

		Bukkit.getScheduler().scheduleSyncDelayedTask(MonsterSkyblock.plugin, new Runnable() {
			public void run() {
				isSummoning = false;
				Location sl = thrownItem.getLocation().clone();
				sl.getWorld().playSound(trainerPlayer.getLocation(), Sound.BLOCK_NOTE_BELL, 0.5f, 0.5f);
				sl.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, sl, 40, 0.5F, 0.5f, 0.5f, 0.2f);
				thrownItem.remove();
				monster.spawn(sl.add(0,1,0));
				usingAmout += 1;
			}
		}, 30l);
		
		return true;
	}
	
	public boolean returnMonster() {
		if (trainerPlayer == null) return false;

		MyMonster monster = getSelectedMonster();
		if (monster == null)
			return false;
		if (monster.isSleep()) {
			sendTitle("", "§e§l해당 몬스터는 소환 상태가 아닙니다.", 60);
			return false;
		}
		
		sendTitle("", "§e§l돌아와!", 60);
		trainerPlayer.getWorld().playSound(trainerPlayer.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 1.5F, 0.1F);
		monster.enterToBall();
		usingAmout -= 1;
		return true;
	}
	
	public boolean returnMonster(MyMonster monster) {
		if (trainerPlayer == null) return false;

		if(!monsterList.contains(monster)) return false;
		
		if (monster == null)
			return false;
		if (monster.isSleep()) {
			sendTitle("", "§e§l해당 몬스터는 소환 상태가 아닙니다.", 60);
			return false;
		}
		
		sendTitle("", "§e§l돌아와!", 60);
		trainerPlayer.getWorld().playSound(trainerPlayer.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 1.5F, 0.1F);
		monster.enterToBall();
		usingAmout -= 1;
		return true;
	}
	
	private void monsterUIClick(Player p, InventoryClickEvent e) {
		InventoryView invView = e.getView();
		if(invView != null) {
			if(e.getRawSlot() == e.getSlot()) { //상단 인벤 클릭시만
				e.setCancelled(true);
				Inventory topInv = invView.getTopInventory();
				if(e.getSlot() < 0 || e.getSlot() >= topInv.getSize()) return;
				ItemStack clickItem = topInv.getItem(e.getSlot());
				if(clickItem != null) {
					if(clickItem.equals(deco) || clickItem.equals(deco2)) return;
					
					sendSound(Sound.UI_BUTTON_CLICK);
					
					if(e.getClick() == ClickType.LEFT) {
						int slot = e.getSlot();
						if(slot >= 19) {
							if(slot > 22) {
								slot -= 20;
							} else {
								slot -= 19;
							}
							int clickIndex = slot;
							selectMonster(clickIndex);
						}		
					} else if(e.getClick() == ClickType.RIGHT) {
						int slot = e.getSlot();
						if(slot >= 19) {
							if(slot > 22) {
								slot -= 20;
							} else {
								slot -= 19;
							}
							int clickIndex = slot;
							MyMonster monster = getSelectedMonster(clickIndex);
							if(monster == null) {
								MyUtility.sendError(trainerPlayer, MyErrorType.NO_MONSTER);
								return;
							}
							if(monster.getMaxEvolveLv() == monster.getEvolveLv()) { //이미 최대 진화단계면
								sendMessage("해당 몬스터는 이미 최대 진화 레벨입니다.");
							} else {
								if(useEvolveStone()) {
									String prevName = monster.getMonsterName();
									monster.evolveUp();
									String nowName = monster.getMonsterName();
									sendMessage("진화완료! "+prevName+"이(가) 진화하여 "+nowName+"이(가) 됐습니다!");
									p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 0.5f, 1.0f);
								} else {
									sendMessage("소지한 진화의 원석이 없습니다.");
								}
							}			
						}
						
					}
				}
			}
				
		}
	}
	
	private boolean useEvolveStone() {
		if(trainerPlayer != null) {
			Inventory inv = trainerPlayer.getInventory();
			String evolveStoneName = evolveStone.getItemMeta().getDisplayName();
			for(ItemStack item : inv.getContents()) {
				if(item == null) continue;
				if(item.hasItemMeta()) {
					ItemMeta meta = item.getItemMeta();
					if(meta.hasDisplayName()) {
						String itemName = meta.getDisplayName();
						if(itemName.equals(evolveStoneName)) { //진화의 원석 찾음
							item.setAmount(item.getAmount()-1); //1개 사용
							return true;
						}
					}
				}
			}
			return false; //못찾은거임
		} else {
			return false;
		}
	}
	
	private void interactMonsterBall(Player p, PlayerInteractEvent e, MonsterBallType ballType) {
		if(ballType == MonsterBallType.normal) {
			if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
				p.openInventory(monsterUI);
				p.playSound(p.getLocation(), Sound.BLOCK_ENDERCHEST_OPEN, 2.0f, 1.0f);
			} else if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(p.isSneaking()) {
					returnMonster();
				} else {
					if(summonMonster()) {
						//p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 2.0f, 1.0f);
						sendMessage("너로 정했다!");
					} else {
						sendMessage("몬스터를 소환할 수 없습니다.");
					}
				}			
			}
		}
	}
	
	public void giveEvolveStone() {
		if(trainerPlayer != null) {
			for(ItemStack item : trainerPlayer.getInventory().getContents()) {
				if(item == null) {
					trainerPlayer.getInventory().addItem(evolveStone);	
					return;
				}
			}
			trainerPlayer.getWorld().dropItem(trainerPlayer.getLocation(), evolveStone);
		}
	}
	
	public void doneAdvanced(Advancement advancement) {
		//Bukkit.getServer().broadcastMessage("새로달성: "+advancement.getCriteria());
		if(advancement == null) {
			archieve += 1;
			sendMessage("도전과제를 달성했습니다. 현재 달성한 도전과제 개수 : §e"+archieve+"개");
			if(archieve % 10 == 0) {
				giveEvolveStone();
				sendMessage(archieve+"개의 도전과제 성공! 보상으로 "+evolveStone.getItemMeta().getDisplayName()+"을 얻습니다.");
			} 
			
			if(archieve % 20 == 0) {
				controlAmout += 1;
				sendMessage(archieve+"개의 도전과제 성공! 이제 동시에 "+controlAmout+"마리의 몬스터를 소환가능합니다.");
			}
			if(trainerPlayer != null) {
				trainerPlayer.getWorld().playSound(trainerPlayer.getLocation(), Sound.BLOCK_NOTE_PLING, 2.0f, 1.0f);
			}
			save();
		} else {
			Collection<String> tmpCollection = advancement.getCriteria();
			for(String str : tmpCollection) {
				if(str.contains("has_the_recipe")){
					return;
				}
				
			}
		}

		if((this.recentAdvancement == null || !MyUtility.compareAdvancement(recentAdvancement, advancement))) {
			//if(recentAdvancement != null) Bukkit.getServer().broadcastMessage("기존: "+recentAdvancement.getCriteria());
			//Bukkit.getServer().broadcastMessage("새로달성: "+advancement.getCriteria());
			this.recentAdvancement = advancement;
			
			archieve += 1;
			sendMessage("도전과제를 달성했습니다. 현재 달성한 도전과제 개수 : §e"+archieve+"개");
			if(archieve % 10 == 0) {
				giveEvolveStone();
				sendMessage(archieve+"개의 도전과제 성공! 보상으로 "+evolveStone.getItemMeta().getDisplayName()+"을 얻습니다.");
			} 
			
			if(archieve % 20 == 0) {
				controlAmout += 1;
				sendMessage(archieve+"개의 도전과제 성공! 이제 동시에 "+controlAmout+"마리의 몬스터를 소환가능합니다.");
			}
			if(trainerPlayer != null) {
				trainerPlayer.getWorld().playSound(trainerPlayer.getLocation(), Sound.BLOCK_NOTE_PLING, 2.0f, 1.0f);
			}
			save();
		}	
	}
	
	public boolean save() {
		File file = new File(dirPath, uuid+".data");
		try {
			FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
			
			fileConfig.set("UUID", uuid); //복구용
			
			fileConfig.set("archieve", archieve);
			fileConfig.set("controlAmout", controlAmout);
			
			
			fileConfig.save(file);
		}catch(Exception e) {
			MyUtility.printLog(uuid+"의 트레이너 데이터 저장중 오류 발생");
			return false;
		}
		return true;
	}
	
	//@Deprecated
	public boolean load() {
		File file = new File(dirPath, uuid+".data");
		if(file == null || !file.exists()) return false;
		try {
			String fileName = file.getName();
			fileName.replace(".data", "");
			FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
			archieve = fileConfig.getInt("archieve");
			controlAmout = fileConfig.getInt("controlAmout");
		}catch(Exception e) {
			MyUtility.printLog(uuid+"의 데이터 로드중 오류 발생");
			return false;
		}
		return true;
	}
	
	
	private class MonsterTrainerEvent implements Listener{
		
		@EventHandler
		public void onJoin(PlayerJoinEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				trainerPlayer = p;
			}
		}
		
		@EventHandler
		public void onQuit(PlayerQuitEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				trainerPlayer = null;
			}
		}
		
		@EventHandler
		public void onClickInventory(InventoryClickEvent e) {
			if(e.getWhoClicked() instanceof Player) {
				Player p = (Player) e.getWhoClicked();
				if(p.getUniqueId().toString().equals(uuid)) {
					String invTitle = e.getInventory().getTitle();
					if(invTitle.equals(monsterUI.getTitle())) {
						monsterUIClick(p, e);
					}
				}	
			}
		}
		
		@EventHandler
		public void onPlayerInteract(PlayerInteractEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				ItemStack clickItem = e.getItem();
				if(clickItem != null) {
					if(clickItem.hasItemMeta()) {
						ItemMeta meta = clickItem.getItemMeta();
						if(meta.hasDisplayName()) {
							String clickItemName = meta.getDisplayName();
							if(clickItemName.equals(monsterBall_normal.getItemMeta().getDisplayName())) {
								interactMonsterBall(p, e, MonsterBallType.normal);
							}	
						}
					}	
				}
			}
		}
		
		
		@EventHandler
		public void onEntityDamagedByEntity(EntityDamageByEntityEvent e) {
			if(e.getEntity() instanceof Player) {
				Player p = (Player) e.getEntity();
				if(p.getUniqueId().toString().equals(uuid)) {
					if(e.getDamager() instanceof Player) {
						Player damager = (Player) e.getDamager();
						for(MyMonster monster : monsterList) {
							if(monster != null) {
								String monsterUUID = monster.getUUID();
								if(damager.getUniqueId().toString().equals(monsterUUID)) {
									e.setCancelled(true);
								}
							}
						}
					}
				}
			}		
		}
		
		@EventHandler
		public void onAdvancedDone(PlayerAdvancementDoneEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				//Bukkit.getLogger().info(recentAdvancement+"");
				doneAdvanced(e.getAdvancement());
			}
		}
	}
	
}
