package com.ellis.algorithm;


import com.ellis.algorithm.genetic.CgaBob;
import com.ellis.algorithm.genetic.Labyrinth;

public class Client {

	public static void main(String[] args) {
		start();
	}			
	
	static void start(){
		Labyrinth lab = new Labyrinth();
		CgaBob cga = new CgaBob();
		double score = 0;
		while(score < 1.0) {
			
			cga.CreateStartPopulation();
			
			cga.Crossover(lab);
			score = cga.getBestFitnessScore();

			lab.showLine(cga.getBasetSGenome().getBits());
			System.out.println("第:" + cga.getGeneration()+"代" +"   　最高分:" +cga.getBestFitnessScore() );
			lab.show();
		}
	}
}

