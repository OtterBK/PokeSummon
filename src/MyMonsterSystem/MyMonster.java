package MyMonsterSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import MonsterSkyblock.MonsterSkyblock;
import MyMonsterSystem.Monster.Bulbasaur;
import MyMonsterSystem.Monster.Charmander;
import MyMonsterSystem.Monster.Chikorita;
import MyMonsterSystem.Monster.Cyndaquil;
import MyMonsterSystem.Monster.MonsterType;
import MyMonsterSystem.Monster.Squirtle;
import MyMonsterSystem.Monster.Totodile;
import MyMonsterSystem.Skill.SkillName;
import Utility.MyUtility;
import javafx.scene.shape.Cylinder;

public abstract class MyMonster {
		
	public static HashMap<String, MyMonster> monsterMap = new HashMap<String, MyMonster>();
	
	protected final String MS = "��f[ ��eBS ��f] "; 
	protected final String dirPath;
	protected final int MAX_SKILL_SIZE = 4;
	
	private boolean isDead = false;
	private boolean isSleep = false;
	private int deadTimer_id; 
	private int deadTimer_time;
	protected String uuid; //����
	protected Player monsterPlayer; 
	protected int evolveLv = 1; //����
	protected HashMap<SkillName, Integer> totalSkillMap = new HashMap<SkillName, Integer>();
	protected ArrayList<MySkill> skillList = new ArrayList<MySkill>(4); //���� ����
	
	protected ArrayList<String> statusLore = new ArrayList<String>();
	protected MyMonsterEvent myMonsterEvent;
	
	protected MonsterTrainer trainer; //���� uuid��
	
	protected String monsterName;
	protected MonsterType monsterType;
	protected int maxEvolve = 3;
	
	protected Inventory hotBarBackupInv;
	protected Inventory skillSlotInv;
	protected boolean isBattleMode;
	
	private ItemStack skillBookIcon;
	private MyMonster monsterInstance;
	
	public static int loadAllData() {
		int loadCnt = 0;
		
		File dir = new File(MonsterSkyblock.plugin.getDataFolder()+"/MonsterPlayers/");
		if(dir == null || !dir.isDirectory()) {
			return 0;
		}
		
		for(File file : dir.listFiles()) {
			try {
				String fileName = file.getName();
				String fileUUID = fileName.replace(".data", "");
				FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
				
				MonsterType monsterType = MonsterType.valueOf(fileConfig.getString("monsterType")); //� �������� �ҷ���
				MyMonster monster = createMonster(monsterType, fileUUID);
				
				int evolveLv = fileConfig.getInt("evolveLv");
				if(evolveLv >= 2) {
					monster.applyEvolve2();
				}
				if(evolveLv >= 3) {
					monster.applyEvolve3();
				}
				
				String trainerUUID = fileConfig.getString("trainerUUID");
				MonsterTrainer trainer = MonsterTrainer.trainerMap.get(trainerUUID);
				if(trainer != null) {
					trainer.addMonster(monster);
					//monster.setTrainer(trainer);	
				}
				
				
				List<String> tmpList = new ArrayList<String>();
				tmpList = fileConfig.getStringList("skillList");
				monster.skillList.clear();
				for(String name : tmpList) {
					SkillName skillName = SkillName.valueOf(name);
					if(skillName != null) {
						MySkill skill = MySkill.createSkillClassFromName(skillName);
						if(skill != null) {
							monster.addSkill(skill);
						}
					}
				}
				monster.isSleep = fileConfig.getBoolean("isSleep");
				loadCnt++;
			}catch (Exception e) {
				MyUtility.printLog(file.getName()+"�� ������ �ε��� ���� �߻�");
				
				e.printStackTrace();
			}
		}
		return loadCnt;
	}
	
	public static MyMonster createMonster(MonsterType monsterType, String uuid) {
		MyMonster monster = null;
		switch(monsterType) {
		case �̻��ؾ�: monster = new Bulbasaur(uuid); break;
		case �̻���Ǯ: monster = new Bulbasaur(uuid); break;
		case �̻��ز�: monster = new Bulbasaur(uuid); break;
		
		case ���̸�: monster = new Charmander(uuid); break;
		case ���ڵ�: monster = new Charmander(uuid); break;
		case ���ڸ�: monster = new Charmander(uuid); break;
		
		case ���α�: monster = new Squirtle(uuid); break;
		case ��Ϻα�: monster = new Squirtle(uuid); break;
		case �źϿ�: monster = new Squirtle(uuid); break;
		
		case ġ�ڸ�Ÿ: monster = new Chikorita(uuid); break;
		case ���̸���: monster = new Chikorita(uuid); break;
		case �ް��Ͽ�: monster = new Chikorita(uuid); break;
		
		case ������: monster = new Cyndaquil(uuid); break;
		case ��������: monster = new Cyndaquil(uuid); break;
		case ���̹�: monster = new Cyndaquil(uuid); break;
		
		case ������: monster = new Totodile(uuid); break;
		case ��������: monster = new Totodile(uuid); break;
		case ��ũ�δ���: monster = new Totodile(uuid); break;
		
		
		default: return null;
		}
		
		return monster;
	}
	
