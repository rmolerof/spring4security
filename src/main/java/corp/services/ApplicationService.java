package corp.services;

import java.net.MalformedURLException;
import java.net.URL;
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
import javax.xml.ws.WebServiceException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import corp.businessModel.Dispenser;
import corp.businessModel.ExpenseOrCredit;
import corp.businessModel.GasPrice;
import corp.businessModel.GasPricesVo;
import corp.businessModel.Station;
import corp.businessModel.Tank;
import corp.businessModel.TanksVo;
import corp.businessModel.TotalDay;
import corp.businessModel.TotalDayUnit;
import corp.controller.SecurityController;
import corp.domain.GasPricesDao;
import corp.domain.GasPricesRepository;
import corp.domain.InvoiceDao;
import corp.domain.InvoicesRepository;
import corp.domain.StationDao;
import corp.domain.StationRepository;
import corp.domain.TanksDao;
import corp.domain.TanksRepository;
import corp.model.DayDataCriteria;
import corp.model.InvoiceVo;
import corp.model.SubmitInvoiceGroupCriteria;
import corp.model.SunatSubmitServiceResponse;
import corp.model.User;
import corp.sunat.XmlSunat;
import webbonusgx.BonusPointsOperationResponse;
import webbonusgx.BonusVo;
import webbonusgx.WSAcumuPx;
import webbonusgx.WSAcumuPxExecute;
import webbonusgx.WSAcumuPxExecuteResponse;
import webbonusgx.WSAcumuPxSoapPort;

@Service
public class ApplicationService {

	private static Logger logger = LogManager.getLogger(ApplicationService.class);
	
	private List<User> users;
	private Station currentStation;
	private TanksVo currentTanksVo;
	private GasPricesVo currentGasPricesVo;
	public static final char SUCCESS = '1';
	public static final char FAIL = '0';
	public static final String FACTURA = "01";
	public static final String BOLETA = "03";
	public static final String NOTADECREDITO = "07";
	public static final String PENDING_STATUS = "PENDIENTE";
	public static final String SENT_STATUS = "ENVIADO";
	public static final String VOIDED_STATUS = "ANULADO";
	public static final String FAILURE_STATUS = "FALLADO";
	public static final String DNI = "1";
	public static final String RUC = "6";
	public static final String CLIENTES_VARIOS_DOC_NUMBER = "0";
	public static final String SUCCESS_FLAG = "0";
	public static final String FAILURE_FLAG = "1";
	public static final String PRIMAX_CODE_D2 = "00000000000051";
	public static final String PRIMAX_CODE_G90 = "00000000000021";
	public static final String PRIMAX_CODE_G95 = "00000000000031";
	public static final String SUNAT_ALREADY_RECEIVED_MSG = "El comprobante fue registrado previamente con otros datos";
	
