package com.FabioAssignment8;

import java.util.List;

public class UniqueNumberCount {

	public void uniqueNumber(List<Integer> list) {
		
		for (int i = 0; i < 15; i++) {
			
			int nbr = i;
			
			long size = list.stream().filter(o -> o == nbr).count();
			System.out.println(i + "=" + size);
		}
		
	}
}
