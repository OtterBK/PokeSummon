package MyMonsterSystem.Monster;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

import MonsterSkyblock.MonsterSkyblock;
import MyMonsterSystem.MyMonster;
import MyMonsterSystem.MySkill;
import MyMonsterSystem.Skill.SkillName;

public class Charmander extends MyMonster{

	public Charmander(String uuid) {
		super(uuid);
		
		myMonsterEvent = new CharmanderEvent();
		Bukkit.getServer().getPluginManager().registerEvents(myMonsterEvent, MonsterSkyblock.plugin);
		totalSkillMap.put(SkillName.������, 0);
		totalSkillMap.put(SkillName.�Ҳɼ���, 3);
		totalSkillMap.put(SkillName.ȭ�����, 10);
		totalSkillMap.put(SkillName.����, 40);
	}

	@Override
	public void applyEvolve1() {
		this.monsterName = "���̸�";
		this.monsterType = MonsterType.���̸�;
		if(!addSkill(MySkill.createSkillClassFromName(SkillName.�Ҳɼ���))) {
			sendMessage("��� "+SkillName.�Ҳɼ���.toString()+"�� ����� ���߽��ϴ�.");
		}
	}

	@Override
	public void applyEvolve2() {
		this.monsterName = "���ڵ�";
		this.monsterType = MonsterType.���ڵ�;
	}

	@Override
	public void applyEvolve3() {
		this.monsterName = "���ڸ�";
		this.monsterType = MonsterType.���ڸ�;
	}
	
	private class CharmanderEvent extends MyMonsterEvent{
		
		@Override @EventHandler
		public void onEmptyBucket(PlayerBucketEmptyEvent e) {
			Player p = e.getPlayer();
			if(p.getUniqueId().toString().equals(uuid)) {
				if(evolveLv == 1) { //���� 1�̸� �� ��ġ ����
					if(e.getBucket() != Material.LAVA_BUCKET) {
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
						if(e.getBlockClicked().getType() == Material.LAVA || e.getBlockClicked().getType() == Material.STATIONARY_LAVA) {
							
						} else {
							sendTitle("", "��c��l��ȭ ������ ���� �ش� �ൿ�� �Ұ����մϴ�.", 80);
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
				if(evolveLv == 1) { //���� 1�̸� �� �ı� ����
					if(e.getBlock().getType() == Material.LAVA || e.getBlock().getType() == Material.STATIONARY_LAVA) {
						
					} else {
						sendTitle("", "��c��l��ȭ ������ ���� �ش� �ൿ�� �Ұ����մϴ�.", 80);
						e.setCancelled(true);
					}
					
				}
			}
		}
		
		
	}

}
