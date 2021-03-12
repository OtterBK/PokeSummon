package MyMonsterSystem.Skill;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import MyMonsterSystem.MySkill;

public class ThrowSeeds extends MySkill{
	
	public ThrowSeeds() {
		skillName = SkillName.씨뿌리기;
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
		sendTitle("", "§c§l가루를 생성해냈습니다.", 60);
		
		return true;
	}
	
	@Override
	public void initSkillLore() {
		skillLore.clear();
		skillLore.add("");
		skillLore.add("§7- §bPassive");
		skillLore.add("§c  작물을 심을 수 있습니다.");
		skillLore.add("§c  괭이를 사용할 수 있습니다.");
		skillLore.add("");
		skillLore.add("§7- §a재사용 대기시간: §c"+cooldownTime/1000);
		skillLore.add("");
	}
	
}
