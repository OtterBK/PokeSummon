package MonsterSkyblock;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import MyMonsterSystem.MonsterTrainer;
import MyMonsterSystem.MyMonster;
import MyMonsterSystem.Monster.Bulbasaur;
import MyMonsterSystem.Monster.Charmander;
import MyMonsterSystem.Monster.Chikorita;
import MyMonsterSystem.Monster.Cyndaquil;
import MyMonsterSystem.Monster.Squirtle;
import MyMonsterSystem.Monster.Totodile;

public class MonsterSkyblock extends JavaPlugin implements Listener{

	public static JavaPlugin plugin;
	
	private final String MS = "§f[ §bBMS §f] ";
	
	public void onEnable() {
		plugin = this;
		
		
		Bukkit.getLogger().info("■■■■■■■■■■■■■■■■■■■■■");
		Bukkit.getLogger().info("■□□□□□□■■■■■□□□□□□□■■");
		Bukkit.getLogger().info("■□■■■■■□■■■■■■■■■■■□■");
		Bukkit.getLogger().info("■□■■■■■□■■■■■■■■■□□■■");
		Bukkit.getLogger().info("■□■■■■■□■■■■■■■■□□■■■");
		Bukkit.getLogger().info("■□□□□□□■■■■■■■■□□■■■■");
		Bukkit.getLogger().info("■□■■■■■□■■■■■■□□■■■■■");
		Bukkit.getLogger().info("■□■■■■■□■■■■■□□■■■■■■");
		Bukkit.getLogger().info("■□■■■■■□■■■■□■■■■■■■■");
		Bukkit.getLogger().info("■□□□□□□■■■■■■□□□□□□■■");
		Bukkit.getLogger().info("■■■■■■■■■■■■■■■■■■■■■");
		
		int loadCnt = MonsterTrainer.loadAllData();
		Bukkit.getLogger().info(loadCnt+"개의 트레이너 데이터 로드됨");
		loadCnt = MyMonster.loadAllData();
		Bukkit.getLogger().info(loadCnt+"개의 몬스터 데이터 로드됨");
		
		Bukkit.getLogger().info("[BMonsterSkyBlock] BMonsterSkyBlock 플러그인 로드됨");
		
	}
	
	public void onDisable() {
		Bukkit.getLogger().info("[BMonsterSkyBlock] BMonsterSkyBlock 플러그인 언로드됨");
		int saveCnt = 0;
		for(MonsterTrainer trainer : MonsterTrainer.trainerMap.values()) {
			trainer.save();
			saveCnt += 1;
		}
		Bukkit.getLogger().info("[BMonsterSkyBlock] "+saveCnt+"개의 트레이너 데이터 저장됨");
		saveCnt = 0;
		for(MyMonster monster : MyMonster.monsterMap.values()) {
			monster.save();
			saveCnt += 1;
		}
		Bukkit.getLogger().info("[BMonsterSkyBlock] "+saveCnt+"개의 몬스터 데이터 저장됨");
	}
	
