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

public class Bulbasaur extends MyMonster{

	public Bulbasaur(String uuid) {
		super(uuid);
		
		myMonsterEvent = new BulbasaurEvent();
		Bukkit.getServer().getPluginManager().registerEvents(myMonsterEvent, MonsterSkyblock.plugin);
		totalSkillMap.put(SkillName.몸통박치기, 0);
		totalSkillMap.put(SkillName.덩굴채찍, 3);
		totalSkillMap.put(SkillName.씨뿌리기, 9);
		totalSkillMap.put(SkillName.광합성, 27);
	}

	@Override
	public void applyEvolve1() {
		this.monsterName = "이상해씨";
		this.monsterType = MonsterType.이상해씨;
		if(!addSkill(MySkill.createSkillClassFromName(SkillName.씨뿌리기))) {
			sendMessage("기술 "+SkillName.씨뿌리기.toString()+"를 배우지 못했습니다.");
		}
	}

	@Override
	public void applyEvolve2() {
		this.monsterName = "이상해풀";
		this.monsterType = MonsterType.이상해풀;
		
	}

	@Override
	public void applyEvolve3() {
		this.monsterName = "이상해꽃";
		this.monsterType = MonsterType.이상해꽃;
		
	}
	
	private class BulbasaurEvent extends MyMonsterEvent{
		
		
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
