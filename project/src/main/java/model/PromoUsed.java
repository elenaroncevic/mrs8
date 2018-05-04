package model;

import java.text.SimpleDateFormat;

public class PromoUsed extends Promo {
	private SimpleDateFormat endingDate;
	//private Dictionary<RegisteredUser, Double> bids;
	//private RegisteredUser owner;

	public SimpleDateFormat getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(SimpleDateFormat endingDate) {
		this.endingDate = endingDate;
	}
	
	
	
}
