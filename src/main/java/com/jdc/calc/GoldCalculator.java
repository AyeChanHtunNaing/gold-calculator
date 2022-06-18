package com.jdc.calc;
import java.util.*;
public class GoldCalculator {
	private Integer refPrice;

	public enum Unit {
		Kyat(1), Pae(16), Ywae(120);
		private int value;
		private Unit(int value) {
			this.value=value;
		}
		public int getValue() {
			return value;
		}
	}

	public enum Purity {
		Sixteen(16), Fifteen(15), Fourteen(14);

		private int value;

		private Purity(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
    public int getPrice(Purity purity,Unit unit,int weight) {
    	if(null==refPrice) {
    		throw new IllegalStateException("There is no reference price for calculator");
    	}
    	if(null == purity || null == unit || weight == 0) {
    		throw new IllegalArgumentException("data must not be empty");
    	}
    	var oneUnitPrice=refPrice/16;
    	var pricePerKyat=oneUnitPrice*purity.getValue();
		return pricePerKyat/unit.getValue()*weight;	
    }
    public int getPrice(Purity purity,Map<Unit,Integer> weight) {
    	int total = 0;
		for(var unit:weight.keySet()){
			total+=getPrice(purity,unit,weight.get(unit));
		}
		return total;
		
    }
	public int getPrice(Purity purity) {
		if (null == refPrice) {
			throw new IllegalStateException("There is no reference price for calculator");
		}
		if (null == purity) {
			throw new IllegalArgumentException("Purity must not be null");
		}
		var oneUnitPrice = refPrice / 16;
		return oneUnitPrice*purity.getValue();
	}

	public void setReferencePrice(int refPrice) {
		this.refPrice=refPrice;
	}
	//အခေါက်ရွှေ
	//တစ်ကျပ်သား = ၁၆ပဲ
	//တစ်ကျပ်သား = ၁၂၀ရွေး
}
