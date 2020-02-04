package com.FabioAssignment8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class multithreadingApplication {

	static List<Integer> fullList = Collections.synchronizedList(new ArrayList<>());
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		Assignment8 input = new Assignment8();
		UniqueNumberCount numberCount = new UniqueNumberCount();
		
		ExecutorService servicePool = Executors.newCachedThreadPool();
				
		for (int i = 0; i < 1000; i++) {
			
			// Don't think we need this one as you have the Thread.sleep() in your Assignment8 file
			// try { Thread.sleep(500); } catch (InterruptedException e1) {e1.printStackTrace();}
			
			CompletableFuture<List<Integer>> task = CompletableFuture.supplyAsync
					(() -> input.getNumbers(), servicePool)
					.thenApply(o -> o.stream().collect(Collectors.toList()));
			
			CompletableFuture.supplyAsync(() -> addToMainList(task), servicePool);
									
			System.out.println(fullList.stream().count());
			
		}
	    while (fullList.stream().count() < 1000000) {
	    	Long elements = fullList.stream().count();
		    System.out.println("Number of integers is: " + elements);
	    }
		System.out.println("Last Count: " + fullList.stream().count());
		numberCount.uniqueNumber(fullList);
	}

	private static Object addToMainList(CompletableFuture<List<Integer>> list) {
				
		List<Integer> listOf1000 = Collections.synchronizedList(new ArrayList<>());
		
		// Tried to use it without the nested synchronized block but it would not read in all 
		// 1,000,000 Integers if I did that. 
		try {
			synchronized (listOf1000) {
				listOf1000 = list.get();
				synchronized (fullList) {
					fullList.addAll(listOf1000.stream().collect(Collectors.toList()));
				}
			}
			
		} catch (InterruptedException | ExecutionException e1) {
			e1.printStackTrace();
		}
							
		return null;
	}

//

}
