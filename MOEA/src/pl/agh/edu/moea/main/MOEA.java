package pl.agh.edu.moea.main;

import java.util.ArrayList;
import java.util.List;

import pl.agh.edu.moea.objective.ObjectiveFunction;
import pl.agh.edu.moea.operation.SetSelection;
import pl.agh.edu.moea.set.ContinousSolutionsSet;
import pl.agh.edu.moea.set.Solution;
import pl.agh.edu.moea.set.SolutionSet;

public class MOEA {
	
	private Parameters parameters;
	private List<SolutionSet> solutionsSetPopulation;
	
	public MOEA(Parameters parameters){
		this.parameters = parameters;
		
		int nrOfSetsInpopulation = parameters.getNrOfSetsInPopulation();
		int nrOfSolutionsInSet = parameters.getNrOfSolutionsInSet();
		ObjectiveFunction objectiveFunction = parameters.getObjectiveFunction();
		Optimization optimization = parameters.getOptimization();
		double verticalBoundary = parameters.getFunction1Boundary();
		double horizontalBoundary = parameters.getFunction2Boundary(); 
		
		solutionsSetPopulation = new ArrayList<SolutionSet>(nrOfSetsInpopulation);
		for(int i = 0; i<nrOfSetsInpopulation; i++){
			solutionsSetPopulation.add(
					new ContinousSolutionsSet(nrOfSolutionsInSet, 1, objectiveFunction,optimization,verticalBoundary,horizontalBoundary));
		}
		
		
		
	}
	
	
	public void start(){
		
		SetSelection setSelection = new SetSelection();
		
		for(int i =0; i<parameters.getNrOfIterations(); i++){
			
			for(SolutionSet solutionSet : solutionsSetPopulation){
				solutionSet.mutate(parameters.getDecisionSpaceWidth(), 0,
						parameters.getProbabilitiyOfMutation(), parameters.getPolynomialIdstributionIndex(), parameters.getNumberOfMutationsInIteration());
			}
			
			
			
			List<SolutionSet> newPopulation = 
					setSelection.doMatingAndEnvironmentalSelection(solutionsSetPopulation,
							parameters.getFunction1Boundary(),parameters.getFunction2Boundary(), parameters.getOptimization());
				
			for(SolutionSet ss : solutionsSetPopulation){
				System.out.println("set ");
				for(Solution s : ss.getSolutions())
					System.out.println("decision: " +  s.getDecisionVector()); 
			}
			
			solutionsSetPopulation = newPopulation;
		}
		
		
	}
	
	

}
