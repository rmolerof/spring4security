package hello.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import hello.Application;
import hello.businessModel.Dispenser;
import hello.businessModel.ExpenseOrCredit;
import hello.businessModel.GasPrice;
import hello.businessModel.GasPricesVo;
import hello.businessModel.Station;
import hello.businessModel.Tank;
import hello.businessModel.TanksVo;
import hello.businessModel.TotalDay;
import hello.domain.GasPricesDao;
import hello.domain.GasPricesRepository;
import hello.domain.StationDao;
import hello.domain.StationRepository;
import hello.domain.TanksDao;
import hello.domain.TanksRepository;
import hello.model.DayDataCriteria;
import hello.model.User;

@Service
public class UserService {

	private static Logger logger = LogManager.getLogger(Application.class);
	
	private List<User> users;
	private Station currentStation;
	private TanksVo currentTanksVo;
	private GasPricesVo currentGasPricesVo;
	
	@Autowired
    private StationRepository stationRepository;
	@Autowired
    private TanksRepository tanksRepository;
	@Autowired
    private GasPricesRepository gasPricesRepository;
	@Autowired
	private Utils utils;
	
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
	
	public List<Station> findLatestStationStatus(String dateEnd, String dateBeg) {
		Station laJoya = new Station();
		List<StationDao> stationDaos = stationRepository.findLatest(dateEnd, dateBeg);
		StationDao stationDao = null;
		
		if (null == stationDaos) {
			stationDao = new StationDao();
			
			stationDao.setStationId(101L);
			stationDao.setName("La Joya");
			stationDao.setShift("2");
			stationDao.setPumpAttendantNames("");
			stationDao.setDate(new Date());
			stationDao.setTotalCash(0D);
			stationDao.setExpensesAndCredits(Arrays.asList(new ExpenseOrCredit()));
			
			Tank d2 = new Tank(1L, "d2", 0D, 0D);
			Tank g90 = new Tank(2L, "g90", 0D, 0D);
			Tank g95 = new Tank(3L, "g95", 0D, 0D);
			
			Map<String, Tank> tanks = new HashMap<String, Tank>();
			tanks.put(d2.getFuelType(), d2);
			tanks.put(g90.getFuelType(), g90);
			tanks.put(g95.getFuelType(), g95);
			
			stationDao.setTanks(tanks);
			
			Dispenser d2_1 = new Dispenser(1, "d2", 0,	0,	0, 9);
			Dispenser d2_2 = new Dispenser(2, "d2", 0,	0,	0, 9);
			Dispenser d2_3 = new Dispenser(3, "d2", 0,	0,	0, 8);
			Dispenser d2_4 = new Dispenser(4, "d2", 0,	0,	0, 9);
			Dispenser d2_5 = new Dispenser(5, "d2", 0,	0,	0, 9);
			Dispenser d2_6 = new Dispenser(6, "d2", 0,	0,	0, 8);
			Dispenser g90_1 = new Dispenser(1, "g90", 0, 0,	0, 8);
			Dispenser g90_2 = new Dispenser(2, "g90", 0, 0,	0, 8);
			Dispenser g90_3 = new Dispenser(3, "g90", 0, 0,	0, 8);
			Dispenser g90_4 = new Dispenser(4, "g90", 0, 0,	0, 9);
			Dispenser g95_1 = new Dispenser(1, "g95", 0, 0,	0, 8);
			Dispenser g95_2 = new Dispenser(2, "g95", 0, 0,	0, 8);

			
			
			Map<String, Dispenser> dispensers = new LinkedHashMap<String, Dispenser>();
			dispensers.put(d2_1.getName() + "_" + Long.toString(d2_1.getId()), d2_1);
			dispensers.put(g90_1.getName() + "_" + Long.toString(g90_1.getId()), g90_1);
			dispensers.put(d2_2.getName() + "_" + Long.toString(d2_2.getId()), d2_2);
			dispensers.put(d2_3.getName() + "_" + Long.toString(d2_3.getId()), d2_3);
			dispensers.put(g90_2.getName() + "_" + Long.toString(g90_2.getId()), g90_2);
			dispensers.put(d2_4.getName() + "_" + Long.toString(d2_4.getId()), d2_4);
			
			dispensers.put(g95_1.getName() + "_" + Long.toString(g95_1.getId()), g95_1);
			dispensers.put(g90_3.getName() + "_" + Long.toString(g90_3.getId()), g90_3);
			dispensers.put(d2_5.getName() + "_" + Long.toString(d2_5.getId()), d2_5);
			dispensers.put(g95_2.getName() + "_" + Long.toString(g95_2.getId()), g95_2);
			dispensers.put(g90_4.getName() + "_" + Long.toString(g90_4.getId()), g90_4);
			dispensers.put(d2_6.getName() + "_" + Long.toString(d2_6.getId()), d2_6);
			
			
			stationDao.setDispensers(dispensers);
		}
		
		List<Station> stations = null;
		if (dateEnd.equalsIgnoreCase("latest") && dateBeg.equalsIgnoreCase("")) {
			updateStatus(stationDaos.get(0));
			laJoya = new Station(stationDaos.get(0));
			setCurrentStation(laJoya);
			
			stations = Stream.of(laJoya).collect(Collectors.toList());
			
		}  else if (dateEnd.equalsIgnoreCase("latest") && dateBeg.equalsIgnoreCase("previous")) {
			
			stations = stationDaos.stream().map(stationdao -> {
				Station station = new Station(stationdao);
				return station;
			}).collect(Collectors.toList());
			
			// Update ObjectId and date from latest station status
			stations.get(1).setId(stations.get(0).getId());
			stations.get(1).setDate(stations.get(0).getDate());
			setCurrentStation(stations.get(1));
		}
		
		return stations;
	}
	
