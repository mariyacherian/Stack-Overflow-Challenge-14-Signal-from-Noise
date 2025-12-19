/**
 *
 * 
 */
package integersort;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * there will be given a list of integers that is supposed to form a continuous range from A to B, but:A and B are
 * unknown.Exactly one integer is missing from within the sequence.Exactly one integer appears twice.The array may
 * contain extra random noise values outside the range. (It is possible that the noise values could form a
 * mini-sequence, but the noise sequence will be shorter than the primary sequence)The challenge is to determine the
 * start value, end value, missing value and duplicate value.Here are some examples:Example 1:[5, 9, 7, 6, 7, 4, 100,
 * -2]Start value: 4End value: 9Missing value: 8Duplicate value: 7Noise: -2, 100Example 2:[4, 5, 6, -1, 8, 8, -2, -3,
 * -2]Start value: 4End value: 8Missing value: 7Duplicate value: 8Noise: -1, -2, -2, -3Note: The noise values of -1, -2,
 * -3 form a sequence but this smaller than the full sequence
 * 
 * @author mariya.cherian
 * @version 1.0
 * @since Dec 18, 2025
 */
public class NumberSequences
{
	/**
	 * This will be the starting point of execution First this will read input from the file 'RandomNumbers2.txt' at
	 * input folder ,In the attached file, we have 100 sets of 100 sequences
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		int total = 0;
		try
		{
			Path path = Paths.get("input", "RandomNumbers2.txt");
			System.out.println("Looking for file at: " + path.toAbsolutePath());
			if (!Files.exists(path))
			{
				System.err.println("File does not exist. Please check path and file name!");
				return;
			}
			List<List<Integer>> allLists = readIntegerLists(path);
			for (List<Integer> t : allLists)
			{
				total = total + doOperation(t);
			}
			System.out.println("----------------------------------------------------------------------");
			System.out.println(" sum of the sequence start values and end values : " + total);
			System.out.println("----------------------------------------------------------------------");

		}
		catch (IOException e)
		{
			System.out.println("failed to read file ");
			e.printStackTrace();
		}
	}

	/**
	 * This will read input file from the specified path then read each line and remove the enclosing brackets if
	 * present and save the integer list in {<code></code>
	 * 
	 * @param path the path where the input file exists
	 * @return the list contains the list of integer input
	 * @throws IOException
	 */
	private static List<List<Integer>> readIntegerLists(Path path) throws IOException
	{
		List<String> lines = Files.readAllLines(path);
		List<List<Integer>> result = new ArrayList<>();
		for (String line : lines)
		{
			line = line.trim();
			if (line.isEmpty())
				continue;
			if (line.startsWith("[") && line.endsWith("]"))
			{
				line = line.substring(1, line.length() - 1);
			}
			String[] parts = line.split("\\s*,\\s*");
			List<Integer> list = new ArrayList<>();
			for (String part : parts)
			{
				list.add(Integer.parseInt(part));
			}
			result.add(list);
		}
		return result;
	}

	/**
	 * it will first find the noise then remove this noise from the list.then find out the start and end value then
	 * return that from this
	 * 
	 * @param input the list to be processed
	 * @return the total of start and end value
	 */
	private static int doOperation(List<Integer> input)
	{
		List<Integer> sorted = new ArrayList<>(input);
		Collections.sort(sorted);
		List<Integer> noise = new ArrayList<>();
		for (Integer item : sorted)
		{
			if (item < 0)
			{
				noise.add(item);
			}
		}
		sorted.removeAll(noise);
		System.out.println("without Noise:>> " + sorted);
		if (!sorted.isEmpty())
		{
			List<Integer> list = new ArrayList<>(sorted);
			Integer prev = list.get(0);
			Integer start = prev;
			Integer end = prev;
			Integer missing = null;
			Integer actualStart = start;
			Integer actualEnd = end;
			Integer actualMissing = null;
			int actualLength = 1;
			for (int i = 1; i < list.size(); i++)
			{
				int current = list.get(i);
				int diff = current - prev;
				if (diff == 0)
				{
					continue;
				}
				if (diff == 1)
				{
					end = current;
				}
				else if (diff == 2 && missing == null)
				{
					missing = prev + 1;
					end = current;
				}
				else
				{
					int length = end - start + 1;
					if (length > actualLength)
					{
						actualLength = length;
						actualStart = start;
						actualEnd = end;
						actualMissing = missing;
					}
					start = current;
					end = current;
					missing = null;
				}
				prev = current;
			}
			int length = end - start + 1;
			if (length > actualLength)
			{
				actualStart = start;
				actualEnd = end;
				actualMissing = missing;
			}
			if (actualStart.equals(actualEnd))
			{
				actualStart = 0;
				actualEnd = 0;
			}
			System.out.println("Start  : " + actualStart);
			System.out.println("Missing: " + actualMissing);
			System.out.println("End    : " + actualEnd);
			return actualStart + actualEnd;
		}
		return 0;
	}
}
