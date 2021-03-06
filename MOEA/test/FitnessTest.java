import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pl.agh.edu.moea.fitness.FitnessEvaluator;
import pl.agh.edu.moea.main.Optimization;
import pl.agh.edu.moea.objective.ObjectiveFunction;
import pl.agh.edu.moea.set.ContinousSolutionsSet;
import pl.agh.edu.moea.set.Solution;
import pl.agh.edu.moea.set.SolutionSet;


public class FitnessTest {

	private SolutionSet ss; 
	private SolutionSet ss2; 
	private FitnessEvaluator fe; 
	
	
	@Before
	public void before(){
	
		ss = new ContinousSolutionsSet(2, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = solution.getDecisionVector()[0];
				objV[1] = solution.getDecisionVector()[0];
				return objV; 
						
				
			}
		},null,0,0,1);
		
		fe = new FitnessEvaluator();
		ss.getSolutions().get(0).setDecisionVector(new double[] { 0.2 });
		ss.getSolutions().get(1).setDecisionVector(new double[] { 0.6 });
		
		ss2 = new ContinousSolutionsSet(2, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = solution.getDecisionVector()[0];
				objV[1] = solution.getDecisionVector()[0];
				return objV; 
						
				
			}
		},null,1,1,1);
		
		fe = new FitnessEvaluator();
		ss2.getSolutions().get(0).setDecisionVector(new double[] { 0.3 });
		ss2.getSolutions().get(1).setDecisionVector(new double[] { 0.6 });
	}
	

	@Test
	public void fitnessTestMinimization(){
		
		fe.assignFitness(ss2.getSolutions().size(), Optimization.MINIMIZATION, 1, 1, ss2);
		
		assertEquals(ss2.getSolutions().get(0).getFitness(),0.575,0.00001);
		assertEquals(ss2.getSolutions().get(1).getFitness(),0.245,0.00001);


	}
	
	@Test
	public void fitnessTestMaximization(){
		
		fe.assignFitness(ss.getSolutions().size(), Optimization.MAXIMALIZATION, 0, 0, ss);
		
		assertEquals(ss.getSolutions().get(0).getFitness(),0.18,0.00001);
		assertEquals(ss.getSolutions().get(1).getFitness(),0.50,0.00001);


	}
	
	@Test
	public void fitnessTest(){
		
		ss = new ContinousSolutionsSet(4, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = 1 - solution.getDecisionVector()[0];
				objV[1] = solution.getDecisionVector()[0];
				return objV; 
						
				
			}
		},null,0,0,1);
		
		fe = new FitnessEvaluator();
		ss.getSolutions().get(0).setDecisionVector(new double[] { 0.2 });
		ss.getSolutions().get(1).setDecisionVector(new double[] { 0.6 });
		ss.getSolutions().get(2).setDecisionVector(new double[] { 0.6 });
		ss.getSolutions().get(3).setDecisionVector(new double[] { 0.6 });
		Solution s = ss.getSolutions().get(0);
		
		
		fe.assignFitness(2, Optimization.MAXIMALIZATION, 0, 0, ss);
		
		assertEquals(s.getFitness(),0.093333333,0.00001);
		
		
		
	}
	
	
}
