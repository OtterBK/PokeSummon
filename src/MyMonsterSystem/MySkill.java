package MyMonsterSystem;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import MyMonsterSystem.Skill.LavaBucket;
import MyMonsterSystem.Skill.SkillName;
import MyMonsterSystem.Skill.ThrowSeeds;
import MyMonsterSystem.Skill.WaterBucket;

public abstract class MySkill {
	
	protected SkillName skillName;
	protected String ownerUUID;
	protected ArrayList<String> skillLore = new ArrayList<String>();
	protected long lastUseTime;
	protected long cooldownTime;
	protected MyMonster ownerMonster;
	
	public MySkill() {
		//initSkillLore();
	}
	
	public void setOwner(MyMonster monster) {
		this.ownerMonster = monster;
		if(monster != null) {
			this.ownerUUID = monster.getUUID();
		}
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(UUID.fromString(ownerUUID));
	}
	
	public void sendMessage(String str) {
		Player p = getPlayer();
		if(p != null) {
			p.sendMessage("¡×f[ ¡×b½ºÅ³ ¡×f] "+str);
		}
	}
	
	public void sendTitle(String mainTitle, String subTitle, int time) {
		Player p = getPlayer();
		if(p != null) {
			p.sendTitle(mainTitle, subTitle, 0, time, 0);
		}
	}
	
	public void sendSound(Sound sound) {
		Player p = getPlayer();
		if(p != null) {
			p.getWorld().playSound(p.getLocation(), sound, 1.0f, 1.0f);
		}
	}
	
	public SkillName getSkillName() {
		return this.skillName;
	}
	
	public ArrayList<String> getSkillLore(){
		return this.skillLore;
	}
	
	public void setCooldown() {
		long currentTime = System.currentTimeMillis();
		lastUseTime = currentTime;
	}
	
	public boolean isCooldown() {
		long currentTime = System.currentTimeMillis(); 
		if(lastUseTime + cooldownTime >= currentTime) {
			return true;
		} else return false;
	}
	
	public long getLeftCooldown() {
		long currentTime = System.currentTimeMillis();
		return (lastUseTime + cooldownTime) - currentTime;
	}
	
	public static MySkill createSkillClassFromName(SkillName skillName) {
		
		MySkill skill = null;
		
		switch(skillName) {
		
			case ¾¾»Ñ¸®±â: skill = new ThrowSeeds(); break;
			case ºÒ²É¼¼·Ê: skill = new LavaBucket(); break;
			case ¹°´ëÆ÷: skill = new WaterBucket(); break;
		
			default: return null;
			
		}
		
		return skill;
	}
	
	public abstract boolean mainSkill();
	public abstract void initSkillLore();
	
}
