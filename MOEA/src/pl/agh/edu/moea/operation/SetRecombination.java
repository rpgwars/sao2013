package pl.agh.edu.moea.operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.agh.edu.moea.fitness.FitnessEvaluator;
import pl.agh.edu.moea.main.Optimization;
import pl.agh.edu.moea.set.ContinousSolutionsSet;
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
				
				double totalFitness = fe.computeTotalFitness(solutionsA, horizontalBoundary, verticalBoundary, optimization,-1);
				int worstPosition = removeWorstSolution(solutionsA, horizontalBoundary, verticalBoundary, optimization);
				Solution worstSolution = solutionsA.remove(worstPosition);
				int bestPosition = selectBestSolution(solutionsA, solutionsB, horizontalBoundary, verticalBoundary, optimization);
				
				 
				Solution bestSolution = solutionsB.remove(bestPosition);
				
				int bestSolutionInsertionPosition = getInsertionPosition(solutionsA, bestSolution, optimization);
				solutionsA.add(bestSolutionInsertionPosition, bestSolution);
				double newTotalFitness = fe.computeTotalFitness(solutionsA, horizontalBoundary, verticalBoundary, optimization,-1);
				
				
				if(totalFitness > newTotalFitness){
					
					fitnessImproved = false; 
					solutionsA.remove(bestSolutionInsertionPosition);
					solutionsA.add(worstPosition, worstSolution);
				}
				
		}
		
		return new ContinousSolutionsSet(optimization, 
				horizontalBoundary, verticalBoundary, solutionsA);
		
	}
	
	private int removeWorstSolution(List<Solution> solutionsA, 
			double horizontalBoundary, double verticalBoundary, Optimization optimization){
		
		
		FitnessEvaluator fitnessEvaluator = new FitnessEvaluator();
		int i =0; 
		double min = Double.MAX_VALUE;
		int solutionWithWorstDominatedSpace = 0;
		for(Solution solution : solutionsA){
			double dominatedSpace = fitnessEvaluator.computeObjectiveSpaceDominatedBySolution(solutionsA, 
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
		List<Solution> tmpSolutions = new ArrayList<Solution>(solutionsA.size() + 1);
		for(Solution solutionToInsert : solutionsB){
			tmpSolutions.clear();
			tmpSolutions.addAll(solutionsA);
			
			int insertionPosition = getInsertionPosition(tmpSolutions, solutionToInsert, optimization);
				
			tmpSolutions.add(insertionPosition, solutionToInsert);
			FitnessEvaluator fe = new FitnessEvaluator();
			double totalFitness = fe.computeTotalFitness(tmpSolutions, horizontalBoundary, verticalBoundary, optimization,-1);
			if(totalFitness > maxTotalFitness){
				maxTotalFitness = totalFitness;
				bestSolutionPosition = currentSolutionPosition;
			}
				
			currentSolutionPosition++; 
		}
		
		return bestSolutionPosition;
	}
	
	private int getInsertionPosition(List<Solution> solutions, Solution solutionToInsert, Optimization optimization){
		int insertionPosition = 0;
		boolean changed; 
		for(Solution solution : solutions){
			changed=false; 
			if(solution.getObjectiveVector()[0] < solutionToInsert.getObjectiveVector()[0] && optimization == Optimization.MAXIMALIZATION){
				insertionPosition++;
				changed = true; 
			}
			if(solution.getObjectiveVector()[0] < solutionToInsert.getObjectiveVector()[0] && optimization == Optimization.MINIMIZATION){
				insertionPosition++;
				changed = true; 
				
			}
			if(!changed)
				break; 
				 
		}
		
		return insertionPosition;
	}
	
}
