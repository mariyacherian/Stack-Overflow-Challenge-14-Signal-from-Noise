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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This is for sorting integer in 6*6 matrix.Each cell will have one number. place all the integers into the grid such
 * that
 * 
 * Each row is descendingly sorted (ties are not allowed). For example: 6 5 4 3 2 1 is valid but 6 5 5 5 3 2 is not
 * valid.Each column is descendingly sorted (ties are not allowed)or declare this impossible.Example on a 3x3 grid:
 * Given these numbers to sort [95, 75, 75, 74, 74, 74, 54, 45, 40]A valid response would be [[95, 75, 74], [75, 74,
 * 54], [74, 45, 40]]
 * 
 * @author mariya.cherian
 * @version 1.0
 * @since Nov 11, 2025
 */
public class IntegerSortInMatrix
{
	/**
	 * This will be the starting point of execution First this will read input from the file 'RandomNumbers.txt' at
	 * input folder ,In the attached file, we have 100 sets of 36 random integers. The integers are between 0 and 99.
	 * Then for each input line call the method {@link sortMatrix} to sort the values
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		int total = 0;
		try
		{
			Path path = Paths.get("input", "RandomNumbers.txt");
			System.out.println("Looking for file at: " + path.toAbsolutePath());
			if (!Files.exists(path))
			{
				System.err.println("File does not exist. Please check path and file name!");
				return;
			}
			List<List<Integer>> allLists = readIntegerLists(path);
			for (List<Integer> t : allLists)
			{
				total = sortMatrix(t, total);
			}
			System.out.println("----------------------------------------------------------------------");
			System.out.println(" total count of cases for which the sorting was impossible : " + total);
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
	 * This is to sort the 6*6 matrix of integers in descending order.if sorting is not possible display that else
	 * display the matrix
	 * 
	 * @param input the list of integers that have to be sorted and placed in grid
	 * @param total the count of impossible matrix
	 * @return the total the count of impossible matrix
	 */
	private static int sortMatrix(List<Integer> input, int total)
	{
		System.out.println("input numbers:>> " + input);
		List<Integer> sorted = new ArrayList<>(input);
		sorted.sort(Collections.reverseOrder());
		System.out.println("Sorted numbers:>> " + sorted);
		sorted = sorted.subList(0, 36);
		int n = 6;
		int[][] matrix = new int[n][n];
		for (int i = 0; i < n; i++)
		{
			Arrays.fill(matrix[i], -1);
		}
		boolean success = fillMatrix(sorted, matrix, 0, n);
		if (success)
		{
			total = total + 1;
			System.out.println(" this is impossible!!!!!!!!!!!. ");
		}
		else
		{
			displayMatrix(matrix, n);
		}
		return total;
	}

	/**
	 * This is to fill the matrix from the sorted integers here the j indicate the column index and i indicate the row
	 * index.then from the sorted list of integers,first evaluate for the postion 00, then to check the row wise
	 * conflict skip the first element in row (j != 0 ) then check the item is same as the previous item in matrix, if
	 * match found then need to swap the item in the matrix that means place the item in the first col of next row for
	 * that this will check if it is in the last row inorder to avoid the {@link ArrayIndexOutOfBoundsException}.In the
	 * swapping case if the first index of the next row having a value then continue for the next iteration
	 * 
	 * @param list the list of items to be placed in the matrix
	 * @param matrix the matrix to be sorted
	 * @param index the
	 * @param n the matrix index
	 * @return the flag that indicate the tie exist or not
	 */
	private static boolean fillMatrix(List<Integer> list, int[][] matrix, int index, int n)
	{
		boolean rowflag = false;
		int k = 0;
		outer: for (int i = 0; i < n; i++)
		{
			inner: for (int j = 0; j < n; j++)
			{
				if (i == 0 && j == 0)
				{
					matrix[i][j] = list.get(k);
					k++;
				}
				else if (matrix[i][j] != -1)
				{
					continue;
				}
				else if (j != 0 && list.get(k) == matrix[i][j - 1])
				{
					if (i == 5)
					{
						rowflag = true;
						break outer;
					}
					int row = i + 1, col = j;
					swapcol: for (int indexs = 0; indexs < col; indexs++)
					{
						rowflag = true;
						if ((matrix[row][indexs] == -1) && indexs != 0 && matrix[row][indexs - 1] == list.get(k))

						{
							continue;
						}
						else if ((matrix[row][indexs] == -1) && matrix[row - 1][indexs] != list.get(k)
								&& matrix[row - 1][indexs] > list.get(k))
						{
							matrix[row][indexs] = list.get(k);
							k++;
							j--;
							rowflag = false;
							break swapcol;
						}
						else if ((matrix[row][indexs] != -1) && matrix[row][indexs] != list.get(k)
								&& matrix[row][indexs] > list.get(k))
						{
							continue;
						}
						else
						{
							rowflag = true;
							break outer;

						}

					}
				}
				else if (j != 0 && list.get(k) != matrix[i][j - 1])
				{
					matrix[i][j] = list.get(k);
					k++;
				}
				else
				{
					matrix[i][j] = list.get(k);
					k++;
				}
			}
		}
		return rowflag;
	}

	/**
	 * This is to display this 6*6 matrix with valid sorted items
	 * 
	 * @param matrix the {@link IntegerSortInMatrix} to be displayed
	 * @param n the count of index in matrix
	 */
	private static void displayMatrix(int[][] matrix, int n)
	{
		System.out.println("6×6 Matrix:");
		Matrix: for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				if (matrix[i][j] == -1)
				{
					System.out.println();
					break Matrix;
				}
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}
	}
}
