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
	
	private final String MS = "��f[ ��bBMS ��f] ";
	
	public void onEnable() {
		plugin = this;
		
		
		Bukkit.getLogger().info("����������������������");
		Bukkit.getLogger().info("����������������������");
		Bukkit.getLogger().info("����������������������");
		Bukkit.getLogger().info("����������������������");
		Bukkit.getLogger().info("����������������������");
		Bukkit.getLogger().info("����������������������");
		Bukkit.getLogger().info("����������������������");
		Bukkit.getLogger().info("����������������������");
		Bukkit.getLogger().info("����������������������");
		Bukkit.getLogger().info("����������������������");
		Bukkit.getLogger().info("����������������������");
		
		int loadCnt = MonsterTrainer.loadAllData();
		Bukkit.getLogger().info(loadCnt+"���� Ʈ���̳� ������ �ε��");
		loadCnt = MyMonster.loadAllData();
		Bukkit.getLogger().info(loadCnt+"���� ���� ������ �ε��");
		
		Bukkit.getLogger().info("[BMonsterSkyBlock] BMonsterSkyBlock �÷����� �ε��");
		
	}
	
	public void onDisable() {
		Bukkit.getLogger().info("[BMonsterSkyBlock] BMonsterSkyBlock �÷����� ��ε��");
		int saveCnt = 0;
		for(MonsterTrainer trainer : MonsterTrainer.trainerMap.values()) {
			trainer.save();
			saveCnt += 1;
		}
		Bukkit.getLogger().info("[BMonsterSkyBlock] "+saveCnt+"���� Ʈ���̳� ������ �����");
		saveCnt = 0;
		for(MyMonster monster : MyMonster.monsterMap.values()) {
			monster.save();
			saveCnt += 1;
		}
		Bukkit.getLogger().info("[BMonsterSkyBlock] "+saveCnt+"���� ���� ������ �����");
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
									p.sendMessage(MS+"�������� �ʴ� ����Դϴ�.");
								} else if(reason == Reason.INVAILD_ROLE) {
									p.sendMessage(MS+"�������� �ʴ� �����Դϴ�.");
								} else if(reason == Reason.SUCCESS) {
									p.sendMessage(MS+"���� �ο� ����.");
								}
							} else {
								p.sendMessage(MS+"������ ���� �̸��� �����ּ���. ��) /bms set Steve ���̸�");
							}
						} else {
							p.sendMessage(MS+"������ ����� �����ּ���. ��) /bms set Steve ���̸�");
						}
					} else if(args[0].equals("clear")){
						if(args.length >= 2) {
							String target = args[1];
							Reason reason = clearRole(target);
							if(reason == Reason.INVAILD_ROLE) {
								p.sendMessage(MS+target+"�� �̹� ������ ���� ���� �ʽ��ϴ�.");
							} else if(reason == Reason.SUCCESS) {
								p.sendMessage(MS+target+"�� ������ �����߽��ϴ�.");
							} else if(reason == Reason.INVAILD_TARGET) {
								p.sendMessage(MS+target+"����� ������ ã�� �� �����ϴ�.");
							}
						} else {
							p.sendMessage(MS+"������ ������ ����� �����ּ���. ��) /bms remove Steve");
						}
					} else if(args[0].equals("add")) {
						if(args.length >= 2) {
							String trainer = args[1];
							if(args.length >= 3) {
								String target = args[2];
								Reason reason = addMonster(trainer, target);
								if(reason == Reason.INVAILD_TARGET) {
									p.sendMessage(MS+"�������� �ʴ� ����Դϴ�.");
								} else if(reason == Reason.INVAILD_MONSTER) {
									p.sendMessage(MS+"�������� �ʴ� ���� �������Դϴ�.");
								} else if(reason == Reason.INVAILD_TRAINER){
									p.sendMessage(MS+"�������� �ʴ� Ʈ���̳� �������Դϴ�.");
								} else if(reason == Reason.SUCCESS) {
									p.sendMessage(MS+"���� �߰� ����.");
								}
							} else {
								p.sendMessage(MS+"������ ���� �̸��� �����ּ���. ��) /bms add Steve Norch");
							}
						} else {
							p.sendMessage(MS+"������ ����� �����ּ���. ��) /bms add Steve Norch");
						}
					} else if(args[0].equals("remove")) {
						if(args.length >= 2) {
							String trainer = args[1];
							if(args.length >= 3) {
								String target = args[2];
								Reason reason = removeMonster(trainer, target);
								if(reason == Reason.INVAILD_TARGET) {
									p.sendMessage(MS+"�������� �ʴ� ����Դϴ�.");
								} else if(reason == Reason.INVAILD_MONSTER) {
									p.sendMessage(MS+"�������� �ʴ� ���� �������Դϴ�.");
								} else if(reason == Reason.INVAILD_TRAINER){
									p.sendMessage(MS+"�������� �ʴ� Ʈ���̳� �������Դϴ�.");
								} else if(reason == Reason.SUCCESS) {
									p.sendMessage(MS+"���� ���� ����.");
								}
							} else {
								p.sendMessage(MS+"������ ���� �̸��� �����ּ���. ��) /bms remove Steve Norch");
							}
						} else {
							p.sendMessage(MS+"������ ����� �����ּ���. ��) /bms remove Steve Norch");
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
							p.sendMessage(MS+"������ ����� �����ּ���. ��) /bms remove Steve Norch");
						}
					}
				}
			}
		}
		return false;
	}
	
	public void helpCommands(Player p) {
		p.sendMessage("��7--------------------------------");
		p.sendMessage(MS+"/bms set <�г���> <���͸�/Ʈ���̳�> - ��e���͸� �Ǵ� Ʈ���̳� ������ �ο��մϴ�.");
		p.sendMessage(MS+"/bms clear <�г���> - ��e������ �����մϴ�.");
		p.sendMessage(MS+"/bms add <Ʈ���̳�> <�г���> - ��eƮ���̳ʿ��� �г��� ���͸� �ο��մϴ�.");
		p.sendMessage(MS+"/bms remove <Ʈ���̳�> <�г���> - ��eƮ���̳ʿ��Լ� �г��� ���͸� �����մϴ�.");
		p.sendMessage("��7--------------------------------");
	}
	
	public Reason setRole(String target, String roleName) {
		Player p = Bukkit.getPlayer(target);
		if(p == null) return Reason.INVAILD_TARGET;
		
		String uuid = p.getUniqueId().toString();
		switch(roleName) {
			case "Ʈ���̳�": clearRole(target); new MonsterTrainer(uuid); break;
			case "�̻��ؾ�": clearRole(target); new Bulbasaur(uuid); break;
			case "���̸�": clearRole(target); new Charmander(uuid); break;
			case "���α�": clearRole(target); new Squirtle(uuid); break;
			case "ġ�ڸ�Ÿ": clearRole(target); new Chikorita(uuid); break;
			case "������": clearRole(target); new Cyndaquil(uuid); break;
			case "������": clearRole(target); new Totodile(uuid); break;
			
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
