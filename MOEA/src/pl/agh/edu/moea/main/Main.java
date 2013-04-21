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
		
		
		SolutionSet test = new ContinousSolutionsSet(30, 1, new ObjectiveFunction() {
				
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
					//0.8 - 0.6 0.4 - 0.2
					objV[1] = 0.0;
//					objV[1] = (Math.sin(13*solution.getDecisionVector())+1)/2;
					return objV;
						
					
//					objV[0] = 0.3;
//					if(solution.getDecisionVector() < 0.3)
//						objV[1] = solution.getDecisionVector();
//					if(solution.getDecisionVector() > 0.7)
//						objV[1] = 1 - solution.getDecisionVector();
//					if(solution.getDecisionVector() > 0.3 && solution.getDecisionVector() < 0.7)
//						objV[1] = 0.3;
					 
				}
			},Optimization.MAXIMALIZATION,0,0); 
		
		
		
		test.mutate(1.0, 0, 0.4, 0, 20);
		System.out.println("---------------------------------");
		test.printFitness();
		
		
	}

}
