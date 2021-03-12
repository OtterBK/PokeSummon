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
		totalSkillMap.put(SkillName.�����ġ��, 0);
		totalSkillMap.put(SkillName.����ä��, 3);
		totalSkillMap.put(SkillName.���Ѹ���, 9);
		totalSkillMap.put(SkillName.���ռ�, 27);
	}

	@Override
	public void applyEvolve1() {
		this.monsterName = "�̻��ؾ�";
		this.monsterType = MonsterType.�̻��ؾ�;
		if(!addSkill(MySkill.createSkillClassFromName(SkillName.���Ѹ���))) {
			sendMessage("��� "+SkillName.���Ѹ���.toString()+"�� ����� ���߽��ϴ�.");
		}
	}

	@Override
	public void applyEvolve2() {
		this.monsterName = "�̻���Ǯ";
		this.monsterType = MonsterType.�̻���Ǯ;
		
	}

	@Override
	public void applyEvolve3() {
		this.monsterName = "�̻��ز�";
		this.monsterType = MonsterType.�̻��ز�;
		
	}
	
	private class BulbasaurEvent extends MyMonsterEvent{
		
		
		@Override @EventHandler
		public void onPlaceBlock(BlockPlaceEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				if(evolveLv == 1) { //���� 1�̸� �� ��ġ ����
					Material blockMt = e.getBlock().getType();
					
					if(blockMt == Material.CROPS
							|| blockMt == Material.POTATO
							|| blockMt == Material.CARROT
							|| blockMt == Material.PUMPKIN_STEM
							|| blockMt == Material.SAPLING
							|| blockMt == Material.MELON_STEM
							|| blockMt == Material.SOIL) {
						//��ġ ����
					} else {
						sendTitle("", "��c��l��ȭ ������ ���� �ش� �ൿ�� �Ұ����մϴ�...", 80);
						e.setCancelled(true);
					}			
				}
			}
		}
		
		
		
	}
	
}
