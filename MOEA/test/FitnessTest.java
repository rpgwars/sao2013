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
	private FitnessEvaluator fe; 
	
	
	@Before
	public void before(){
	
		ss = new ContinousSolutionsSet(2, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = solution.getDecisionVector();
				objV[1] = solution.getDecisionVector();
				return objV; 
						
				
			}
		},null,0,0);
		
		fe = new FitnessEvaluator();
		ss.getSolutions().get(0).setDecisionVector(0.2);
		ss.getSolutions().get(1).setDecisionVector(0.6);
		
		
	}
	
	/*
	@Test
	public void fitnessTestMinimization(){
		
		fe.assignFitness(ss.getSolutions().size(), Optimization.MINIMIZATION, 1, 1, ss);
		
		assertEquals(ss.getSolutions().get(0).getFitness(),0.96,0.00001);
		assertEquals(ss.getSolutions().get(1).getFitness(),0.48,0.00001);
		
		
		fe.computeTotalFitness(ss.getSolutions(), 1, 1, Optimization.MINIMIZATION);
		
	}
	*/
	@Test
	public void fitnessTestMaximization(){
		
		fe.assignFitness(ss.getSolutions().size(), Optimization.MAXIMALIZATION, 0, 0, ss);
		
//		assertEquals(ss.getSolutions().get(0).getFitness(),0.22,0.00001);
//		assertEquals(ss.getSolutions().get(1).getFitness(),0.54,0.00001);
//		assertEquals(ss.getSolutions().get(0).getFitness(),0,0.00001);
//		assertEquals(ss.getSolutions().get(1).getFitness(),0.54,0.00001);
		System.out.println("------------------");
		System.out.println(ss.getSolutions().get(0).getFitness() + " " + ss.getSolutions().get(0).getDecisionVector());
		System.out.println(ss.getSolutions().get(1).getFitness()+ " " + ss.getSolutions().get(1).getDecisionVector());
	// 0.50 , 0.18	
	}
	
}
