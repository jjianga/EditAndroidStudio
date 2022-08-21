package com.zhkj.code.bean;

import java.util.ArrayList;
import java.util.List;

//���ù�ϵ
public class InvokingRelationship {
	private int maxRoot;
	private int isRoot = 0;					//��ǰ��Ŀ¼�ȼ�
	private int isUpRoot = 0;				//�ϴδ����Ž���ʱ�ĵȼ�
	private ClazzObjectTrait clazzObjectTraitTemporary;			//�����õ���
	private ClazzMethodTrait clazzMethodTraitTemporary;			//�����õķ���
	private ClazzParamTrait clazzParamTraitTemporary;			//�����õ�����
	List<InvokingRelationship> invokingRelationshipS = new ArrayList<InvokingRelationship>();			//�¼�������
	
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
	 * ���һ������
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
	 * ɾ����Ч�Ļ���
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
	 * ��ȡ��ǰִ�е���
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
	 * ����һ��������ʼ�ĵط�
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
