package project_1;

import java.util.List;

/**
 * 
 * @author Simeon Bikorimana
 *
 *         QuickSort algorithm: Sorting preferences: ascending or descending
 *         with date and price as parameters
 */
public class Quicksort {

	public int partition(List<DataHandler> myList, int lo, int hi, String sortPreference, String sortColumn) {

		DataHandler pivot = myList.get(hi);

		int i = (lo - 1);// -1
		for (int j = lo; j < hi; j++) {

			// Sorting by Price
			if (sortColumn.equalsIgnoreCase("PRICE")) {

				if (sortPreference.equals("ASC")) {

					if (myList.get(j).getAdjClose() <= pivot.getAdjClose()) {

						i++;

						DataHandler temp = myList.get(i);
						myList.set(i, myList.get(j));
						myList.set(j, temp);

					} else {
						@SuppressWarnings("unused")
						String test = " QuickSort failed";
					}
				} else if (sortPreference.equals("DESC")) {

					if (myList.get(j).getAdjClose() >= pivot.getAdjClose()) {

						i++;
						//
						DataHandler temp = myList.get(i);
						myList.set(i, myList.get(j));
						myList.set(j, temp);

					}
				} else {
					System.out.println("invalid input");
				}

			}

			// Sorting by Date

			if (sortColumn.equalsIgnoreCase("DATE")) {

				if (sortPreference.equals("ASC")) {// Oldest to newest

					if (myList.get(j).getDate().before(pivot.getDate())) {

						i++;

						DataHandler temp = myList.get(i);
						myList.set(i, myList.get(j));
						myList.set(j, temp);

					}
				} else if (sortPreference.equals("DESC")) {// Newest to oldest

					if (myList.get(j).getDate().after(pivot.getDate())) {

						i++;

						DataHandler temp = myList.get(i);
						myList.set(i, myList.get(j));
						myList.set(j, temp);

					}
				}

			}

		}

		DataHandler temp = myList.get(i + 1);
		myList.set(i + 1, myList.get(hi));
		myList.set(hi, temp);

		return i + 1;// return of the pivot index
	}

	public void sort(List<DataHandler> myList, int lo, int hi, String sortPreference, String sortColumn) {
		if (lo < hi) {

			int pIndex = partition(myList, lo, hi, sortPreference, sortColumn);

			sort(myList, lo, pIndex - 1, sortPreference, sortColumn);
			sort(myList, pIndex + 1, hi, sortPreference, sortColumn);
		}
	}

}
