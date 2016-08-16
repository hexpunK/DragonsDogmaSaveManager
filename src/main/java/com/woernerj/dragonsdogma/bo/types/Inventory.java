package com.woernerj.dragonsdogma.bo.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Node;

import com.woernerj.dragonsdogma.util.XPathUtils;

public class Inventory {

	public static class InventoryBlock {
		
		private long itemCount;
		private List<ItemData> items;
		
		public InventoryBlock() {
			this.items = new ArrayList<>();
		}
		
		public long getItemCount() {
			return itemCount;
		}
		public List<ItemData> getItems() {
			return items;
		}
		public void setItemCount(long itemCount) {
			this.itemCount = itemCount;
		}
		public void setItems(List<ItemData> items) {
			this.items = items;
		}
		
		@Override
		public String toString() {
			return String.format("%d items", this.getItemCount());
		}
		
		public static InventoryBlock build(Node root) {
			InventoryBlock obj = new InventoryBlock();
			
			XPathUtils.getDouble(root, "u32[@name='mItemCount']/@value").ifPresent(itemCount -> {
				obj.setItemCount(itemCount.longValue());
			});
			
			int arraySize = XPathUtils.getDouble(root, "array[@name='mItem']/@count").get().intValue();
			XPathUtils.findNodes(root, "array[@name='mItem']/child::class").ifPresent(nodes -> {
				List<ItemData> items = new ArrayList<>(Collections.nCopies(arraySize, ItemData.EMPTY_ITEM));
				
				long size = obj.getItemCount();
				for (int i = 0; i < size; i++) {
					ItemData item = ItemData.build(nodes.item(i));
					items.set(i, item);
				}
				
				obj.setItems(items);
			});
			
			return obj;
		}
	}
	
	private List<InventoryBlock> inventoryBlocks;
	
	public Inventory() {
		this.inventoryBlocks = new ArrayList<>();
	}
	
	public List<InventoryBlock> getInventoryBlocks() {
		return inventoryBlocks;
	}
	public void setInventoryBlocks(List<InventoryBlock> inventoryBlocks) {
		this.inventoryBlocks = inventoryBlocks;
	}
	
	@Override
	public String toString() {
		long total = this.inventoryBlocks.stream().mapToLong( i -> i.getItemCount() ).sum();
		return String.format("%d items", total);
	}
	
	public static Inventory build(Node root) {
		Inventory obj = new Inventory();
		
		XPathUtils.findNodes(root, "array[@name='mItem']/child::class").ifPresent(inventory -> {
			final int arraySize = XPathUtils.getDouble(root, "array[@name='mItem']/@count").get().intValue();
			List<InventoryBlock> blocks = new ArrayList<>(arraySize);
			
			for (int i = 0; i < arraySize; i++) {
				blocks.add(i, InventoryBlock.build(inventory.item(i)));
			}
			
			obj.setInventoryBlocks(blocks);
		});
		
		return obj;
	}
}
