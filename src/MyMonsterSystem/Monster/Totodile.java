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

public class Totodile extends MyMonster{

	public Totodile(String uuid) { //브케인
		super(uuid);
		
		myMonsterEvent = new TotodileEvent();
		Bukkit.getServer().getPluginManager().registerEvents(myMonsterEvent, MonsterSkyblock.plugin);
		totalSkillMap.put(SkillName.몸통박치기, 0);
		totalSkillMap.put(SkillName.물대포, 3);
		totalSkillMap.put(SkillName.아쿠아테일, 9);
		totalSkillMap.put(SkillName.하이드로펌프, 27);
	}

	@Override
	public void applyEvolve1() {
		this.monsterName = "리아코";
		this.monsterType = MonsterType.리아코;
		if(!addSkill(MySkill.createSkillClassFromName(SkillName.몸통박치기))) {
			sendMessage("기술 "+SkillName.몸통박치기.toString()+"를 배우지 못했습니다.");
		}
	}

	@Override
	public void applyEvolve2() {
		this.monsterName = "엘리게이";
		this.monsterType = MonsterType.엘리게이;
		
	}

	@Override
	public void applyEvolve3() {
		this.monsterName = "장크로다일";
		this.monsterType = MonsterType.장크로다일;
		
	}
	
	private class TotodileEvent extends MyMonsterEvent{
		
		
		@Override @EventHandler
		public void onEmptyBucket(PlayerBucketEmptyEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				if(evolveLv == 1) { //레벨 1이면 블럭 설치 금지
					if(e.getBucket() != Material.WATER_BUCKET) {
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
						if(e.getBlockClicked().getType() == Material.WATER || e.getBlockClicked().getType() == Material.STATIONARY_WATER) {
							
						} else {
							sendTitle("", "§c§l진화 레벨이 낮아 해당 행동은 불가능합니다.", 80);
							e.setCancelled(true);
						}				
					}
				}
			}
		}
		
		
		
	}
	
}