	public boolean onCommand(CommandSender talker, Command cmd, String string, String[] args) {
		if(talker instanceof Player) {
			Player p = (Player) talker;
			if(string.equals("bms") && p.isOp()) {
				if(args.length == 0) {
					helpCommands(p);
				} else if(args.length >= 1) {
					if(args[0].equals("set")) {
						if(args.length >= 2) {
							String target = args[1];
							if(args.length >= 3) {
								String roleName = args[2];
								Reason reason = setRole(target, roleName);
								if(reason == Reason.INVAILD_TARGET) {
									p.sendMessage(MS+"존재하지 않는 대상입니다.");
								} else if(reason == Reason.INVAILD_ROLE) {
									p.sendMessage(MS+"존재하지 않는 역할입니다.");
								} else if(reason == Reason.SUCCESS) {
									p.sendMessage(MS+"역할 부여 성공.");
								}
							} else {
								p.sendMessage(MS+"적용할 몬스터 이름을 적어주세요. 예) /bms set Steve 파이리");
							}
						} else {
							p.sendMessage(MS+"적용할 대상을 적어주세요. 예) /bms set Steve 파이리");
						}
					} else if(args[0].equals("clear")){
						if(args.length >= 2) {
							String target = args[1];
							Reason reason = clearRole(target);
							if(reason == Reason.INVAILD_ROLE) {
								p.sendMessage(MS+target+"은 이미 역할을 갖고 있지 않습니다.");
							} else if(reason == Reason.SUCCESS) {
								p.sendMessage(MS+target+"의 역할을 해제했습니다.");
							} else if(reason == Reason.INVAILD_TARGET) {
								p.sendMessage(MS+target+"대상의 정보를 찾을 수 없습니다.");
							}
						} else {
							p.sendMessage(MS+"역할을 해제할 대상을 적어주세요. 예) /bms remove Steve");
						}
					} else if(args[0].equals("add")) {
						if(args.length >= 2) {
							String trainer = args[1];
							if(args.length >= 3) {
								String target = args[2];
								Reason reason = addMonster(trainer, target);
								if(reason == Reason.INVAILD_TARGET) {
									p.sendMessage(MS+"존재하지 않는 대상입니다.");
								} else if(reason == Reason.INVAILD_MONSTER) {
									p.sendMessage(MS+"존재하지 않는 몬스터 역할자입니다.");
								} else if(reason == Reason.INVAILD_TRAINER){
									p.sendMessage(MS+"존재하지 않는 트레이너 역할자입니다.");
								} else if(reason == Reason.SUCCESS) {
									p.sendMessage(MS+"몬스터 추가 성공.");
								}
							} else {
								p.sendMessage(MS+"적용할 몬스터 이름을 적어주세요. 예) /bms add Steve Norch");
							}
						} else {
							p.sendMessage(MS+"적용할 대상을 적어주세요. 예) /bms add Steve Norch");
						}
					} else if(args[0].equals("remove")) {
						if(args.length >= 2) {
							String trainer = args[1];
							if(args.length >= 3) {
								String target = args[2];
								Reason reason = removeMonster(trainer, target);
								if(reason == Reason.INVAILD_TARGET) {
									p.sendMessage(MS+"존재하지 않는 대상입니다.");
								} else if(reason == Reason.INVAILD_MONSTER) {
									p.sendMessage(MS+"존재하지 않는 몬스터 역할자입니다.");
								} else if(reason == Reason.INVAILD_TRAINER){
									p.sendMessage(MS+"존재하지 않는 트레이너 역할자입니다.");
								} else if(reason == Reason.SUCCESS) {
									p.sendMessage(MS+"몬스터 삭제 성공.");
								}
							} else {
								p.sendMessage(MS+"적용할 몬스터 이름을 적어주세요. 예) /bms remove Steve Norch");
							}
						} else {
							p.sendMessage(MS+"적용할 대상을 적어주세요. 예) /bms remove Steve Norch");
						}
					} else if(args[0].equals("debug")) {
						if(args.length >= 2) {
							String debugID = args[1];
							if(debugID.equals("1")) {
								for(Player tmpP : Bukkit.getOnlinePlayers()) {
									Bukkit.getServer().broadcastMessage(tmpP.getName()+" : "+tmpP.getUniqueId().toString());
								}
							} else if(debugID.equals("2")) {
								MonsterTrainer trainer = MonsterTrainer.trainerMap.get(p.getUniqueId().toString());
								trainer.doneAdvanced(null);
							}
						} else {
							p.sendMessage(MS+"적용할 대상을 적어주세요. 예) /bms remove Steve Norch");
						}
					}
				}
			}
		}
		return false;
	}
	
	public void helpCommands(Player p) {
		p.sendMessage("§7--------------------------------");
		p.sendMessage(MS+"/bms set <닉네임> <몬스터명/트레이너> - §e몬스터명 또는 트레이너 역할을 부여합니다.");
		p.sendMessage(MS+"/bms clear <닉네임> - §e역할을 해제합니다.");
		p.sendMessage(MS+"/bms add <트레이너> <닉네임> - §e트레이너에게 닉네임 몬스터를 부여합니다.");
		p.sendMessage(MS+"/bms remove <트레이너> <닉네임> - §e트레이너에게서 닉네임 몬스터를 삭제합니다.");
		p.sendMessage("§7--------------------------------");
	}
	
