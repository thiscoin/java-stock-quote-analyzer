package project_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Simeon Bikorimana
 * @Student ID#: N19909465
 *
 */
public class DataHandler {

	private Date date;
	private Double open;
	private Double high;
	private Double low;
	private Double close;
	private int volume;
	private Double adjClose;

	private String sortMethod;
	private String sortPreference;
	private String sortColumn;

	List<DataHandler> myList = new ArrayList<DataHandler>();
	Map<Date, DataHandler> sortedDataMap = new LinkedHashMap<Date, DataHandler>();

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getClose() {
		return close;
	}

	public void setClose(Double close) {
		this.close = close;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public Double getAdjClose() {
		return adjClose;
	}

	public void setAdjClose(Double adjClose) {
		this.adjClose = adjClose;
	}

	/**
	 * loadPriceData method: This function takes the file name, sort method
	 * specification(QuickSort or BubbleSort), and sort preference-ascending or
	 * descending, and price or date -as parameters.The file content gets loaded
	 * into memory and sorted according to the above sort preferences. After this
	 * method is called, the data should be held in the instance variables of an
	 * object of class DataHandler.
	 * 
	 * @param fileName
	 *            this is the name of the file ....
	 * @param sortMethod
	 *            -----
	 * @param sortPreference
	 * @param sortColumn
	 * @return
	 * @throws ParseException
	 * @throws NumberFormatException
	 * @throws IOException
	 */

	public List<DataHandler> loadPriceData(String fileName, String sortMethod, String sortPreference, String sortColumn)
			throws ParseException, NumberFormatException, IOException {

		// File path validation
		if (isInvalidFilePath(fileName)) {
			System.out.println(fileName + "  file path is invalid");
			System.exit(0);

		}

		List<DataHandler> handlerList = new ArrayList<DataHandler>();

		if (!verifyInputParameter(sortMethod, sortPreference, sortColumn))
			return handlerList;

		else {

			this.sortMethod = sortMethod;
			this.sortPreference = sortPreference;
			this.sortColumn = sortColumn;

			handlerList = readFile(fileName);

			if (sortMethod.equalsIgnoreCase("QUICKSORT")) {

				Quicksort stq = new Quicksort();
				stq.sort(handlerList, 0, handlerList.size() - 1, sortPreference, sortColumn);

			} else if (sortMethod.equalsIgnoreCase("BUBBLESORT")) {
				Bubblesort stb = new Bubblesort();
				stb.bbsort(handlerList, 0, handlerList.size() - 1, sortPreference, sortColumn);

			}

			return handlerList;
		}
	}

	/**
	 * ReadFile method to read input files
	 * 
	 * @param fileName
	 * @return handlerList
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<DataHandler> readFile(String fileName) throws NumberFormatException, IOException, ParseException {

		List<DataHandler> handlerList = new ArrayList<DataHandler>();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String line = "";

		int firstLine = 0;

		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(fileName));

		while ((line = br.readLine()) != null) {

			if (firstLine > 0) {

				String[] dataArr = line.split(",");

				DataHandler dh = new DataHandler();

				dh.setDate(df.parse(dataArr[0]));
				dh.setOpen(Double.parseDouble(dataArr[1]));
				dh.setHigh(Double.parseDouble(dataArr[2]));
				dh.setLow(Double.parseDouble(dataArr[3]));
				dh.setClose(Double.parseDouble(dataArr[4]));
				dh.setVolume(Integer.parseInt(dataArr[5]));
				dh.setAdjClose(Double.parseDouble(dataArr[6]));

				handlerList.add(dh);

			}

			firstLine++;
		}

		return handlerList;

	}

	/**
	 * 
	 * Validation of typed inputs such as QuickSort, BubbleSort, asc, desc, date,
	 * and price
	 * 
	 * @param sortMethod
	 * @param sortPreference
	 * @param sortColumn
	 * @return
	 */

	public boolean verifyInputParameter(String sortMethod, String sortPreference, String sortColumn) {

		if (sortMethod.trim().equalsIgnoreCase("QUICKSORT") || sortMethod.trim().equalsIgnoreCase("BUBBLESORT")) {

			if (sortPreference.trim().equalsIgnoreCase("ASC") || sortPreference.trim().equalsIgnoreCase("DESC")) {

				if ((sortColumn.trim()).equalsIgnoreCase("DATE") || sortColumn.trim().equalsIgnoreCase("PRICE")) {

					return true;
				}
			}
		}

		return false;
	}

	public List<DataHandler> getValues(Date fromDate, Date toDate) {

		List<DataHandler> selectedObjects = new ArrayList<DataHandler>();

		for (Date myKey : sortedDataMap.keySet()) {

			DataHandler d = sortedDataMap.get(myKey);

			if (!d.getDate().before(fromDate) && !d.getDate().after(toDate)) {

				selectedObjects.add(d);
			}

		}

		return selectedObjects;

	}

	public List<Double> getPrices(Date fromDate, Date toDate) {

		List<Double> selectedPrices = new ArrayList<Double>();

		for (Date key : sortedDataMap.keySet()) {

			DataHandler d = sortedDataMap.get(key);

			if (!d.getDate().before(fromDate) && !d.getDate().after(toDate)) {

				selectedPrices.add(d.getAdjClose());
			}

		}

		return selectedPrices;

	}

	/**
	 * 
	 * WriteToFile method is used to write results in the results.txt file
	 * 
	 * @param fileName
	 * @param dhList
	 * @throws IOException
	 */

	public void writeToFile(File fileName, List<Double> dhList) throws IOException {

		BufferedWriter bw = testBufferedWriter(fileName, true);

		for (int i = 0; i < dhList.size(); i++) {
			bw.append("" + dhList.get(i));

			bw.newLine();
		}

		bw.close();

	}

	public BufferedWriter testBufferedWriter(File fileName, Boolean isFileOpen) throws IOException {

		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, isFileOpen));

