package pl.agh.edu.moea.main;

import java.awt.BorderLayout;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;
import javax.swing.Renderer;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.plot.CombinedCategoryPlot;
import org.jfree.experimental.chart.plot.CombinedXYPlot;
import pl.agh.edu.moea.objective.ObjectiveFunction;
import pl.agh.edu.moea.set.Solution;




public class SOAFrame extends JFrame {

    public SOAFrame() {
        loadParametersFromPropertiesFile();
        initBasicFrameParameters();
        initParameterPanel();
        initChartsTabbedPane();
        initOtherComponents();
        initFrameLayout();
    }
    
    private double getFunc(double x, int func) {
        // "f(x) = x", "f(x) = 1", "f(x) = sin(x)", "f(x) = cos(x)" 
        JComboBox f = (func == 1 ? function1ComboBox : function2ComboBox);
        
        switch(f.getSelectedIndex()) {
            case 0:
                return x;
            case 1:
                return 1;
            case 2:
                return Math.sin(x*10)/2.0 + 0.5;
            case 3:
                return Math.cos(x);
            case 4: {
                if(x < 0.1){
                    return 0.0;
                     
                }
                if(x < 0.2){
                    return x - 0.1;
                }
                if(x < 0.3){
                    return 0.1;
                }
                if(x < 0.4){
                    return 0.4 - x;
                }
                if(x < 0.5){
                    return 0.0;
                }
                if(x < 0.6){
                    return x - 0.5;
                }
                if(x < 0.7){
                    return 0.1;
                }
                if(x < 0.8){
                    return 0.8 - x;

                }
                return 0.0;
            }
            case 5:
                return 1-x;
        }
        
        return 0.0;
    }
    
    
        
    
    private void initOtherComponents() {
        startButton = new JButton("START");
        startButton.addActionListener(new ActionListener() {
            

            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);
                savePropertiesFile();
                ObjectiveFunction obj = new ObjectiveFunction() {

                    @Override
                    public double[] getObjectiveSpaceSolutionValues(Solution solution) {
                        double[] objV = new double[2];
                        objV[0] = getFunc(solution.getDecisionVector(), 1);
                        objV[1] = getFunc(solution.getDecisionVector(), 2);
                        /*
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
                        */
                        //objV[1] = 0.0;
                        return objV;
                    }
                };
/*
                obj = new ObjectiveFunction() {

                    @Override
                    public double[] getObjectiveSpaceSolutionValues(Solution solution) {
                        double[] objV = new double[2];
                        objV[0] = 0.1;
                        
                        objV[1] = Math.sin(solution.getDecisionVector()*10)/2;


                        return objV;


                    }
                    
                };
             */
                parameters.setObjectiveFunction(obj);
                
                MOEA moea = new MOEA(parameters);
                moea.start();
                System.out.println("END");
                results = moea.getResults();
                if (results != null && !results.isEmpty()) {
                    resultsSlider.setEnabled(true);
                }
  
                
                startButton.setEnabled(true);
            }
        });
    }                    
          
   
    
    public void savePropertiesFile() {
        Properties prop = new Properties();
        
        prop.setProperty("soa.solutions_in_set", Integer.toString(parameters.getNrOfSolutionsInSet()));
        prop.setProperty("soa.sets_in_population", Integer.toString(parameters.getNrOfSetsInPopulation()));
        prop.setProperty("soa.iterations", Integer.toString(parameters.getNrOfIterations()));
        prop.setProperty("soa.mutations_in_iteration", Integer.toString(parameters.getNumberOfMutationsInIteration()));
        prop.setProperty("soa.plynomial_distrubution_index", Integer.toString(parameters.getPolynomialIdstributionIndex()));
        prop.setProperty("soa.probability_of_mutation", Double.toString(parameters.getProbabilitiyOfMutation()));
        prop.setProperty("soa.function_1_boundary", Double.toString(parameters.getFunction1Boundary()));
        prop.setProperty("soa.function_2_boundary", Double.toString(parameters.getFunction2Boundary()));
        prop.setProperty("soa.decision_space_width", Double.toString(parameters.getDecisionSpaceWidth()));
        prop.setProperty("soa.optimization", (parameters.getOptimization() != null ? parameters.getOptimization().toString() : "MAXIMALIZATION"));
        
        File f = new File(propertiesFile);
        
        try {
            if (!f.exists())
                f.createNewFile();
            
            prop.store(new FileOutputStream(f), null);
        } catch (IOException ex) {
            Logger.getLogger(SOAFrame1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadParametersFromPropertiesFile() {
        Properties prop = new Properties();
           
        try {
            
            File f = new File(propertiesFile);
            if (!f.exists())
                f.createNewFile();
        
            prop.load(new FileInputStream(propertiesFile));
            
            parameters.setNrOfIterations(Integer.parseInt(prop.getProperty("soa.iterations", "1")));
            parameters.setNrOfSetsInPopulation(Integer.parseInt(prop.getProperty("soa.sets_in_population", "1")));
            parameters.setNrOfSolutionsInSet(Integer.parseInt(prop.getProperty("soa.solutions_in_set", "10")));
            parameters.setNumberOfMutationsInIteration(Integer.parseInt(prop.getProperty("soa.mutations_in_iteration", "3")));
            parameters.setPolynomialIdstributionIndex(Integer.parseInt(prop.getProperty("soa.plynomial_distrubution_index", "2")));
            parameters.setDecisionSpaceWidth(Double.parseDouble(prop.getProperty("soa.decision_space_width", "1")));
            parameters.setFunction1Boundary(Double.parseDouble(prop.getProperty("soa.function_1_boundary", "0")));
            parameters.setFunction2Boundary(Double.parseDouble(prop.getProperty("soa.function_2_boundary", "0")));
            parameters.setProbabilitiyOfMutation(Double.parseDouble(prop.getProperty("soa.probability_of_mutation", "0.7")));
            parameters.setOptimization(prop.getProperty("soa.optimization", "MAXIMALIZATION").equals("MAXIMALIZATION") ? Optimization.MAXIMALIZATION : Optimization.MINIMIZATION);
            
        } catch (IOException ex) {
            Logger.getLogger(SOAFrame1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initLabels() {
        numberOfIterationsLabel = new JLabel("iterations");
        numberOfSolutionsInSetLabel = new JLabel("solutions in set");
        numberOfSetsInPopulationLabel = new JLabel("sets in population");
        numberOfMutationsInIterationLabel = new JLabel("mutations in iteration");
        probabilitiyOfMutationLabel = new JLabel("probability of mutation");
        polynomialDstributionIndexLabel = new JLabel("polynomial dstribution index");
        decisionSpaceWidthLabel = new JLabel("decision space width");
        function1BoundaryLabel = new JLabel("function 1 boundary");
        function2BoundaryLabel = new JLabel("function 2 boundary");
        optimizationLabel = new JLabel("optimization");
        function1Label = new JLabel("function 1");
        function2Label = new JLabel("function 2");
    }
    
    private void initSpinners() {
        numberOfIterationsSpinner = new JSpinner();
        numberOfSolutionsInSetSpinner = new JSpinner();
        numberOfSetsInPopulationSpinner = new JSpinner();
        numberOfMutationsInIterationSpinner = new JSpinner();
        probabilitiyOfMutationSpinner = new JSpinner();
        polynomialDstributionIndexSpinner = new JSpinner();
        decisionSpaceWidthSpinner = new JSpinner();
        function1BoundarySpinner = new JSpinner();
        function2BoundarySpinner = new JSpinner();
        
        assignSpinnersModels();
        assingSpinnersChangeListeners();
    }
    
    private void assignSpinnersModels() {
        numberOfIterationsSpinner.setModel(new SpinnerNumberModel(parameters.getNrOfIterations(), new Integer(1), null, new Integer(1)));
        numberOfSolutionsInSetSpinner.setModel(new SpinnerNumberModel(parameters.getNrOfSolutionsInSet(), 1, null, 1));
        numberOfSetsInPopulationSpinner.setModel(new SpinnerNumberModel(parameters.getNrOfSetsInPopulation(), 1, null, 1));
        numberOfMutationsInIterationSpinner.setModel(new SpinnerNumberModel(parameters.getNumberOfMutationsInIteration(), 1, null, 1));
        probabilitiyOfMutationSpinner.setModel(new SpinnerNumberModel(parameters.getProbabilitiyOfMutation(), 0.0, 1.0, 0.01));
        polynomialDstributionIndexSpinner.setModel(new SpinnerNumberModel(parameters.getPolynomialIdstributionIndex(), 1, null, 1));
        decisionSpaceWidthSpinner.setModel(new SpinnerNumberModel(parameters.getDecisionSpaceWidth(), 0.0, null, 0.1));
        function1BoundarySpinner.setModel(new SpinnerNumberModel(parameters.getFunction1Boundary(), 0.0, null, 0.1));;
        function2BoundarySpinner.setModel(new SpinnerNumberModel(parameters.getFunction2Boundary(), 0.0, null, 0.1));;
    }
    
    private void assingSpinnersChangeListeners() {
        numberOfIterationsSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                parameters.setNrOfIterations(Integer.parseInt(numberOfIterationsSpinner.getValue().toString()));
                resultsSlider.setMaximum(Integer.parseInt(numberOfIterationsSpinner.getValue().toString()));
            }    
        });
        
        numberOfSolutionsInSetSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                parameters.setNrOfSolutionsInSet(Integer.parseInt(numberOfSolutionsInSetSpinner.getValue().toString()));
            }    
        });
        
        numberOfSetsInPopulationSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                parameters.setNrOfSetsInPopulation(Integer.parseInt(numberOfSetsInPopulationSpinner.getValue().toString()));
            }    
        });
        
        numberOfMutationsInIterationSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                parameters.setNumberOfMutationsInIteration(Integer.parseInt(numberOfMutationsInIterationSpinner.getValue().toString()));
            }    
        });
        
        probabilitiyOfMutationSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                parameters.setProbabilitiyOfMutation(Double.parseDouble(probabilitiyOfMutationSpinner.getValue().toString()));
            }    
        });
        
        polynomialDstributionIndexSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                parameters.setPolynomialIdstributionIndex(Integer.parseInt(polynomialDstributionIndexSpinner.getValue().toString()));
            }    
        });
        
        decisionSpaceWidthSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                parameters.setDecisionSpaceWidth(Double.parseDouble(decisionSpaceWidthSpinner.getValue().toString()));
            }    
        });
        function1BoundarySpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                parameters.setFunction1Boundary(Double.parseDouble(function1BoundarySpinner.getValue().toString()));
            }    
        });
        
        function2BoundarySpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                parameters.setFunction2Boundary(Double.parseDouble(function2BoundarySpinner.getValue().toString()));
            }    
        });
    }
    
    private void initComboBoxes() {
        optimizationComboBox = new JComboBox();
        function1ComboBox = new JComboBox();
        function2ComboBox = new JComboBox();
        
        assignComboBoxesModels();
        assignComboBoxesActionListeners();
    }
    
    private void assignComboBoxesModels() {
        optimizationComboBox.setModel(new DefaultComboBoxModel(new String[] { "MAXIMALIZATION", "MINIMIZATION"}));
  
        String [] functions = new String[] { "f(x) = x", "f(x) = 1", "f(x) = sin(x)", "f(x) = cos(x)", "f(x) = user func", "f(x) = 1 - x" };
        
        function1ComboBox.setModel(new DefaultComboBoxModel(functions));

        function2ComboBox.setModel(new DefaultComboBoxModel(functions));
    }
    
    private void assignComboBoxesActionListeners() {
         optimizationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (optimizationComboBox.getSelectedItem().toString().equals("MAXIMALIZATION")) {
                    parameters.setOptimization(Optimization.MAXIMALIZATION);
                } else {
                    parameters.setOptimization(Optimization.MINIMIZATION);
                }
            }
        });
         
         function1ComboBox.addActionListener(new ActionListener() {
             
             @Override
             public void actionPerformed(ActionEvent e) {
                 updateChart(-1);
             }
         });
         
        function2ComboBox.addActionListener(new ActionListener() {
             
             @Override
             public void actionPerformed(ActionEvent e) {
                 updateChart(-1);
             }
         });
         
         
    }
    
    private void initCheckBoxes() {
        consoleOutputCheckBox = new JCheckBox("console output");
        
    }
    
    private void assingCheckBoxesActionListeners() {
        consoleOutputCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }
    
    private void initParameterPanel() {
        parametersPanel = new JPanel();
        parametersPanel.setBorder(BorderFactory.createTitledBorder("Parameters"));
        resultsSlider = new JSlider(JSlider.HORIZONTAL,0, parameters.getNrOfIterations(), 1);
        resultsSlider.setEnabled(false);
       
        resultsSlider.setMinorTickSpacing(1);
        resultsSlider.setMajorTickSpacing(5);
        resultsSlider.setPaintTicks(true);
        resultsSlider.setPaintLabels(true);
        resultsSlider.setPaintTicks(true);
        resultsSlider.setPaintTrack(true);
        
        nextSolutionSetButton = new JButton(">");
        previousSolutionSetButton = new JButton("<");
        
        nextSolutionSetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (resultsSlider.isEnabled()) {
                    resultsSlider.setValue(resultsSlider.getValue()+1);
                }
            }
        });
        
        previousSolutionSetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (resultsSlider.isEnabled()) {
                    resultsSlider.setValue(resultsSlider.getValue()-1);
                }
            }
        });
        
        resultsSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                int value = resultsSlider.getValue() - 1;
                if (results != null && value < results.size())
                    updateChart(value);
            }
        });
        
        initLabels();
        initSpinners();
        initComboBoxes();
        initCheckBoxes();
    }
    
    private void initChartsTabbedPane() {
        chartsTabbedPane = new JTabbedPane();
        firstChartPanel = new JPanel();
        firstChartPanel.setLayout(new BorderLayout());
        chartsTabbedPane.addTab("tab1", firstChartPanel);
        initChart();
    }
    
    private void initChart() {
        XYSeries series = new XYSeries("");
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        
        chart = ChartFactory.createXYLineChart(
            "",//Tytuł
            "decision vector", // x-axis Opis
            "solutions", // y-axis Opis
            dataset, // Dane
            PlotOrientation.VERTICAL, // Orjentacja wykresu /HORIZONTAL
            true, // pozkaż legende
            true, // podpowiedzi tooltips
            false
        );
        
        CP = new ChartPanel(chart);
        
        firstChartPanel.add(CP, BorderLayout.CENTER);
        firstChartPanel.validate(); 
    }
    
    private void createChart(int value) {

        chart = ChartFactory.createXYLineChart(
                "",//Tytuł
                "decision vector", // x-axis Opis
                "solutions", // y-axis Opis
                getDataset(value), // Dane
                PlotOrientation.VERTICAL, // Orjentacja wykresu /HORIZONTAL
                true, // pozkaż legende
                true, // podpowiedzi tooltips
                false
        );
        
        final XYPlot plot = chart.getXYPlot();
        if (plot.getSeriesCount() > 2) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
            renderer.setSeriesLinesVisible(2, false);
            renderer.setSeriesShapesVisible(2, true);
            renderer.setSeriesLinesVisible(3, false);
            renderer.setSeriesShapesVisible(3, true);
            
            plot.setRenderer(renderer);
        }
        
        CP = new ChartPanel(chart);
        
        firstChartPanel.add(CP, BorderLayout.CENTER);
        firstChartPanel.validate();
    }
    
    private XYSeriesCollection getDataset(int value) {
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        
        
        
        // Functions 
        XYSeries series = new XYSeries("Function 1 - " + function1ComboBox.getSelectedItem().toString());
        for(double i = 0.0; i < 1.0; i += 0.01)
            series.add(i, getFunc(i, 1));
        
        XYSeries series2 = new XYSeries("Function 2 - " + function2ComboBox.getSelectedItem().toString());
        for(double i = 0.0; i < 1.0; i += 0.01)
            series2.add(i, getFunc(i, 2));
        
        dataset.addSeries(series);
        dataset.addSeries(series2);
        
        
        // SOULTIONS
        if (value >= 0 && (results.size() > value)) {
            ArrayList<XYSeries> r = results.get(value);
            dataset.addSeries(r.get(0));
            dataset.addSeries(r.get(1));
        }
        
        return dataset;
    }
    
    private void updateChart(int value) {
        firstChartPanel.remove(CP);
        createChart(value);
    }
    
    private void initBasicFrameParameters() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setName("MOEA");
        setPreferredSize(new java.awt.Dimension(1200, 800));
    }
    
    private void initParametersPanelLayout() {
        javax.swing.GroupLayout parametersPanelLayout = new javax.swing.GroupLayout(parametersPanel);
        parametersPanel.setLayout(parametersPanelLayout);
        parametersPanelLayout.setHorizontalGroup(
            parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parametersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resultsSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(parametersPanelLayout.createSequentialGroup()
                        .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(parametersPanelLayout.createSequentialGroup()
                                .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(function2BoundarySpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                    .addComponent(function1BoundarySpinner)
                                    .addComponent(decisionSpaceWidthSpinner, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(polynomialDstributionIndexSpinner, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(probabilitiyOfMutationSpinner, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(numberOfMutationsInIterationSpinner)
                                    .addComponent(numberOfSetsInPopulationSpinner)
                                    .addComponent(numberOfSolutionsInSetSpinner)
                                    .addComponent(numberOfIterationsSpinner))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(numberOfIterationsLabel)
                                    .addComponent(numberOfSolutionsInSetLabel)
                                    .addComponent(numberOfSetsInPopulationLabel)
                                    .addComponent(numberOfMutationsInIterationLabel)
                                    .addComponent(probabilitiyOfMutationLabel)
                                    .addComponent(polynomialDstributionIndexLabel)
                                    .addComponent(decisionSpaceWidthLabel)
                                    .addComponent(function1BoundaryLabel)
                                    .addComponent(function2BoundaryLabel)))
                            .addGroup(parametersPanelLayout.createSequentialGroup()
                                .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(function2ComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 100, Short.MAX_VALUE)
                                    .addComponent(function1ComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(optimizationComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(optimizationLabel)
                                    .addComponent(function1Label)
                                    .addComponent(function2Label)))
                            .addComponent(consoleOutputCheckBox)
                            .addGroup(parametersPanelLayout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(previousSolutionSetButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nextSolutionSetButton)))
                        .addGap(0, 6, Short.MAX_VALUE)))
                .addContainerGap())
        );
        parametersPanelLayout.setVerticalGroup(
            parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parametersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numberOfIterationsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numberOfIterationsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numberOfSolutionsInSetSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numberOfSolutionsInSetLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numberOfSetsInPopulationSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numberOfSetsInPopulationLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numberOfMutationsInIterationSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numberOfMutationsInIterationLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(probabilitiyOfMutationSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(probabilitiyOfMutationLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(polynomialDstributionIndexSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(polynomialDstributionIndexLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(decisionSpaceWidthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(decisionSpaceWidthLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(function1BoundarySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(function1BoundaryLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(function2BoundarySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(function2BoundaryLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(optimizationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(optimizationLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(function1ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(function1Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(function2ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(function2Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(consoleOutputCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(resultsSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nextSolutionSetButton)
                    .addComponent(previousSolutionSetButton))
                .addContainerGap(173, Short.MAX_VALUE))
        );
    }
    
    private void initGlobalFrameLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chartsTabbedPane, GroupLayout.DEFAULT_SIZE, 907, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(parametersPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(startButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(chartsTabbedPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(parametersPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(startButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }
    
    private void initFrameLayout() {
        initParametersPanelLayout();
        initGlobalFrameLayout();
        pack();
    }

    /*********************
     ***** VARIABLES *****
     ********************/
    
    // PROPERTIES / PARAMETERS
    private String propertiesFile = "sao.properties";
    private Parameters parameters = new Parameters();
    
    // PARAMETERS PANEL VARIABLES
    private JPanel parametersPanel;
    
    private JLabel numberOfIterationsLabel;
    private JLabel numberOfSolutionsInSetLabel;
    private JLabel numberOfSetsInPopulationLabel;
    private JLabel numberOfMutationsInIterationLabel;
    private JLabel probabilitiyOfMutationLabel;
    private JLabel polynomialDstributionIndexLabel;
    private JLabel decisionSpaceWidthLabel;
    private JLabel function1BoundaryLabel;
    private JLabel function2BoundaryLabel;
    private JLabel optimizationLabel;
    private JLabel function1Label;
    private JLabel function2Label;
    
    private JSpinner numberOfIterationsSpinner;
    private JSpinner numberOfSolutionsInSetSpinner;
    private JSpinner numberOfSetsInPopulationSpinner;
    private JSpinner numberOfMutationsInIterationSpinner;
    private JSpinner probabilitiyOfMutationSpinner;
    private JSpinner polynomialDstributionIndexSpinner;
    private JSpinner decisionSpaceWidthSpinner;
    private JSpinner function1BoundarySpinner;
    private JSpinner function2BoundarySpinner;
   
    private JComboBox optimizationComboBox;
    private JComboBox function1ComboBox;
    private JComboBox function2ComboBox;
    
    private JCheckBox consoleOutputCheckBox;
    
    private JButton nextSolutionSetButton;
    private JButton previousSolutionSetButton;
    
    // TABBED PANE VARIABLES
    private JTabbedPane chartsTabbedPane;
    private JPanel firstChartPanel;
    
    // OTHERS VARIABLES
    private JButton startButton;    
    
    private JSlider resultsSlider;
    
    JFreeChart chart;
    ChartPanel CP;
    
    private ArrayList<ArrayList<XYSeries>> results;
    
}
