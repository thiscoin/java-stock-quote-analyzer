package project_1;

/**
 * @Author Simeon Bikorimana
 *  BubbleSort algorithm: Sorting preferences: ascending or descending with date and price as parameters
 */

import java.util.List;

public class Bubblesort {

	public void bbsort(List<DataHandler> myList, int lo, int hi, String sortPreference, String sortColumn) {
		// sort by price
		if (sortColumn.equalsIgnoreCase("PRICE")) {

			if (sortPreference.equals("ASC")) {
				for (int i = 0; i < myList.size(); i++) {
					for (int j = 1; j < (myList.size() - i); j++) {

						if (myList.get(j - 1).getAdjClose() > myList.get(j).getAdjClose()) {

							DataHandler temp = myList.get(j - 1);
							myList.set(j - 1, myList.get(j));
							myList.set(j, temp);

						}
					}
				}
			}

			else if (sortPreference.equals("DESC")) {

				for (int i = 0; i < myList.size(); i++) {
					for (int j = 1; j < (myList.size() - i); j++) {

						if (myList.get(j - 1).getAdjClose() < myList.get(j).getAdjClose()) {

							DataHandler temp = myList.get(j - 1);
							myList.set(j - 1, myList.get(j));
							myList.set(j, temp);

						}

					}

				}

			}

		}
		// sort by Date

		if (sortColumn.equalsIgnoreCase("DATE")) {

			if (sortPreference.equals("ASC")) {
				for (int i = 0; i < myList.size(); i++) {
					for (int j = 1; j < (myList.size() - i); j++) {

						if (myList.get(j - 1).getDate().after(myList.get(j).getDate())) {

							DataHandler temp = myList.get(j - 1);
							myList.set(j - 1, myList.get(j));
							myList.set(j, temp);

						}
					}
				}
			}

			else if (sortPreference.equals("DESC")) {

				for (int i = 0; i < myList.size(); i++) {
					for (int j = 1; j < (myList.size() - i); j++) {

						if (myList.get(j - 1).getDate().before(myList.get(j).getDate())) {

							DataHandler temp = myList.get(j - 1);
							myList.set(j - 1, myList.get(j));
							myList.set(j, temp);

						}

					}

				}

			}

		}
	}

}
