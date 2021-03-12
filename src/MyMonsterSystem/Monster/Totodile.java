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

	public Totodile(String uuid) { //������
		super(uuid);
		
		myMonsterEvent = new TotodileEvent();
		Bukkit.getServer().getPluginManager().registerEvents(myMonsterEvent, MonsterSkyblock.plugin);
		totalSkillMap.put(SkillName.�����ġ��, 0);
		totalSkillMap.put(SkillName.������, 3);
		totalSkillMap.put(SkillName.���������, 9);
		totalSkillMap.put(SkillName.���̵������, 27);
	}

	@Override
	public void applyEvolve1() {
		this.monsterName = "������";
		this.monsterType = MonsterType.������;
		if(!addSkill(MySkill.createSkillClassFromName(SkillName.�����ġ��))) {
			sendMessage("��� "+SkillName.�����ġ��.toString()+"�� ����� ���߽��ϴ�.");
		}
	}

	@Override
	public void applyEvolve2() {
		this.monsterName = "��������";
		this.monsterType = MonsterType.��������;
		
	}

	@Override
	public void applyEvolve3() {
		this.monsterName = "��ũ�δ���";
		this.monsterType = MonsterType.��ũ�δ���;
		
	}
	
	private class TotodileEvent extends MyMonsterEvent{
		
		
		@Override @EventHandler
		public void onEmptyBucket(PlayerBucketEmptyEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				if(evolveLv == 1) { //���� 1�̸� �� ��ġ ����
					if(e.getBucket() != Material.WATER_BUCKET) {
						sendTitle("", "��c��l��ȭ ������ ���� �ش� �ൿ�� �Ұ����մϴ�.", 80);
						e.setCancelled(true);
					}
				}
			}
		}
		
		@Override @EventHandler
		public void onFillBucket(PlayerBucketFillEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				if(evolveLv == 1) { //���� 1�̸� �� ��ġ ����
					if(e.getBucket() != Material.BUCKET) {
						if(e.getBlockClicked().getType() == Material.WATER || e.getBlockClicked().getType() == Material.STATIONARY_WATER) {
							
						} else {
							sendTitle("", "��c��l��ȭ ������ ���� �ش� �ൿ�� �Ұ����մϴ�.", 80);
							e.setCancelled(true);
						}				
					}
				}
			}
		}
		
		
		
	}
	
}
