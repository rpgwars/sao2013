package pl.agh.edu.moea.operation;

import java.util.ArrayList;
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
		
		FitnessEvaluator fe = new FitnessEvaluator(); 
		boolean fitnessImproved = true;
		while(fitnessImproved && solutionsB.size() > 0){
				double totalFitness = fe.computeTotalFitness(solutionsA, horizontalBoundary, verticalBoundary, optimization);
				int worstPosition = removeWorstSolution(solutionsA, horizontalBoundary, verticalBoundary, optimization);
				
				
		}
		
		
		
		
		
		return null;
		
	}
	
	private int removeWorstSolution(List<Solution> solutionsA, 
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
		
		return solutionWithWorstDominatedSpace;
		
	}
	
	private int selectBestSolution(List<Solution> solutionsA, List<Solution> solutionsB,
			double horizontalBoundary, double verticalBoundary, Optimization optimization){
		
		double maxTotalFitness = 0; 
		int bestSolutionPosition = 0; 
		int currentSolutionPosition = 0; 
		List<Solution> tmpSolutions = new ArrayList<Solution>(solutionsA.size());
		for(Solution solutionToInsert : solutionsB){
			tmpSolutions.clear();
			tmpSolutions.addAll(solutionsA);
			
			int insertionPosition = 0; 
			for(Solution solution : tmpSolutions){
				if(solution.getObjectiveVector()[0] < solutionToInsert.getObjectiveVector()[0] && optimization == Optimization.MAXIMALIZATION){
					insertionPosition++;
					break; 
				}
				if(solution.getObjectiveVector()[0] > solutionToInsert.getObjectiveVector()[0] && optimization == Optimization.MINIMIZATION){
					insertionPosition++; 
					break;
				}
					 
			}
				
			tmpSolutions.add(insertionPosition, solutionToInsert);
			FitnessEvaluator fe = new FitnessEvaluator();
			double totalFitness = fe.computeTotalFitness(tmpSolutions, horizontalBoundary, verticalBoundary, optimization);
			if(totalFitness > maxTotalFitness){
				maxTotalFitness = totalFitness;
				bestSolutionPosition = currentSolutionPosition;
			}
				
			currentSolutionPosition++; 
		}
		
		return bestSolutionPosition;
	}
	
}
