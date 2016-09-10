package me.hpt.EquiChests.Utils;

import me.hpt.EquiChests.ChestData;
import org.bukkit.util.Vector;

public class Sorting {

	private static ChestData[] input;
	private static ChestData[] output;
	private static int length;
	private static Vector vector;

	/**
	 * Start a new Merge Sort
	 * @param vec Distance to measure from
	 * @param array Data to sort
	 */
	public static void startSort(Vector vec, ChestData[] array) {
		input = array;
		length = array.length;
		output = new ChestData[length];
		vector = vec;
		doMergeSort(0, length - 1);
	}

	private static void doMergeSort(int lowerIndex, int higherIndex) {
		if (lowerIndex < higherIndex) {
			int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
			doMergeSort(lowerIndex, middle);
			doMergeSort(middle + 1, higherIndex);
			mergeHalves(lowerIndex, middle, higherIndex);
		}
	}

	private static void mergeHalves(int lowerIndex, int middle, int higherIndex) {
		for (int i = lowerIndex; i <= higherIndex; ++i) {
			output[i] = input[i];
		}
		int i = lowerIndex;
		int j = middle + 1;
		int k = lowerIndex;
		while (i <= middle && j <= higherIndex) {
			if (output[i].distanceTo(vector) <= output[j].distanceTo(vector)) {
				input[k] = output[i];
				i++;
			} else {
				input[k] = output[j];
				j++;
			}
			k++;
		}
		while (i <= middle) {
			input[k] = output[i];
			k++;
			i++;
		}
	}
}
