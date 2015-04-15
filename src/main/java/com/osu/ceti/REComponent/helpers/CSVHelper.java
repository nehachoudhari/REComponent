package com.osu.ceti.REComponent.helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * This class is used to convert data set into comma separated files
 * @author nhchdhr
 *
 */
public class CSVHelper {

	/**
	 * Convert a tab delimited file to a csv file 
	 * @param inputFile : path of the input file
	 * @param outputFile : path of the output file
	 * @throws Exception
	 */
	public static void convertToCSV(String inputFile, String outputFile) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
		
		String line;
		while((line = br.readLine()) != null) {
			String[] values = line.split("\\t");
			bw.write(values[0] + "," + values[1] + "," + values[2] + "\n");
		}
		
		br.close();
		bw.close();
	}
	
	
}