	public List<Station> findStationStatusByDates(String dateEnd, String dateBeg) {
		
		/*Station laJoya = new Station();
		laJoya.setStationId(101L);
		laJoya.setName("La Joya");
		laJoya.setShift("1");
		laJoya.setPumpAttendantNames("miriam, sadit");
		//laJoya.setDate(new Date(1533441600000L));
		laJoya.setDate(new Date());
		
		Tank d2 = new Tank(1, "d2", 10000D);
		Tank g90 = new Tank(2, "g90", 3000D);
		Tank g95 = new Tank(3, "g95", 3000D);
		
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

		
		
		Map<String, Dispenser> dispensers = new LinkedHashMap<String, Dispenser>();
		dispensers.put(d2_1.getName() + "_" + Long.toString(d2_1.getId()), d2_1);
		dispensers.put(g90_1.getName() + "_" + Long.toString(g90_1.getId()), g90_1);
		dispensers.put(d2_2.getName() + "_" + Long.toString(d2_2.getId()), d2_2);
		dispensers.put(d2_3.getName() + "_" + Long.toString(d2_3.getId()), d2_3);
		dispensers.put(g90_2.getName() + "_" + Long.toString(g90_2.getId()), g90_2);
		dispensers.put(d2_4.getName() + "_" + Long.toString(d2_4.getId()), d2_4);
		
		dispensers.put(g95_1.getName() + "_" + Long.toString(g95_1.getId()), g95_1);
		dispensers.put(g90_3.getName() + "_" + Long.toString(g90_3.getId()), g90_3);
		dispensers.put(d2_5.getName() + "_" + Long.toString(d2_5.getId()), d2_5);
		dispensers.put(g95_2.getName() + "_" + Long.toString(g95_2.getId()), g95_2);
		dispensers.put(g90_4.getName() + "_" + Long.toString(g90_4.getId()), g90_4);
		dispensers.put(d2_6.getName() + "_" + Long.toString(d2_6.getId()), d2_6);
		
		
		laJoya.setDispensers(dispensers);
		
		laJoya.setTotalCash(300.98D);
		
		laJoya.setExpensesAndCredits(new ExpenseOrCredit[] {
				new ExpenseOrCredit("uno", 1.1D),
				new ExpenseOrCredit("dos", 2.2D)});
		
		StationDao stationDao = stationRepository.save(new StationDao(laJoya));
		logger.info(stationDao);
		return null;*/
		
		
		/*StationDao s = new StationDao();
		s.setId(new ObjectId("5b6b4c3c4ddd5a7f3c951838"));
		Example<StationDao> example = Example.of(s);
		StationDao stationDao = stationRepository.findOne(example);*/
		
		
		//List<Station> laJoya = new Station();
		List<StationDao> stationDaos = stationRepository.findLatestMonth();
		/*List<Station> stations = new LinkedList<Station>();
		stations.add(new Station(stationDaos.get(0)));
		for (int i = 1; i < stationDaos.size(); i++) {
			Station station = new Station(stationDaos.get(i));
			stations.add(station);
			stations.get(i - 1).setPriorStation(station);
		}*/
		
		List<Station> stations = stationDaos.stream().map(stationDao -> {
			Station station = new Station(stationDao);
			return station;
		}).collect(Collectors.toList());
		
		return stations;
	}
	
