package MyMonsterSystem.Monster;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

import MonsterSkyblock.MonsterSkyblock;
import MyMonsterSystem.MyMonster;
import MyMonsterSystem.MySkill;
import MyMonsterSystem.Skill.SkillName;

public class Squirtle extends MyMonster{

	public Squirtle(String uuid) {
		super(uuid);
		myMonsterEvent = new SquirtleEvent();
		Bukkit.getServer().getPluginManager().registerEvents(myMonsterEvent, MonsterSkyblock.plugin);
		
		totalSkillMap.put(SkillName.몸통박치기, 0);
		totalSkillMap.put(SkillName.물대포, 3);
		totalSkillMap.put(SkillName.아쿠아테일, 10);
		totalSkillMap.put(SkillName.하이드로펌프, 40);
	}

	@Override
	public void applyEvolve1() {
		this.monsterName = "꼬부기";
		if(!addSkill(MySkill.createSkillClassFromName(SkillName.물대포))) {
			sendMessage("기술 "+SkillName.물대포.toString()+"를 배우지 못했습니다.");
		}
		
	}

	@Override
	public void applyEvolve2() {
		this.monsterName = "어니부기";
		
	}

	@Override
	public void applyEvolve3() {
		this.monsterName = "거북왕";
		
	}
	
private class SquirtleEvent extends MyMonsterEvent{
		
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
