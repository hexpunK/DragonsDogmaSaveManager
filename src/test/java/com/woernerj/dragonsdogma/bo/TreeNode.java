package com.woernerj.dragonsdogma.bo;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> {

	private T item;
	private List<TreeNode<?>> children;
	private ToStringHandler toStringHandler;
	
	public TreeNode(T item) {
		this.item = item;
		this.children = new ArrayList<>();
	}
	
	public void addChild(TreeNode<?> treeNode) {
		this.children.add(treeNode);
	}
	
	public void addChild(Object item) {
		TreeNode<?> newChild = new TreeNode<>(item);
		this.children.add(newChild);
	}
	
	public List<TreeNode<?>> getChildren() {
		return this.children;
	}
	
	public void setToStringHandler(ToStringHandler handler) {
		this.toStringHandler = handler;
	}
	
	public String toString() {
		if (item == null) return "NULL";
		return toStringHandler.toString(item);
	}
	
	@FunctionalInterface
	public static interface ToStringHandler {
		
		public String toString(Object item);
	}
}
