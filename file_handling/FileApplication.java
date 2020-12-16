/**
 *
 */
package file_handling;

import file_handling.manager.ApplicationManager;
import file_handling.manager.ConsoleManager;
import file_handling.service.FileService;
import java.io.IOException;

import java.util.*;

/**
 * @author EmericStophe
 *
 */
public class FileApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		ConsoleManager.getInstance().printToConsole(FileApplication.class.getName() + " - Start", true);
		ConsoleManager.getInstance().consoleLineBreak();

		// instantiate application
		ApplicationManager applicationManager = new ApplicationManager();

		// launch application
		applicationManager.run();

		ConsoleManager.getInstance().consoleLineBreak();
		ConsoleManager.getInstance().printToConsole(FileApplication.class.getName() + " - End", true);

		ConsoleManager.getInstance().closeScanner();
	}
}
