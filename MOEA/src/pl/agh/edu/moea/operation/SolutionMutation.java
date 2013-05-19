package pl.agh.edu.moea.operation;

import java.util.Random;

import pl.agh.edu.moea.set.Solution;
import pl.agh.edu.moea.set.SolutionSet;

public class SolutionMutation {
	
	
	public void mutateSet(SolutionSet set, double upperBound, double lowerBound, double probabilityOfMutation, int distributionIndex){
		
		Random random = new Random();
		
		for(Solution solution : set.getSolutions()){
			for(int i = 0; i<solution.getDecisionVector().length; i++){
				if(random.nextDouble() < probabilityOfMutation){
					double delta = Math.min(upperBound - solution.getDecisionVector()[i], solution.getDecisionVector()[i] - lowerBound)
							/(upperBound - lowerBound);
					double r = random.nextDouble();
			
					double delta_q;
					if(r <= 0.5){
						delta_q = (2.0*r + (1.0-2*r)*Math.pow((1.0-delta),distributionIndex+1));
					
						delta_q = Math.pow(delta_q, 1.0/(distributionIndex+1.0)) - 1;
					
					}
					else{
						delta_q = (2*(1-r) + 2*(r -0.5)*Math.pow((1-delta),distributionIndex+1));
					
						delta_q = -Math.pow(delta_q,1.0/(distributionIndex + 1)) + 1;
				
					}
					double[] decisionVector = solution.getDecisionVector();
					decisionVector[i] = decisionVector[i] + delta_q*(upperBound - lowerBound);
					solution.setDecisionVector(decisionVector); 
					
				}
			}
		}
		
		
		
	}
	

}
