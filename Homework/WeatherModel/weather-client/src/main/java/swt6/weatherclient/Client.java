package swt6.weatherclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.weaterstation.WeatherStation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;

public class Client {

    private static Logger logger = LoggerFactory.getLogger(Client.class);

    static String promptFor(BufferedReader in, String p) {
        System.out.print(p + "> ");
        System.out.flush();
        try {
            return in.readLine();
        } catch (Exception e) {
            return promptFor(in, p);
        }
    }

    public static void main(String[] args) {
        logger.info("Creating Weather Station");
        WeatherStation station = new WeatherStation();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String availCmds = "commands: quit, getsingle, getaverage";

        System.out.println(availCmds);
        String userCmd = promptFor(in, "");

        try {
            while (!userCmd.equals("quit")) {
                switch (userCmd) {
                    case "getsingle":
                        System.out.printf("%s%n", station.getLatestMeasurement());
                        break;
                    case "getaverage":
                        int noMeasurements = 0;
                        boolean validNumber = false;
                        while (!validNumber) {
                            try {
                                String amount = promptFor(in, "how many measurements?");
                                noMeasurements = Integer.parseInt(amount);
                                validNumber = true;
                            } catch (NumberFormatException ex) {
                                System.out.println("Please enter a valid number");
                            }
                        }
                        System.out.printf("%s%n", station.getAverage(noMeasurements));
                        break;
                    default:
                        System.out.println("ERROR: invalid command");
                        break;
                }

                System.out.println(availCmds);
                userCmd = promptFor(in, "");
            } // while
        } // try
        catch (Exception ex) {
            logger.error(ex.getMessage());
        } // catch
        finally {
            station.shutdown();
        }
    }
}
