package com.snlab.maple.mapleclinet.core.tracetree;

import java.util.List;

public class D extends Node{
	
	public Node subtree;// we assume all the subtree are equal
	
	public String dataType;

	@Override
	public void delete(Node node) {
		// TODO Auto-generated method stub
		subtree = null;
	}

	@Override
	public void augment(List<TraceItem> trace, Action action) {
		// TODO Auto-generated method stub
		if (trace.isEmpty()) {
		      return;
		}
		TraceItem next = trace.remove(0);//next is topo, D
		if(next.field.equals(TraceItem.Field.TOPOLOGY))dataType = "topology";
		
		if (trace.size()==0) {  // Add leaf node.
		      L child = new L(action);
		      child.father = this;
		      subtree = child;
		    }
		else{
		    if(subtree != null)subtree.augment(trace, action);
			else{
				if (trace.get(0) instanceof TraceItemT) {
			          T child = new T();
			          child.father = this;
			          subtree = child;
			          child.augment(trace, action);
			    }else if (trace.get(0) instanceof TraceItemV) {
			          V child = new V();
			          child.father = this;
			          subtree = child;
			          child.augment(trace, action);
			    }else if (trace.get(0) instanceof TraceItemD) {
			          D child = new D();
			          child.father = this;
			          subtree = child;
			          child.augment(trace, action);
			    }else {
			          //FIXME:TODO: Replace println with a uniform LOG module.
			          System.out.println("Error in D.augment: Unknown type of TraceItem.");
			    }
			}
		}
		    
	}

}
