package operations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import data.MonitoredData;

public class TimeOperations {

	public Long getTotalMonitoredDays(List<MonitoredData> lista) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		@SuppressWarnings("deprecation")
		long nrOfDays= lista.stream()
			.map(a ->  a.getStartTime())
			.map(a -> {
				try {
					return sd.parse(a);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return null;
			})
			.map(a ->a.getYear() + " " + a.getMonth()+"  " + a.getDate())
			.distinct()
			.count();
		return nrOfDays;
	}
	
	
	
	public List<Long> duration(List<MonitoredData> lista) {
		List<Long> duratio = lista.stream()
			.map(a -> {
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date start = new Date();
				try {
					start = sd.parse(a.getStartTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Date end = new Date();
				try {
					end = sd.parse(a.getEndTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				long dif = (end.getTime()-start.getTime())/1000;
				return dif;
			})
			.collect(Collectors.toList());
		return duratio;
	}
}
