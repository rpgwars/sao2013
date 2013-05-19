package pl.agh.edu.moea.main;

import java.util.ArrayList;
import java.util.List;
import org.jfree.data.xy.XYSeries;

import pl.agh.edu.moea.objective.ObjectiveFunction;
import pl.agh.edu.moea.operation.SetSelection;
import pl.agh.edu.moea.set.ContinousSolutionsSet;
import pl.agh.edu.moea.set.Solution;
import pl.agh.edu.moea.set.SolutionSet;

public class MOEA {
	
	private Parameters parameters;
	private List<SolutionSet> solutionsSetPopulation;
        private ArrayList<ArrayList<XYSeries>> results = new ArrayList<>();
	
	public MOEA(Parameters parameters){
		this.parameters = parameters;
		
		int nrOfSetsInpopulation = parameters.getNrOfSetsInPopulation();
		int nrOfSolutionsInSet = parameters.getNrOfSolutionsInSet();
		ObjectiveFunction objectiveFunction = parameters.getObjectiveFunction();
		Optimization optimization = parameters.getOptimization();
		double verticalBoundary = parameters.getFunction1Boundary();
		double horizontalBoundary = parameters.getFunction2Boundary(); 
		int decisionSpaceDimension = parameters.getDecisionSpaceDimension();
		
		solutionsSetPopulation = new ArrayList<SolutionSet>(nrOfSetsInpopulation);
		for(int i = 0; i<nrOfSetsInpopulation; i++){
			solutionsSetPopulation.add(
					new ContinousSolutionsSet(nrOfSolutionsInSet, 1, objectiveFunction,optimization,verticalBoundary,horizontalBoundary,
							decisionSpaceDimension));
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
				
                        XYSeries func1 = new XYSeries("Func1 - " + i);
                        XYSeries func2 = new XYSeries("Func2 - " + i);
                        
			for(SolutionSet ss : solutionsSetPopulation) {
				System.out.println("set ");
				for(Solution s : ss.getSolutions()) {
                                        func1.add(s.getDecisionVector()[0], s.getObjectiveVector()[0]);
                                        func2.add(s.getDecisionVector()[0], s.getObjectiveVector()[1]);
					System.out.println("decision: " +  s.getDecisionVector()[0] + " " + s.getDecisionVector()[1]); 
                                }     
			}
                        ArrayList<XYSeries> oneStepSolutions = new ArrayList<>();
                        oneStepSolutions.add(func1);
                        oneStepSolutions.add(func2);
                        results.add(oneStepSolutions);
			
			solutionsSetPopulation = newPopulation;
			
		}
		
		
	}
        
        public ArrayList<ArrayList<XYSeries>> getResults() {
            return results;
        }
	
	
}
