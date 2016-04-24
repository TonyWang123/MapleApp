package com.snlab.maple.mapleclient.examples;

import com.snlab.maple.mapleclient.api.Link;
import com.snlab.maple.mapleclient.core.odl.ODLLink;

import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.Taint;

public class TestConditionApp{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i = MultiTainter.taintedInt(1, "a");
		int m = MultiTainter.taintedInt(2, "b");
		
		int u;
		if(m > i){
			u = i;
			System.out.println(MultiTainter.getTaint(u));
		}

		System.out.println(i + " i - taint is " + MultiTainter.getTaint(i));

		int k;

		if (i > 0)//control-tag-add-i
		{
			k = 5;
			System.out.println(MultiTainter.getTaint(k));
			if(m > 0)// add m
				k++;
			//remove m
		}
		else
		{
			//control-tag-add-i
			k = 6;
			if(m > 0)// add m
				k++;
			//remove m
		}
		int f = MultiTainter.taintedInt(4, "Foo");
		//control-tag-remove-i
		int r = 54;
		switch (f) {
		case 0:
			r = 5;
			break;
		case 1:
			r = 6;
			break;
		case 2:
			r = 7;
			break;
		}
		System.out.println(i + " i - taint is " + MultiTainter.getTaint(i));
		System.out.println(f + " f - taint is " + MultiTainter.getTaint(f));
		System.out.println(k + " k - taint is " + MultiTainter.getTaint(k));
		System.out.println(r + " r - taint is " + MultiTainter.getTaint(r));
		
		ODLLink l1 = new ODLLink();
		l1.setBandwidth(100);
		int b1 = MultiTainter.taintedInt(l1.getBandwidth(), "l1");
		ODLLink l2 = new ODLLink();
		l2.setBandwidth(50);
		int b2 = MultiTainter.taintedInt(l2.getBandwidth(), "l2");
		Link link = null;
		if(b1 > b2)link = l1;
		else link = l2;
		System.out.println("link taint: " + MultiTainter.getTaint(link));
	}

}
