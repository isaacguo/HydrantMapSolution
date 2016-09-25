package com.isaac.hydrantmap.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;

public abstract class AbstractService
{
	@Autowired
	private CounterService counterService;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected void logServiceInfo(String methodName, Object... args)
	{
		String argString = " args" + (args.length > 0 ? ": " + argsToString(args) : ".");
		logger.info("> " + this.getClass().getName() + "." + methodName + " was invoked with " + args.length + argString);
		counterService.increment("method.invoked." + getClass().getName() + " " + methodName);
	}

	/**
	 * This method may cause jpa entities print recursive toString information
	 * @param args
	 * @return
	 */
	private String argsToString(Object... args)
	{
		if (args.length > 0)
		{
			StringBuilder sb = new StringBuilder();
			int i;
			for (i = 0; i < args.length - 1; i++)
			{
				sb.append("arg" + i + ":" + args[i].toString() + ", ");
			}
			sb.append("arg" + i + ":" + args[args.length - 1]);
			return sb.toString();
		}
		else
		{
			return "";
		}
	}
}
