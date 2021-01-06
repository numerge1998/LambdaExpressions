package start;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.crypto.Data;

import data.MonitoredData;
import operations.ActivityOperations;
import operations.TimeOperations;

public class Launcher {
	
	public static List<MonitoredData> lista;

	private static void readFile(String adresa){
        try (Stream<String> stream = Files.lines(Paths.get(adresa))) {  
           
        	lista=  stream.map(line -> line.split("\t\t"))
            	.map(a -> new MonitoredData(a[0], a[1], a[2]))
            	.collect(Collectors.toList());
            	
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	

	public static void main(String args[]) throws ParseException {
	TimeOperations n = new TimeOperations();
	ActivityOperations s = new ActivityOperations();
	//citirea si crearea arrayListului
	String adresa = "E:\\an2\\semestrul 2\\TP\\tema5\\Activities.txt";
	SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	readFile(adresa);
	System.out.println("Punctul 1");
	for(MonitoredData l:lista)
		System.out.println(l.toString());
	
	
	//punctul 2 : afisare nuamrul de zile pe care se intinde fisierul
	long nrOfDays = n.getTotalMonitoredDays(lista);
	System.out.println("Punctul 2");
	System.out.println(nrOfDays);
	
	//punctul 3 :afisare nr de aparitii pentru fiecare activitate
	Map<String,Long> res = s.countEachActivity(lista);
    	//System.out.println(res);
	Map<String, Long> eazyRes = lista.stream() .collect(Collectors.groupingBy(MonitoredData::getActivity, Collectors.counting()));
	System.out.println("Punctul 3");
	System.out.println(eazyRes);
	
	//punctul 4 fiecare activitate de cate ori apare pentru fiecare zi
	
	@SuppressWarnings("deprecation")
	Map<String, Map<String, Long>> eazyRes1 = lista.stream() .collect(Collectors.groupingBy((a -> {try {
		return ((sd.parse(a.getStartTime()).getMonth()+1) + "-" + sd.parse(a.getStartTime()).getDate());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}}), Collectors.groupingBy(MonitoredData::getActivity,Collectors.counting())));
	System.out.println("Punctul 4");		
	System.out.println(eazyRes1.toString());
	

    //punctul 5 : durata pentru fiecare linie
	List<Long> duration1 = n.duration(lista);
	System.out.println("Punctul 5");
    for(Long l:duration1)
	    System.out.println(l/3600 + " hours " + (l%3600)/60 + " min " + l%60 + " sec");
	
	//punctul 6 : durata totala pentru fiecare activitate
	List<ActivityOperations> dura = s.durationForEachActivity(lista);
	//for(ActivityOperations l:dura)
		//System.out.println(l.getActiv() + " : " + l.getCount()/3600 + " hours " + (l.getCount()%3600)/60 + " min " + l.getCount()%60 + " sec");
	
	Map<String, Long> eazyRes2 = lista.stream() .collect(Collectors.groupingBy(MonitoredData::getActivity, Collectors.summingLong(a->{
		try {
			return sd.parse(a.getEndTime()).getTime() - sd.parse(a.getStartTime()).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return 11;
			//e.printStackTrace();
		}
	})));
	System.out.println("Punctul 6");
	eazyRes2.entrySet().stream()
	.map(a -> {return "" + a.getKey() +" : " +  a.getValue()/3600000 + " hours "+ a.getValue()%3600000/60000 + " minutes " +  a.getValue()/1000%60 + " seconds"; })
	.forEach(System.out::println );
	
	
	//punctul 7
	//final Map<String, Long> map1 = new HashMap<String, Long>();
	Map<String, Long> map2 = new HashMap<String, Long>();
	List<String> l90 = new ArrayList<String>();
	final Map<String, Long> map1 = lista.stream().collect(Collectors.groupingBy(a-> a.getActivity(), Collectors.counting()));
	 map2 = lista.stream()
	        .filter(a-> {
				try {
					return (sd.parse(a.getEndTime()).getTime() - sd.parse(a.getStartTime()).getTime())<= 300000;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			})
	        .collect(Collectors.groupingBy(a-> a.getActivity(), Collectors.counting()));
	 l90 =  map2.entrySet().stream()
	          .filter(a -> a.getValue() >= 0.9 * map1.get(a.getKey() ) )
	          .map(x->x.getKey())
	          .collect(Collectors.toList());
	 System.out.println("Punctul 7");
	System.out.println(l90.toString());
}
}
