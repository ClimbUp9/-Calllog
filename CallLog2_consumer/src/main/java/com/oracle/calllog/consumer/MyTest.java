package com.oracle.calllog.consumer;

import java.io.IOException;

import org.junit.Test;

public class MyTest {

	@Test
	public void testgetPartition() throws IOException{
//		16057563899,–Ì∫Í—ﬁ,16468595837,÷‹Ë•,2018/01/22 00:21:25,1230
//		18856521012,≥¬∞Î—©,14382184011,…Ú÷ÏÊ√,2018/01/06 10:51:49,2852
//		13818189989,÷£÷“Ωø,18058990235,∫ŒŸªŸª,2018/05/16 23:43:54,0185
//		16090585997,ª™—Û,15981479062,Ò“¿Ú,2018/04/25 01:53:22,1648
//		18664015893,”»π‚¿º,13904361845,÷‹—©√∑,2018/04/27 07:13:06,6242

//		String partition1 = "6468595837" + "1801";
////		Long l = Long.parseLong(partition1)^64;
////		partition1 = l+"";
//		String partition2 = "4382184011" + "1802";
//		String partition3 = "6057563899" + "1803";
//		int number1 = partition1.hashCode()%6;
//		int number2 = partition2.hashCode()%6;
//		int number3 = partition3.hashCode()%6;
//		System.out.println(number1);
//		System.out.println(number2);
//		System.out.println(number3);
//		
		String str = "2018/04/27 07:13:06";
		String[] strs = str.split("/");
		System.out.println(strs[0]+strs[1]);
	}
	
}
