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

import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.SchrimpfFactory;
import com.graphhopper.jsprit.core.algorithm.selector.SelectBest;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.instance.reader.SolomonReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class SolomonExample {
    public static void getAllFileName(String path, ArrayList<String> listFilename) {
        File file = new File(path);
        File[] files = file.listFiles();
        String[] names = file.list();
        if (names != null) {
            String[] completNames = new String[names.length];

            for (int i = 0; i < names.length; i++) {
                completNames[i] = path + names[i];
            }
            listFilename.addAll(Arrays.asList(completNames));
            for (File a : files) {
                if (a.isDirectory()) {
                    getAllFileName(a.getAbsolutePath() + "\\", listFilename);
                }
            }
        }
    }

    public static void main(String[] args) {

        /*
         * Build the problem.
         *
         * But define a problem-builder first.
         */
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("result.txt"));

            ArrayList<String> listFileName = new ArrayList<String>();
            getAllFileName("D:\\java learn\\jsprit-master\\input", listFileName);

            for (String name : listFileName) {
                for (int i = 100; i <= 2000; i += 300) {
                    if ((!name.contains("txt")) && (!name.contains("TXT"))) {
                        continue;
                    }
                    long starttime = System.currentTimeMillis();
                    VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();

                    /*
                     * A solomonReader reads solomon-instance files, and stores the required information in the builder.
                     */
                    new SolomonReader(vrpBuilder).read(name);

                    /*
                     * Finally, the problem can be built. By default, transportCosts are crowFlyDistances (as usually used for vrp-instances).
                     */
                    VehicleRoutingProblem vrp = vrpBuilder.build();

                    // new Plotter(vrp).plot("output/solomon_C101.png", "C101");

                    /*
                     * Define the required vehicle-routing algorithms to solve the above problem.
                     *
                     * The algorithm can be defined and configured in an xml-file.
                     */
                    VehicleRoutingAlgorithm vra = new SchrimpfFactory().createAlgorithm(vrp);
                    vra.setMaxIterations(i);
                    /*
                     * Solve the problem.
                     *
                     *
                     */
                    Collection<VehicleRoutingProblemSolution> solutions = vra.searchSolutions();

                    /*
                     * Retrieve best solution.
                     */
                    VehicleRoutingProblemSolution solution = new SelectBest().selectSolution(solutions);
                    long endtime = System.currentTimeMillis();
                    long time = endtime - starttime;
                    String data = solution.toString();
                    out.write(name);
                    out.newLine();
                    out.write("运行时间:" + time + "ms");
                    out.write(data);
                    out.newLine();

                    /*
                     * Plot solution.
                     */

                }
            }
                out.close();
            }
        catch(IOException e){
            }
        }
    }
