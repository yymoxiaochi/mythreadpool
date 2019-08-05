package apptest;

import task.Task;
import threadpool.ThreadPool;

public class AppTest {
	public static void main(String[] args) {
		ThreadPool pool = new ThreadPool(5);
		for (int i=0;i<20;i++) {
			Task task = new Task(i);
//			if(i==5) {
//				pool.addPool(2);
//				System.out.println("----------增加线程池大小-----------"+pool);
//			}
//			if(i==8) {
//				pool.delPool(2);
//				System.out.println("----------设置线程池大小-----------");
//			}
			pool.addTask(task);
		}
	}
}
