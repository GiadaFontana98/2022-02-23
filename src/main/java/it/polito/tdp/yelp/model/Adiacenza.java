package it.polito.tdp.yelp.model;

import java.time.LocalDate;
import java.util.Objects;


public class Adiacenza {
	Review d1;
	Review d2;
	LocalDate data1;
	LocalDate data2;
	public Adiacenza(Review d1, Review d2, LocalDate data1, LocalDate data2) {
		super();
		this.d1=d1;
		this.d2= d2;
		this.data1 = data1;
		this.data2 = data2;
	}

	



	public Review getD1() {
		return d1;
	}


	public void setD1(Review d1) {
		this.d1 = d1;
	}


	public Review getD2() {
		return d2;
	}


	public void setD2(Review d2) {
		this.d2 = d2;
	}


	public LocalDate getData1() {
		return data1;
	}
	public void setData1(LocalDate data1) {
		this.data1 = data1;
	}
	public LocalDate getData2() {
		return data2;
	}
	public void setData2(LocalDate data2) {
		this.data2 = data2;
	}
	@Override
	public int hashCode() {
		return Objects.hash(d1, d2, data1, data2);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adiacenza other = (Adiacenza) obj;
		return Objects.equals(d1, other.d1) && Objects.equals(d2, other.d2) && Objects.equals(data1, other.data1)
				&& Objects.equals(data2, other.data2);
	}
	
	

}
