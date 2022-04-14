import java.util.Random;
import java.util.*;
import java.util.concurrent.*;
public class atmosphere
{

	public static class temperature implements Comparable<temperature>
	{
		public int minute1;
		public int minute2;
		public int difference;

		public temperature(int minute1, int minute2, int difference)
		{
			this.minute1 = minute1;
			this.minute2 = minute2;
			this.difference = difference;
		}

		@Override
		public int compareTo (temperature o)
		{
			if (this.difference > o.difference)
				return 1;

			else if (this.difference < o.difference)
				return -1;

			else
				return 0;

		}
	}

	public static int [][] temps = new int[8][60];
	public static PriorityQueue<Integer> max = new PriorityQueue<Integer>(Collections.reverseOrder());
	public static PriorityQueue<Integer> min = new PriorityQueue<Integer>();
	public static PriorityQueue<temperature> difference = new PriorityQueue<temperature>(Collections.reverseOrder());
	public static Random r = new Random();
	public static int low = -100;
	public static int high = 71;

	public static void findFiveMaxAndMin()
	{
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 60; j++)
			{
				max.add(temps[i][j]);
				min.add(temps[i][j]);
			}
	}

	public static void findLargestDifference()
	{
		for (int j = 0; j < 8; j++)
		{
			for (int i = 0; i < 49; i++)
			{
				temperature temp = new temperature(i, i + 10, Math.abs(temps[j][i] - temps[j][i + 10]));
				difference.add(temp);
			}
		}
	}

	public static void printAll ()
	{
		for (int i = 0; i < 8; i++)
		{
			System.out.println(i + " index:");

			for (int j = 0; j < 60; j++)
			{
				System.out.print(temps[i][j] + " ");
			}

			System.out.println();
		}
	}

	public static class Threading extends Thread
	{
		public int threadNumber;

		// Constructor
	 	public Threading (int threadNumber)
	 	{
	 		this.threadNumber = threadNumber;
	 	}

	 	@Override
	 	public void run ()
	 	{
	 		for (int i = 0; i < 60; i++)
	 		{
				int result = r.nextInt(high-low) + low;
				temps[threadNumber][i] = result;
			}
	 	}
	}

	public static void main (String[] args)
	{
		Scanner in = new Scanner(System.in);

		System.out.print("How many hours do you want the Rover to get Atmospheric Temperature: ");
		int hours = in.nextInt();
		System.out.println();

		for (int i = 0; i < hours; i++)
		{
			ArrayList<Threading> threads = new ArrayList<Threading>();

			for (int j = 0; j < 8; j++)
			{
				Threading thread = new Threading(j);
				threads.add(thread);
				thread.start();
			}

			for (int j = 0; j < 8; j++)
			{
				Threading thread = threads.get(j);
				try{
					thread.join();
				}catch(Exception e)
				{

				}

			}

			findFiveMaxAndMin();
			findLargestDifference();

			System.out.println("Hour " + (i + 1) + ": ");
			System.out.print("Lowest Temperatures: ");
			for (int k = 0; k < 5; k++)	
				System.out.print(min.poll() + " ");
			

			System.out.print(" Highest Temperatures: ");
			for (int k = 0; k < 5; k++)
				System.out.print(max.poll() + " ");

			temperature temp = difference.poll();

			System.out.println("Biggest temp difference: " + temp.difference + " at " + temp.minute1 + " " + temp.minute2);
			System.out.println();
			max.clear();
			min.clear();
			

			difference.clear();

		}


	}
}