	public void updateStatus(StationDao stationDao) {
		// update prices
		List<GasPrice> currentGasPrices = gasPricesRepository.findLatest("latest", "").get(0).getGasPrices();
		for (Entry<String, Dispenser> entry: stationDao.getDispensers().entrySet()) {
			for (GasPrice gasPrice: currentGasPrices) {
				if (entry.getKey().contains(gasPrice.getFuelType())) {
					entry.getValue().setPrice(gasPrice.getPrice());
					entry.getValue().setCost(gasPrice.getCost());
				}
			}
		}
		
		// update Stock
		List<Tank> currentTanks = tanksRepository.findLatest("latest", "").get(0).getTanks();
		for (Entry<String, Tank> entry: stationDao.getTanks().entrySet()) {
			for (Tank tank: currentTanks) {
				if (entry.getKey().contains(tank.getFuelType())) {
					entry.getValue().setGals(tank.getGals());
					entry.getValue().setTankId(tank.getTankId());
				}
			}
			
		}
		
	}
	
	public List<Station> submitDayData(DayDataCriteria dateDataCriteria) {
		
		Station updatedStation = utils.updateStation(getCurrentStation(), dateDataCriteria);
		
		StationDao stationdao = new StationDao(updatedStation);
		stationdao.setId(new ObjectId());
		StationDao stationDao = stationRepository.save(new StationDao(updatedStation));
		
		TanksVo tanksVo = new TanksVo(stationDao.getPumpAttendantNames(), stationDao.getDate(), new ArrayList<>(stationDao.getTanks().values()));
		submitTanksVo(tanksVo, "save");
		
		Station resetStationFromDB = new Station(stationDao);
		setCurrentStation(resetStationFromDB);
		
		return Stream.of(resetStationFromDB).collect(Collectors.toList());
	}
	
	public List<Station> updateLatestDayData(DayDataCriteria dateDataCriteria) {
		
		Station updatedStation = utils.updateStation(getCurrentStation(), dateDataCriteria);
		
		StationDao stationDao = stationRepository.save(new StationDao(updatedStation));
		
		TanksVo tanksVo = new TanksVo(stationDao.getPumpAttendantNames(), stationDao.getDate(), new ArrayList<>(stationDao.getTanks().values()));
		submitTanksVo(tanksVo, "update");
		
		Station resetStationFromDB = new Station(stationDao);
		setCurrentStation(resetStationFromDB);
		
		return Stream.of(resetStationFromDB).collect(Collectors.toList());
	}
	
	public List<Station> resetStatus(DayDataCriteria dateDataCriteria) {
		
		Station resetStation = resetStation(getCurrentStation(), dateDataCriteria);
		
		StationDao stationdao = new StationDao(resetStation);
		stationdao.setId(new ObjectId());
		StationDao stationDao = stationRepository.save(stationdao);
		
		Station resetStationFromDB = new Station(stationDao);
		setCurrentStation(resetStationFromDB);
		
		return Stream.of(resetStationFromDB).collect(Collectors.toList());
	}

