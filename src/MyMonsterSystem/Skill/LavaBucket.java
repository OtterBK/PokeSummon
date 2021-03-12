package MyMonsterSystem.Skill;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import MyMonsterSystem.MySkill;
import Utility.MyUtility;

public class LavaBucket extends MySkill{
	
	
	public LavaBucket() {
		skillName = SkillName.불꽃세례;
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
		sendTitle("", "§c§l용암을 생성해냈습니다.", 60);
		
		return true;
	}
	
	@Override
	public void initSkillLore() {
		skillLore.clear();
		skillLore.add("");
		skillLore.add("§7- §bPassive");
		skillLore.add("§c  용암 양동이를 사용할 수 있습니다.");
		skillLore.add("");
		skillLore.add("§7- §bActive");
		skillLore.add("§c  용암 양동이를 1개 소환합니다.");
		skillLore.add("");
		skillLore.add("§7- §a재사용 대기시간: §c"+cooldownTime/1000);
		skillLore.add("");
	}
	
}
