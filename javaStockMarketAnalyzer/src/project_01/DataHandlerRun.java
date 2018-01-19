package project_1;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Simeon Bikorimana
 * @Student ID#: N19909465
 *
 */

public class DataHandlerRun {

	public static void main(String[] args) throws ParseException, NumberFormatException, IOException {
		// File path for loading "prices.csv"
		String prices = "C:\\Users\\Simeon\\Dropbox\\APPLICATIONS\\SUMMER_HW\\prices.csv"; 
		// File path for loading "corrections.csv"
		String corrections = "C:\\Users\\Simeon\\Dropbox\\APPLICATIONS\\SUMMER_HW\\corrections.csv";
		// File path for writing results"results.txt"
		File fOutput = new File("C:\\Users\\Simeon\\Dropbox\\APPLICATIONS\\SUMMER_HW\\results.txt");
		
		// Enter your window size here for computing moving average.
		// Choose a window size that is less than the size of the selected date range.
		int window = 10;

		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		// (1). An object of class DataHandler is created here
		DataHandler dataHand = new DataHandler();

		// (2). LoadPriceData method is called to load data from the input file
		// "priceS.csv".
		// This method takes sorting method specification of QucickSort or BubbleSort,
		// and sort preference-Ascending or descending, and price or date as parameters.
		// ===============================================================================
		// CURRENT CASE: The date is sorted by date using a QuickSort method with
		// sorting preference of ascending order"ASC".
		// ===============================================================================

		List<DataHandler> dh = dataHand.loadPriceData(prices, "QUICKSORT", "ASC", "DATE");

		if (dh.size() == 0) {
			System.out.println("Please make sure you input parameters are correct");
			System.exit(0);
		}

		for (DataHandler d : dh) {

			dataHand.sortedDataMap.put(d.getDate(), d);
		}

		// (3). Calling the DataHandler object’s correctPrices method
		// specifying the corrections.csv file as the source of the corrections

		if (dataHand.isInvalidFilePath(corrections)) {

			System.out.println(corrections + " file path is invalid");
			System.exit(0);
		}
		
		 dataHand.correctPrices(corrections);

		// (4). Retrieving the appropriate prices for the specified dates using the
		// getPrices method and append them to the results.txt file
		// Specified dated for price selection(i.e., from "08/15/2004" to "08/20/2004")
		// Different dates can be used as inputs.Use the same date format as shown below

		MyDate dateForPriceSelection = new MyDate("08/15/2004", "08/20/2004");
		MyDate dateForAverage = new MyDate("08/15/2004", "09/15/2004");
		MyDate dateForMaximuPrice = new MyDate("04/15/2004", "06/15/2004");
		MyDate dateForMovAv = new MyDate("08/15/2004", "09/15/2004");

		List<Double> selectPrices = dataHand.getPrices(dateForPriceSelection.getFromDate(),
				dateForPriceSelection.getToDate());

		String myText = "The Prices of SPY between " + df.format(dateForPriceSelection.getFromDate()) + " and "
				+ df.format(dateForPriceSelection.getToDate()) + " are: ";
		// Appending selected dates to the results file"Results.txt"

		dataHand.appendFile(fOutput, myText, false);
		dataHand.writeToFile(fOutput, selectPrices);

		// (5). Retrieving the average price for the specified dates(e.g., from
		// 08/15/2004
		// to 09/15/2004) using the computeAverage method and append it to the results
		// file "Results.txt"

		String myAveragetext = "The Average Price of SPY between " + df.format(dateForAverage.getFromDate()) + " and "
				+ df.format(dateForAverage.getToDate()) + " is: ";

		Double computedAverage = dataHand.computeAverage(dateForAverage.getFromDate(), dateForAverage.getToDate());
		List<Double> testComputedAverage = new ArrayList<Double>();
		testComputedAverage.add(computedAverage);

		// Appending computed average value to the results file"Results.txt"
		dataHand.appendFile(fOutput, myAveragetext, true);
		dataHand.writeToFile(fOutput, testComputedAverage);

		// (6). Retrieving the maximum price for the specified dates using the computeMax
		// method and append it to the results file "Results.txt"
		String myMaximumtext = "The Maximum Price of SPY between " + df.format(dateForMaximuPrice.getFromDate())
				+ " and " + df.format(dateForMaximuPrice.getToDate()) + " is: ";
		Double computedMax = dataHand.computeMax(dateForMaximuPrice.getFromDate(), dateForMaximuPrice.getToDate());
		List<Double> testComputedMax = new ArrayList<Double>();
		testComputedMax.add(computedMax);

		// Appending computed maximum value to the results file"Results.txt"
		dataHand.appendFile(fOutput, myMaximumtext, true);
		dataHand.writeToFile(fOutput, testComputedMax);

		// (7). Retrieving the moving average for the specified dates using the
		// computedMovingAverage method and append the results to the results file"
		// Results.txt"
		List<Double> computedMA = dataHand.computeMovingAverage(window, dateForMovAv.getFromDate(),
				dateForMovAv.getToDate());

		String myMAtext = "The Moving Average Price of SPY between " + df.format(dateForMovAv.getFromDate()) + " and "
				+ df.format(dateForMovAv.getToDate()) + " for WindowSize " + window + " is : ";

		// Appending computed moving average value to the results file"Results.txt"
		dataHand.appendFile(fOutput, myMAtext, true);
		dataHand.writeToFile(fOutput, computedMA);
		
		System.out.println("DONE");

	}

}