	public MyMonster(String uuid) {
		this.uuid = uuid;
		monsterName = "������";
		dirPath = MonsterSkyblock.plugin.getDataFolder()+"/MonsterPlayers/";
		
		applyEvolve1();
		//Bukkit.getServer().getPluginManager().registerEvents(myMonsterEvent, MonsterSkyblock.plugin);
		if(monsterMap.containsKey(uuid)) {
			MyMonster prevMonster = monsterMap.get(uuid);
			prevMonster.delete();
		}
		monsterMap.put(uuid, this);	
		hotBarBackupInv = Bukkit.createInventory(null, 9);
		skillSlotInv = Bukkit.createInventory(null, 9);
		
		Player p = Bukkit.getPlayer(UUID.fromString(uuid));
		if(p != null) {
			monsterPlayer = p;
			sendMessage("����� ���� "+monsterName+"�Դϴ�.");		
		}
		
		skillBookIcon = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta meta = skillBookIcon.getItemMeta();
		meta.setDisplayName("��f[ ��b��� ���� ��f]");
		skillBookIcon.setItemMeta(meta);
		
		monsterInstance = this;
		
		skillUpdateTimer();
		
		updateStatus();
	}
	
	public void delete() {
		monsterPlayer.setGameMode(GameMode.SURVIVAL);
		File file = new File(dirPath, uuid);
		if(file != null && file.exists()) file.delete();
		monsterMap.remove(uuid);
		this.uuid = null;
		this.monsterPlayer = null;
		freeMonster();
		MyUtility.unregisterEvents(myMonsterEvent);
	}
	
	public boolean isDead() {
		return this.isDead;
	}
	
	public boolean isSleep() {
		return this.isSleep;
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public void sendMessage(String str) {
		if(monsterPlayer != null) {
			monsterPlayer.sendMessage(MS+str);
		}
	}
	
	public void sendTitle(String mainTitle, String subTitle, int time) {
		if(monsterPlayer != null) {
			monsterPlayer.sendTitle(mainTitle, subTitle, 0, time, 0);
		}
	}
	
	public void sendSound(Sound sound) {
		if(monsterPlayer != null) {
			monsterPlayer.playSound(monsterPlayer.getLocation(), sound, 2.0f, 1.0f);
		}
	}
	
	public String getMonsterName() {
		return this.monsterName;
	}
	
	public ArrayList<String> getStatusLore(){
		ArrayList<String> tmpList = new ArrayList<String>(statusLore.size());
		tmpList.addAll(statusLore);
		return tmpList;
	}
	
	public void updateStatus() {
		statusLore.clear();
		statusLore.add("");
		statusLore.add("��a- ��2��ȭ ����: ��2"+evolveLv);
		
		if(isDead())
			statusLore.add("��a- ��2ȸ�� ��");
		else 
			statusLore.add("��a- ��2�ൿ ����");
		
		if(isSleep())
			statusLore.add("��a- ��2��� ��");
		else
			statusLore.add("��a- ��2��ȯ��");
		
		statusLore.add("");
	}
	
	public boolean setTrainer(MonsterTrainer trainer) {
		if(this.trainer != null) { //�̹� �����ڰ� �ִٸ�
			return false;
		} else {
			this.trainer = trainer;
			if(trainer != null) sendMessage("���� Ʈ���̳� ��e"+trainer.getName()+"��f�� �����Դϴ�.");
			enterToBall();
			save();
			return true;
		}
	}
	
	public MonsterTrainer getTrainer() {
		return this.trainer;
	}
	
	public void freeMonster() {
		this.trainer = null;
		
		save();
	}
	
	public void die() {
		if(deadTimer_id != 0) Bukkit.getScheduler().cancelTask(deadTimer_id);
		isDead = true;
		deadTimer_time = 300; //������ 5�а� ��Ȱ �Ұ���
		sendMessage("����ϼ̽��ϴ�. ��e"+deadTimer_time+"�� ��f�Ŀ� ȸ���� �Ϸ��մϴ�.");
		updateStatus();
		deadTimer_id = Bukkit.getScheduler().scheduleSyncRepeatingTask(MonsterSkyblock.plugin, new Runnable() {
			public void run() {
				if(--deadTimer_time <= 0) {
					Bukkit.getScheduler().cancelTask(deadTimer_id);
					revive();
				} else {
					if(deadTimer_time % 60 == 0) {
						//sendMessage("��e"+(deadTimer_time/60)+"�� ��f�Ŀ� ȸ���� �Ϸ��մϴ�.");
					} else if(deadTimer_time == 10) {
						//sendMessage("��e"+deadTimer_time+"�� ��f�Ŀ� ȸ���� �Ϸ��մϴ�.");
						
					}
					sendTitle("", "��7��lȸ�� �Ϸ���� ��a��l"+deadTimer_time+"��", 30);
				}
			}
		}, 0l, 20l);
	}
	
	public void revive() {
		isDead = false;
		sendTitle("��a��lȸ�� �Ϸ�", "", 80);
		sendMessage("ȸ���� �Ϸ��߽��ϴ�. �ٽ� Ȱ���� �� �־��!");
		updateStatus();
	}
	
	public void enterToBall() {
		isSleep = true;
		sendTitle("��b��l���ƿ�!", "��7��lƮ���̳ʰ� ����� �����Խ��ϴ�.", 80);
		sendMessage("Ʈ���̳ʰ� ����� ���ͺ��� �ǵ��Ƚ��ϴ�.");
		if(monsterPlayer != null) {
			monsterPlayer.setGameMode(GameMode.SPECTATOR);
			monsterPlayer.getWorld().playSound(monsterPlayer.getLocation(), Sound.BLOCK_NOTE_BELL, 0.5f, 0.5f);
			monsterPlayer.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, monsterPlayer.getLocation(), 40, 0.5F, 0.5f, 0.5f, 0.2f);
		}
		if(isBattleMode) {
			toggleBattleMode();
		}
	}
	
