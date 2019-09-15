package com.graphhopper.jsprit.examples;


import com.graphhopper.jsprit.analysis.toolbox.GraphStreamViewer;
import com.graphhopper.jsprit.analysis.toolbox.Plotter;
import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.SchrimpfFactory;
import com.graphhopper.jsprit.core.algorithm.selector.SelectBest;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.Solutions;
import com.graphhopper.jsprit.instance.reader.TSPLIB95Reader;

import java.io.File;
import java.util.Collection;
import java.lang.String;
public class tsptest {
    public static void main(String args[])
    {
        /*写结果准备*/
        File dir = new File("output");
        {
            if (!dir.exists()) {
                System.out.println("creating directory./output");
                boolean result = dir.mkdir();

                if (result) System.out.println("./output created");
            }
        }
        VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();//实例化新问题
        new TSPLIB95Reader(vrpBuilder).read("D:\\java learn\\jsprit-master\\jsprit-instances\\instances\\tsplib\\ali535.tsp");
        VehicleRoutingProblem vrp = vrpBuilder.build();
        new Plotter(vrp).plot("output/solomon_berlin52.png","c1");

        VehicleRoutingAlgorithm vra = new SchrimpfFactory().createAlgorithm(vrp);
        vra.setMaxIterations(1000);
        Collection<VehicleRoutingProblemSolution> solutions = vra.searchSolutions();
        VehicleRoutingProblemSolution solution = new SelectBest().selectSolution(solutions);

        new Plotter(vrp, Solutions.bestOf(solutions)).plot("output/plot","plot");
        SolutionPrinter.print(vrp,solution,SolutionPrinter.Print.VERBOSE);
        new GraphStreamViewer(vrp,solution).setCameraView(700,700,1).labelWith(GraphStreamViewer.Label.ID).setRenderDelay(100).display();
        

    }
}
