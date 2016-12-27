package com.ellis.algorithm.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class SGenome {

	private double fitScore;
	
	private int steps;
	
	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public double getFitScore() {
		return fitScore;
	}

	public void setFitScore(double fitScore) {
		this.fitScore = fitScore;
	}

	private int lenght;
	private Vector<Integer>  bits;
	
	public SGenome(int lenght) {
		this.lenght = lenght;
		bits = new Vector<Integer>(lenght);
		Random random = new Random();
		//String ss = "";
		for (int i = 0; i < lenght; i++) {
			int tmp = random.nextInt(2) ;
			bits.add(tmp);
			//ss += "" + tmp;
		}	
		//System.out.println("dna--" + ss);
	}

	public Double updateFitnessScores(Labyrinth lab) {
		StringBuffer jyl = new StringBuffer();
		for (int i = 0; i < bits.size(); i++) {
			jyl.append(bits.get(i));
		}
		
		List<String> steps = this.slit(jyl.toString());
		
		lab.reset();
		int tmp = 0;
		for (String step : steps) {
			lab.runStep(step);
			tmp++;
			if(lab.isEnd()) {
				break;
			} 
		}
		this.fitScore = lab.calcScore().doubleValue();
		this.setSteps(tmp);
		
		return fitScore;
	}
	
	
	public int getLenght() {
		return lenght;
	}
	public void setLenght(int lenght) {
		this.lenght = lenght;
	}
	public Vector<Integer> getBits() {
		return bits;
	}
	public void setBits(Vector<Integer> bits) {
		this.bits = bits;
	}
	
	public static void main(String[] args) {
		
		new SGenome(140);
	}
	
	private List<String> slit(String str) {
		List<String> retList = new ArrayList<String>();
		if(str == null){
			return retList;
		}
		int size = str.length();
		int index = 0;
		while(index < size) {
		
			retList.add(str.substring(index, index + 2));
			index +=2;
		}
		return retList;
	}
	
	
}
