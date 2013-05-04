package pl.agh.edu.moea.operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.agh.edu.moea.main.Optimization;
import pl.agh.edu.moea.set.Solution;
import pl.agh.edu.moea.set.SolutionSet;

public class SetSelection {
	
	
	
	public List<SolutionSet> doMatingAndEnvironmentalSelection(List<SolutionSet> solutionsSetList,
			double horizontalBoundary, double verticalBoundary, Optimization optimization){
		
		List<SolutionSet> newSetsPopulation = new ArrayList<SolutionSet>
			(solutionsSetList.size()*(solutionsSetList.size() - 1));
		
		SetRecombination setRecombination = new SetRecombination();
		
		
		for(SolutionSet solutionSetA : solutionsSetList){
			for(SolutionSet solutionSetB : solutionsSetList){
				SolutionSet solutionSetACopy = solutionSetA.getSetCopy();
				SolutionSet solutionSetBCopy = solutionSetB.getSetCopy();
				newSetsPopulation.add(setRecombination.recombine(solutionSetACopy, solutionSetBCopy,
						horizontalBoundary, verticalBoundary, optimization));
			}
		}
		
		
		
		
		Collections.sort(newSetsPopulation, solutionsSetList.get(0).getComparator());
		for(int i = newSetsPopulation.size() - 1; i>=solutionsSetList.size(); i--){
			newSetsPopulation.remove(i);
		}
		return newSetsPopulation; 
	}
	
	

}
