package multiThreading;

import java.lang.management.ManagementFactory;

public class MyRunnable implements Runnable
{

	@Override
	public void run() {
		 System.out.println("Runnable running");  
		 //System.out.println(ManagementFactory.getRuntimeMXBean().getName());	
		 System.out.println("Run by "+Thread.currentThread().getName());
	} 
}
