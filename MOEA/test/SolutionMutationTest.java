import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pl.agh.edu.moea.objective.ObjectiveFunction;
import pl.agh.edu.moea.operation.SolutionMutation;
import pl.agh.edu.moea.set.ContinousSolutionsSet;
import pl.agh.edu.moea.set.Solution;
import pl.agh.edu.moea.set.SolutionSet;


public class SolutionMutationTest {
	
	private SolutionSet ss; 
	private SolutionMutation sm; 
	
	
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
		},null,0,0,2);
		
		sm = new SolutionMutation();

	}
	
	
	@Test
	public void mutationTest(){
		
		Solution s1 = ss.getSolutions().get(0);
		Solution s2 = ss.getSolutions().get(1); 
		
		System.out.println(s1.getDecisionVector());
		System.out.println(s2.getDecisionVector());
		for(int i = 0; i<50; i++){
			
			sm.mutateSet(ss, 1, 0, 1, 2);
			assertEquals(s1.getDecisionVector()[0] <= 1.0 && s1.getDecisionVector()[0] >= 0.0, true);
			assertEquals(s2.getDecisionVector()[0] <= 1.0 && s2.getDecisionVector()[0] >= 0.0, true);
			assertEquals(s1.getDecisionVector()[1] <= 1.0 && s1.getDecisionVector()[1] >= 0.0, true);
			assertEquals(s2.getDecisionVector()[1] <= 1.0 && s2.getDecisionVector()[1] >= 0.0, true);
			System.out.println("s1 " + s1.getDecisionVector()[0]);
			System.out.println("s1 " + s1.getDecisionVector()[1]);
			System.out.println("s2 " + s2.getDecisionVector()[0]);
			System.out.println("s2 " + s2.getDecisionVector()[1]);
			
		}
		
	}

}