	/*private Station updateStation(Station currentStation, DayDataCriteria dayDataCriteria) {
		
		Map<String, Double> dayData = dayDataCriteria.getDayData();
		String pumpAttendantNames = dayDataCriteria.getPumpAttendantNames();
		Date date = dayDataCriteria.getDate();
		String shift = dayDataCriteria.getShift();
		Double totalCash = dayDataCriteria.getTotalCash();
		List<ExpenseOrCredit> expensesAndCredits = dayDataCriteria.getExpensesAndCredits(); 
		
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
		
		newCurrentStation.setPumpAttendantNames(pumpAttendantNames);
		newCurrentStation.setDate(date);
		newCurrentStation.setShift(shift);
		newCurrentStation.setTotalCash(totalCash);
		newCurrentStation.setExpensesAndCredits(expensesAndCredits);
		
		
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
	}*/
	
	private Station resetStation(Station currentStation, DayDataCriteria dayDataCriteria) {
		
		Map<String, Double> dayData = dayDataCriteria.getDayData();
		String pumpAttendantNames = dayDataCriteria.getPumpAttendantNames();
		Date date = dayDataCriteria.getDate();
		String shift = dayDataCriteria.getShift();
		
		
		// Update Station numbers
		Station newCurrentStation = new Station(currentStation);
		newCurrentStation.setPumpAttendantNames(pumpAttendantNames);
		newCurrentStation.setDate(date);
		newCurrentStation.setShift(shift == "1" ? "2": "1");
		newCurrentStation.setTotalCash(0D);
		newCurrentStation.setExpensesAndCredits(new ArrayList<ExpenseOrCredit>());
		newCurrentStation.setTotalDay(new TotalDay());
		
		// Update gallons counter
		for (Entry<String, Dispenser> entry: newCurrentStation.getDispensers().entrySet()) {
			entry.getValue().setGallons(dayData.get(entry.getKey()));
		}
		
		return newCurrentStation;
	}
	
	public List<TanksVo> findStockByDates(String dateEnd, String dateBeg) {

		//TanksVo latestTankStatus = null;
		List<TanksDao> tanksDaos = tanksRepository.findLatest(dateEnd, dateBeg);
		
		/*latestTankStatus = new TanksVo(tanksDao);
		setCurrentTanksVo(latestTankStatus);
		
		return Stream.of(latestTankStatus).collect(Collectors.toList());*/
		
		
		List<TanksVo> tanksVos = tanksDaos.stream().map(tanksDao -> {
			TanksVo tanksVo = new TanksVo(tanksDao);
			return tanksVo;
		}).collect(Collectors.toList());
		
		return tanksVos;
		
		/*Tank tank = new Tank();
		tank.setTankId(1L);
		tank.setFuelType("d2");
		tank.setGals(0D);
		
		Tank tank2 = new Tank();
		tank2.setTankId(2L);
		tank2.setFuelType("g91");
		tank2.setGals(10D);
		
		Tank tank3 = new Tank();
		tank3.setTankId(3L);
		tank3.setFuelType("g95");
		tank3.setGals(20D);
		
		TanksVo tanksVo = new TanksVo(new Date(), new ArrayList<Tank>(Arrays.asList(tank, tank2, tank3)));
		
		TanksDao tanksDao = tanksRepository.save(new TanksDao(tanksVo));
		return null;*/
		
	}
	
	public List<TanksVo> submitTanksVo(TanksVo tanksVoCriteria, String operation) {
		
		TanksVo tanksVo = null;
		TanksDao tanksDao = null;
		if (operation.equals("save")) {
			tanksDao = tanksRepository.save(new TanksDao(tanksVoCriteria));
			tanksVo = new TanksVo(tanksDao);
			setCurrentTanksVo(tanksVo);
		} else if (operation.equals("update"))  {
			tanksDao = tanksRepository.findLatest("latest", "").get(0);
			tanksDao.setDate(tanksVoCriteria.getDate());
			tanksDao.setPumpAttendantNames(tanksVoCriteria.getPumpAttendantNames());
			tanksDao.setTanks(tanksVoCriteria.getTanks());
			
			tanksVo = new TanksVo(tanksRepository.save(tanksDao));
			setCurrentTanksVo(tanksVo);
		}
		
		return Stream.of(tanksVo).collect(Collectors.toList());
	}
	
