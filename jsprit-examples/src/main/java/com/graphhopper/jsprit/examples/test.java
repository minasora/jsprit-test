/*
 * Licensed to GraphHopper GmbH under one or more contributor
 * license agreements. See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 *
 * GraphHopper GmbH licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.graphhopper.jsprit.examples;

import com.graphhopper.jsprit.analysis.toolbox.Plotter;
import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.SchrimpfFactory;
import com.graphhopper.jsprit.core.algorithm.selector.SelectBest;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.instance.reader.SolomonReader;

import java.io.File;
import java.util.Collection;

public class test {

    public static void main(String[] args) {
        /*
        输出准备
        */
        File dir = new File("output");
        {
            if (!dir.exists()) {
                System.out.println("creating directory./output");
                boolean result = dir.mkdir();

                if (result) System.out.println("./output created");
            }
        }
        /*写入数据，采用c101标准测试样例*/

        for (int i = 1 ;i<=9;i++)
        {
            System.out.println(i);
            VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();

            VehicleImpl.Builder vehicleBuilder = VehicleImpl.Builder.newInstance("vechile");
            String s1 = Integer.toString(i);
            String tar = "D:\\java learn\\jsprit-master\\jsprit-instances\\instances\\solomon\\R10" + s1 + ".txt";
            new SolomonReader(vrpBuilder).read(tar);
            VehicleRoutingProblem vrp = vrpBuilder.build();
            String output = "output/solomon_R10" + s1 + ".png";
            new Plotter(vrp).plot(output, "R10"+s1);//绘图

            VehicleRoutingAlgorithm vra = new SchrimpfFactory().createAlgorithm(vrp);

            Collection<VehicleRoutingProblemSolution> solutions = vra.searchSolutions();
            VehicleRoutingProblemSolution solution = new SelectBest().selectSolution(solutions);

            SolutionPrinter.print(vrp, solution, SolutionPrinter.Print.CONCISE);

        }
        /*
         * Plot solution.
         */
        // Plotter plotter = new Plotter(vrp, solution);
//		plotter.setBoundingBox(30, 0, 50, 20);
        // plotter.plot("output/solomon_C101_solution.png", "C101");

       // new GraphStreamViewer(vrp, solution).setCameraView(40, 50, 0.5).labelWith(GraphStreamViewer.Label.ID).setRenderDelay(100).display();





    }

}
