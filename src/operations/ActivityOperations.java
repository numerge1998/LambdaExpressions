package operations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import data.MonitoredData;

public class ActivityOperations {

	private String activ;
	private long count;
	
	private int sleepInc = 0;
	private int toiletInc = 0;
	private int showerInc = 0;
	private int breakfInc = 0;
	private int snackInc = 0;
	private int groomInc = 0;
	private int leaveInc = 0;
	private int lunchInc = 0;
	private int spareInc = 0;
	


	private int sleepDur = 0;
	private int toiletDur = 0;
	private int showerDur = 0;
	private int breakfDur = 0;
	private int snackDur = 0;
	private int groomDur = 0;
	private int leaveDur = 0;
	private int lunchDur = 0;
	private int spareDur = 0;
	



	
	public ActivityOperations(String activ, long count) {
		super();
		this.activ = activ;
		this.count = count;
	}
	
	public ActivityOperations() {
		super();

	}

	public String getActiv() {
		return activ;
	}
	
	public void setActiv(String activ) {
		this.activ = activ;
	}
	
	public long getCount() {
		return count;
	}
	
	public void setCount(long count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return "ActivityCount [activ=" + activ + ", day=" + count + "]";
	}
	
	public Map<String,Long> countEachActivity(List<MonitoredData> lista){
		String sleep = "Sleeping";
		String toilet = "Toileting	";
		String shower = "Showering	";
		String breakf = "Breakfast	";
		String groom = "Grooming	";
		String spare = "Spare_Time/TV";
		String leave = "Leaving	";
		String lunch = "Lunch";
		String snack = "Snack	";
		
		List<ActivityOperations> result1 = lista.stream()
				.map(a -> a.getActivity())
				.map(a -> {
					ActivityOperations act = new ActivityOperations();
					act.setActiv(a);
					if(act.getActiv().equals(sleep)) { 
						sleepInc++;
						act.setCount(sleepInc);
					}
					if(act.getActiv().equals(toilet)) {
						toiletInc++;
						act.setCount(toiletInc);
					}
					if(act.getActiv().equals(shower)) {
						showerInc++;
						act.setCount(showerInc);
					}
					if(act.getActiv().equals(breakf)) {
						breakfInc++;
						act.setCount(breakfInc);
					}
					if(act.getActiv().equals(snack)) {
						snackInc++;
						act.setCount(snackInc);
					}
					if(act.getActiv().equals(groom)) {
						groomInc++;
						act.setCount(groomInc);
					}
					if(act.getActiv().equals(spare)) { 
						spareInc++;
						act.setCount(spareInc);
					}
					if(act.getActiv().equals(leave)) { 
						leaveInc++;
						act.setCount(leaveInc);
					}
					if(act.getActiv().equals(lunch)) {
						lunchInc++;
						act.setCount(lunchInc);
					}
					
					
					return act;
				})
				.sorted(Comparator.comparing(ActivityOperations::getCount))
				.collect(Collectors.toList());
		
		List<ActivityOperations> result = new ArrayList();
		result = result1.stream()
			.filter(q -> q.getCount() == lunchInc && q.getActiv().equals(lunch) || 
					   q.getCount() == leaveInc && q.getActiv().equals(leave) || 
					   q.getCount() == spareInc && q.getActiv().equals(spare) || 
					   q.getCount() == groomInc && q.getActiv().equals(groom) || 
					   q.getCount() == snackInc && q.getActiv().equals(snack) || 
					   q.getCount() == breakfInc && q.getActiv().equals(breakf) || 
					   q.getCount() == showerInc && q.getActiv().equals(shower) || 
					   q.getCount() == toiletInc && q.getActiv().equals(toilet) || 
					   q.getCount() == sleepInc && q.getActiv().equals(sleep))
			.sorted(Comparator.comparing(ActivityOperations::getCount))
			.collect(Collectors.toList());
			
		Map<String,Long> res= new HashMap<String, Long>();
		res = result1.stream()
				.collect(Collectors.toMap(ActivityOperations::getActiv, ActivityOperations::getCount,(oldVal, newVal) -> newVal));
		return res;
	}
	
	public List<ActivityOperations> durationForEachActivity(List<MonitoredData> lista){
		String sleep = "Sleeping";
		String toilet = "Toileting	";
		String shower = "Showering	";
		String breakf = "Breakfast	";
		String groom = "Grooming	";
		String spare = "Spare_Time/TV";
		String leave = "Leaving	";
		String lunch = "Lunch";
		String snack = "Snack	";
		List<ActivityOperations> duration = lista.stream()
				.map(a -> {
					SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					List<ActivityOperations> act = new ArrayList(); 
					ActivityOperations rez = new ActivityOperations();
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
					
					if(a.getActivity().equals(sleep)) { 
						sleepDur+=dif;
						rez = new ActivityOperations(a.getActivity(),sleepDur);
					}
					if(a.getActivity().equals(toilet)) {
						toiletDur += dif;
						rez = new ActivityOperations(a.getActivity(),toiletDur);
					}
					if(a.getActivity().equals(shower)) {
						showerDur += dif;
						rez = new ActivityOperations(a.getActivity(),showerDur);
					}
					if(a.getActivity().equals(breakf)) {
						breakfDur += dif;
						rez = new ActivityOperations(a.getActivity(),breakfDur);
					}
					if(a.getActivity().equals(snack)) {
						snackDur += dif;
						rez = new ActivityOperations(a.getActivity(),snackDur);
					}
					if(a.getActivity().equals(groom)) {
						groomDur += dif;
						rez = new ActivityOperations(a.getActivity(),groomDur);
					}
					if(a.getActivity().equals(spare)) {
						spareDur += dif;
						rez = new ActivityOperations(a.getActivity(),spareDur);
					}
					if(a.getActivity().equals(leave)) {
						leaveDur += dif;
						rez = new ActivityOperations(a.getActivity(),leaveDur);
					}
					if(a.getActivity().equals(lunch)) {
						lunchDur += dif;
						rez = new ActivityOperations(a.getActivity(),lunchDur);
					}
					
					return rez;
				})
				.sorted(Comparator.comparing(ActivityOperations::getCount))
				.collect(Collectors.toList());
		
		duration = duration.stream()
				.filter(q -> q.getCount() == lunchDur && q.getActiv().equals(lunch) || 
						   q.getCount() == leaveDur && q.getActiv().equals(leave) || 
						   q.getCount() == spareDur && q.getActiv().equals(spare) || 
						   q.getCount() == groomDur && q.getActiv().equals(groom) || 
						   q.getCount() == snackDur && q.getActiv().equals(snack) || 
						   q.getCount() == breakfDur && q.getActiv().equals(breakf) || 
						   q.getCount() == showerDur && q.getActiv().equals(shower) || 
						   q.getCount() == toiletDur && q.getActiv().equals(toilet) || 
						   q.getCount() == sleepDur && q.getActiv().equals(sleep))
				.collect(Collectors.toList());
		return duration;
	}	
	
	public List<MonitoredData> longActivityes(List<MonitoredData> lista){
		List<MonitoredData> res = lista.stream()
				//.filter(a -> )
				.collect(Collectors.toList());
		return res;
	}
}