	public List<GasPricesVo> findPricesByDates(String dateEnd, String dateBeg) {
		//GasPricesVo latestGasPricesVo = new GasPricesVo();
		List<GasPricesDao> gasPricesDaos = gasPricesRepository.findLatest(dateEnd, dateBeg);
		
		/*latestGasPricesVo = new GasPricesVo(gasPricesDao);
		setCurrentGasPricesVo(latestGasPricesVo);
		
		return Stream.of(latestGasPricesVo).collect(Collectors.toList());*/
		
		List<GasPricesVo> gasPricesVos = gasPricesDaos.stream().map(gasPricesDao -> {
			GasPricesVo gasPricesVo = new GasPricesVo(gasPricesDao);
			return gasPricesVo;
		}).collect(Collectors.toList());
		
		return gasPricesVos;
		
		
		
		/*GasPrice gasPrice = new GasPrice();
		gasPrice.setFuelType("d2");
		gasPrice.setCost(11D);
		gasPrice.setPrice(12D);
		
		GasPrice gasPrice1 = new GasPrice();
		gasPrice1.setFuelType("d2");
		gasPrice1.setCost(12D);
		gasPrice1.setPrice(122D);
		
		GasPrice gasPrice2 = new GasPrice();
		gasPrice2.setFuelType("d2");
		gasPrice2.setCost(13D);
		gasPrice2.setPrice(133D);
		
		GasPricesVo gasPricesVo = new GasPricesVo(new Date(), new ArrayList<GasPrice>(Arrays.asList(gasPrice, gasPrice1, gasPrice2)));
		
		GasPricesDao gasPriceDao = gasPricesRepository.save(new GasPricesDao(gasPricesVo));
		return null;*/
	}
	
	public List<GasPricesVo> submitGasPricesVo(GasPricesVo tanksVoCriteria, String saveOrUpdate) {
		
		/*GasPricesDao gasPricesDao = gasPricesRepository.save(new GasPricesDao(tanksVoCriteria));
		
		GasPricesVo gasPricesVo = new GasPricesVo(gasPricesDao);
		setCurrentGasPricesVo(gasPricesVo);
		
		return Stream.of(gasPricesVo).collect(Collectors.toList());*/
		
		GasPricesVo gasPricesVo = null;
		GasPricesDao gasPricesDao = null;
		if (saveOrUpdate.equals("save")) {
			gasPricesDao = gasPricesRepository.save(new GasPricesDao(tanksVoCriteria));
			gasPricesVo = new GasPricesVo(gasPricesDao);
			setCurrentGasPricesVo(gasPricesVo);
		} else if (saveOrUpdate.equals("update"))  {
			gasPricesDao = gasPricesRepository.findLatest("latest", "").get(0);
			gasPricesDao.setDate(tanksVoCriteria.getDate());
			gasPricesDao.setPumpAttendantNames(tanksVoCriteria.getPumpAttendantNames());
			gasPricesDao.setGasPrices(tanksVoCriteria.getGasPrices());
			
			gasPricesVo = new GasPricesVo(gasPricesRepository.save(gasPricesDao));
			setCurrentGasPricesVo(gasPricesVo);
		}
		
		return Stream.of(gasPricesVo).collect(Collectors.toList());
	}
	
	public TanksVo getCurrentTanksVo() {
		return currentTanksVo;
	}

	public void setCurrentTanksVo(TanksVo currentTanksVo) {
		this.currentTanksVo = currentTanksVo;
	}

	public GasPricesVo getCurrentGasPricesVo() {
		return currentGasPricesVo;
	}

	public void setCurrentGasPricesVo(GasPricesVo currentGasPricesVo) {
		this.currentGasPricesVo = currentGasPricesVo;
	}

	public Station getCurrentStation() {
		return currentStation;
	}

	public void setCurrentStation(Station currentStation) {
		this.currentStation = currentStation;
	}
}
