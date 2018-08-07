package hello.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import hello.businessModel.Dispenser;
import hello.businessModel.Station;
import hello.businessModel.Tank;
import hello.businessModel.TotalDay;
import hello.model.DayDataCriteria;
import hello.model.User;

@Service
public class UserService {

	private List<User> users;
	private Station currentStation;
	
	@PostConstruct
	private void initDataForTesting() {
		users = new ArrayList<User>();
        
        User user1 = new User("mkyong", "password111", "mkyong@yahoo.com");
        User user2 = new User("yflow", "password222", "yflow@yahoo.com");
        User user3 = new User("laplap", "password333", "mkyong@yahoo.com");
        
        users.add(user1);
        users.add(user2);
        users.add(user3);
	}
	
	public List<User> findByUserNameOrEmail(String username){
		if(username.equals("current")) {
			// Retrieving actual users
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			return new ArrayList<User>(Arrays.asList(new User(authentication.getName(), "*********", authentication.getAuthorities().toString())));
		} else {
			List<User> result = users.stream().
					filter(x -> x.getUsername().equalsIgnoreCase(username)).
					collect(Collectors.toList());
			return result;
		}
		
	}
	
	public List<Station> findStationStatusByDates(String dateEnd, String dateBeg) {
		Station laJoya = new Station();
		laJoya.setId(101L);
		laJoya.setName("La Joya");
		laJoya.setDate(new Date(1533441600000L));
		
		Tank d2 = new Tank(1, "d2", 10000D);
		Tank g90 = new Tank(2, "g90", 3000D);
		Tank g95 = new Tank(3, "g95", 3000D);
		
		Map<String, Tank> tanks = new HashMap<String, Tank>();
		tanks.put(d2.getFuelType(), d2);
		tanks.put(g90.getFuelType(), g90);
		tanks.put(g95.getFuelType(), g95);
		
		laJoya.setTanks(tanks);
		
		Dispenser d2_1 = new Dispenser(1, "d2", 288934.73,	12.39,	11.01);
		Dispenser d2_2 = new Dispenser(2, "d2", 144360.82,	12.39,	11.01);
		Dispenser d2_3 = new Dispenser(3, "d2", 73147.96,	12.39,	11.01);
		Dispenser d2_4 = new Dispenser(4, "d2", 211896.21,	12.39,	11.01);
		Dispenser d2_5 = new Dispenser(5, "d2", 723954.62,	12.39,	11.01);
		Dispenser d2_6 = new Dispenser(6, "d2", 83166.11,	12.39,	11.01);
		Dispenser g90_1 = new Dispenser(1, "g90", 39150.31,	12.89,	10.48);
		Dispenser g90_2 = new Dispenser(2, "g90", 32190.28,	12.89,	10.48);
		Dispenser g90_3 = new Dispenser(3, "g90", 64742.82,	12.89,	10.48);
		Dispenser g90_4 = new Dispenser(4, "g90", 174792.25, 12.89,	10.48);
		Dispenser g95_1 = new Dispenser(1, "g95", 96791.69,	13.99,	11.45);
		Dispenser g95_2 = new Dispenser(2, "g95", 98998.05,	13.99,	11.45);

		
		
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
		
		setCurrentStation(laJoya);
		
		return Stream.of(laJoya).collect(Collectors.toList());
	}
	
	
	public List<Station> submitDayData(DayDataCriteria dateDataCriteria) {
		
		Station updatedStation = updateStation(getCurrentStation(), dateDataCriteria);
		
		return Stream.of(updatedStation).collect(Collectors.toList());
	}

	public Station getCurrentStation() {
		return currentStation;
	}

	public void setCurrentStation(Station currentStation) {
		this.currentStation = currentStation;
	}
	
	private Station updateStation(Station currentStation, DayDataCriteria dateDataCriteria) {
		
		Map<String, Double> dayData = dateDataCriteria.getDayData();
		Date date = dateDataCriteria.getDate();
		
		TotalDay totalDay = new TotalDay();
		
		for (Entry<String, Dispenser> entry: currentStation.getDispensers().entrySet()) {
			
			double gallonsDiff = dayData.get(entry.getKey()) - entry.getValue().getGallons();
			String name = entry.getKey().substring(0, entry.getKey().lastIndexOf("_"));
			totalDay.setTotalGalsSoldDay(name, totalDay.getTotalGalsSoldDay(name) + dayData.get(entry.getKey()) - entry.getValue().getGallons());
			totalDay.setTotalSolesRevenueDay(name, totalDay.getTotalSolesRevenueDay(name) + gallonsDiff * entry.getValue().getPrice());
			totalDay.setTotalSolesRevenueDay(totalDay.getTotalSolesRevenueDay() + gallonsDiff * entry.getValue().getPrice());
			totalDay.setTotalProfitDay(name, totalDay.getTotalGalsSoldDay(name) * (entry.getValue().getPrice() - entry.getValue().getCost()));
			totalDay.setTotalProfitDay(totalDay.getTotalProfitDay() + gallonsDiff * (entry.getValue().getPrice() - entry.getValue().getCost()));
			totalDay.setStockGals(name, currentStation.getTanks().get(name).getGals() - totalDay.getTotalGalsSoldDay(name));
		}
		
		// Update Station numbers
		Station newCurrentStation = new Station(currentStation);
		
		newCurrentStation.setDate(date);
		
		// Update gallons counter
		for (Entry<String, Dispenser> entry: newCurrentStation.getDispensers().entrySet()) {
			entry.getValue().setGallons(dayData.get(entry.getKey()));
		}
		
		// Update tanks' stock
		for (Entry<String, Tank> entry: newCurrentStation.getTanks().entrySet()) {
			entry.getValue().setGals(totalDay.getStockGals(entry.getKey()));
		}
		
		// Save updated station status
		System.out.println(newCurrentStation);
		
		return newCurrentStation;
	}
	
	
}
