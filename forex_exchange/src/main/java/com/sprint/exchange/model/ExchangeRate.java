package com.sprint.exchange.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "exchange_rate")
public class ExchangeRate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "from_currency")
	private String fromCurrency;
	
	@Column(name = "to_currency")
	private String toCurrency;
	
	@Column(name = "rate")
	private Double rate;
	
	@Column(name = "date")
	private LocalDate date;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "admin_id")
	private Admin admin;
	
	

	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFromCurrency() {
		return fromCurrency;
	}
	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}
	public String getToCurrency() {
		return toCurrency;
	}
	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}			
}
