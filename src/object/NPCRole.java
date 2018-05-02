package object;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import utils.Utils;

public class NPCRole extends Role {
	boolean ifBeatback=true;
	float damageSpeed=1.f;//基础攻击速度
	static final String[] npcRolesNameStrings=new String[]{
		"贪吃蛇",
		"捞月狗",
		"蹦蹦虎",
		"大肠杆菌",
		"葡萄皮",
		"椰子肉",
		"蜘蛛网",
		"温州皮鞋",
		"烤冷面",
		"胡巴",
		"周杰伦",
		"吉泽明步",
		"渣渣辉",
		"流星落",
		"万达广场",
	};
	public NPCRole() {
		// TODO Auto-generated constructor stub
		int namesNum=npcRolesNameStrings.length;
		Random random=new Random();
		int nameIndex=random.nextInt(namesNum);
		setRoleName(npcRolesNameStrings[nameIndex]);
		setBaseDamage(1.f);
	}
	
	public void setRoleCoordinate(float[] roleCoordinate,boolean isFirstCreate,int npcRoleIndex){
		setRoleCoordinate(roleCoordinate);
		if(isFirstCreate){
			Utils.SystemPrintln("恭喜您创建角色 "+getRoleName()+" 成功！");
			Utils.SystemPrintln("您的起始出生位置为：("+roleCoordinate[0]+","+roleCoordinate[1]+","+roleCoordinate[2]+")");
		}
		else {
			System.out.println(npcRoleIndex+"、"+getRoleName()+" 位置：("+roleCoordinate[0]+","+roleCoordinate[1]+","+roleCoordinate[2]+")");
		}
	}
	//双向锁定
	public void lockedRole(Role role){
		System.out.println("loced role name::"+role.getRoleName());
		lockOpponent(role);
		 beatBack(null,role);
	}
	public void beatBack(String roleId,final Role fightingRole){
		//当两者距离相差1的时候
		//当血量比初始值少的时候，但大于0的时候
		//当反击属性被设置为true的时候
		System.out.println("ifBeatback::"+ifBeatback);
		if(!ifBeatback){
			return;
		}
		System.out.println("ifBeatback22::"+ifBeatback);
		final Timer timer=new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
//				System.out.println("timer:fightingRole:"+fightingRole.getRoleCoordinate()[0]);
//				System.out.println("timer:getRoleCoordinate():"+getRoleCoordinate()[0]);
				if(fightingRole.getRoleCoordinate()[0]==getRoleCoordinate()[0]&&
						fightingRole.getRoleCoordinate()[1]==getRoleCoordinate()[1]&&
						fightingRole.getRoleCoordinate()[2]==getRoleCoordinate()[2]){
					System.out.println("timer11::");
					if(getRoleCurHealth()>0&&getRoleCurHealth()<getRoleMaxHealth()){
						System.out.println("timer22::");
						
						fightWithLockOppo();
						timer.cancel();
					}
				
				}
			}
		}, 0,1000);
	}
}
