package task;

public class Task implements Runnable{
	private String name;
	
	public Task(int i) {
		this.name="task"+i;
	}
	public String getNamem() {
		return this.name;
	}
	public void run() {
		System.out.println(this.name+"开始");
		try {
			Thread.sleep(500);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(this.name+"结束");
	}
	
	public String toString() {
		return this.name;
	}
}
