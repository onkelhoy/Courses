/*
 * File:	Process.java 
 * Course: 	Operating Systems
 * Code: 	1DV512
 * Author: 	Suejb Memeti
 * Date: 	November, 2016
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FCFS{
	
	// The list of processes to be scheduled
	public ArrayList<Process> processes;

	// Class constructor
	public FCFS(ArrayList<Process> processes) {
		this.processes = processes;
	}

	public void run() {
		// TODO Implement the FCFS algorithm here

		//sort processes by arrival time
		Collections.sort(processes, new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
				return o1.getArrivalTime()-o2.getArrivalTime();
			}
		});

		int totalTime = 0;
		//do the actual calculations
		for(Process o : processes){
			int wait = totalTime - o.getArrivalTime(), //assuming arrival time >= 0
				complete = wait + o.getBurstTime() + o.getArrivalTime(),
				tut = complete - o.getArrivalTime();

			o.setWaitingTime(wait);
			o.setCompletedTime(complete);
			o.setTurnaroundTime(tut);

			totalTime += o.getBurstTime();
			if(wait < 0) totalTime += Math.abs(wait); //if cpu has to wait for process the totalTime should increase as well
		}

		//should we call the printTable now?
	}

	public void printTable() {
		// TODO Print the list of processes in form of a table here
		String[] topArr = {
				" Process Id ",
				" Arrival Time ",
				" Burst Time ",
				" Completed Time ",
				" Turnaround Time ",
				" Waiting Time "
		};

		for(String s : topArr){
			System.out.print("|"+s);
		}
		System.out.print("|\n");

		for(Process o : processes){
			StringBuilder strb = new StringBuilder();
			strb.append("|"+prettyPrint(topArr[0].length(), o.getProcessId()));
			strb.append("|"+prettyPrint(topArr[1].length(), o.getArrivalTime()));
			strb.append("|"+prettyPrint(topArr[2].length(), o.getBurstTime()));
			strb.append("|"+prettyPrint(topArr[3].length(), o.getCompletedTime()));
			strb.append("|"+prettyPrint(topArr[4].length(), o.getTurnaroundTime()));
			strb.append("|"+prettyPrint(topArr[5].length(), o.getWaitingTime()));
			System.out.print(strb.toString()+"|\n");
		}
	}

	private String prettyPrint(int box, int value){
		int valLength = (value+"").length()-1;
		int middle = (box-1+valLength/2)/2;
		StringBuilder strb = new StringBuilder();
		for(int i = 0; i < box-valLength; i++){
			if(i == middle) strb.append(value);
			else strb.append(" ");
		}

		return strb.toString();
	}

	public void printGanttChart() {
		// TODO Print the demonstration of the scheduling algorithm using Gantt Chart
		StringBuilder top = new StringBuilder(), bottom = new StringBuilder();
		top.append("|");
		bottom.append(0);

		int dont = 0;
		for(Process o : processes){
			int wait = o.getWaitingTime();
			if(wait < 0){
				wait = Math.abs(wait);
				for(int i = 0; i < wait; i++){
					top.append("#");//CPU wait
					bottom.append(" ");
				}
				top.append("|");
				bottom.append(o.getArrivalTime());
			}

			int middle  = (o.getBurstTime()-1)/2;
			for(int i = 0; i < o.getBurstTime(); i++){
				if(i == middle) top.append(o.getProcessId()+"");
				else top.append(" ");

				if(dont <= 0) bottom.append(" ");
				else dont--;
			}
			top.append("|");
			String comp = o.getCompletedTime()+"";
			if(comp.length()>1) dont = comp.length()-1;
			bottom.append(comp);
		}


		System.out.println(top.toString());
		System.out.println(bottom.toString());
	}
}
