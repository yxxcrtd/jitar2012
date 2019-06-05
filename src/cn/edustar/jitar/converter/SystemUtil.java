package cn.edustar.jitar.converter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class SystemUtil {
	public static List<Integer> getProcessPids(String processName) {
		List<Integer> pids = new LinkedList<Integer>();
		Process process;
		int pid = 0;
		Scanner in = null;
		try {
			process = Runtime.getRuntime().exec("tasklist");
			in = new Scanner(process.getInputStream());
			while (in.hasNextLine()) {
				String p = in.nextLine();
				// 打印所有进程
				if (p.contains(processName)) {
					StringTokenizer token = new StringTokenizer(p);
					token.nextToken();
					pid = Integer.parseInt(token.nextToken());
					pids.add(pid);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			if(in!=null)
			{
				in.close();
				in = null;
			}
		}
		return pids;
	}
	
	public static void killProcessByPid(int pid){
		try {
			Runtime.getRuntime().exec("tskill" + " " + pid);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void killProcessByName(String processName) {
		Process process;
		int pid = 0;
		Scanner in = null;
		try {
			process = Runtime.getRuntime().exec("tasklist");
			in = new Scanner(process.getInputStream());
			while (in.hasNextLine()) {
				String p = in.nextLine();
				// 打印所有进程
				if (p.contains(processName)) {
					StringTokenizer token = new StringTokenizer(p);
					token.nextToken();
					pid = Integer.parseInt(token.nextToken());
					Runtime.getRuntime().exec("tskill" + " " + pid);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(in != null)
			{
				in.close();
				in = null;
			}
		}
	}
}
