package pl.agh.edu.moea.operation;

import java.util.Collections;
import java.util.List;

import pl.agh.edu.moea.fitness.FitnessEvaluator;
import pl.agh.edu.moea.main.Optimization;
import pl.agh.edu.moea.set.Solution;
import pl.agh.edu.moea.set.SolutionSet;

public class SetRecombination {
	
	public SolutionSet recombine(SolutionSet setA, SolutionSet setB, 
			double horizontalBoundary, double verticalBoundary, Optimization optimization){
		
		List<Solution> solutionsA = setA.getSolutions();
		List<Solution> solutionsB = setB.getSolutions();
		
		Collections.sort(solutionsA);
		Collections.sort(solutionsB);
		
		return null;
		
	}
	
	private void removeWorstSolution(List<Solution> solutionsA, List<Solution> solutionsB, 
			double horizontalBoundary, double verticalBoundary, Optimization optimization){
		
		
		FitnessEvaluator fitnessEvaluator = new FitnessEvaluator();
		int i =0; 
		double min = Double.MAX_VALUE;
		int solutionWithWorstDominatedSpace = 0;
		for(Solution solution : solutionsA){
			double dominatedSpace = fitnessEvaluator.computeObjectiveSpaceDominatedBySolution(solutionsA, solution,
					horizontalBoundary, verticalBoundary, optimization, i);
			
			if(dominatedSpace < min){
				min = dominatedSpace;
				solutionWithWorstDominatedSpace = i; 
			}
			i++;
		}
		
		solutionsA.remove(solutionWithWorstDominatedSpace);
		
	}
	
	private double insertBestSolution(List<Solution> solutionsA, List<Solution> solutionsB){
		
		for(Solution solutionToInsert : solutionsB){

			//Collections.binarySearch(solutionsA, solutionToInsert.getObjectiveVector()[0])
			
		}
		
		return -100;
	}

}