	public Reason setRole(String target, String roleName) {
		Player p = Bukkit.getPlayer(target);
		if(p == null) return Reason.INVAILD_TARGET;
		
		String uuid = p.getUniqueId().toString();
		switch(roleName) {
			case "트레이너": clearRole(target); new MonsterTrainer(uuid); break;
			case "이상해씨": clearRole(target); new Bulbasaur(uuid); break;
			case "파이리": clearRole(target); new Charmander(uuid); break;
			case "꼬부기": clearRole(target); new Squirtle(uuid); break;
			case "치코리타": clearRole(target); new Chikorita(uuid); break;
			case "브케인": clearRole(target); new Cyndaquil(uuid); break;
			case "리아코": clearRole(target); new Totodile(uuid); break;
			
			default: return Reason.INVAILD_ROLE;
		}
		
		return Reason.SUCCESS;
	}
	
	public Reason clearRole(String target) {
		OfflinePlayer p = Bukkit.getOfflinePlayer(target);
		if(p == null) return Reason.INVAILD_TARGET;
		String uuid = p.getUniqueId().toString();
		
		for(MonsterTrainer trainer : MonsterTrainer.trainerMap.values()) {
			if(trainer.getUUID().equals(uuid)) {
				trainer.delete();
				return Reason.SUCCESS;
			}
		}
		
		for(MyMonster monster : MyMonster.monsterMap.values()) {
			if(monster.getUUID().equals(uuid)) {
				if(monster.getTrainer() != null) {
					monster.getTrainer().removeMonster(uuid);	
				}
				monster.delete();
				return Reason.SUCCESS;
			}
		}
		
		return Reason.INVAILD_ROLE;
	}
	
	public Reason addMonster(String trainerName, String monsterName) {
		OfflinePlayer trainerP = Bukkit.getOfflinePlayer(trainerName);
		if(trainerP == null) return Reason.INVAILD_TARGET;
		String trainerUUID = trainerP.getUniqueId().toString();
		
		OfflinePlayer monsterP = Bukkit.getOfflinePlayer(monsterName);
		if(monsterP == null) return Reason.INVAILD_TARGET;
		String monsterUUID = monsterP.getUniqueId().toString();
		
		
		MonsterTrainer monsterTrainer = MonsterTrainer.trainerMap.get(trainerUUID);
		if(monsterTrainer == null) return Reason.INVAILD_TRAINER;
		
		MyMonster monster = MyMonster.monsterMap.get(monsterUUID);
		if(monster == null) return Reason.INVAILD_MONSTER;
		
		monsterTrainer.addMonster(monster);
		return Reason.SUCCESS;
	}
	
	public Reason removeMonster(String trainerName, String monsterName) {
		OfflinePlayer trainerP = Bukkit.getOfflinePlayer(trainerName);
		if(trainerP == null) return Reason.INVAILD_TARGET;
		String trainerUUID = trainerP.getUniqueId().toString();
		
		OfflinePlayer monsterP = Bukkit.getOfflinePlayer(monsterName);
		if(monsterP == null) return Reason.INVAILD_TARGET;
		String monsterUUID = monsterP.getUniqueId().toString();
		
		
		MonsterTrainer monsterTrainer = MonsterTrainer.trainerMap.get(trainerUUID);
		if(monsterTrainer == null) return Reason.INVAILD_TRAINER;
		
		MyMonster monster = MyMonster.monsterMap.get(monsterUUID);
		if(monster == null) return Reason.INVAILD_MONSTER;
		
		monsterTrainer.removeMonster(monsterUUID);
		
		return Reason.SUCCESS;
	}
	
	enum Reason{
		INVAILD_ROLE, SUCCESS, INVAILD_TARGET, INVAILD_TRAINER, INVAILD_MONSTER
	}
	
}
