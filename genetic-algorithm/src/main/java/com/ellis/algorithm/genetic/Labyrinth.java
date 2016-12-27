package com.ellis.algorithm.genetic;

import com.ellis.algorithm.genetic.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Labyrinth {

	public Labyrinth(int[][] coordinate) {
		this.coordinate = coordinate;
	}

	public int[][] getCoordinate() {
		return coordinate;
	}

	private int[][] coordinate = {
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1 },
			{ 5, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1 },
			{ 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1 },
			{ 1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 8 },
			{ 1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	// 迷宫长和宽
	private int weight = 15;
	private int high = 10;

	private Integer currentX;
	private Integer currentY;

	public int getCurrentX() {
		return currentX;
	}

	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}

	public int getCurrentY() {
		return currentY;
	}

	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}

	// 起点坐标
	private Integer startX = 2;
	private Integer startY = 0;

	// 重点坐标
	private Integer endX = 7;
	private Integer endY = 14;

	public void reset() {
		this.currentX = this.startX;
		this.currentY = this.startY;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getEndX() {
		return endX;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

	public Labyrinth() {
	}

	// Bob走一步
	public void runStep(String direction) {
		switch (direction) {
		case "00": // 北
			if (this.canRun(currentX - 1, currentY)) {
				currentX -= 1;
			}
			break;
		case "01": // 南
			if (this.canRun(currentX + 1, currentY)) {
				currentX += 1;
			}
			break;
		case "11": // 西
			if (this.canRun(currentX, currentY - 1)) {
				currentY -= 1;
			}
			break;
		case "10":// 东
			if (this.canRun(currentX, currentY + 1)) {
				currentY += 1;
			}
			break;
		default:
			
			break;
		}
		//System.out.println("x:" + currentX + "  " + "y:" + currentY);
	}

	// 是否能走
	private boolean canRun(int tmpx, int tmpy) {
		int x = tmpx;
		int y = tmpy;
		boolean flag = true;
		if (x >= this.high || y >= this.weight|| x < 0 || y < 0) {
			flag = false;
		} else {
			if (this.coordinate[x][y] == 1) {
				flag = false;
			}
		}
		//System.out.println(flag);
		return flag;
	}

	// 是否到达出口
	public boolean isEnd() {
		if (currentX.doubleValue() == endX.doubleValue() && currentY.doubleValue() == endY.doubleValue()) {
			return true;
		} else {
			return false;
		}
	}

	public Double calcScore() {
		int x = Math.abs(currentX.intValue() - endX.intValue());
		int y = Math.abs(currentY.intValue() - endY.intValue());
		Double fm = x + y + 1.0;
		return 1 / fm;
	}

	// 打印迷宫
	public void show() {
		for (int i = 0; i < coordinate.length; i++) {
			int[] tmp = coordinate[i];
			for (int j = 0; j < tmp.length; j++) {
				System.out.print(tmp[j] + "  ");
			}
			System.out.println();
		}
	}

	public void showLine(Vector<Integer> bits) {

		StringBuffer jyl = new StringBuffer();
		for (int i = 0; i < bits.size(); i++) {
			jyl.append(bits.get(i));
		}

		List<String> steps = Util.slit(jyl.toString());
		List<String> zbList = new ArrayList<String>();
		zbList.add(this.currentX + "-" + currentY);
		this.reset();
		for (String step : steps) {
			this.runStep(step);
			String zb = this.currentX + "-" + currentY;
			zbList.add(zb);
			if (this.isEnd()) {
				break;
			}
		}
		
		for (int i = 0; i < coordinate.length; i++) {
			int[] tmp = coordinate[i];
			for (int j = 0; j < tmp.length; j++) {
				for (String zbx : zbList) {
					
					String[] arr = zbx.split("-");
					int x = Integer.valueOf(arr[0]) ;
					int y = Integer.valueOf(arr[1]) ;
					if(i == x && j == y){
						if(!(coordinate[i][j] ==  8 || coordinate[i][j] == 5)) {
							coordinate[i][j] = 4;
						}
						if(coordinate[i][j] ==  8){
							coordinate[i][j] = 88;
							
						}
					}
					
				}
			}
		}
		
		
	}

}
