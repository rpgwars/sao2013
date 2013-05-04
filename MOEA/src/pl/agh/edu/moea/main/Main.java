package pl.agh.edu.moea.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.agh.edu.moea.objective.ObjectiveFunction;
import pl.agh.edu.moea.operation.SolutionMutation;
import pl.agh.edu.moea.set.ContinousSolutionsSet;
import pl.agh.edu.moea.set.Solution;
import pl.agh.edu.moea.set.SolutionSet;

public class Main {
	
	public static void main(String[] args){
		
		ObjectiveFunction obj = new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = 0.4;
				if(solution.getDecisionVector() < 0.1){
					objV[1] = 0.0;
					return objV;
				}
				if(solution.getDecisionVector() < 0.2){
					objV[1] = solution.getDecisionVector() - 0.1;
					return objV;
				}
				if(solution.getDecisionVector() < 0.3){
					objV[1] = 0.1;
					return objV;
				}
				if(solution.getDecisionVector() < 0.4){
					objV[1] = 0.4 - solution.getDecisionVector();
					return objV;
				}
				if(solution.getDecisionVector() < 0.5){
					objV[1] = 0.0;
					return objV;
				}
				if(solution.getDecisionVector() < 0.6){
					objV[1] = solution.getDecisionVector() - 0.5;
					return objV;
				}
				if(solution.getDecisionVector() < 0.7){
					objV[1] = 0.1;
					return objV;
				}
				if(solution.getDecisionVector() < 0.8){
					objV[1] = 0.8 - solution.getDecisionVector();
					return objV;
				}

				objV[1] = 0.0;

				return objV;
				
				 
			}
		};
		
		Parameters parameters = new Parameters();
		parameters.setDecisionSpaceWidth(1);
		parameters.setFunction1Boundary(0);
		parameters.setFunction2Boundary(0);
		parameters.setNrOfIterations(10);
		parameters.setNrOfSetsInPopulation(5);
		parameters.setNrOfSolutionsInSet(20);
		parameters.setNumberOfMutationsInIteration(3);
		parameters.setObjectiveFunction(obj);
		parameters.setOptimization(Optimization.MAXIMALIZATION);
		parameters.setPolynomialIdstributionIndex(2);
		parameters.setProbabilitiyOfMutation(0.7);
		
		MOEA moea = new MOEA(parameters);
		moea.start();
		
		
		
	}

}
