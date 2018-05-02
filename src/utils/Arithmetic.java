package utils;

import java.awt.List;
import java.util.ArrayList;
import java.util.Random;

import object.NPCRole;
import object.Role;

public class Arithmetic {
	//得到三维球体内部随机数
	//算法1：得到球体外切立方体xyz范围，再缩成球范围
	public static ArrayList<float[]> getRandomInGlobe(float[] centreCoord,float radius,int numbs){
		float centreX=centreCoord[0];
		float centreY=centreCoord[1];
		float centreZ=centreCoord[2];
		//范围 centreX-radius~centreX+radius
		Random random=new Random();
		ArrayList arrayList=new ArrayList();
		while(true){
			float randomFloatX=(random.nextInt((int)radius*2+1)+(centreX-radius));
			float randomFloatY=(random.nextInt((int)radius*2+1)+(centreY-radius));
			float randomFloatZ=(random.nextInt((int)radius*2+1)+(centreZ-radius));
			float distance=((centreX-randomFloatX)*(centreX-randomFloatX)
					+(centreY-randomFloatY)*(centreY-randomFloatY)
					+(centreZ-randomFloatZ)*(centreZ-randomFloatZ));
			if(distance<=radius*radius){
//				System.out.println("distance:"+Math.sqrt((double)distance));
				arrayList.add(new float[]{randomFloatX,randomFloatY,randomFloatZ});
			}
			if(arrayList.size()==numbs){
				break;
			}
		}
		return arrayList;
	}
	//指定数量、半径、中心点坐标，刷怪
	//模拟NPCId
		static int npcIdTest=1;
		//初始化敌人，周围距离10以内5个敌人（测试用）
		//指定坐标，指定半径，指定数量，刷怪
		public static ArrayList<NPCRole> initOppos(int opponentsNum,float distant,float[] centreCoord){
			float[] roleCoor=centreCoord;
			ArrayList<NPCRole> opponentRoles=new ArrayList<NPCRole>();
			ArrayList<float[]> arrayList= Arithmetic.getRandomInGlobe(roleCoor, distant, opponentsNum);
			for (float[] floats : arrayList) {
//				System.out.println("X:"+floats[0]+" Y:"+floats[1]+" Z:"+floats[2]);
				NPCRole npcRole=new NPCRole();
				npcRole.setRoleCoordinate(floats,false,opponentRoles.size());
				npcRole.setRoleId(""+npcIdTest++);
				opponentRoles.add(npcRole);
			}
			return opponentRoles;
		}
}
