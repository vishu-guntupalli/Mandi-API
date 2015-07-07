package com.wku.mandi.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;

@Aspect
public class MandiServiceAspectLogging {
	
	private static final Logger log = Logger.getLogger(MandiServiceAspectLogging.class);
	
	@Around("execution(* com.wku.mandi.*.*(..))")
	   public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		long begintime = System.currentTimeMillis();
		log.info("-------------------------------------------------------------------------------" );
		log.info("APILoggingAspect Before method name: " + joinPoint.getSignature().getName());
		log.info("APILoggingAspect method arguments: " + Arrays.toString(joinPoint.getArgs()));
		log.info("APILoggingAspect method target: " + joinPoint.getTarget());
		Object result = "EMPTY RESULT OBJECT";
		try {
			result = joinPoint.proceed(); //continue on the intercepted method
		}
		catch (Exception ex) {
		}
		long endtime = System.currentTimeMillis();
		log.info("APILoggingAspect After result: " + result.toString());
        log.info("APILoggingAspect After method name: " + joinPoint.getSignature().getName() + " in time " + (endtime-begintime) + "ms");
        log.info("-------------------------------------------------------------------------------" );
        return result;
	   }

}