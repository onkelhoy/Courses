package application;
import controller.Admin;
import view.Console;
import view.IView;

public class MainProgram {

	public static void main(String[] args) {
		
		IView a_view = new Console();
		Admin secretary = new Admin(a_view);
		secretary.manage();
	}

}