		return bw;

	}

	/**
	 * --------------------------Computing Maximum--------------------------
	 * ComputeAverage method takes the parameters fromDate and toDate.
	 * It returns the average price for the period specified by the two dates.
	 * The calculation of average price includes prices for the start date,
	 * end date, and everything in between.
	 * ----------------------------------------------------------------------
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	
	public Double computeAverage(Date fromDate, Date toDate) {

		Double sum = 0.0;

		Double averAdjClose = 0.0;

		List<Double> selectedPrice = getPrices(fromDate, toDate);

		if (!selectedPrice.isEmpty()) {

			for (int i = 0; i < selectedPrice.size(); i++) {

				sum = sum + selectedPrice.get(i);

			}

			averAdjClose = sum / selectedPrice.size();

		} else {
			System.out.println("No data for selected date");
		}

		return averAdjClose;
	}

	/**
	 * --------------------------Computing Maximum---------------------------------
	 * ComputeMax method takes the parameters fromDate and toDate.
	 * It returns the maximum price for the period specified by the two dates.
	 * The calculation of maximum price should include prices for the start date,
	 * the end date, and everything in between.
	 * -----------------------------------------------------------------------------
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	 
	public Double computeMax(Date fromDate, Date toDate) {

		List<Double> selectedData = getPrices(fromDate, toDate);
		Double maxPrice = 0.0;

		for (int i = 0; i < selectedData.size(); i++) {

			if (!selectedData.isEmpty()) {

				if (selectedData.get(i) > maxPrice) {
					maxPrice = selectedData.get(i);
				}

			} else
				break;

		}

		return maxPrice;
	}
/**
 *  ----------Computing moving average---------------------------
 * This method should take the parameters windowSize, fromDate, and toDate.
 * It should return a moving average of the last n elements of price, where
 * n = windowSize(declared as int window). The prices used in this calculation
 * includes prices for the start date, the end date, and everything in between.
 * For example, if the dates specified are from the 1st of July to the 20th of
 * July and the window size is 10, this method should return an array or list in
 * which the 1st value is the average of the price from the 1st of July through the
 * 10th of July, the 2nd value is the average of the price from the 2nd of July through the
 * 11th of July,and so on, until the last value used in the calculation is the price from the
 * 20th of July.
 * -------------------------------------------------------------------------------------
 * 
 * @param windowSize
 * @param fromDate
 * @param toDate
 * @return
 * @throws ParseException
 */
	

	public List<Double> computeMovingAverage(int windowSize, Date fromDate, Date toDate) throws ParseException {

		List<Double> movingAverage = new ArrayList<Double>();
		List<DataHandler> selectedData = getValues(fromDate, toDate);

		Quicksort stq = new Quicksort();
		stq.sort(selectedData, 0, selectedData.size() - 1, "ASC", "DATE");

		for (int i = 0; i < selectedData.size(); i++) {

			if (selectedData.size() - i >= windowSize) {

				Double sum = 0.0;
				int count = 0;
				for (int j = i; j < selectedData.size(); j++) {
					count++;
					sum = sum + selectedData.get(j).getAdjClose();

					if (count == windowSize) {

						movingAverage.add(sum / windowSize);
						break;
					}

				}

			} else {
				break;
			}

		}

		return movingAverage;

	}

	
	
	/**
	 *  --------------------------InsertPrice------------------------------------------------
	 * The insertPrice method takes a price record as a parameter. As in the price file,
	 * a single price record has a date, open price, high price, low price, close
	 * price, trading volume, and adjusted closing price.The insertPrice method is used 
	 * to insert a price into the price data contained in an object of class DataHandler. 
     * the date already exists,the insertPrice method overwrites the record that is already there.
	 * The data is kept sorted when inserting or overwriting a new price record.
	 *----------------------------------------------------------------------------------------
	 * @param dh
	 */
	public void insertPrice(DataHandler dh) {

		sortedDataMap.put(dh.getDate(), dh);

	}

	public void correctPrices(String fileName) throws NumberFormatException, ParseException, IOException {

		List<DataHandler> newData = readFile(fileName); // read data from corrections file

		for (DataHandler correctionsData : newData) {

			insertPrice(correctionsData);
		}

		List<DataHandler> allData = new ArrayList<DataHandler>(sortedDataMap.values());

		if (sortMethod.equalsIgnoreCase("QUICKSORT")) {

			new Quicksort().sort(allData, 0, allData.size() - 1, sortPreference, sortColumn);
		} else {

			new Bubblesort().bbsort(allData, 0, allData.size() - 1, sortPreference, sortColumn);
		}

		sortedDataMap.clear();

		for (int i = 0; i < allData.size(); i++) {

			sortedDataMap.put(allData.get(i).getDate(), allData.get(i));
		}

	}

	/**
	 * -------------------------AppendFile--------------------------------------------
	 * This method appends results on the results.txt file that are obtained from
	 * data selection, computing average, maximum, and simple moving average.
	 *  ------------------------------------------------------------------------------
	 * @param fileName
	 * @param text
	 * @param clearFileContents
	 * @throws IOException
	 */
	

	public void appendFile(File fileName, String text, boolean clearFileContents) throws IOException {

		BufferedWriter bw = testBufferedWriter(fileName, clearFileContents);
		;
		bw.append(text);
		bw.newLine();
		bw.close();

	}

	/**
	 * -----------------------IsInvalidFilePath--------------------------------------
	 * This method is used to check if the file path exists or not.
	 * -----------------------------------------------------------------------------
	 * @param filePath
	 * @return
	 */
	

	public boolean isInvalidFilePath(String filePath) {

		return !new File(filePath).exists();

	}

}
