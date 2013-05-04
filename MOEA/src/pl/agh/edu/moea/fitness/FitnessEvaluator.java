package pl.agh.edu.moea.fitness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor.SetterOnlyReflection;

import pl.agh.edu.moea.main.Optimization;
import pl.agh.edu.moea.set.Solution;
import pl.agh.edu.moea.set.SolutionSet;

public class FitnessEvaluator {
	
	
	private class Fitness{
		public double fitness; 
		public double verticalBoundary; 
		public double horizontalBoundary; 
	}
	
	//granica lewa i dolna dla maksymalizacji albo prawa i gorna dla minimalizacji, fitness zawsze im wiekszy tym lepszy
	public void assignFitness(int nrOfSolutionsToBeRemoved,
			Optimization optimization, double horizontalBoundary,
			double verticalBoundary, SolutionSet solutionsSet) {
		
		
		double originalHorizontalBoundary = horizontalBoundary; 
		double originalVerticalBoundary = verticalBoundary; 
		List<Solution> solutions = solutionsSet.getSolutions();
		
		int solutionWithoutFitness = 0; 
		Collections.sort(solutions);
		double totalSetFitness = computeTotalFitness(solutions, originalHorizontalBoundary, originalVerticalBoundary, optimization, -1);
		List<Solution> tmp = new ArrayList<Solution>(solutions.size());
		List<Solution> tmp2 = new ArrayList<Solution>(solutions.size());
		tmp2.addAll(solutions);
		 
		for(Solution solution : solutions){
			
			
			tmp.clear();
			
			tmp.addAll(tmp2);
			
			double totalFitness = 0;
			for(int i = 0; i<nrOfSolutionsToBeRemoved; i++){
				horizontalBoundary = originalHorizontalBoundary; 
				verticalBoundary = originalVerticalBoundary;
				List<Solution> randomSolutions = solutionsSet.getRandomSolutions(i,solutionWithoutFitness);
				randomSolutions.add(solution);
				
				//Collections.sort(randomSolutions);
				
				//optimization.removeDominatedSolutions(randomSolutions);
				
				
				double remainingFitness = 0;
		
		
				tmp.removeAll(randomSolutions);
		
				Collections.sort(tmp);
				List<Solution> dominatedSolutions = optimization.removeDominatedSolutions(tmp);
		
				
				for(Solution dominatingSolution : tmp){
					Fitness fitness = computeFitness(dominatingSolution, horizontalBoundary, verticalBoundary, optimization);
					remainingFitness += fitness.fitness;
					horizontalBoundary = fitness.horizontalBoundary; 
					verticalBoundary = fitness.verticalBoundary;
				}
		
				tmp.addAll(randomSolutions);
				tmp.addAll(dominatedSolutions);
		

				
				//System.out.println("partial fit " + solution.getDecisionVector() + " " + x);
				totalFitness += computeAlpha(i+1, solutions.size(), nrOfSolutionsToBeRemoved)*(totalSetFitness-remainingFitness)/(double)(i+1);
				//if(i - 1 == nrOfSolutionsToBeRemoved)
					
				//System.out.println(solutions.get(1).getDecisionVector());
				/* 
				for(Solution dominatingSolution : randomSolutions){
					
						
					Fitness fitness = computeFitness(dominatingSolution, horizontalBoundary, verticalBoundary, optimization);
					
					totalFitness += computeAlpha(i+1, solutions.size(), nrOfSolutionsToBeRemoved)*fitness.fitness/(double)(i+1); 
					horizontalBoundary = fitness.horizontalBoundary; 
					verticalBoundary = fitness.verticalBoundary;
					
				}
				*/

			}
			
			solution.setFitness(totalFitness);
			solutionWithoutFitness++; 
		}
	
	}
	
	public double computeTotalFitness(List<Solution> solutionList, double horizontalBoundary, double verticalBoundary,
			Optimization optimization, int ignoreSolutionNr){
		double totalFitness = 0; 
		List<Solution> tmpSolutions = new ArrayList<Solution>(solutionList.size());
		tmpSolutions.addAll(solutionList);
		
		if(ignoreSolutionNr != -1){
			tmpSolutions.remove(ignoreSolutionNr);
		}
		
		optimization.removeDominatedSolutions(tmpSolutions);
		

		for(Solution dominatingSolution : tmpSolutions){
			
			Fitness fitness = computeFitness(dominatingSolution, horizontalBoundary, verticalBoundary, optimization);
		
			totalFitness += fitness.fitness; 
			horizontalBoundary = fitness.horizontalBoundary; 
			verticalBoundary = fitness.verticalBoundary;
			
		}
		
		//zdominowane musza wrocic, to jest opulacja calego zbioru
		return totalFitness;
	}
	
	
	public double computeObjectiveSpaceDominatedBySolution(List<Solution> solutionList, 
			double horizontalBoundary, double verticalBoundary, Optimization optimization, int solutionPosition){
		
		double total = 
				computeTotalFitness(solutionList, horizontalBoundary, verticalBoundary, optimization, -1);
		
		
		double remaining =
				computeTotalFitness(solutionList, horizontalBoundary, verticalBoundary, optimization, solutionPosition);

		
		return total - remaining;
	}
	
	private Fitness computeFitness(Solution dominatingSolution, double horizontalBoundary, double verticalBoundary, Optimization optimization){
		
		Fitness fitness = new Fitness();
		switch(optimization){
		case MAXIMALIZATION:
			fitness.fitness = (dominatingSolution.getObjectiveVector()[1]-horizontalBoundary)*(dominatingSolution.getObjectiveVector()[0]-verticalBoundary);
			fitness.verticalBoundary = dominatingSolution.getObjectiveVector()[0];
			fitness.horizontalBoundary = horizontalBoundary;
			return fitness;
			
		case MINIMIZATION:
			
			fitness.fitness = (verticalBoundary - dominatingSolution.getObjectiveVector()[0])*(horizontalBoundary - dominatingSolution.getObjectiveVector()[1]);
			fitness.verticalBoundary = verticalBoundary;
			fitness.horizontalBoundary = dominatingSolution.getObjectiveVector()[1];
			return fitness;
		
			default:
				return null; 
		}
		
	}
	
	private double computeAlpha(int i, int sizeOfSet, int nrOfSolutionsToBeRemoved){
		
		double alpha = 1; 
		for(int j = 1; j<i; j++){
			alpha*=(double)(nrOfSolutionsToBeRemoved - j)/(double)(sizeOfSet - j);
		
		}
		
		return alpha;
		
	}

}
