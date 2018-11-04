package kostas_verveniotis_cbproject1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author krocos
 */
public class FileManipulation {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void writeToFile(Message m, String fileName) {
        String date = sdf.format(m.getDate());
        File f = new File(fileName);
        if (f.exists()) {
            try (FileWriter fw = new FileWriter(fileName, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)) {
                out.printf("%12s%27s%27s%22s%35s%n", m.getId(), m.getSender(), m.getReceiver(), date, m.getMessageData());
                out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FileManipulation.class.getName()).log(Level.SEVERE, null, ex);
            }
            try (FileWriter fw = new FileWriter(fileName, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)) {
                out.printf("%12s%27s%27s%22s%35s%n", "MESSAGE ID", "SENDER", "RECEIVER", "DATE", "MESSAGE DATA");
                out.println("-------------------------------------------------------------------"
                        + "--------------------------------------------------------");
                out.printf("%12s%27s%27s%22s%35s%n", m.getId(), m.getSender(), m.getReceiver(), date, m.getMessageData());
                out.println();
            } catch (IOException ex) {
                Logger.getLogger(FileManipulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void checkDestination(Message m) {
        String writeFile;
        String fileNameSender = m.getSender() + ".txt";
        String fileNameReceiver = m.getReceiver() + ".txt";
        if (fileNameSender.equals(fileNameReceiver)) {
            writeToFile(m, fileNameSender);
        } else {
            writeToFile(m, fileNameSender);
            writeToFile(m, fileNameReceiver);
        }
    }

    public void dateBackup() {
        String fileName = "backup.txt";
        File f = new File(fileName);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String date = sdf.format(timestamp);
        if (f.exists()) {
            try (FileWriter fw = new FileWriter(fileName, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)) {
                out.print("BACKUP DATE: " + date);
                out.println();
                out.printf("%12s%27s%27s%22s%22s%35s%n", "MESSAGE ID", "SENDER", "RECEIVER", "RECEIVED DATE", "UPDATED DATE", "MESSAGE DATA");
                out.println("---------------------------------------------------------------------"
                        + "----------------------------------------------------------------------------");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                f.createNewFile();
                System.out.println("File backup.txt was created successfully!!\n");
            } catch (IOException ex) {
                Logger.getLogger(FileManipulation.class.getName()).log(Level.SEVERE, null, ex);
            }
            try (FileWriter fw = new FileWriter(fileName, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)) {
                out.print("BACKUP DATE: " + date);
                out.println();
                out.printf("%12s%27s%27s%22s%22s%35s%n", "MESSAGE ID", "SENDER", "RECEIVER", "RECEIVED DATE", "UPDATED DATE", "MESSAGE DATA");
                out.println("---------------------------------------------------------------------"
                        + "----------------------------------------------------------------------------");
                out.println();
            } catch (IOException ex) {
                Logger.getLogger(FileManipulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void backupFiles(int msgId, String sender, String receiver, String date, String updateDate, String msgData) {
        String fileName = "backup.txt";
        File f = new File(fileName);
        try (FileWriter fw = new FileWriter(fileName, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.printf("%12s%27s%27s%22s%22s%35s%n", msgId, sender, receiver, date, updateDate, msgData);
            out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
