package MyMonsterSystem.Monster;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import MonsterSkyblock.MonsterSkyblock;
import MyMonsterSystem.MyMonster;
import MyMonsterSystem.MySkill;
import MyMonsterSystem.Skill.SkillName;

public class Cyndaquil extends MyMonster{

	public Cyndaquil(String uuid) { //브케인
		super(uuid);
		
		myMonsterEvent = new CyndaquilEvent();
		Bukkit.getServer().getPluginManager().registerEvents(myMonsterEvent, MonsterSkyblock.plugin);
		totalSkillMap.put(SkillName.할퀴기, 0);
		totalSkillMap.put(SkillName.불꽃세례, 3);
		totalSkillMap.put(SkillName.화염방사, 9);
		totalSkillMap.put(SkillName.비행, 27);
	}

	@Override
	public void applyEvolve1() {
		this.monsterName = "브케인";
		this.monsterType = MonsterType.브케인;
		if(!addSkill(MySkill.createSkillClassFromName(SkillName.할퀴기))) {
			sendMessage("기술 "+SkillName.할퀴기.toString()+"를 배우지 못했습니다.");
		}
	}

	@Override
	public void applyEvolve2() {
		this.monsterName = "마그케인";
		this.monsterType = MonsterType.마그케인;
		
	}

	@Override
	public void applyEvolve3() {
		this.monsterName = "블레이범";
		this.monsterType = MonsterType.블레이범;
		
	}
	
	private class CyndaquilEvent extends MyMonsterEvent{
		
		
		@Override @EventHandler
		public void onEmptyBucket(PlayerBucketEmptyEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				if(evolveLv == 1) { //레벨 1이면 블럭 설치 금지
					if(e.getBucket() != Material.LAVA_BUCKET) {
						sendTitle("", "§c§l진화 레벨이 낮아 해당 행동은 불가능합니다.", 80);
						e.setCancelled(true);
					}
				}
			}
		}
		
		@Override @EventHandler
		public void onFillBucket(PlayerBucketFillEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				if(evolveLv == 1) { //레벨 1이면 블럭 설치 금지
					if(e.getBucket() != Material.BUCKET) {
						if(e.getBlockClicked().getType() == Material.LAVA || e.getBlockClicked().getType() == Material.STATIONARY_LAVA) {
							
						} else {
							sendTitle("", "§c§l진화 레벨이 낮아 해당 행동은 불가능합니다.", 80);
							e.setCancelled(true);
						}				
					}
				}
			}
		}
		
		@Override @EventHandler
		public void onBreakBlock(BlockBreakEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				if(evolveLv == 1) { //레벨 1이면 블럭 파괴 금지
					if(e.getBlock().getType() == Material.LAVA || e.getBlock().getType() == Material.STATIONARY_LAVA) {
						
					} else {
						sendTitle("", "§c§l진화 레벨이 낮아 해당 행동은 불가능합니다.", 80);
						e.setCancelled(true);
					}
					
				}
			}
		}
		
		
		
	}
	
}
