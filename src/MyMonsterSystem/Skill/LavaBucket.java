package MyMonsterSystem.Skill;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import MyMonsterSystem.MySkill;
import Utility.MyUtility;

public class LavaBucket extends MySkill{
	
	
	public LavaBucket() {
		skillName = SkillName.�Ҳɼ���;
		cooldownTime = 300*(1000);
		initSkillLore();
	}

	@Override
	public boolean mainSkill() {
		
		Player p = getPlayer();
		if(p == null) return false;
		
		if(isCooldown()) return false;
		
		setCooldown();
		
		p.getWorld().dropItem(p.getLocation().clone().add(0,1,0), new ItemStack(Material.LAVA_BUCKET, 1));
		sendTitle("", "��c��l����� �����س½��ϴ�.", 60);
		
		return true;
	}
	
	@Override
	public void initSkillLore() {
		skillLore.clear();
		skillLore.add("");
		skillLore.add("��7- ��bPassive");
		skillLore.add("��c  ��� �絿�̸� ����� �� �ֽ��ϴ�.");
		skillLore.add("");
		skillLore.add("��7- ��bActive");
		skillLore.add("��c  ��� �絿�̸� 1�� ��ȯ�մϴ�.");
		skillLore.add("");
		skillLore.add("��7- ��a���� ���ð�: ��c"+cooldownTime/1000);
		skillLore.add("");
	}
	
}
