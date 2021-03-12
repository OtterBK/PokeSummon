package MyMonsterSystem.Skill;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import MyMonsterSystem.MySkill;

public class ThrowSeeds extends MySkill{
	
	public ThrowSeeds() {
		skillName = SkillName.���Ѹ���;
		cooldownTime = 300*(1000);
		initSkillLore();
	}

	@Override
	public boolean mainSkill() {
		
		Player p = getPlayer();
		if(p == null) return false;
		
		if(isCooldown()) return false;
		
		setCooldown();
		p.getWorld().dropItem(p.getLocation().clone().add(0,1,0), new ItemStack(Material.INK_SACK, 12, (byte)15));
		sendTitle("", "��c��l���縦 �����س½��ϴ�.", 60);
		
		return true;
	}
	
	@Override
	public void initSkillLore() {
		skillLore.clear();
		skillLore.add("");
		skillLore.add("��7- ��bPassive");
		skillLore.add("��c  �۹��� ���� �� �ֽ��ϴ�.");
		skillLore.add("��c  ���̸� ����� �� �ֽ��ϴ�.");
		skillLore.add("");
		skillLore.add("��7- ��a���� ���ð�: ��c"+cooldownTime/1000);
		skillLore.add("");
	}
	
}
