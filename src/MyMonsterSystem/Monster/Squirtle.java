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
		
		totalSkillMap.put(SkillName.�����ġ��, 0);
		totalSkillMap.put(SkillName.������, 3);
		totalSkillMap.put(SkillName.���������, 10);
		totalSkillMap.put(SkillName.���̵������, 40);
	}

	@Override
	public void applyEvolve1() {
		this.monsterName = "���α�";
		if(!addSkill(MySkill.createSkillClassFromName(SkillName.������))) {
			sendMessage("��� "+SkillName.������.toString()+"�� ����� ���߽��ϴ�.");
		}
		
	}

	@Override
	public void applyEvolve2() {
		this.monsterName = "��Ϻα�";
		
	}

	@Override
	public void applyEvolve3() {
		this.monsterName = "�źϿ�";
		
	}
	
private class SquirtleEvent extends MyMonsterEvent{
		
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
