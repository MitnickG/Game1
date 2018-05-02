package firstpacket;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import object.NPCRole;
import object.Role;
import utils.Arithmetic;
import utils.Utils;


public class MainClass {
	public static void main(String[] args){
		Scanner scanner=new Scanner(System.in);
		Utils.SystemPrintln("请输入创建角色的名称：");
		String roleNameString=scanner.nextLine();
		Role mainRole=new Role();
		mainRole.setRoleName(roleNameString);
		Random random=new Random();
		float[] coordinate=new float[]{	random.nextFloat()*100.f,	random.nextFloat()*100.f,		random.nextFloat()*100.f};
		mainRole.setRoleCoordinate(coordinate,true);
		mainRole.setRoleId("0");
		mainRole.printGeneConstitution();
		mainRole.printRole();
		
		Utils.SystemPrintln("是否为您刷怪：1、是 2、否");
		int isInitOpponents=scanner.nextInt();
		if(isInitOpponents==1){
			ArrayList<NPCRole> opponentRoles= Arithmetic.initOppos(10,10,mainRole.getRoleCoordinate());
			Utils.SystemPrintln("请输入准备锁定的目标：");
			int lockIndex=scanner.nextInt();
			while(lockIndex<0||lockIndex>opponentRoles.size()-1){
				Utils.SystemPrintln("不要调皮，请输入指定范围内怪物索引：");
				 lockIndex=scanner.nextInt();
			}
			mainRole.lockOpponent(opponentRoles.get(lockIndex));
			Utils.SystemPrintln("是否要攻击锁定目标：1、是 2、放过他");
			int isFightWithLockOppo=scanner.nextInt();
			if(isFightWithLockOppo==1){
				mainRole.fightWithLockOppo();
//				opponentRoles.get(lockIndex).beatBack(null, mainRole);
			}
			
		}else{
			
		}
		
	/*	
		mainRole.lockOpponent(opponentRoles.get(2));
		mainRole.fightWithLockOppo();*/
		
	}
	
}