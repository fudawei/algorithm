package com.ellis.algorithm.genetic;

import java.util.Random;
import java.util.Vector;

public class CgaBob {
	private double bestFitnessScore = -1;
	private SGenome basetSGenome;
	public SGenome getBasetSGenome() {
		return basetSGenome;
	}

	public void setBasetSGenome(SGenome basetSGenome) {
		this.basetSGenome = basetSGenome;
	}

	private Integer chromeLength;
	// 杂交率
	private Double crossoverRate;
	private int fittestGenome;

	// 第几代
	private int generation;

	private Boolean isBusy;
	// 变异率
	private Double mutationRate;
	// 群体大小
	private Integer popSize;
	// 基因群体
	private Vector<SGenome> sgenomes;
	
	private double totalFitnessScore;

	

	// 创建一个随机的二进制位串的初始群体
	public void CreateStartPopulation() {
		sgenomes = new Vector<SGenome>();
		for (int i = 0; i < Content.POP_SIZE; i++) {
			sgenomes.add(new SGenome(Content.CHROMO_LENGTH));
		}
	}

	public double calcScore(Labyrinth lab) {
		if (sgenomes == null || sgenomes.size() == 0) {
			return 0;
		}
		double total = 0;
		for (SGenome sGenome : sgenomes) {
			total += sGenome.updateFitnessScores(lab);
		}
		this.totalFitnessScore = total;
		return total;
	}

	// 杂交
	public void Crossover(Labyrinth lab) {
		Vector<SGenome> current = new Vector<SGenome>();
		while(current.size() < Content. POP_SIZE) {
			SGenome dad = this.Selection(lab);
			SGenome mum = this.Selection(lab);
			Random random = new Random();
			double tmp = random.nextInt(10)/ 10.0;
			if(tmp < Content.CROSSVOER_RATE) {
				//杂交过程
				int index = random.nextInt(Content.CHROMO_LENGTH);
				Vector<Integer> tmpDad = new Vector<Integer>(Content.CHROMO_LENGTH);
				Vector<Integer> tmpMum = new Vector<Integer>(Content.CHROMO_LENGTH);
				for (int i = 0; i < Content.CHROMO_LENGTH; i++) {
					if(i < index) {
						tmpDad.add(i, mum.getBits().get(i));
						tmpMum.add(i, mum.getBits().get(i));
					} else {
						
						tmpMum.add(i, dad.getBits().get(i));
						tmpDad.add(i, dad.getBits().get(i));
					}
				}
				
				SGenome newDadBaby1 =  new SGenome(Content.CHROMO_LENGTH);
				SGenome newDadBaby2 = new SGenome(Content.CHROMO_LENGTH);
				
				//变异 start
				Random random2 = new Random();
				for (int i = 0; i < tmpMum.size(); i++) {
					double iszj =  random2.nextInt(1000) /1000.0;
					if(iszj < Content.MUTATION_RATE) {
						//System.out.println("变异");
						tmpMum.set(i, tmpMum.get(i) == 1 ? 0 : 1);
					}
				}
				for (int i = 0; i < tmpDad.size(); i++) {
					double iszj =  random2.nextInt(1000) /1000.0;
					if(iszj < Content.MUTATION_RATE) {
						//System.out.println("变异");
						tmpDad.set(i, tmpDad.get(i) == 1 ? 0 : 1);
					}
				}
				//变异 end
				
				
				newDadBaby1.setBits(tmpDad);
				newDadBaby2.setBits(tmpMum);
				newDadBaby1.updateFitnessScores(lab);
				newDadBaby2.updateFitnessScores(lab);
				SGenome score = newDadBaby1.getFitScore()  > newDadBaby2.getFitScore()? newDadBaby1 : newDadBaby2;
				if(this.bestFitnessScore == -1) {
					this.bestFitnessScore =  score.getFitScore();
					this.basetSGenome = score;
				} else {
					if(this.bestFitnessScore <  score.getFitScore()){
						this.bestFitnessScore = score.getFitScore();
						this.basetSGenome = score;
					}
				}
			
				//添加到新的群体
				
				current.add(newDadBaby1);
				current.add(newDadBaby2);
			} else {
				current.add(dad);
				current.add(mum);
			}
		}
		 this.generation +=1;
		this.sgenomes = current;
	}

	private SGenome Selection(Labyrinth lab) {
		this.calcScore(lab);
		Random random = new Random();
		Double ran = (random.nextInt(100)) / 100.0;
		Double slice = ran * this.totalFitnessScore;

		double cfTotal = 0;
		SGenome genome = null;
		for (int i = 0; i < this.sgenomes.size(); i++) {
			SGenome tmp = sgenomes.get(i);
			cfTotal += tmp.getFitScore();
			if (cfTotal > slice) {
				genome = tmp;
				break;
			}
		}
		if(genome == null) {
			throw new RuntimeException("genome is null");
		}
		return genome;
	}

	public double getBestFitnessScore() {
		return bestFitnessScore;
	}

	public Integer getChromeLength() {
		return chromeLength;
	}

	public Double getCrossoverRate() {
		return crossoverRate;
	}

	public int getFittestGenome() {
		return fittestGenome;
	}

	public int getGeneration() {
		return generation;
	}

	public Boolean getIsBusy() {
		return isBusy;
	}

	public Double getMutationRate() {
		return mutationRate;
	}

	public Integer getPopSize() {
		return popSize;
	}

	public Vector<SGenome> getSgenomes() {
		return sgenomes;
	}

	public double getTotalFitnessScore() {
		return totalFitnessScore;
	}

	// 变异
	public void Mutate(Vector<Integer> bits) {

	}

	public void setBestFitnessScore(double bestFitnessScore) {
		this.bestFitnessScore = bestFitnessScore;
	}

	public void setChromeLength(Integer chromeLength) {
		this.chromeLength = chromeLength;
	}

	public void setCrossoverRate(Double crossoverRate) {
		this.crossoverRate = crossoverRate;
	}

	public void setFittestGenome(int fittestGenome) {
		this.fittestGenome = fittestGenome;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}

	public void setIsBusy(Boolean isBusy) {
		this.isBusy = isBusy;
	}

	public void setMutationRate(Double mutationRate) {
		this.mutationRate = mutationRate;
	}

	public void setPopSize(Integer popSize) {
		this.popSize = popSize;
	}

	public void setSgenomes(Vector<SGenome> sgenomes) {
		this.sgenomes = sgenomes;
	}

	public void setTotalFitnessScore(double totalFitnessScore) {
		this.totalFitnessScore = totalFitnessScore;
	}

	// 用新的适应性分数来更新基因组原有的适
	// 应性分数，并计算群体的最高适应性分数和适应性分数最高的那个成员。
	public void UpdateFitnessScores() {

	}

}
