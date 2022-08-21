package com.zhkj.code.bean;

import java.util.ArrayList;
import java.util.List;

//调用关系
public class InvokingRelationship {
	private int maxRoot;
	private int isRoot = 0;					//当前根目录等级
	private int isUpRoot = 0;				//上次大括号结束时的等级
	private ClazzObjectTrait clazzObjectTraitTemporary;			//被调用的类
	private ClazzMethodTrait clazzMethodTraitTemporary;			//被调用的方法
	private ClazzParamTrait clazzParamTraitTemporary;			//被调用的属性
	List<InvokingRelationship> invokingRelationshipS = new ArrayList<InvokingRelationship>();			//下级被调用
	
	public int getIsRoot() {
		return isRoot;
	}
	public void setIsRoot(int isRoot) {
		this.isRoot = isRoot;
	}
	public int getIsUpRoot() {
		return isUpRoot;
	}
	public void setIsUpRoot(int isUpRoot) {
		this.isUpRoot = isUpRoot;
	}
	public ClazzMethodTrait getClazzMethodTraitTemporary() {
		return clazzMethodTraitTemporary;
	}
	public void setClazzMethodTraitTemporary(ClazzMethodTrait clazzMethodTraitTemporary) {
		this.clazzMethodTraitTemporary = clazzMethodTraitTemporary;
	}
	public ClazzObjectTrait getClazzObjectTraitTemporary() {
		return clazzObjectTraitTemporary;
	}
	public void setClazzObjectTraitTemporary(ClazzObjectTrait clazzObjectTraitTemporary) {
		this.clazzObjectTraitTemporary = clazzObjectTraitTemporary;
	}
	public ClazzParamTrait getClazzParamTraitTemporary() {
		return clazzParamTraitTemporary;
	}
	public void setClazzParamTraitTemporary(ClazzParamTrait clazzParamTraitTemporary) {
		this.clazzParamTraitTemporary = clazzParamTraitTemporary;
	}

	public InvokingRelationship getInvokingRelationship(int endRoot){
		for (int i = 0; i < invokingRelationshipS.size(); i++) {
			if(invokingRelationshipS.get(i).isRoot == endRoot){
				return invokingRelationshipS.get(i);
			}
		}
		return this;
	}
	/**
	 * 添加一条缓存
	 * @param invokingRelationship
	 * @return
	 */
	public InvokingRelationship addInvokingRelationship(InvokingRelationship invokingRelationship) {
		this.maxRoot ++;
		this.isRoot = this.maxRoot;
		invokingRelationship.isRoot = this.maxRoot;
		invokingRelationshipS.add(invokingRelationship);
		return this;
	}
	/**
	 * 删除无效的缓存
	 * @param endRoot
	 */
	public void removeUpInvokingRelationshipS(int endRoot){
		this.isRoot = endRoot;
		for (int i = 0; i < invokingRelationshipS.size(); i++) {
			if(invokingRelationshipS.get(i).isRoot > endRoot){
				invokingRelationshipS.remove(i);i--;
			}
		}
	}
	/**
	 * 获取当前执行的类
	 * @return
	 */
	public ClazzObjectTrait getInvokingRelationshipS(){
		for (InvokingRelationship invokingRelationshipT : invokingRelationshipS) {
			if(invokingRelationshipT.getIsRoot() 
					== this.isRoot){
				return invokingRelationshipT.getClazzObjectTraitTemporary();
			}
		}
		return null;
	}
	/**
	 * 到上一个方法开始的地方
	 */
	public void setPreviousIsUpRoot() {
		for (InvokingRelationship invokingRelationshipT :
			invokingRelationshipS) {
			if(invokingRelationshipT.getIsRoot() == this.isUpRoot){
				this.isUpRoot = invokingRelationshipT.getIsUpRoot();
				break;
			}
		}
	}
}
