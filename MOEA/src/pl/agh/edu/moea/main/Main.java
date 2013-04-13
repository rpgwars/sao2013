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
	
	
	static class Test implements Comparable<Test>{

		int x; 
		public Test(int x){
			this.x = x; 
		}
		
		@Override
		public int compareTo(Test o) {
			return x - o.x;
			
		}
		
	}
	
	public static void main(String[] args){
		
		
		SolutionSet test = new ContinousSolutionsSet(20, 1, new ObjectiveFunction() {
				
				@Override
				public double[] getObjectiveSpaceSolutionValues(Solution solution) {
					double[] objV = new double[2];
					objV[0] = 0.4;
					objV[1] = (Math.cos(13*solution.getDecisionVector())+1)/2;
//					objV[0] = 0.3;
//					if(solution.getDecisionVector() < 0.3)
//						objV[1] = solution.getDecisionVector();
//					if(solution.getDecisionVector() > 0.7)
//						objV[1] = 1 - solution.getDecisionVector();
//					if(solution.getDecisionVector() > 0.3 && solution.getDecisionVector() < 0.7)
//						objV[1] = 0.3;
					return objV; 
				}
			},Optimization.MAXIMALIZATION,0,0); 
		
		
		
		test.mutate(1.0, 0, 0.9, 0, 5);
		System.out.println("---------------------------------");
		test.printFitness();
		
		
	}

}
