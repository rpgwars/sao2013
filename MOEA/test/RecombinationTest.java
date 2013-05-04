import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pl.agh.edu.moea.fitness.FitnessEvaluator;
import pl.agh.edu.moea.main.Optimization;
import pl.agh.edu.moea.objective.ObjectiveFunction;
import pl.agh.edu.moea.operation.SetRecombination;
import pl.agh.edu.moea.set.ContinousSolutionsSet;
import pl.agh.edu.moea.set.Solution;
import pl.agh.edu.moea.set.SolutionSet;


public class RecombinationTest {

	
	private SolutionSet ss1; 
	private SolutionSet ss2; 
	
	
	private SolutionSet ss3; 
	private SolutionSet ss4;
	
	@Before
	public void before(){
	
		ss1 = new ContinousSolutionsSet(3, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = solution.getDecisionVector();
				objV[1] = 1 - solution.getDecisionVector();
				return objV; 
						
				
			}
		},null,0,0);
		
		ss1.getSolutions().get(0).setDecisionVector(0.2);
		ss1.getSolutions().get(1).setDecisionVector(0.6);
		ss1.getSolutions().get(2).setDecisionVector(0.7);
		
		ss2 = new ContinousSolutionsSet(3, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = solution.getDecisionVector();
				objV[1] = 1 - solution.getDecisionVector();
				return objV; 
						
				
			}
		},null,0,0);
		
		ss2.getSolutions().get(0).setDecisionVector(0.4);
		ss2.getSolutions().get(1).setDecisionVector(0.5);
		ss2.getSolutions().get(2).setDecisionVector(0.6);
		
		ss3 = new ContinousSolutionsSet(3, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = solution.getDecisionVector();
				objV[1] = 1 - solution.getDecisionVector();
				return objV; 
						
				
			}
		},null,1,1);
		
		ss3.getSolutions().get(0).setDecisionVector(0.2);
		ss3.getSolutions().get(1).setDecisionVector(0.6);
		ss3.getSolutions().get(2).setDecisionVector(0.7);
		
		
		ss4 = new ContinousSolutionsSet(3, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = solution.getDecisionVector();
				objV[1] = 1 - solution.getDecisionVector();
				return objV; 
						
				
			}
		},null,1,1);
		
		ss4.getSolutions().get(0).setDecisionVector(0.4);
		ss4.getSolutions().get(1).setDecisionVector(0.5);
		ss4.getSolutions().get(2).setDecisionVector(0.6);
		
		
	}
	
	
	@Test
	public void solutionRecombinationTest(){
		
		SetRecombination setRecombination = new SetRecombination();
		SolutionSet ss = 
				setRecombination.recombine(ss1, ss2, 0, 0, Optimization.MAXIMALIZATION);
		
		
		FitnessEvaluator fe = new FitnessEvaluator(); 
		assertEquals(fe.computeTotalFitness(ss.getSolutions(), 0, 0, Optimization.MAXIMALIZATION, -1),0.36,0.0001); 
		
		
	}
	
	@Test
	public void solutionRecombinationTest2(){
		
		SetRecombination setRecombination = new SetRecombination();
		SolutionSet ss = 
				setRecombination.recombine(ss3, ss4, 1, 1, Optimization.MINIMIZATION);
		
		
		FitnessEvaluator fe = new FitnessEvaluator(); 
		assertEquals(fe.computeTotalFitness(ss.getSolutions(), 1, 1, Optimization.MINIMIZATION, -1),0.36,0.0001); 
		
		
	}
}
