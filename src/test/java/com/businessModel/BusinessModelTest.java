package com.businessModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hello.businessModel.Company;
import hello.businessModel.Dispenser;
import hello.businessModel.ExpenseOrCredit;
import hello.businessModel.Station;
import hello.businessModel.Tank;
import hello.model.DayDataCriteria;
import hello.services.Utils;

public class BusinessModelTest {

	public static void main(String[] args) {
		
		Company com = new Company(100, "Molfer");
		Station laJoya = new Station();
		laJoya.setStationId(101L);
		laJoya.setName("La Joya");
		laJoya.setPumpAttendantNames("Rudy");
		laJoya.setDate(new Date());
		laJoya.setTotalCash(8000D);
		laJoya.setExpensesAndCredits(new ArrayList<ExpenseOrCredit>());
		
		Tank d2 = new Tank(1L, "d2", 9517.40D, 0D);
		Tank g90 = new Tank(2L, "g90", 2831.86D, 0D);
		Tank g95 = new Tank(3L, "g95", 2972.23D, 0D);
		
		Map<String, Tank> tanks = new HashMap<String, Tank>();
		tanks.put(d2.getFuelType(), d2);
		tanks.put(g90.getFuelType(), g90);
		tanks.put(g95.getFuelType(), g95);
		
		laJoya.setTanks(tanks);
		
		Dispenser d2_1 = new Dispenser(1, "d2", 288934.73,	12.39,	11.01, 9);
		Dispenser d2_2 = new Dispenser(2, "d2", 144360.82,	12.39,	11.01, 9);
		Dispenser d2_3 = new Dispenser(3, "d2", 73147.96,	12.39,	11.01, 8);
		Dispenser d2_4 = new Dispenser(4, "d2", 211896.21,	12.39,	11.01, 9);
		Dispenser d2_5 = new Dispenser(5, "d2", 723954.62,	12.39,	11.01, 9);
		Dispenser d2_6 = new Dispenser(6, "d2", 83166.11,	12.39,	11.01, 8);
		Dispenser g90_1 = new Dispenser(1, "g90", 39150.31,	12.89,	10.48, 8);
		Dispenser g90_2 = new Dispenser(2, "g90", 32190.28,	12.89,	10.48, 8);
		Dispenser g90_3 = new Dispenser(3, "g90", 64742.82,	12.89,	10.48, 8);
		Dispenser g90_4 = new Dispenser(4, "g90", 174792.25, 12.89,	10.48, 9);
		Dispenser g95_1 = new Dispenser(1, "g95", 96791.69,	13.99,	11.45, 8);
		Dispenser g95_2 = new Dispenser(2, "g95", 98998.05,	13.99,	11.45, 8);

		
		
		Map<String, Dispenser> dispensers = new HashMap<String, Dispenser>();
		dispensers.put(d2_1.getName() + "_" + Long.toString(d2_1.getId()), d2_1);
		dispensers.put(d2_2.getName() + "_" + Long.toString(d2_2.getId()), d2_2);
		dispensers.put(d2_3.getName() + "_" + Long.toString(d2_3.getId()), d2_3);
		dispensers.put(d2_4.getName() + "_" + Long.toString(d2_4.getId()), d2_4);
		dispensers.put(d2_5.getName() + "_" + Long.toString(d2_5.getId()), d2_5);
		dispensers.put(d2_6.getName() + "_" + Long.toString(d2_6.getId()), d2_6);
		dispensers.put(g90_1.getName() + "_" + Long.toString(g90_1.getId()), g90_1);
		dispensers.put(g90_2.getName() + "_" + Long.toString(g90_2.getId()), g90_2);
		dispensers.put(g90_3.getName() + "_" + Long.toString(g90_3.getId()), g90_3);
		dispensers.put(g90_4.getName() + "_" + Long.toString(g90_4.getId()), g90_4);
		dispensers.put(g95_1.getName() + "_" + Long.toString(g95_1.getId()), g95_1);
		dispensers.put(g95_1.getName() + "_" + Long.toString(g95_2.getId()), g95_2);
		
		laJoya.setDispensers(dispensers);
		
		// Simulation
		Map<String, Double> dayData = new HashMap<String, Double>();
		dayData.put("d2_1", 289041.95);
		dayData.put("d2_2", 144382.63);
		dayData.put("d2_3", 73242.59);
		dayData.put("d2_4", 211990.12);
		dayData.put("d2_5", 724116.58);
		dayData.put("d2_6", 83397.64);
		dayData.put("g90_1", 39187.64);
		dayData.put("g90_2", 32222.86);
		dayData.put("g90_3", 64773.44);
		dayData.put("g90_4", 174827.79);
		dayData.put("g95_1", 96795.94);
		dayData.put("g95_2", 99017.05);

		Utils utils = new Utils();
		DayDataCriteria dayDataCriteria = new DayDataCriteria();
		dayDataCriteria.setDate(new Date());
		dayDataCriteria.setDayData(dayData);
		dayDataCriteria.setShift("1");
		
		Station laJoyaCalculated = utils.updateStation(laJoya, dayDataCriteria);
		/*TotalDay totalDay = new TotalDay();
		
		for (Entry<String, Dispenser> entry: laJoya.getDispensers().entrySet()) {
			
			double gallonsDiff = dayData.get(entry.getKey()) - entry.getValue().getGallons();
			String name = entry.getKey().substring(0, entry.getKey().lastIndexOf("_"));
			totalDay.setTotalGalsSoldDay(name, totalDay.getTotalGalsSoldDay(name) + dayData.get(entry.getKey()) - entry.getValue().getGallons());
			totalDay.setTotalSolesRevenueDay(name, totalDay.getTotalSolesRevenueDay(name) + gallonsDiff * entry.getValue().getPrice());
			totalDay.setTotalSolesRevenueDay(totalDay.getTotalSolesRevenueDay() + gallonsDiff * entry.getValue().getPrice());
			totalDay.setTotalProfitDay(name, totalDay.getTotalGalsSoldDay(name) * (entry.getValue().getPrice() - entry.getValue().getCost()));
			totalDay.setTotalProfitDay(totalDay.getTotalProfitDay() + gallonsDiff * (entry.getValue().getPrice() - entry.getValue().getCost()));
			totalDay.setStockGals(name, tanks.get(name).getGals() - totalDay.getTotalGalsSoldDay(name));
		}
		
		com.setStations(new ArrayList<Station>(Arrays.asList(laJoya)));*/
		
		// Report
		System.out.println(laJoyaCalculated.getTotalDay().toString());
		
	}

}
