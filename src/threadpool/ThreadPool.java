package threadpool;

import java.util.LinkedList;

public class ThreadPool { //需不需要先接口，再实现
	//线程池大小
	private int poolsize;
	
	private LinkedList<Runnable>  tasks= new LinkedList<Runnable>(); //存储任务
	private LinkedList<DoTaskThread>  threads= new LinkedList<DoTaskThread>(); //存储线程池线程
	
	public ThreadPool() { //默认大小5
		poolsize=5;
		makeThread();
	}
	
	public ThreadPool(int num) { //初始化设置线程数量
		this.poolsize=num;
		makeThread();
	}
	
	public void makeThread() {  //制造池中线程
		synchronized(tasks) {
			for(int i=0;i<poolsize;i++) {
				DoTaskThread thread = new DoTaskThread("线程"+i);
				thread.start();
				threads.add(thread);
			}
		}
	}
	
	public int getPoolSize() {//获得池子大小
		return this.poolsize;
	}
	
	public void addPool(int num ) { //增加池子容量
		synchronized(tasks) {
			for(int i=0;i<num;i++) {
				DoTaskThread thread = new DoTaskThread("线程"+(i+poolsize));
				thread.start();
				threads.add(thread);
			}
			poolsize+=num;
		}
	}
	public void delPool(int num ) { //减少池子容量
		synchronized(tasks) {
			if(num>=this.poolsize) {
				System.out.println("池子水不多，取不了，本次不执行");
			}else{
				for(int i=0;i<num;i++) {
					DoTaskThread d=threads.removeLast();
					d.stopWorker();
				}
				poolsize-=num;
			}
		}
	}
	public void setPool(int num) {//通过输入值设置线程池大小
		if(num>0) {
			if(num>this.poolsize) {
				this.addPool(num-this.poolsize);
			}else {
				this.delPool(this.poolsize-num);
			}
		}
	}
	
	public void addTask(Runnable r) { //添加任务
		synchronized (tasks) {
			tasks.add(r);
			tasks.notifyAll();
		}
	}
	public void delTask(Runnable r) { //删除任务
		synchronized (tasks) {
			tasks.remove(r);//这里还未覆写了Task类的equals()和hashcode()
			tasks.notifyAll();
		}
	}
	public int getTaskNum() {//获得任务数
		synchronized (tasks) {
			return tasks.size();
		}
	}
	
	class DoTaskThread extends Thread{//线程池中运行的线程，一直保持运行状态
		private boolean isRunning=true;

		public DoTaskThread(String name) {
			super(name);
		}
		
		Runnable task;
		
		public void run() {
			System.out.println("启动:"+this.getName());
			while(isRunning) {
				synchronized (tasks) {
					while(tasks.isEmpty()) {
						try {
							tasks.wait();
						}catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					task=tasks.removeFirst();
					tasks.notifyAll();
				}
				System.out.println(this.getName()+"获取到任务,并执行");
//				System.out.println("当前剩余任务:"+tasks);
//				System.out.println("当前池中线程数"+threads);
				task.run();
			}
		}
		
		@Override
		public String toString() {
			return this.getName();
		}
		
		// 停止工作，让该线程自然执行完run方法，自然结束  
        public void stopWorker() {  
            isRunning = false;  
        }
	}
	
}