	@Autowired
    private StationRepository stationRepository;
	@Autowired
    private TanksRepository tanksRepository;
	@Autowired
    private GasPricesRepository gasPricesRepository;
	@Autowired
	private Utils utils;
	@Autowired
	private InvoicesRepository invoicesRepository;
	@Autowired
	private NextSequenceInvoiceService nextSequenceInvoiceService;
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private GlobalProperties globalProperties;
	@Autowired
	private SecurityController securityController;
	
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
					filter(x -> x.getName().equalsIgnoreCase(username)).
					collect(Collectors.toList());
			return result;
		}
		
	}
	
	public List<Station> findStationStatusByShiftDateAndShift(String shiftDate, String shift) {
		StationDao stationDao = stationRepository.findFirsByShiftDateAndShift(shiftDate, shift);
		StationDao stationDaoBeforeDate = stationRepository.findFirstBeforeDate(stationDao.getDate());
		
		// Update ObjectId and date from latest station status into current station as it is used lated to submit updated day status
		stationDaoBeforeDate.setId(stationDao.getId());
		stationDaoBeforeDate.setDate(stationDao.getDate());
		
		setCurrentStation(new Station(stationDaoBeforeDate));
		
		return new LinkedList<Station>(Arrays.asList(new Station(stationDao), new Station(stationDaoBeforeDate)));
	}
	
	public List<Station> findLatestStationStatus(String dateEnd, String dateBeg, int backDataCount) {
		Station station = new Station();
		List<StationDao> stationDaos = stationRepository.findLatest(dateEnd, dateBeg, backDataCount);
		Station stationVo = null;
		StationDao stationDao = null;
		List<Station> stations = null;
		
		if (null == stationDaos) {
			stationDao = new StationDao();
			
			stationDao.setStationId(101L);
			stationDao.setName("La Joya");
			stationDao.setShift("2");
			stationDao.setPumpAttendantNames("");
			stationDao.setDate(new Date());
			stationDao.setTotalCash(0D);
			stationDao.setExpensesAndCredits(Arrays.asList(new ExpenseOrCredit()));
			
			TotalDay totalDay = new TotalDay();
			totalDay.setTotalDayUnits(new HashMap<String, TotalDayUnit>());
			totalDay.setTotalSolesRevenueDay(0D);
			totalDay.setTotalProfitDay(0D);
			stationDao.setTotalDay(totalDay);
			
			Tank d2 = new Tank(1L, "d2", 0D, 0D);
			Tank g90 = new Tank(2L, "g90", 0D, 0D);
			Tank g95 = new Tank(3L, "g95", 0D, 0D);
			
			Map<String, Tank> tanks = new LinkedHashMap<String, Tank>();
			tanks.put(d2.getFuelType(), d2);
			tanks.put(g90.getFuelType(), g90);
			tanks.put(g95.getFuelType(), g95);
			
			stationDao.setTanks(tanks);
			
			Dispenser d2_1 = new Dispenser(1, "d2", 0,	0,	0, 9);
			Dispenser d2_2 = new Dispenser(2, "d2", 0,	0,	0, 9);
			Dispenser d2_3 = new Dispenser(3, "d2", 0,	0,	0, 9);
			Dispenser d2_4 = new Dispenser(4, "d2", 0,	0,	0, 9);
			Dispenser d2_5 = new Dispenser(5, "d2", 0,	0,	0, 9);
			Dispenser d2_6 = new Dispenser(6, "d2", 0,	0,	0, 9);
			Dispenser g90_1 = new Dispenser(1, "g90", 0, 0,	0, 9);
			Dispenser g90_2 = new Dispenser(2, "g90", 0, 0,	0, 9);
			Dispenser g90_3 = new Dispenser(3, "g90", 0, 0,	0, 9);
			Dispenser g90_4 = new Dispenser(4, "g90", 0, 0,	0, 9);
			Dispenser g95_1 = new Dispenser(1, "g95", 0, 0,	0, 9);
			Dispenser g95_2 = new Dispenser(2, "g95", 0, 0,	0, 9);
			
			
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
			updateStatus(stationDao);
			stationVo = new Station(stationDao);
			
			setCurrentStation(stationVo);
			stations = Stream.of(stationVo).collect(Collectors.toList());
			
		} else {
		
			if (dateEnd.equalsIgnoreCase("latest") && dateBeg.equalsIgnoreCase("")) {

				station = new Station(stationDaos.get(0));
				setCurrentStation(station);
				
				stations = Stream.of(station).collect(Collectors.toList());
				
			}  else if (dateEnd.equalsIgnoreCase("latest") && dateBeg.equalsIgnoreCase("previous")) {
				
				stations = stationDaos.stream().map(stationdao -> {
					Station st = new Station(stationdao);
					return st;
				}).collect(Collectors.toList());
				
				// Update ObjectId and date from latest station status
				stations.get(1).setId(stations.get(0).getId());
				stations.get(1).setDate(stations.get(0).getDate());
				setCurrentStation(stations.get(1));
			}
		}
		
		return stations;
	}
	
	public List<Station> findStationStatusByDates(String dateEnd, String dateBeg) {
		
		List<StationDao> stationDaos = stationRepository.findLatest(dateEnd, dateBeg, 0);
		List<Station> stations = new ArrayList<Station>();
		
		if (null != stationDaos) {
			stations = stationDaos.stream().map(stationDao -> {
				Station station = new Station(stationDao);
				return station;
			}).collect(Collectors.toList());
		}
		
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
		StationDao stationDao = stationRepository.save(stationdao);
		
		TanksVo tanksVo = new TanksVo(stationDao.getPumpAttendantNames(), stationDao.getDate(), new LinkedList<>(stationDao.getTanks().values()));
		submitTanksVo(tanksVo, "save");
		
		Station resetStationFromDB = new Station(stationDao);
		setCurrentStation(resetStationFromDB);
		
		return Stream.of(resetStationFromDB).collect(Collectors.toList());
	}
	
	public List<Station> updateLatestDayData(DayDataCriteria dayDataCriteria) {
		
		Station updatedStation = utils.updateStation(getCurrentStation(), dayDataCriteria);
		
		StationDao stationDao = stationRepository.save(new StationDao(updatedStation));
		
		TanksVo tanksVo = new TanksVo(stationDao.getPumpAttendantNames(), stationDao.getDate(), new ArrayList<>(stationDao.getTanks().values()));
		submitTanksVo(tanksVo, "update");
		
		//recalculateUpOf(dayDataCriteria.getShiftDate(), dayDataCriteria.getShift());
		
		Station resetStationFromDB = new Station(stationDao);
		setCurrentStation(resetStationFromDB);
		
		return Stream.of(resetStationFromDB).collect(Collectors.toList());
	}
	
	public void recalculateUpOf(String shiftDate, String shift) {
		
		// Find all later stations
		List<StationDao> stationDaos = stationRepository.findLatestMonth();
		
		int index = 0;
		for (StationDao stationDao: stationDaos) {
			
			if (stationDao.getShiftDate().trim().equals(shiftDate) && stationDao.getShift().trim().equals(shift)) {
				break;
			}
			
			index++;
		}
		
		stationDaos = stationDaos.subList(0, index + 2);
		
		for (int i = stationDaos.size()-1; i >= 0 ; i--) {
			
			if (i > 0) {
				DayDataCriteria dayDataCriteria = new DayDataCriteria(stationDaos.get(i - 1));
				StationDao updatedStation = utils.updateStationDao(stationDaos.get(i), dayDataCriteria);
				StationDao stationDao = stationRepository.save(updatedStation);
			}
		}
		
		/*
		 *  change the update station to StationDao and make sure the save becomes update
		 */
		
		
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

		List<TanksDao> tanksDaos = tanksRepository.findLatest(dateEnd, dateBeg);
		List<TanksVo> tanksVos = null;
		
		if (tanksDaos.size() == 0) {
			List<Tank> defaultStock = new LinkedList<Tank>();
			defaultStock.add(new Tank(1L, "d2", 0D, 0D));
			defaultStock.add(new Tank(2L, "g90", 0D, 0D));
			defaultStock.add(new Tank(3L, "g95", 0D, 0D));
			TanksVo tanksVo = new TanksVo("", new Date(), defaultStock);
			tanksVos = Stream.of(tanksVo).collect(Collectors.toList());
		} else {
			tanksVos = tanksDaos.stream().map(tanksDao -> {
				TanksVo tanksVo = new TanksVo(tanksDao);
				return tanksVo;
			}).collect(Collectors.toList());
		}
		
		return tanksVos;
		
	}
	
	public Station updateTanksToStation(TanksVo tanksVoCriteria) {
		// Find latest station
		//List<StationDao> stationDaos = stationRepository.findLatest("latest", "", 0);
		
		StationDao sD = stationRepository.findFirsByShiftDateAndShift(tanksVoCriteria.getShiftDate(), "2");
		if (null == sD) {
			sD = stationRepository.findFirsByShiftDateAndShift(tanksVoCriteria.getShiftDate(), "1");
		}
		
		List<StationDao> stationDaos = Arrays.asList(sD);
		
		Station station = null;
		
		if (null != stationDaos) {
		
			// Update Stock
			for (Entry<String, Tank> entry: stationDaos.get(0).getTanks().entrySet()) {
				for (Tank tank: tanksVoCriteria.getTanks()) {
					if (entry.getKey().contains(tank.getFuelType())) {
						entry.getValue().setGals(tank.getGals());
						entry.getValue().setTankId(tank.getTankId());
						entry.getValue().setCost(tank.getCost());
						entry.getValue().setPumpAttendantNames(tanksVoCriteria.getPumpAttendantNames());
						entry.getValue().setSaveOrUpdate(tanksVoCriteria.getSaveOrUpdate());
						entry.getValue().setSupplierRUC(tanksVoCriteria.getSupplierRUC());
						entry.getValue().setTruckDriverName(tanksVoCriteria.getTruckDriverName());
						entry.getValue().setDelivery(tanksVoCriteria.isDelivery());
						entry.getValue().setDate(tanksVoCriteria.getDate());
						entry.getValue().setShiftDate(tanksVoCriteria.getShiftDate());
					}
				}
			}
			
			// save station
			StationDao stationDao = stationDaos.get(0);
			stationDao.setId(new ObjectId());
			stationDao.setDate(new Date(stationDao.getDate().getTime() + 1L));
			stationDao.setPumpAttendantNames(tanksVoCriteria.getPumpAttendantNames());
			stationDao.setTotalCash(0D);
			for (Entry<String, TotalDayUnit> totalDayUnit: stationDao.getTotalDay().getTotalDayUnits().entrySet()) {
				totalDayUnit.getValue().setTotalGalsSoldDay(0D);
				totalDayUnit.getValue().setTotalSolesRevenueDay(0D);
				totalDayUnit.getValue().setTotalProfitDay(0D);
				totalDayUnit.getValue().setStockGals(0D);
			}
			stationDao.getTotalDay().setTotalSolesRevenueDay(0D);
			stationDao.getTotalDay().setTotalProfitDay(0D);
			stationDao.setExpensesAndCredits(new ArrayList<ExpenseOrCredit>());
			
			//recalculateUpOf(tanksVoCriteria.getShiftDate(), "1");
			
			station = new Station(stationRepository.save(stationDao));
			setCurrentStation(station);
		}
		
		return station;
	}
	
	public List<TanksVo> submitTanksVo(TanksVo tanksVoCriteria, String operation) {
		
		TanksVo tanksVo = null;
		TanksDao tanksDao = null;
		if (operation.equals("save")) {
			tanksDao = tanksRepository.save(new TanksDao(tanksVoCriteria));
			tanksVo = new TanksVo(tanksDao);
			setCurrentTanksVo(tanksVo);
		} else if (operation.equals("update"))  {
			//tanksDao = tanksRepository.findLatest("latest", "").get(0);
			tanksDao = tanksRepository.findFirstByDate(tanksVoCriteria.getDate());
			tanksDao.setDate(tanksVoCriteria.getDate());
			tanksDao.setPumpAttendantNames(tanksVoCriteria.getPumpAttendantNames());
			tanksDao.setTanks(tanksVoCriteria.getTanks());
			
			tanksVo = new TanksVo(tanksRepository.save(tanksDao));
			setCurrentTanksVo(tanksVo);
		}
		
		return Stream.of(tanksVo).collect(Collectors.toList());
	}
	
	public List<GasPricesVo> findPricesByDates(String dateEnd, String dateBeg) {
		List<GasPricesDao> gasPricesDaos = gasPricesRepository.findLatest(dateEnd, dateBeg);
		List<GasPricesVo> gasPricesVos = null;
		
		if (gasPricesDaos.size() == 0) {
			List<GasPrice> defaultPrices = new LinkedList<GasPrice>();
			defaultPrices.add(new GasPrice("d2", 0D, 0D));
			defaultPrices.add(new GasPrice("g90", 0D, 0D));
			defaultPrices.add(new GasPrice("g95", 0D, 0D));
			GasPricesVo gasPricesVo = new GasPricesVo("", new Date(), defaultPrices);
			gasPricesVos = Stream.of(gasPricesVo).collect(Collectors.toList());
		} else {
			gasPricesVos = gasPricesDaos.stream().map(gasPricesDao -> {
				GasPricesVo gasPricesVo = new GasPricesVo(gasPricesDao);
				return gasPricesVo;
			}).collect(Collectors.toList());
		}
		
		return gasPricesVos;
	}
	
	public Station updateGasPricesToStation(GasPricesVo gasPricesVoCriteria) {
		// Find latest station
		List<StationDao> stationDaos = stationRepository.findLatest("latest", "", 0);
		Station station = null; 
		
		if (null != stationDaos) {
			// update prices
			for (Entry<String, Dispenser> entry: stationDaos.get(0).getDispensers().entrySet()) {
				for (GasPrice gasPrice: gasPricesVoCriteria.getGasPrices()) {
					if (entry.getKey().contains(gasPrice.getFuelType())) {
						entry.getValue().setPrice(gasPrice.getPrice());
						entry.getValue().setCost(gasPrice.getCost());
					}
				}
			}
			
			// save station
			StationDao stationDao = stationDaos.get(0);
			stationDao.setId(new ObjectId());
			stationDao.setDate(new Date());
			stationDao.setPumpAttendantNames(gasPricesVoCriteria.getPumpAttendantNames());
			TotalDay totalDay = stationDao.getTotalDay();
			for (Entry<String, TotalDayUnit> totalDayUnit: totalDay.getTotalDayUnits().entrySet()) {
				totalDayUnit.getValue().setTotalGalsSoldDay(0D);
				totalDayUnit.getValue().setTotalSolesRevenueDay(0D);
				totalDayUnit.getValue().setTotalProfitDay(0D);
				totalDayUnit.getValue().setStockGals(0D);
			}
			
			totalDay.setTotalSolesRevenueDay(0D);
			totalDay.setTotalProfitDay(0D);
			stationDao.setExpensesAndCredits(new ArrayList<ExpenseOrCredit>());
			stationDao.setTotalCash(0D);
			
			station = new Station(stationRepository.save(stationDao));
			setCurrentStation(station);
		}
		
		return station;
	}
	
	public List<GasPricesVo> submitGasPricesVo(GasPricesVo gasPricesVoCriteria, String saveOrUpdate) {
		
		GasPricesVo gasPricesVo = null;
		GasPricesDao gasPricesDao = null;
		if (saveOrUpdate.equals("save")) {
			gasPricesDao = gasPricesRepository.save(new GasPricesDao(gasPricesVoCriteria));
			gasPricesVo = new GasPricesVo(gasPricesDao);
			setCurrentGasPricesVo(gasPricesVo);
		} else if (saveOrUpdate.equals("update"))  {
			gasPricesDao = gasPricesRepository.findLatest("latest", "").get(0);
			gasPricesDao.setDate(gasPricesVoCriteria.getDate());
			gasPricesDao.setPumpAttendantNames(gasPricesVoCriteria.getPumpAttendantNames());
			gasPricesDao.setGasPrices(gasPricesVoCriteria.getGasPrices());
			
			gasPricesVo = new GasPricesVo(gasPricesRepository.save(gasPricesDao));
			setCurrentGasPricesVo(gasPricesVo);
		}
		
		return Stream.of(gasPricesVo).collect(Collectors.toList());
	}
	
	public List<InvoiceVo> submitInvoicesToSunat(String processingType, Date processPendingInvoicesTillDate) {
		
		List<InvoiceVo> invalidInvoiceVos = null;
		List<InvoiceDao> pendingInvoiceDaos = null;
		
		if (null == processPendingInvoicesTillDate) {
			pendingInvoiceDaos = invoicesRepository.findAllPendingByRegex(PENDING_STATUS, new Sort(Sort.Direction.ASC, "date"));
		} else {
			pendingInvoiceDaos = invoicesRepository.findAllPendingInvoicesTillDate(processPendingInvoicesTillDate, new Sort(Sort.Direction.ASC, "date"));
		}
		
		if (pendingInvoiceDaos.size() > 0) {
			InvoiceDao lastSunatProcessedFacturaOrNotaDeCredito = invoicesRepository.findLastSentInvoice(FACTURA);
			InvoiceDao lastSunatProcessedBoleta = invoicesRepository.findLastSentInvoice(BOLETA);
			
			if (processingType.equalsIgnoreCase(SubmitInvoiceGroupCriteria.NORMAL)) {
				if(validateInvoices(pendingInvoiceDaos, lastSunatProcessedBoleta, lastSunatProcessedFacturaOrNotaDeCredito)) {
					return processInvoicesBySunat(pendingInvoiceDaos);
				} else {
					invalidInvoiceVos = pendingInvoiceDaos.stream().map(invoiceDao -> {
						InvoiceVo invoiceVo = new InvoiceVo(invoiceDao);
						return invoiceVo;
					}).collect(Collectors.toList());
					return invalidInvoiceVos;
				}
			} else {
				return processInvoicesBySunat(pendingInvoiceDaos);
			}
		} else {
			return new ArrayList<InvoiceVo>();
		}
		
	}
	
	public List<InvoiceVo> submitInvoicesToBonus(String processingType, Date processPendingInvoicesTillDate) {
		
		List<InvoiceDao> pendingInvoiceDaos = null;
		
		if (null == processPendingInvoicesTillDate) {
			pendingInvoiceDaos = invoicesRepository.findAllPendingByRegexForBonus(PENDING_STATUS, new Sort(Sort.Direction.ASC, "date"));
		} else {
			pendingInvoiceDaos = invoicesRepository.findAllPendingInvoicesTillDateForBonus(processPendingInvoicesTillDate, new Sort(Sort.Direction.ASC, "date"));
		}
		
		if (pendingInvoiceDaos.size() > 0) {
			return processInvoicesByBonus(pendingInvoiceDaos);
		} else {
			return new ArrayList<InvoiceVo>();
		}
		
	}
	
	public boolean validateInvoices(List<InvoiceDao> invoiceDaos, InvoiceDao lastSunatProcessedBoleta, InvoiceDao lastSunatProcessedFacturaOrNotaDeCredito) {
		
		boolean isPendingInvoiceGroupValidated = true; 
		
		try {
			Long lastFacturaOrNotaDeCreditoNumberInLong = invoiceNumberToLong(lastSunatProcessedFacturaOrNotaDeCredito.getInvoiceNumber());
			Long lastBoletaNumberInLong = invoiceNumberToLong(lastSunatProcessedBoleta.getInvoiceNumber());
			
			for (InvoiceDao invoiceDao: invoiceDaos) {
				
				if (invoiceDao.getInvoiceType().equals(ApplicationService.BOLETA) || invoiceDao.getInvoiceTypeModified().equals(ApplicationService.BOLETA)) {
					Long nextInvoiceNumber = invoiceNumberToLong(invoiceDao.getInvoiceNumber());
					
					if (nextInvoiceNumber - lastBoletaNumberInLong != 1) {
						invoiceDao.setSunatValidated(false);
						isPendingInvoiceGroupValidated = false;
					} else {
						invoiceDao.setSunatValidated(true);
					}
					
					lastBoletaNumberInLong = nextInvoiceNumber;
				} else {
					Long nextInvoiceNumber = invoiceNumberToLong(invoiceDao.getInvoiceNumber());
					
					if (nextInvoiceNumber - lastFacturaOrNotaDeCreditoNumberInLong != 1) {
						invoiceDao.setSunatValidated(false);
						isPendingInvoiceGroupValidated = false;
					} else {
						invoiceDao.setSunatValidated(true);
					}
					
					lastFacturaOrNotaDeCreditoNumberInLong = nextInvoiceNumber;
				}
			}
		} catch(Exception e) {
			return false;
		}
		
		return isPendingInvoiceGroupValidated;
		
	}
	
	public long invoiceNumberToLong(String invoiceNumber) {
		StringBuilder invoiceNumberImmutable = new StringBuilder(invoiceNumber);
		return Integer.parseInt(invoiceNumberImmutable.substring(5, invoiceNumberImmutable.length())); 
	}
	
	public List<InvoiceVo> processInvoicesByBonus(List<InvoiceDao> invoiceDaos){
		List<InvoiceVo> invoiceVos = invoiceDaos.stream().map(invoiceDao -> {
			InvoiceVo invoiceVo = new InvoiceVo(invoiceDao);
			
			BonusPointsOperationResponse bonusPointsOperationResponse = accumulateInvoiceForBonusPoints(invoiceVo);
			
			InvoiceDao invDao = new InvoiceDao(invoiceVo);

			if (bonusPointsOperationResponse.getResponseFlag().equals("0")) {
				invDao.setBonusStatus("ENVIADO");
				invDao.setBonusAccumulatedPoints(bonusPointsOperationResponse.getAccumulatedPoints());
				invDao.setBonusSubmittedDate(new Date());
				invoiceVo.setBonusStatus("ENVIADO");
				invoiceVo.setBonusAccumulatedPoints(bonusPointsOperationResponse.getAccumulatedPoints());
				invoiceVo.setBonusSubmittedDate(invDao.getBonusSubmittedDate());
				invoicesRepository.save(invDao);
				logger.info("Bonus points for invoice " + invoiceVo.getInvoiceNumber() + " marked as ENVIADO.");
			}
			
			return invoiceVo;
		}).collect(Collectors.toList());
		
		return invoiceVos;
	}
	
	public List<InvoiceVo> processInvoicesBySunat(List<InvoiceDao> invoiceDaos){
		List<InvoiceVo> invoiceVos = invoiceDaos.stream().map(invoiceDao -> {
			InvoiceVo invoiceVo = new InvoiceVo(invoiceDao);
			
			// Sunat
			String basePath = "";
			SunatSubmitServiceResponse deliveryResponse = null;
			try {
				basePath = resourceLoader.getResource("classpath:/static/").getFile().getPath();
				XmlSunat.invokeSunat(invoiceVo, basePath);
				XmlSunat.firma(invoiceVo, basePath, globalProperties.getSunatSignatureFileName(), globalProperties.getSunatSignaturePassword());
				deliveryResponse = XmlSunat.envio(invoiceVo, basePath, globalProperties.getSunatInvoicingServiceURL(), globalProperties.getSunatSolUsername(), globalProperties.getSunatSolPassword());
				invoiceVo.setInvoiceHash(deliveryResponse.getHashCdr());
				invoiceVo.setSunatErrorStr(deliveryResponse.getResponseDetailMsg());
				
			} catch (Exception e) {
				invoiceVo.setStatus("0");
				invoiceVo.setSunatErrorStr("Error de Proceso. " + e.getMessage());
			}
			
			InvoiceDao invDao = new InvoiceDao(invoiceVo);

			if (deliveryResponse.getResponseCode().charAt(0) == SUCCESS) {
				invDao.setSunatStatus("ENVIADO");
				invDao.setSunatSubmittedDate(new Date());
				invoiceVo.setSunatStatus("ENVIADO");
				invoiceVo.setSunatSubmittedDate(invDao.getSunatSubmittedDate());
				invoicesRepository.save(invDao);
				logger.info("Invoice " + invoiceVo.getInvoiceNumber() + " marked as ENVIADO.");
			} else if (deliveryResponse.getResponseCode().charAt(0) == FAIL && deliveryResponse.getResponseDetailMsg().contains(SUNAT_ALREADY_RECEIVED_MSG)) {
				SunatSubmitServiceResponse sunatSubmitServiceResponse = new SunatSubmitServiceResponse(XmlSunat.consultInvoice(invoiceVo, basePath + "/CDR/", globalProperties));
				if (sunatSubmitServiceResponse.getResponseCode().charAt(0) == SUCCESS) {
					invDao.setSunatStatus("ENVIADO");
					invDao.setInvoiceHash(sunatSubmitServiceResponse.getHashCdr());
					invDao.setSunatErrorStr(sunatSubmitServiceResponse.getResponseDetailMsg());
					invDao.setSunatSubmittedDate(new Date());
					invoiceVo.setSunatStatus("ENVIADO");
					invoiceVo.setInvoiceHash(sunatSubmitServiceResponse.getHashCdr());
					invoiceVo.setSunatErrorStr(sunatSubmitServiceResponse.getResponseDetailMsg());
					invoiceVo.setSunatSubmittedDate(invDao.getSunatSubmittedDate());
					invoicesRepository.save(invDao);
					logger.info("Invoice " + invoiceVo.getInvoiceNumber() + " marked as ENVIADO.");
				}
			}
			
			return invoiceVo;
		}).collect(Collectors.toList());
		
		return invoiceVos;
	}
	
	public Long getNextInvoiceSequence(String invoiceType) {
		
		if (invoiceType.equals(BOLETA)) {
			InvoiceDao lastSunatProcessedBoletaOrNotaDeCredito = invoicesRepository.findLastNotVoidedInvoice(BOLETA);
			return invoiceNumberToLong(lastSunatProcessedBoletaOrNotaDeCredito.getInvoiceNumber()) + 1;
		} else if (invoiceType.equals(FACTURA)){
			InvoiceDao lastSunatProcessedFacturaOrNotaDeCredito = invoicesRepository.findLastNotVoidedInvoice(FACTURA);
			return invoiceNumberToLong(lastSunatProcessedFacturaOrNotaDeCredito.getInvoiceNumber()) + 1;
		}
		
		return 0L;
	}
	
	public List<InvoiceVo> submitInvoice(InvoiceVo invoiceVo) {
		
		try {
			
			invoiceVo.setTotalVerbiage(XmlSunat.Convertir(invoiceVo.getTotal().toString(), true, "PEN"));
			
			if (invoiceVo.getSaveOrUpdate().equals("save")) {
				
				if (invoiceVo.getInvoiceNumber().contains("XXXXXXXX")) {
					if (invoiceVo.getInvoiceType().equalsIgnoreCase(BOLETA)) {
						// Boleta
						Long receiptNbr = getNextInvoiceSequence(BOLETA);
						String autocompletedReceiptNbr = String.format("%08d", receiptNbr);
						invoiceVo.setInvoiceNumber("B001-" + autocompletedReceiptNbr);
					} else if (invoiceVo.getInvoiceType().equalsIgnoreCase(FACTURA)) {
						// Factura
						Long receiptNbr = getNextInvoiceSequence(FACTURA);
						String autocompletedReceiptNbr = String.format("%08d", receiptNbr);
						invoiceVo.setInvoiceNumber("F001-" + autocompletedReceiptNbr);
					} else if (invoiceVo.getInvoiceType().equalsIgnoreCase(NOTADECREDITO)) {
						// Nota de credito
						if (invoiceVo.getInvoiceTypeModified().equalsIgnoreCase(BOLETA)) {
							Long receiptNbr = getNextInvoiceSequence(BOLETA);
							String autocompletedReceiptNbr = String.format("%08d", receiptNbr);
							invoiceVo.setInvoiceNumber("B001-" + autocompletedReceiptNbr);
						} else if (invoiceVo.getInvoiceTypeModified().equalsIgnoreCase(FACTURA)) {
							Long receiptNbr = getNextInvoiceSequence(FACTURA);
							String autocompletedReceiptNbr = String.format("%08d", receiptNbr);
							invoiceVo.setInvoiceNumber("F001-" + autocompletedReceiptNbr);
						}
					}
				}
				
				InvoiceDao invoiceDao = new InvoiceDao(invoiceVo);
				// Find out any existing invoice with the same invoice number
				InvoiceDao existingInvoiceDao = invoicesRepository.findFirstByInvoiceNumberNotVoided(invoiceDao.getInvoiceNumber());
				
				if (existingInvoiceDao.getClientName().equals(InvoiceDao.INVOICE_NOT_FOUND_NAME)) {
					invoicesRepository.save(invoiceDao);
					logger.info("Unprocessed invoice " + invoiceDao.getInvoiceNumber() + " sent to database");
				} else {
					throw new Exception("Comprobante Nro " + invoiceDao.getInvoiceNumber() + " ya existe.");
				}

				invoiceVo.setStatus("1");
				invoiceVo.setSunatErrorStr("1");
			} else if (invoiceVo.getSaveOrUpdate().equals("update")) {
				
				InvoiceDao invoiceDao = new InvoiceDao(invoiceVo);
				invoicesRepository.save(invoiceDao);
				logger.info("Unprocessed invoice " + invoiceDao.getInvoiceNumber() + " updated in database");
				
				invoiceVo.setSunatErrorStr("1|Updated");
				invoiceVo.setStatus("1");
			}
			
			// Clean up Bonus Number = "000000000000" 
			/*if (invoiceVo.getBonusNumber().trim().length() > 7 && invoiceVo.getBonusNumber().trim().substring(7).equals("000000000000")) {
				invoiceVo.setBonusNumber("");
				utils.saveBonusNumber(invoiceVo);
			}
			*/
			// Save Bonus Number if present
			if (null != invoiceVo.getBonusNumber() && !invoiceVo.getBonusNumber().trim().equals("")) {
				utils.saveBonusNumber(invoiceVo);
			}
			
			// Save address if present for DNI
			if (null != invoiceVo.getClientAddress() && !invoiceVo.getClientAddress().trim().equals("")) {
				utils.saveClientAddressForDNIOrRUC(invoiceVo);
			}
			
		} catch (Exception e) {
			invoiceVo.setStatus("0");
			invoiceVo.setSunatErrorStr("Error de Proceso. " + e.getMessage());
		}
		
		return Stream.of(invoiceVo).collect(Collectors.toList());
	}
	
	public List<InvoiceVo> findInvoicesSummaryData(String loadInvoiceAmountCriteria, boolean voidedInvoicesIncluded) {

		List<InvoiceDao> invoiceDaos = invoicesRepository.findInvoicesByAmountCriteriaAndVoidedIncludedFlag(loadInvoiceAmountCriteria, voidedInvoicesIncluded);
		
		List<InvoiceVo> invoiceVos = invoiceDaos.stream().map(invoiceDao -> {
			InvoiceVo invoiceVo = new InvoiceVo(invoiceDao);
			return invoiceVo;
		}).collect(Collectors.toList());
		
		return invoiceVos;
	}
	
	public List<InvoiceVo> findInvoicesSummaryDataForBonus(String loadInvoiceAmountCriteria, boolean voidedInvoicesIncluded) {

		List<InvoiceDao> invoiceDaos = invoicesRepository.findInvoicesByAmountCriteriaAndVoidedIncludedFlagForBonus(loadInvoiceAmountCriteria, voidedInvoicesIncluded);
		
		List<InvoiceVo> invoiceVos = invoiceDaos.stream().map(invoiceDao -> {
			InvoiceVo invoiceVo = new InvoiceVo(invoiceDao);
			return invoiceVo;
		}).collect(Collectors.toList());
		
		return invoiceVos;
	}
	
	public List<InvoiceVo> findInvoice(String invoiceNbr) {

		InvoiceDao invoiceDao = invoicesRepository.findFirstByInvoiceNumberNotVoided(invoiceNbr);
		if (!invoiceDao.getClientName().equals(InvoiceDao.INVOICE_NOT_FOUND_NAME)) {
			InvoiceVo invoiceVo = new InvoiceVo(invoiceDao);
			
			invoiceVo.setUser(securityController.getCurrentUser());
		
			return Stream.of(invoiceVo).collect(Collectors.toList());
		} else {
			return Stream.of(InvoiceVo.NOT_FOUND).collect(Collectors.toList());
		}
	}
	
	public String deleteInvoice(String invoiceNbr) {

		InvoiceDao invoiceDao = invoicesRepository.findFirstByInvoiceNumberNotVoided(invoiceNbr);
		invoiceDao.setSunatStatus(VOIDED_STATUS);
		invoiceDao.setInvoiceNumber(makeInvoiceNumberVoided(invoiceDao.getInvoiceNumber()));
		
		
		InvoiceDao savedInvoiceDao = saveVoidedInvoice(invoiceDao);
		
		if (null != savedInvoiceDao) {
			return "1";
		} else {
			return "0";
		}
		
		/*invoicesRepository.delete(invoiceDao);
		
		invoiceDao = invoicesRepository.findFirstByInvoiceNumberAndSunatStatus(invoiceNbr);
		if (null != invoiceDao) {
		
			return "0";
		} else {
			return "1";
		}*/
	}
	
	public InvoiceDao saveVoidedInvoice(InvoiceDao invoiceDao) {
		
		while (!isInvoiceNotFound(invoiceDao)) {
			invoiceDao.setInvoiceNumber(incrementVoidedInvoiceNumberSequence(invoiceDao.getInvoiceNumber()));
		}
		
		return invoicesRepository.save(invoiceDao);
	}
	
	public String incrementVoidedInvoiceNumberSequence(String invoiceNumber) {
		Integer incrementedVoidedInvoiceNumberCount = Integer.valueOf(invoiceNumber.substring(2, 4)) + 1;
		return (new StringBuilder(invoiceNumber).replace(2, 4, String.format("%02d", incrementedVoidedInvoiceNumberCount))).toString(); 
	}
	
	public boolean isInvoiceNotFound(InvoiceDao invoiceDao) {
		return null == invoicesRepository.findFirstByInvoiceNumberAndSunatStatus(invoiceDao.getInvoiceNumber(), ApplicationService.VOIDED_STATUS);
	}
	
	public String makeInvoiceNumberVoided(String invoiceNumber) {
		return (new StringBuilder(invoiceNumber).replace(1, 2, "A")).toString();
	}
	
	public BonusPointsOperationResponse accumulateInvoiceForBonusPoints(InvoiceVo invoiceVo) {
		
		BonusVo bonusVo = new BonusVo();
		bonusVo.setComercio(globalProperties.getComercio());
		bonusVo.setTarjetac11(invoiceVo.getBonusNumber().substring(7, 18));
		bonusVo.setFechaTransaccion(Utils.formatDate(Utils.transformGMTDateToZone(invoiceVo.getDate(), globalProperties.getTimeZoneID()), "ddMMyyyy"));
		bonusVo.setHoraTransaccion(Utils.formatDate(Utils.transformGMTDateToZone(invoiceVo.getDate(), globalProperties.getTimeZoneID()), "HHmm"));
		String invoiceNumber = invoiceVo.getInvoiceNumber();
		bonusVo.setPosId("00" + invoiceNumber.substring(0, invoiceNumber.indexOf("-")));//globalProperties.getPosId()"00F001"
		bonusVo.setPosSecuencial(invoiceNumber.substring(invoiceNumber.length()-6));//invoiceVo.getInvoiceNumber()"000062"
		String totalSolesBonusFormat = String.format("%08.2f", invoiceVo.getTotal()).replace(".", "");// First 0 indicates sign
		bonusVo.setLineasDetalle("000" + totalSolesBonusFormat + getPrimaxProductCodeAndQuantity(invoiceVo));
		bonusVo.setTotalSolesTrn(totalSolesBonusFormat);
		bonusVo.setFlagOperacion("0");
		
		BonusPointsOperationResponse bonusPointsOperationResponse = executeBonusPointsOperation(bonusVo);
		String bonusPointsOperationMessage = "Accumulated Points: " + bonusPointsOperationResponse.getAccumulatedPoints() + ", Bonus Number: " + invoiceVo.getBonusNumber().substring(7) + ", Invoice Number: " + invoiceVo.getInvoiceNumber() + ", amount: S/ " + String.format("%#.2f", invoiceVo.getTotal());
		
		if (bonusPointsOperationResponse.getResponseFlag().equals(SUCCESS_FLAG)) {
			logger.info("Bonus points were added. " + bonusPointsOperationMessage);
		} else {
			logger.info("Bonus points NOT added. " + bonusPointsOperationMessage);
		}
		
		return bonusPointsOperationResponse;
	}
	
	public String getPrimaxProductCodeAndQuantity(InvoiceVo invoiceVo) {
		
		String primaxProductCode = "";
		String totalGalsBonusFormat = "";
		
		if (invoiceVo.getGalsD2() > 0D) {
			primaxProductCode = PRIMAX_CODE_D2;
			totalGalsBonusFormat = String.format("%09.3f", invoiceVo.getGalsD2()).replace(".", "");
		} else if (invoiceVo.getGalsG90() > 0D) {
			primaxProductCode = PRIMAX_CODE_G90;
			totalGalsBonusFormat = String.format("%09.3f", invoiceVo.getGalsD2()).replace(".", "");
		} else if (invoiceVo.getGalsG95() > 0D) {
			primaxProductCode = PRIMAX_CODE_G95;
			totalGalsBonusFormat = String.format("%09.3f", invoiceVo.getGalsD2()).replace(".", "");
		}
		
		return primaxProductCode + totalGalsBonusFormat;
	}
	
	public BonusPointsOperationResponse executeBonusPointsOperation(BonusVo bonusVo) {
		WSAcumuPx wsAcumuPx = new WSAcumuPx(getBonusURL(globalProperties.getBonusURL()));
		WSAcumuPxSoapPort wsAcumuPxSoapPort = wsAcumuPx.getWSAcumuPxSoapPort();
		
		WSAcumuPxExecute parameters = new WSAcumuPxExecute();
		parameters.setComercio(bonusVo.getComercio());
		parameters.setTarjetac11(bonusVo.getTarjetac11());
		parameters.setFechatransaccion(bonusVo.getFechaTransaccion());
		parameters.setHoratransaccion(bonusVo.getHoraTransaccion());
		parameters.setPosId(bonusVo.getPosId());
		parameters.setPosSecuencial(bonusVo.getPosSecuencial());
		parameters.setLineasdetalle(bonusVo.getLineasDetalle());
		parameters.setTotalsolestrn(bonusVo.getTotalSolesTrn());
		parameters.setFlagoperacion(bonusVo.getFlagOperacion());
		
		WSAcumuPxExecuteResponse wsAcumuPxExecuteResponse = wsAcumuPxSoapPort.execute(parameters);
		
		return new BonusPointsOperationResponse(wsAcumuPxExecuteResponse.getSaldopuntos(), wsAcumuPxExecuteResponse.getFlagretorno());
	}
	
	public URL getBonusURL(String urlString) {
		URL url = null;
        WebServiceException e = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
            logger.error(e);
        }
        
        return url;
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