	public boolean spawn(Location loc) {
		if(isDead()) return false;
		if(monsterPlayer != null) {
			isSleep = false;
			sendTitle("��b��l����!", "��7��lƮ���̳ʰ� ����� �����½��ϴ�.", 80);
			sendMessage("Ʈ���̳ʰ� ����� �����½��ϴ�.");
			monsterPlayer.teleport(loc);
			monsterPlayer.getWorld().playSound(monsterPlayer.getLocation(), Sound.BLOCK_NOTE_BELL, 0.5f, 0.5f);
			monsterPlayer.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, monsterPlayer.getLocation(), 40, 0.5F, 0.5f, 0.5f, 0.2f);
			monsterPlayer.setGameMode(GameMode.SURVIVAL);
			return true;
		}else return false;
	}
	
	public boolean addSkill(MySkill skill) {
		if(skillList.size() >= MAX_SKILL_SIZE) {
			sendMessage("�̹� "+MAX_SKILL_SIZE+"���� ����� ���� �־� �� �̻� ��� �� �����ϴ�.");
			return false;
		}
		
		if(skill == null) {
			sendMessage("��� ���� null �Դϴ�.");
			return false;
		}
		
		skillList.add(skill);
		skill.setOwner(this); //��ų ����
		
		return true;
	}
	
	public void evolveUp() {
		evolveLv += 1;
		if(evolveLv > maxEvolve){
			evolveLv = maxEvolve;
		}else {
			sendTitle("��b��l��ȭ!", "��7��l��ȭ ������ 1�ܰ� ����߽��ϴ�.", 80);
			sendMessage("��ȭ!!!");
			if(monsterPlayer != null) {
				monsterPlayer.getWorld().playSound(monsterPlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 1.0f);
				monsterPlayer.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, monsterPlayer.getLocation(), 40, 0.5F, 0.5f, 0.5f, 0.2f);
			}
					
			if(evolveLv == 2) applyEvolve2();
			else if(evolveLv == 3) applyEvolve2();
		}
		updateStatus();
		save();
	}
	
	public boolean isBattleMode() {
		return this.isBattleMode;
	}
	
	public int getMaxEvolveLv() {
		return this.maxEvolve;
	}
	
	public int getEvolveLv() {
		return this.evolveLv;
	}
	
	public boolean save() {
		File file = new File(dirPath, uuid+".data");
		try {
			FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
			
			fileConfig.set("UUID", uuid); //������
			
			fileConfig.set("evolveLv", evolveLv);
			fileConfig.set("monsterType", monsterType.toString());
			fileConfig.set("isSleep", isSleep);
			fileConfig.set("trainerUUID", trainer == null ? "null" : trainer.getUUID());
			
			ArrayList<String> tmpList = new ArrayList<String>();
			for(MySkill skill : skillList) {
				tmpList.add(skill.getSkillName().toString());
			}
			
			fileConfig.set("skillList", tmpList);
			
			fileConfig.save(file);
		}catch(Exception e) {
			MyUtility.printLog(uuid+"�� ������ ������ ���� �߻�");
			return false;
		}
		return true;
	}
	
	public void toggleBattleMode() {
		updateSkillSlot();
		if(monsterPlayer == null) return;
		if(hotBarBackupInv == null) return;
		if(isSleep) return;
		if(!isBattleMode) {
			isBattleMode = true;
			for(int i = 0; i < 9; i++) {
				Inventory inv = monsterPlayer.getInventory();
				hotBarBackupInv.setItem(i, inv.getItem(i));
			}
			setSkillHotBar();
			sendMessage("���� ����!");
			monsterPlayer.getInventory().setHeldItemSlot(8);
			updateSkillSlot();
			sendSound(Sound.BLOCK_CHEST_OPEN);
		} else {
			isBattleMode = false;
			for(int i = 0; i < 9; i++) {
				Inventory inv = monsterPlayer.getInventory();
				inv.setItem(i, hotBarBackupInv.getItem(i));
			}
			sendMessage("���� ���� ����");
			sendSound(Sound.BLOCK_CHEST_CLOSE);
		}
	}
	
	
	public void setSkillHotBar() {
		if(monsterPlayer == null) return;
		if(monsterPlayer.isOnline()) {
			Inventory inv = monsterPlayer.getInventory();
			for(int i = 0; i < 9; i++)
				inv.setItem(i, skillSlotInv.getItem(i));
		}
		
	}
	
	public void updateSkillSlot() {
		skillSlotInv.clear();
		for(int i = 0; i < skillList.size(); i++) {
			MySkill skill = skillList.get(i);
			if(skill == null) continue;
			ItemStack skillIcon = skillBookIcon.clone();
			ItemMeta meta = skillIcon.getItemMeta();
			if(meta.hasDisplayName()) {
				String displayName = meta.getDisplayName();
				
				if(skill.isCooldown()) {
					meta.setDisplayName("��f[ ��4"+skill.getSkillName().toString()+" ��f]");
				} else {
					if(displayName.equals("��f[ ��4"+skill.getSkillName().toString()+" ��f]")) {
						sendMessage(skill.getSkillName()+" ����� �غ�ƽ��ϴ�.");
						sendSound(Sound.BLOCK_NOTE_PLING);
					} 
					meta.setDisplayName("��f[ ��b"+skill.getSkillName().toString()+" ��f]");
				}
			}
			meta.setLore(skill.getSkillLore());
			skillIcon.setItemMeta(meta);
			
			if(!skill.isCooldown()) {			
				
				skillIcon.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
			} else {
				skillIcon.removeEnchantment(Enchantment.DAMAGE_ALL);
			}
			
			skillSlotInv.setItem(i, skillIcon);
		}
		//Bukkit.getLogger().info(skillSlotInv.getItem(0).toString());
	}
	
	public void skillUpdateTimer() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(MonsterSkyblock.plugin, new Runnable() {
			public void run() {
				if(isBattleMode) {
					updateSkillSlot();
				}
			}
		}, 0l, 20l);
	}
	
	public void useSkill(int slotCnt) {
		if(slotCnt >= 0 && slotCnt < skillList.size()) { //������� �����
			MySkill skill = skillList.get(slotCnt);
			skill.mainSkill();
		}
	}
	
	@Deprecated
	public boolean load() {
		File file = new File(dirPath, uuid+".data");
		if(file == null || !file.exists()) return false;
		try {
			String fileName = file.getName();
			fileName.replace(".data", "");
			FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
			evolveLv = fileConfig.getInt("evolveLv");
			monsterType = MonsterType.valueOf(fileConfig.getString("monsterType"));
			isSleep = fileConfig.getBoolean("isSleep");
			String trainerUUID = fileConfig.getString("trainerUUID");
			MonsterTrainer trainer = MonsterTrainer.trainerMap.get(trainerUUID);
			this.setTrainer(trainer);
			
			List<String> tmpList = new ArrayList<String>();
			tmpList = fileConfig.getStringList("skillList");
			for(String name : tmpList) {
				SkillName skillName = SkillName.valueOf(name);
				if(skillName != null) {
					MySkill skill = MySkill.createSkillClassFromName(skillName);
					if(skill != null) {
						skillList.add(skill);
					}
				}
			}
			
		}catch(Exception e) {
			MyUtility.printLog(uuid+"�� ������ �ε��� ���� �߻�");
			return false;
		}
		return true;
		
	}
	
	public abstract void applyEvolve1();
	public abstract void applyEvolve2();
	public abstract void applyEvolve3();
	
	protected class MyMonsterEvent implements Listener{
		
		@EventHandler
		public void onJoin(PlayerJoinEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				monsterPlayer = p;
				if(isSleep) {
					enterToBall();
				}
			}
		}
		
		@EventHandler
		public void onQuit(PlayerQuitEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				monsterPlayer = null;
				if(!isSleep) {
					if(trainer != null) {
						trainer.returnMonster(monsterInstance);
					}
				}
			}
		}
		
		@EventHandler
		public void onBreakBlock(BlockBreakEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				if(evolveLv == 1) { //���� 1�̸� �� �ı� ����
					sendTitle("", "��c��l��ȭ ������ ���� �ش� �ൿ�� �Ұ����մϴ�.", 80);
					e.setCancelled(true);
				}
			}
		}
		
		@EventHandler
		public void onPlaceBlock(BlockPlaceEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				if(evolveLv == 1) { //���� 1�̸� �� ��ġ ����
					sendTitle("", "��c��l��ȭ ������ ���� �ش� �ൿ�� �Ұ����մϴ�.", 80);
					e.setCancelled(true);
				}
			}
		}
		
		@EventHandler
		public void onCraftSomething(CraftItemEvent e) {
			if(e.getWhoClicked() instanceof Player) {
				Player p = (Player)e.getWhoClicked();
						if(p.getUniqueId().toString().equals(uuid)) {
							if(evolveLv == 1) { //���� 1�̸� �� ��ġ ����
								sendTitle("", "��c��l��ȭ ������ ���� �ش� �ൿ�� �Ұ����մϴ�.", 80);
								e.setCancelled(true);
							}
						}
			}		
		}
		
		@EventHandler
		public void onEmptyBucket(PlayerBucketEmptyEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				if(evolveLv == 1) { //���� 1�̸� �� ��ġ ����
					sendTitle("", "��c��l��ȭ ������ ���� �ش� �ൿ�� �Ұ����մϴ�.", 80);
					e.setCancelled(true);
				}
			}
		}
		
		@EventHandler
		public void onFillBucket(PlayerBucketFillEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				if(evolveLv == 1) { //���� 1�̸� �� ��ġ ����
					sendTitle("", "��c��l��ȭ ������ ���� �ش� �ൿ�� �Ұ����մϴ�.", 80);
					e.setCancelled(true);
				}
			}
		}
		
		@EventHandler
		public void onPlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				if(p.isSneaking()) {
					toggleBattleMode();	
				}
			}
		}
		
		@EventHandler
		public void onChangeHeldItem(PlayerItemHeldEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)){
				if(isBattleMode) {
					if(e.getPreviousSlot() != e.getNewSlot()) {
						int newSlot = e.getNewSlot();
						e.setCancelled(true);
						if(newSlot < skillList.size()) {
							ItemStack heldItem = p.getInventory().getItem(newSlot);
							if(heldItem != null) {
								useSkill(newSlot);
							}
						}
					}
				}			
			}	
		}
		
		@EventHandler
		public void onPickUpItem(PlayerPickupItemEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)){
				if(isBattleMode) {
					e.setCancelled(true);
				}
			}
		}
		
		@EventHandler
		public void onDropItem(PlayerDropItemEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)){
				if(isBattleMode) {
					e.setCancelled(true);
				}
			}
		}
		
		@EventHandler
		public void onClickInventory(InventoryClickEvent e) {
			if(e.getWhoClicked() instanceof Player) {
				Player p = (Player) e.getWhoClicked();
				if(p.getUniqueId().toString().equals(uuid)){
					if(isBattleMode) {
						e.setCancelled(true);
					}
				}
			}	
		}
		
		@EventHandler
		public void onPlayerDeath(PlayerDeathEvent e) {
			Player p = e.getEntity();
			if(p.getUniqueId().toString().equals(uuid)){
				die();
			}
		}
		
		@EventHandler
		public void onRespawn(PlayerRespawnEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)){
				if(trainer != null) {
					trainer.returnMonster();
					trainer.sendMessage("���Ͱ� �����Ͽ� �����Խ��ϴ�.");
				}
			}
		}
		
	}
	
}
