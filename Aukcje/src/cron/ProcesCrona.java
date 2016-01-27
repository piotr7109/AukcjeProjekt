package cron;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ProcesCrona implements ServletContextListener
{
	private Thread t = null;

	public void contextInitialized(ServletContextEvent contextEvent)
	{
		t = new Thread()
		{
			// task
			public void run()
			{
				try
				{
					while (true)
					{
						SprawdzAutomaty.sprawdz();
						SprawdzFinalAukcji.sprawdz();
						Thread.sleep(1000);
					}
				}
				catch (InterruptedException e)
				{
				}
			}
		};
		t.start();

	}

	public void contextDestroyed(ServletContextEvent contextEvent)
	{
		// context is destroyed interrupts the thread
		t.interrupt();
	}
}