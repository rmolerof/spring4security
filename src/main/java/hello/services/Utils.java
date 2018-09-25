package hello.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import hello.businessModel.Dispenser;
import hello.businessModel.ExpenseOrCredit;
import hello.businessModel.Station;
import hello.businessModel.Tank;
import hello.businessModel.TotalDay;
import hello.model.DayDataCriteria;

@Service
public class Utils {

	public Station updateStation(Station currentStation, DayDataCriteria dayDataCriteria) {
		
		Map<String, Double> dayData = dayDataCriteria.getDayData();
		String pumpAttendantNames = dayDataCriteria.getPumpAttendantNames();
		Date date = dayDataCriteria.getDate();
		String shift = dayDataCriteria.getShift();
		Double totalCash = dayDataCriteria.getTotalCash();
		List<ExpenseOrCredit> expensesAndCredits = dayDataCriteria.getExpensesAndCredits(); 
		
		TotalDay totalDay = new TotalDay();
		
		for (Entry<String, Dispenser> entry: currentStation.getDispensers().entrySet()) {
			
			double gallonsDiff = roundTwo((dayData.get(entry.getKey()) - entry.getValue().getGallons()));
			String name = entry.getKey().substring(0, entry.getKey().lastIndexOf("_"));
			totalDay.setTotalGalsSoldDay(name, toFixedTwo(totalDay.getTotalGalsSoldDay(name) + roundTwo(dayData.get(entry.getKey()) - entry.getValue().getGallons())));
			totalDay.setTotalSolesRevenueDay(name, toFixedTwo(totalDay.getTotalSolesRevenueDay(name) + toFixedTwo(gallonsDiff * entry.getValue().getPrice())));
			totalDay.setTotalSolesRevenueDay(roundTwo(totalDay.getTotalSolesRevenueDay() + toFixedTwo(gallonsDiff * entry.getValue().getPrice())));
			double priceDiff = roundTwo(entry.getValue().getPrice() - entry.getValue().getCost());
			totalDay.setTotalProfitDay(name, roundTwo(totalDay.getTotalProfitDay(name) + toFixedTwo(gallonsDiff * priceDiff)));
			totalDay.setTotalProfitDay(roundTwo(totalDay.getTotalProfitDay() + toFixedTwo(gallonsDiff * priceDiff)));
			totalDay.setStockGals(name, toFixedTwo(currentStation.getTanks().get(name).getGals() - totalDay.getTotalGalsSoldDay(name)));
		}
		
		// Update Station numbers
		Station newCurrentStation = new Station(currentStation);
		
		newCurrentStation.setPumpAttendantNames(pumpAttendantNames);
		newCurrentStation.setDate(date);
		newCurrentStation.setShift(shift);
		newCurrentStation.setTotalCash(totalCash);
		newCurrentStation.setExpensesAndCredits(expensesAndCredits);
		newCurrentStation.setTotalDay(totalDay);
		
		
		// Update gallons counter
		for (Entry<String, Dispenser> entry: newCurrentStation.getDispensers().entrySet()) {
			entry.getValue().setGallons(dayData.get(entry.getKey()));
		}
		
		// Update tanks' stock
		for (Entry<String, Tank> entry: newCurrentStation.getTanks().entrySet()) {
			entry.getValue().setGals(totalDay.getStockGals(entry.getKey()));
		}
		
		// Save updated station status
		//System.out.println(newCurrentStation);
		
		return newCurrentStation;
	}
	
	public double toFixedTwo(double amt) {
		return Math.floor(amt * 100) / 100.0;
	}
	
	public double roundTwo(double amt) {
		return Math.round(amt * 100) / 100.0;
	}
}
