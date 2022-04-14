import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicInteger;

class Node
{
	public ReentrantLock lock;

	Integer item;
	int key;
	Node next;

	public Node(Integer item)
	{
		this.lock = new ReentrantLock();
		this.item = item;
		this.next = null;
		this.key = item.hashCode();
	}
}

class optimisticLinkedList
{
	public static Node head;

	public optimisticLinkedList()
	{
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(0);
	}

	public static boolean validate (Node pred, Node curr)
	{
		Node node = head; 

		if (pred == null || curr == null)
			return false;

		while (node != null && node.key <= pred.key)
		{
			if (node == pred)
				return pred.next == curr;
			node = node.next;
		}

		return false;
	}

	public static boolean contains (Integer item)
	{
		int key = item.hashCode();

		while (true)
		{
			Node pred = head;
			Node curr = pred.next;

			while (curr.key < key && curr.next != null)
			{
				pred = curr;
				curr = curr.next;
			}

			pred.lock.lock();
			curr.lock.lock();
			
			try
			{
				if (validate(pred, curr))
					return (curr.key == key);
				
			}
			finally{
				pred.lock.unlock();
				curr.lock.unlock();
			}
		}
	}

	public static boolean remove (Integer item)
	{
		int key = item.hashCode();

		while (true)
		{
			Node pred = head;
			Node curr = pred.next;
			while (curr.key < key && curr.next != null)
			{
				pred = curr;
				curr = curr.next;
			}
			pred.lock.lock();
			curr.lock.lock();

			try
			{
				if (validate(pred, curr))
				{
					if (curr.key == key)
					{
						pred.next = curr.next;
						// added.getAndDecrement();
						return true;
					}
					else
						return false;
				}
			} finally
			{
				pred.lock.unlock();
				curr.lock.unlock();
			}
		}
	}

		public static boolean add (Integer item)
		{
			int key = item.hashCode();

			while (true)
			{
				Node pred = head;
				Node curr = pred.next;
				while (curr.key < key && curr.next != null)
				{
					pred = curr;
					curr = curr.next;
				}
				
				pred.lock.lock(); 
				curr.lock.lock();

				try
				{
					if (validate(pred, curr))
					{
						if (curr.key == key && curr.next != null)
							return false;
						else
						{
							Node node = new Node(item);
							node.next = curr;
							pred.next = node;
							return true;
						}
						
					}
				} finally
				{
					pred.lock.unlock();
					curr.lock.unlock();
				}
			}  
		}
	}

public class presents
{
	public static AtomicInteger added = new AtomicInteger(1);
	public static optimisticLinkedList list = new optimisticLinkedList();

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
	 		while (added.get() < 500000)
	 		{
	 			int current = added.getAndIncrement();
	 			list.add(current);
	 			list.remove(current);
	 		}

	 	}
	}

	public static void main (String[] args)
	{
		ArrayList<Threading> threads = new ArrayList<Threading>();
	
		for (int i = 0; i < 4; i++)
		{
			Threading thread = new Threading(i);
			threads.add(thread);
			thread.start();
		}

		for (int j = 0; j < 4; j++)
		{
			Threading thread = threads.get(j);
			try{
				thread.join();
			}catch(Exception e)
			{

			}
		}

		list.remove(0);

		System.out.println("All 500,000 Thank You Cards have been written for all 500,000 presents!");
	}
}