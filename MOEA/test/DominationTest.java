import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pl.agh.edu.moea.main.Optimization;
import pl.agh.edu.moea.objective.ObjectiveFunction;
import pl.agh.edu.moea.set.ContinousSolutionsSet;
import pl.agh.edu.moea.set.Solution;
import pl.agh.edu.moea.set.SolutionSet;


public class DominationTest {
	
	private SolutionSet ss;
	private SolutionSet ss2;
	
	
	
	@Before
	public void before(){
	
		ss = new ContinousSolutionsSet(10, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = solution.getDecisionVector();
				objV[1] = solution.getDecisionVector();
				return objV; 
						
				
			}
		},null,0,0);
	
		ss.getSolutions().get(0).setDecisionVector(0.0);
		ss.getSolutions().get(1).setDecisionVector(0.1);
		ss.getSolutions().get(2).setDecisionVector(0.2);
		ss.getSolutions().get(3).setDecisionVector(0.3);
		ss.getSolutions().get(4).setDecisionVector(0.4);
		ss.getSolutions().get(5).setDecisionVector(0.5);
		ss.getSolutions().get(6).setDecisionVector(0.6);
		ss.getSolutions().get(7).setDecisionVector(0.7);
		ss.getSolutions().get(8).setDecisionVector(0.8);
		ss.getSolutions().get(9).setDecisionVector(0.9);
		
		ss2 = new ContinousSolutionsSet(10, 1, new ObjectiveFunction() {
			
			@Override
			public double[] getObjectiveSpaceSolutionValues(Solution solution) {
				double[] objV = new double[2];
				objV[0] = solution.getDecisionVector();
				objV[1] = solution.getDecisionVector();
				return objV; 
						
				
			}
		},null,0,0);
	
		ss2.getSolutions().get(0).setDecisionVector(0.0);
		ss2.getSolutions().get(1).setDecisionVector(0.1);
		ss2.getSolutions().get(2).setDecisionVector(0.2);
		ss2.getSolutions().get(3).setDecisionVector(0.3);
		ss2.getSolutions().get(4).setDecisionVector(0.4);
		ss2.getSolutions().get(5).setDecisionVector(0.5);
		ss2.getSolutions().get(6).setDecisionVector(0.6);
		ss2.getSolutions().get(7).setDecisionVector(0.7);
		ss2.getSolutions().get(8).setDecisionVector(0.8);
		ss2.getSolutions().get(9).setDecisionVector(0.9);

		
	}
	
	
	@Test
	public void testDominatedMax(){
		
		Optimization.MAXIMALIZATION.removeDominatedSolutions(ss.getSolutions());
		assertEquals(ss.getSolutions().size(),1);
		assertEquals(ss.getSolutions().get(0).getDecisionVector(), 0.9,0.00001);
		
	}
	
	@Test
	public void testDominatedMin(){
		
		Optimization.MINIMIZATION.removeDominatedSolutions(ss2.getSolutions());
		assertEquals(ss2.getSolutions().size(),1);
		assertEquals(ss2.getSolutions().get(0).getDecisionVector(), 0.0,0.00001);
		
	}

}
