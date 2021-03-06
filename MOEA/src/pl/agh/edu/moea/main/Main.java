package pl.agh.edu.moea.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.SwingUtilities;

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
                if(solution.getDecisionVector()[0] < 0.1){
                        objV[1] = 0.0;
                        return objV;
                }
                if(solution.getDecisionVector()[0] < 0.2){
                    objV[1] = solution.getDecisionVector()[0] - 0.1;
                    return objV;
                }
                if(solution.getDecisionVector()[0] < 0.3){
                    objV[1] = 0.1;
                    return objV;
                }
                if(solution.getDecisionVector()[0] < 0.4){
                    objV[1] = 0.4 - solution.getDecisionVector()[0];
                    return objV;
                }
                if(solution.getDecisionVector()[0] < 0.5){
                    objV[1] = 0.0;
                    return objV;
                }
                if(solution.getDecisionVector()[0] < 0.6){
                    objV[1] = solution.getDecisionVector()[0] - 0.5;
                    return objV;
                }
                if(solution.getDecisionVector()[0] < 0.7){
                    objV[1] = 0.1;
                    return objV;
                }
                if(solution.getDecisionVector()[0] < 0.8){
                    objV[1] = 0.8 - solution.getDecisionVector()[0];
                    return objV;
                }

                objV[1] = 0.0;

                return objV;

				 
            }
        };
        
        ObjectiveFunction obj2Dim = new ObjectiveFunction() {

            @Override
            public double[] getObjectiveSpaceSolutionValues(Solution solution) {
                double[] objV = new double[2];
                
                double x = solution.getDecisionVector()[0];
                double y = solution.getDecisionVector()[1];
                objV[0] = 1 - (x-1.0/2.0)*(x-1.0/2.0);
                objV[1] = 1 - (y-1.0/2.0)*(y-1.0/2.0);
                return objV;

				 
            }
        };
        
        ObjectiveFunction obj2Dim2 = new ObjectiveFunction() {

            @Override
            public double[] getObjectiveSpaceSolutionValues(Solution solution) {
                double[] objV = new double[2];
                
                double x = solution.getDecisionVector()[0];
                double y = solution.getDecisionVector()[1];
                objV[0] = Math.sin(x*4) + Math.cos(y*16) + 1;
                objV[1] = Math.sin(x*8) + Math.cos(y*8) + 1; 
                return objV;

				 
            }
        };
          /*      
        obj = new ObjectiveFunction() {
			
            @Override
            public double[] getObjectiveSpaceSolutionValues(Solution solution) {
                double[] objV = new double[2];
                objV[0] = 0.1;
                //System.out.println("\t\t\t#" + solution.getDecisionVector());
                objV[1] = Math.sin(solution.getDecisionVector()*10)/2;


                return objV;
				
				 
            }
        };
		*/
        Parameters parameters = new Parameters();
        parameters.setDecisionSpaceWidth(1);
        parameters.setFunction1Boundary(0);
        parameters.setFunction2Boundary(0);
        parameters.setNrOfIterations(10);
        parameters.setNrOfSetsInPopulation(5);
        parameters.setNrOfSolutionsInSet(20);
        parameters.setNumberOfMutationsInIteration(3);
        parameters.setObjectiveFunction(obj2Dim2);
        parameters.setOptimization(Optimization.MAXIMALIZATION);
        parameters.setPolynomialIdstributionIndex(10);
        parameters.setProbabilitiyOfMutation(0.1);
        parameters.setDecisionSpaceDimension(2);
		
        MOEA moea = new MOEA(parameters);
        moea.start();	

    	
    	/*
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SOAFrame soaFrame = new SOAFrame();
                soaFrame.setVisible(true);
                soaFrame.savePropertiesFile();
            }
        });
        */      
                
    }

}
