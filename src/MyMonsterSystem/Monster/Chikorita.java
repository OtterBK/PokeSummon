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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import MonsterSkyblock.MonsterSkyblock;
import MyMonsterSystem.MyMonster;
import MyMonsterSystem.MySkill;
import MyMonsterSystem.Skill.SkillName;

public class Chikorita extends MyMonster{

	public Chikorita(String uuid) { //브케인
		super(uuid);
		
		myMonsterEvent = new ChikoritaEvent();
		Bukkit.getServer().getPluginManager().registerEvents(myMonsterEvent, MonsterSkyblock.plugin);
		totalSkillMap.put(SkillName.몸통박치기, 0);
		totalSkillMap.put(SkillName.덩굴채찍, 3);
		totalSkillMap.put(SkillName.씨뿌리기, 9);
		totalSkillMap.put(SkillName.광합성, 27);
	}

	@Override
	public void applyEvolve1() {
		this.monsterName = "치코리타";
		this.monsterType = MonsterType.치코리타;
		if(!addSkill(MySkill.createSkillClassFromName(SkillName.몸통박치기))) {
			sendMessage("기술 "+SkillName.몸통박치기.toString()+"를 배우지 못했습니다.");
		}
	}

	@Override
	public void applyEvolve2() {
		this.monsterName = "베이리프";
		this.monsterType = MonsterType.베이리프;
		
	}

	@Override
	public void applyEvolve3() {
		this.monsterName = "메가니움";
		this.monsterType = MonsterType.메가니움;
		
	}
	
	private class ChikoritaEvent extends MyMonsterEvent{
		
		
		@Override @EventHandler
		public void onPlaceBlock(BlockPlaceEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				if(evolveLv == 1) { //레벨 1이면 블럭 설치 금지
					Material blockMt = e.getBlock().getType();
					
					if(blockMt == Material.CROPS
							|| blockMt == Material.POTATO
							|| blockMt == Material.CARROT
							|| blockMt == Material.PUMPKIN_STEM
							|| blockMt == Material.SAPLING
							|| blockMt == Material.MELON_STEM
							|| blockMt == Material.SOIL) {
						//설치 가능
					} else {
						sendTitle("", "§c§l진화 레벨이 낮아 해당 행동은 불가능합니다...", 80);
						e.setCancelled(true);
					}			
				}
			}
		}
		
		
		
	}
	
}
