package object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import utils.Utils;

public class Role extends Player {
	String roleName;//名称
	String roleId;//角色id
	int roleType;//角色类型
	static int ROLE_TYPE_OCEAN=1;
//	static int ROLE_TYPE_SKY=2;
	static int ROLE_TYPE_LAND=3;
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleId(){
		return roleId;
	}
	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}
	public int getRoleType() {
		return roleType;
	}
	
	float[] roleCoordinate;//xyz
	//设置角色坐标
	protected void setRoleCoordinate(float[] roleCoordinate) {
		this.roleCoordinate = roleCoordinate;
	}
	public void setRoleCoordinate(float[] roleCoordinate,boolean isFirstCreate){
		setRoleCoordinate(roleCoordinate);
		if(isFirstCreate){
			Utils.SystemPrintln("恭喜您创建角色 "+getRoleName()+" 成功！");
			Utils.SystemPrintln("您的起始出生位置为：("+roleCoordinate[0]+","+roleCoordinate[1]+","+roleCoordinate[2]+")");
		}
		else {
			System.out.println(getRoleName()+" 位置：("+roleCoordinate[0]+","+roleCoordinate[1]+","+roleCoordinate[2]+")");
		}
	}
	public float[] getRoleCoordinate() {
		return roleCoordinate;
	}
	
	float roleCurHealth=100.f;//当前生命值
	public float getRoleCurHealth() {
		return roleCurHealth;
	}
	
	int roleMoodType=ROLE_MOOD_TYPE_HAPPY;//心情
	static final int ROLE_MOOD_TYPE_HAPPY=1;
	static final int ROLE_MOOD_TYPE_SAD=2;
	public void setRoleMoodType(int roleMoodType) {
		this.roleMoodType = roleMoodType;
	}
	public int getRoleMoodType() {
		return roleMoodType;
	}
	
	float roleCurEmpirical=0.0f;//当前经验值
	public float getRoleCurEmpirical() {
		return roleCurEmpirical;
	}
	
	public Role(){
		Random random=new Random();
		int randomInt=random.nextInt(2);
//		System.out.println(randomInt);
		if(randomInt==0){
			geneticConstitution.put(GeneType.Animal, 90.0f);
			geneticConstitution.put(GeneType.Plant, 10.0f);
		}else{
			geneticConstitution.put(GeneType.Plant, 90.0f);
			geneticConstitution.put(GeneType.Animal, 10.0f);
		}
		
	}
	HashMap<GeneType,Float> geneticConstitution=new HashMap<Role.GeneType, Float>();//基因组成
	enum GeneType{//基因种类
		Animal,Plant
	}
	float geneDosage;//单项基因数量，没用
	public HashMap<GeneType, Float> getGeneticConstitution() {
		return geneticConstitution;
	}
	public void setGeneticConsititution(
			HashMap<GeneType, Float> geneticConstitution) {
		this.geneticConstitution = geneticConstitution;
	}
	
		float roleMaxHealth=100.0f;//当前最大生命值
		int roleEvolutionMaxLevel=10;//进化最大等级
		float roleUpgradeMaxEmpirical=100;//升级最大经验值
		float roleMoveSpeed=1.f;//移动速度
		float geneGross;//基因总量
		float baseDamage=5.f;//基础攻击力
		float damageSpeed=5.f;//基础攻击速度
		public void setRoleUpgradeMaxEmpirical(float roleUpgradeMaxEmpirical) {
			this.roleUpgradeMaxEmpirical = roleUpgradeMaxEmpirical;
		}
		public float getRoleUpgradeMaxEmpirical() {
			return roleUpgradeMaxEmpirical;
		}
		public void setRoleEvolutionMaxLevel(int roleEvolutionMaxLevel) {
			this.roleEvolutionMaxLevel = roleEvolutionMaxLevel;
		}
		public int getRoleEvolutionMaxLevel() {
			return roleEvolutionMaxLevel;
		}
		public void setRoleMaxHealth(float roleMaxHealth) {
			this.roleMaxHealth = roleMaxHealth;
		}
		public float getRoleMaxHealth() {
			return roleMaxHealth;
		}
		public void setRoleMoveSpeed(float roleMoveSpeed) {
			this.roleMoveSpeed = roleMoveSpeed;
		}
		public float getRoleMoveSpeed() {
			return roleMoveSpeed;
		}
		public void setBaseDamage(float baseDamage) {
			this.baseDamage = baseDamage;
		}
		public float getBaseDamage() {
			return baseDamage;
		}
	public void setGeneGross(float geneGross) {
		this.geneGross = geneGross;
	}
	public float getGeneGross() {
		return geneGross;
	}
		
	//被攻击
	public float getHurt(float blood){
		roleCurHealth-=blood;
		return roleCurHealth;
	}
	//获得经验值
	public float getEmpirical(float empiricalAddition){
		roleCurEmpirical+=empiricalAddition;
		return roleCurEmpirical;
	}
	//进食，暂时不考虑foodSize因素
	public String eatFood(String roleId,float foodSize){
		
		Role eatenRole=getRoleFromDB(roleId);
		//取出自己的基因
		Set<Entry<GeneType,Float>> myselfGeneSet=getGeneticConstitution().entrySet();
		Iterator<Entry<GeneType,Float>> myGeneIterator=myselfGeneSet.iterator();
		//分析基因组成，根据组成百分比*食物的量*0.05，转化为自身基因
		Set<Entry<GeneType,Float>> foodGeneSet=eatenRole.getGeneticConstitution().entrySet();
		Iterator<Entry<GeneType,Float>> foodGeneIterator=foodGeneSet.iterator();
		
		while(foodGeneIterator.hasNext()){
			Entry<GeneType, Float> foodGeneEntry=foodGeneIterator.next();
			GeneType foodGeneType=foodGeneEntry.getKey();
			float foodGeneValue=foodGeneEntry.getValue();
			while(myGeneIterator.hasNext()){
				Entry<GeneType, Float> myGeneEntry=myGeneIterator.next();
				GeneType myGeneType=myGeneEntry.getKey();
				if(foodGeneType.equals(myGeneType)){
					float newGeneValue=myGeneEntry.getValue()*0.9f+foodGeneValue*0.1f;
					getGeneticConstitution().put(myGeneType, newGeneValue);
					break;
				}
			}
		}
	return null;	
	}
	
	//模仿数据库，通过roleId取出一个entity
	private Role getRoleFromDB(String RoleId){
		Role role=new Role();
		float[] coordinateNpcRole=new float[]{0,0,5};
		role.setRoleCoordinate(coordinateNpcRole);
		role.setRoleId("1");
		return new Role();
	}
	
	//打印基因组成
	public void printGeneConstitution(){
		Utils.SystemPrintln("您的基因组成为：");
		for (Entry<GeneType, Float> entry : getGeneticConstitution().entrySet()) {
			System.out.println("GeneType:"+entry.getKey()+"  GeneValue:"+entry.getValue());
		}
		System.out.println("=============");
	}
	//打印现在的状态
	public void printRole(){
		//起始生命值、攻击力、移动速度、心情、经验值、升级所需经验、进化所需等级
		Utils.SystemPrintln("当前生命值："+getRoleCurHealth());
		Utils.SystemPrintln("当前攻击力："+getBaseDamage());
		Utils.SystemPrintln("当前移动速度："+getRoleMoveSpeed());
		switch(getRoleMoodType()){
			case ROLE_MOOD_TYPE_HAPPY:
				Utils.SystemPrintln("当前心情：开心");
				break;
			case ROLE_MOOD_TYPE_SAD:
				Utils.SystemPrintln("当前心情：难过");
				break;
		}
		Utils.SystemPrintln("当前经验值："+getRoleCurEmpirical());
		Utils.SystemPrintln("当前升级所需经验："+getRoleUpgradeMaxEmpirical());
		Utils.SystemPrintln("当前进化所需等级："+getRoleEvolutionMaxLevel());
		System.out.println("=============");
	}
	
	//攻击目标的集合
	private ArrayList<String> opponentsArrayList=new ArrayList<String>();
	private ArrayList<Role> opponentRolesArrayList=new ArrayList<Role>();
	//锁定目标，测试时用role实例，roleId用于有数据库时
	public void lockOpponent(String roleId){
		opponentsArrayList.set(0, roleId);//单体攻击
	}
	public void lockOpponent(Role role){
		if(opponentRolesArrayList.size()==0)
			opponentRolesArrayList.add( role);//单体攻击
		else {
			opponentRolesArrayList.set( 0,role);//单体攻击
		}
		if(role.getClass().equals(NPCRole.class)){
			((NPCRole)role).lockedRole(this);
		}
		
	}
	//失去目标
	public void finishLockOpponent(){
		opponentsArrayList.clear();
	}
	//是否到达目的地
	boolean ifMoveEnd=false;
	//攻击锁定目标
	public void fightWithLockOppo(){
		//先移动到指定目标再攻击
		move(opponentRolesArrayList.get(0).getRoleCoordinate());
		//到达指定地点后开始攻击
		fight(opponentRolesArrayList.get(0));
	}
	//移动:1、获取指定路线 2、按移动速度更新坐标
	public void move(float[] destination){
		Utils.SystemPrintln("移动中……");
		final ArrayList<float[]> route=new ArrayList<float[]>();
		float roleCoorX=getRoleCoordinate()[0];//起点
		float roleCoorY=getRoleCoordinate()[1];
		float roleCoorZ=getRoleCoordinate()[2];
		float destinationX=destination[0];//终点
		float destinationY=destination[1];
		float destinationZ=destination[2];
		while(true){
			if(roleCoorX!=destinationX)
			{
				roleCoorX=(roleCoorX>destinationX)?--roleCoorX:++roleCoorX;
				route.add(new float[]{roleCoorX,roleCoorY,roleCoorZ});
			}
			else {
				if(roleCoorY!=destinationY)
				{
					roleCoorY=(roleCoorY>destinationY)?--roleCoorY:++roleCoorY;
					route.add(new float[]{roleCoorX,roleCoorY,roleCoorZ});
				}
				else {
					if(roleCoorZ!=destinationZ)
					{
						roleCoorZ=(roleCoorZ>destinationZ)?--roleCoorZ:++roleCoorZ;
						route.add(new float[]{roleCoorX,roleCoorY,roleCoorZ});
					}
					else {
						break;
					}
				}
			}
			
		}
		 //开始走
		final Timer timer=new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
//				int currPosIndex=0;
				// TODO Auto-generated method stub
				if(route.size()==0)
				{
					ifMoveEnd=true;
					timer.cancel();
				}else {
					setRoleCoordinate(route.get(0),false);
					route.remove(0);
				}
			
			}
		}, 0,(long) (500*roleMoveSpeed));
		
	}
	//攻击
	public void fight(final Role role){
		final Timer timer=new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
//				Utils.SystemPrintln("fight!111");
				if(role.getRoleCurHealth()==0||getRoleCurHealth()==0)
				{
//					Utils.SystemPrintln("fight!222");
					timer.cancel();
					ifMoveEnd=false;
				}
				if(ifMoveEnd){
//					Utils.SystemPrintln("fight!333");
					role.getHurt(getBaseDamage());
					System.out.println("getHurt!:"+role.getRoleName()+" 当前生命值："+role.getRoleCurHealth());
				}
			}
		},0,(long) (500));
	}
	//搜索附近目标(需要数据库)
	public void spyOpponent(){
		
	}
	
	//反击
//	 boolean ifBeatback=false;
	
	
}
